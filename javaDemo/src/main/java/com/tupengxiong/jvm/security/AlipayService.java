package com.tupengxiong.jvm.security;

import grails.transaction.Transactional
import grails.util.Holders
import org.apache.commons.codec.digest.DigestUtils
import org.bson.types.ObjectId
import org.springframework.util.Assert
import org.springframework.web.util.UriComponentsBuilder
import xyz.kingsilk.qh.core.code.AlipayMsgTypeEnum
import xyz.kingsilk.qh.core.code.PayTypeEnum
import xyz.kingsilk.qh.core.code.PaymentStatusEnum
import xyz.kingsilk.qh.core.code.QhPayStatusEnum
import xyz.kingsilk.qh.domain.domain.Account
import xyz.kingsilk.qh.domain.domain.AlipayLog
import xyz.kingsilk.qh.domain.domain.Payment
import xyz.kingsilk.qh.domain.domain.QhPay
import xyz.kingsilk.qh.domain.domain.alipay_log.RefundAsyncNotify
import xyz.kingsilk.qh.domain.domain.alipay_log.WapRtAsyncNotify
import xyz.kingsilk.qh.domain.domain.alipay_log.WapRtSyncNotify
import xyz.kingsilk.qh.service.common.Base64

import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.text.DateFormat
import java.text.SimpleDateFormat

@Transactional
class AlipayService {
    def staffService
    def commentLogService
    def sendSmsService
    def unionOrderService;
    def paymentService
    def orderService
    /**
     * 默认商品名称和详细信息
     * @param out_trade_no
     * @param total_amount
     * @param type
     * @return
     */
    Map payment(QhPay qhPay, String type) {
        // 需要支付的金额,支付宝单位是元
        double prices = qhPay.paymentAmount / 100.0;
        return this.payment(qhPay.id, "小皇叔-优质睡眠专家", String.valueOf(prices), null, "优质睡眠", null, type);
    }
    /**
     * @param out_trade_no 订单ID
     * @param subject 商品名称  不能为空
     * @param total_fee 订单金额 。double 类型：9.01元 不可为空
     * @param show_url 商品展示地址，可为空
     * @param body 商品详细信息 可空
     * @param extern_token 钱包token.当商户请求是来自支付宝钱包,在支付宝钱包登录后,有生成登录信息 token 时,使用该参数传 入 token 将可以实现信任登录收银台,不需要再次登录。
     * @param status 自定义传输的字符串，的参数
     * @param type APP支付,wap支付
     */
    Map payment(String outTradeNo, String subject, String totalFee, String showUrl, String body, String externToken, String type) {
        //把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>()
        String sign_Type = null;
        if (type.equals("APP")) {
            sParaTemp.put("service", Holders.config.qh.alipay.app.service);
            sParaTemp.put("seller_id", Holders.config.qh.alipay.app.seller_id)
            sign_Type = Holders.config.qh.alipay.app.sign_type;
        } else {
            sign_Type = Holders.config.qh.alipay.sign_type;
            sParaTemp.put("seller_id", Holders.config.qh.alipay.seller_id)
            sParaTemp.put("service", Holders.config.qh.alipay.service)
            sParaTemp.put("return_url", Holders.config.qh.alipay.return_url)
        }
        sParaTemp.put("partner", Holders.config.qh.alipay.partner)
        sParaTemp.put("_input_charset", Holders.config.qh.alipay.input_charset)
        sParaTemp.put("payment_type", "1")
        sParaTemp.put("notify_url", Holders.config.qh.alipay.notify_url)
        sParaTemp.put("out_trade_no", outTradeNo)
        sParaTemp.put("subject", subject)
        sParaTemp.put("total_fee", totalFee)
        sParaTemp.put("show_url", showUrl)
        sParaTemp.put("body", body)
        sParaTemp.put("it_b_pay", Holders.config.qh.alipay.it_b_pay)
        sParaTemp.put("extern_Token", externToken)

        // 去除空格的参数
        Map<String, String> sPara = this.paraFilter(sParaTemp)
        // 生成签名参数
        String mysign = this.buildRequestMysign(sPara, sign_Type)
        sPara.put("sign", mysign);
        sPara.put("sign_type", sign_Type);
        def resultMap = [:];
        if (type.equals("APP")) {
            String prestr = this.createLinkStringApp(sPara) //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            resultMap.params = prestr;
        } else {
            UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(Holders.config.qh.alipay.ALIPAY_GATEWAY_NEW);
            for (String key : sPara.keySet()) {
                String value = sPara.get(key);
                uri.queryParam(key, value);
            }
            URI uris = uri.build().toUri();
            resultMap.uri = uris.toString();
        }
        return resultMap;
    }

    String aliPayNotifyLog(Map params) {
        String url = "unionOrder/order";
        // 声明变量
        AlipayLog alipayLog = null
        WapRtSyncNotify wapRtSyncNotify = null
        QhPay qhPay = QhPay.get(params.out_trade_no);
        if (qhPay == null) {
            return null
        }
        if (qhPay.integralOrders.size() > 0) {
            url = "vip/record";
        }
        // 生成日志
        if (qhPay.alipayLog) {
            alipayLog = qhPay.alipayLog
        } else {
            alipayLog = new AlipayLog()
            alipayLog.type = AlipayMsgTypeEnum.WAP_RT_SYNC_NOTIFY
        }
        // 返回的链接不同进行返回
        if (Holders.config.qh.alipay.service != params.service) {
            return url
        }
//        // 去除空格的参数,和签名的参数
        Map<String, String> sPara = this.paraFilter(params)
        // 生成签名参数
        String mysign = this.buildRequestMysign(sPara, params.sign_type)
        // 签名不正确
        if (mysign != (params.sign)) {
            return url
        }
        // 对字符串的notify_time 进行转成date的notify_time
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        Date date = df.parse(params.notify_time)
        params.put("notify_time", date)
        // 将string的total_fee 转换成的
        Double total_fee = Double.parseDouble(params.total_fee)
        params.put("total_fee", total_fee)
        // 参数进行注入
        wapRtSyncNotify = new WapRtSyncNotify(params)

        wapRtSyncNotify.no = Integer.toHexString(wapRtSyncNotify.hashCode())

        alipayLog.wapRtSyncNotify = wapRtSyncNotify

        alipayLog.save(flush: true)

        // 如果订单是不为NULL，说明本次是order付款，否则是serviceOrder付款
        // 订单如果成功，将订单状态进行改变
        if (params.trade_status == "TRADE_SUCCESS" || params.trade_status == "TRADE_FINISHED") {
            // 判断订单状态是否正常
            // 防止多次通知，通知被多次调用
            if (!qhPay.status.equals(QhPayStatusEnum.SUCCESS)) {
                unionOrderService.paySuccess(qhPay, PayTypeEnum.ALIPAY, alipayLog, null);
            }
        }
        /*  if (qhPay) {
              logService.orderLog(order.id, order.user, OperatorTypeEnum.PAYMENT, null, "用户完成了订单支付,等待商家确认")
              url = "unionOrder/order"
          } else if (s == ("service")) {
              logService.serviceOrderLog(order.id, order.user, null, ServiceOrderChangeTypeEnum.PAYMENT, "用户完成了订单支付,等待用户发货")
              url = "serviceOrder/detail?orderId=" + order.id
          } else if (s == ("repair")) {
              logService.serviceOrderLog(order.id, order.user, null, ServiceOrderChangeTypeEnum.USER_PAYAGAIN, "用户完成了补差价,等待商家回寄")
              url = "serviceOrder/detail?orderId=" + order.id
          } else if (s == ("rent")) {
              logService.serviceOrderLog(order.id, order.user, null, ServiceOrderChangeTypeEnum.USER_PAYAGAIN, "用户完成了补差价,等待商家回寄")
              url = "rentOrder/detail?rentOrder=" + order.id
          }*/
        return url
    }
    /**
     * 支付宝异步通知消息保存
     */
    void aliPayAsyncNotifyLog(Map params, String s) {
        QhPay qhPay = QhPay.get(params.out_trade_no);
        Assert.notNull(qhPay, "该订单不存在或已被删除")
        // 去除空格的参数,和签名的参数
        Map<String, String> sPara = this.paraFilter(params)
        // 生成签名参数
        boolean mysign = this.buildPublicMysign(sPara, params.sign_type, params.sign)
        // 签名不正确
        if (!mysign) {
            return
        }
        // 声明变量
        AlipayLog alipayLog = null
        WapRtAsyncNotify wapRtAsyncNotify = null
        // 生成日志
        if (qhPay.alipayLog) {
            alipayLog = qhPay.alipayLog
        } else {
            alipayLog = new AlipayLog()
            alipayLog.type = AlipayMsgTypeEnum.WAP_RT_ASYNC_NOTIFY
        }
        // 对字符串的notify_time 进行转成date的notify_time
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        Date date = df.parse(params.notify_time)
        params.put("notify_time", date)
        if (params.gmt_create) {
            date = df.parse(params.gmt_create)
            params.put("gmt_create", date)
        }

        if (params.gmt_payment) {
            date = df.parse(params.gmt_payment)
            params.put("gmt_payment", date)
        }

        if (params.gmt_close) {
            date = df.parse(params.gmt_close)
            params.put("gmt_close", date)
        }

        if (params.gmt_refund) {
            date = df.parse(params.gmt_refund)
            params.put("gmt_refund", date)
        }

        Double totalFee = 0
        // 将string的totalFee 转换成的double的
        if (params.price) {
            totalFee = Double.parseDouble(params.price)
            params.put("price", totalFee)
        }

        if (params.totalFee) {
            totalFee = Double.parseDouble(params.total_fee)
            params.put("total_fee", totalFee)
        }

        if (params.discount) {
            totalFee = Double.parseDouble(params.discount)
            params.put("discount", totalFee)
        }

        if (params.quantity) {
            totalFee = Double.parseDouble(params.quantity)
            params.put("quantity", totalFee)
        }

        wapRtAsyncNotify = new WapRtAsyncNotify(params)
        wapRtAsyncNotify.no = Integer.toHexString(wapRtAsyncNotify.hashCode())
        alipayLog.wapRtAsyncNotify = wapRtAsyncNotify

        alipayLog.save(flush: true)
        // 防止上一次同步的时候已经处理过一次了
        if (params.trade_status == "TRADE_SUCCESS" || params.trade_status == "TRADE_FINISHED") {
            // 判断订单状态是否正常
            // 防止多次通知，通知被多次调用
            if (!qhPay.status.equals(QhPayStatusEnum.SUCCESS)) {
                // 订单如果成功，将订单状态进行改变
                unionOrderService.paySuccess(qhPay, PayTypeEnum.ALIPAY, alipayLog, null);
            }
        }

    }

    /**
     * 支付宝退款URI
     * @param tradeNo 原付款支付宝交易号
     * @param totalFee 退款金额(不能是分)
     * @param reason 退款原因
     * @param seq 退款流水号
     * @return
     */
    def refundUrl(String tradeNo, int totalFee, String reason, String seq) {

        Map<String, String> sParaTemp = new HashMap<String, String>()

        Date date = new Date()
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        String dateStr = df.format(date)

        sParaTemp.put("service", 'refund_fastpay_by_platform_pwd')
        sParaTemp.put("partner", Holders.config.qh.alipay.partner)
        sParaTemp.put("_input_charset", "utf-8")
        sParaTemp.put("notify_url", Holders.config.qh.alipay.refundNotifyUrl)
//        sParaTemp.put("seller_email", null)
        sParaTemp.put("seller_user_id", Holders.config.qh.alipay.seller_id)
        sParaTemp.put("refund_date", dateStr)
        //批次号(唯一),异步回调时，查找退款申请
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd")
        String todayStr = dateFormat.format(date)
        log.info(todayStr + seq)
        sParaTemp.put("batch_no", todayStr + seq)
        sParaTemp.put("batch_num", 1)
        sParaTemp.put("detail_data", tradeNo + "^" + totalFee / 100 + "^" + reason)

        Map<String, String> sPara = this.paraFilter(sParaTemp)
        String mysign = this.buildRequestMysign(sPara, Holders.config.qh.alipay.sign_type)
        sPara.put("sign", mysign)
        sPara.put("sign_type", "MD5")

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(Holders.config.qh.alipay.ALIPAY_GATEWAY_NEW)
        // 先使用一个模板创建UriComponentsBuilder
        sPara.each { key, value ->
            uriBuilder.queryParam(key, value)
        }
        URI uri = uriBuilder.build()
                .encode("UTF-8")
                .toUri()

        return uri.toString()
    }
    /**
     * 用户进行余额支付
     * 这里的不判断余额是否足够，只进行赋值
     * @param account
     * @param qhPay
     * @param boo true的时候还必须余额大于支付金额，true余额支付需要立马保存account的变动，false余额不足选择其他支付的时候不需要.
     * @return
     */
    public QhPay balancePayment(Account account, QhPay qhPay, boolean boo) {
        if (boo) {
            if ((account.notWithdrawAmount + account.canWithdrawAmount) < qhPay.paymentAmount) {
                return null;
            }
        }
        // 不可提现的金额使用了多少
        int notWithdraw = 0;
        // 可提现的余额使用了多少
        int canWithdraw = 0;
        if (account.notWithdrawAmount < qhPay.paymentAmount) {
            // 不可提现的余额不够，使用可以提现余额
            notWithdraw = account.notWithdrawAmount;
            if (account.canWithdrawAmount < (qhPay.paymentAmount - notWithdraw)) {
                // 可提现余额也不够，直接使用全部的
                canWithdraw = account.canWithdrawAmount;
            } else {
                // 可以提现的余额足够
                canWithdraw = qhPay.paymentAmount - notWithdraw;
            }
        } else {
            notWithdraw = qhPay.paymentAmount;
        }
        /*     if (boo) {
                 // 余额支付  进行保存
                 // 保存已经使用的余额
                 account.notWithdrawAmount = account.notWithdrawAmount - notWithdraw;
                 account.canWithdrawAmount = account.canWithdrawAmount - canWithdraw;
                 account.save();
             }*/
        // 分配支付的时候的时候的余额
        qhPay.canWithdrawAmount = canWithdraw;
        qhPay.notWithdrawAmount = notWithdraw;
        return qhPay;
    }
    /**
     * 即时到账有密退款,服务器异步通知
     * cookies、session等在此页面会失效
     */
    def refundAsyncNotifyLog(Map<String, String> map) {
        log.info("支付宝开始退款。")
        Payment payment = Payment.findBySeq(map.batch_no.substring(8))

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        Date date = df.parse(map.notify_time)
        //异步消息日志
        RefundAsyncNotify refundAsyncNotify = new RefundAsyncNotify()
        refundAsyncNotify.no = new ObjectId().toString()
        refundAsyncNotify.notify_time = date
        refundAsyncNotify.notify_type = map.notify_type
        refundAsyncNotify.notify_id = map.notify_id
        refundAsyncNotify.sign_type = map.result_details
        refundAsyncNotify.sign = map.sign
        refundAsyncNotify.batch_no = map.batch_no
        refundAsyncNotify.success_num = map.success_num
        refundAsyncNotify.result_details = map.result_details

        AlipayLog alipayLog = new AlipayLog()
        alipayLog.type = AlipayMsgTypeEnum.RT_ASYNC_REFUND
        alipayLog.refundAsyncNotify = refundAsyncNotify
        alipayLog.save(flush: true)

        log.info("支付宝，退款日志保存成功。")
        payment.refundTime = date
        payment.alipayLog = alipayLog

        this.refundHandle(payment)
        log.info("支付宝退款成功。")
        return true
    }

    /**
     * 退款，订单状态处理
     * @param payment
     * @return
     */
    public refundHandle(Payment payment) {

        payment.status = PaymentStatusEnum.PAYED
        payment.save(flush: true)
        log.info("支付宝，退款状态更新成功。")
        log.info("支付宝退款，订单状态处理开始。")
        paymentService.refundHandle(payment, "支付宝退款")
        log.info("支付宝退款，订单状态处理结束。")
    }

    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @param boo 是否需要双引号 true需要,false不需要
     * @return 签名结果字符串
     */
    public String buildRequestMysign(Map<String, String> sPara, signType) {

        String mysign = ""
        if (signType == ("MD5")) {
            String prestr = this.createLinkString(sPara) //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            mysign = DigestUtils.md5Hex(prestr + Holders.config.qh.alipay.private_key)
        } else if (signType == ("RSA")) {
            String prestr = this.createLinkStringApp(sPara) //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            log.debug(prestr)
            // 目前RSA 只适用于APP支付,私钥由config中取得
            String privateKey = Holders.config.qh.alipay.app.private_key;
            try {
                PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                        Base64.decode(privateKey));
                KeyFactory keyf = KeyFactory.getInstance("RSA");
                PrivateKey priKey = keyf.generatePrivate(priPKCS8);

                java.security.Signature signature = java.security.Signature
                        .getInstance("SHA1WithRSA");

                signature.initSign(priKey);
                signature.update(prestr.getBytes("UTF-8"));

                byte[] signed = signature.sign();

                mysign = Base64.encode(signed);
                try {
                    // 仅需对sign 做URL编码
                    mysign = URLEncoder.encode(mysign, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mysign
    }

    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @param boo 是否需要双引号 true需要,false不需要
     * @return 签名结果字符串
     */
    public boolean buildPublicMysign(Map<String, String> sPara, signType, String sign) {
        String mysign = ""
        if (signType == ("MD5")) {
            String prestr = this.createLinkString(sPara) //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            mysign = DigestUtils.md5Hex(prestr + Holders.config.qh.alipay.private_key)
            if (mysign.equals(sign)) {
                return true;
            }
        } else if (signType == ("RSA")) {
//            sPara.put("_input_charset", Holders.config.qh.alipay.input_charset);
            String prestr = this.createLinkString(sPara) //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            // 目前RSA 只适用于APP支付,私钥由config中取得
            String publicKey = Holders.config.qh.alipay.ali_public_key;
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decode(publicKey)));

                java.security.Signature signature = java.security.Signature
                        .getInstance("SHA1WithRSA");

                signature.initVerify(pubKey);
                signature.update(prestr.getBytes());
                return signature.verify(Base64.decode(sign));
            } catch (Exception e) {
//                e.printStackTrace()
                log.error(e.getMessage())
            }
        }
        return false;
    }

    /*   public String rsaDecrypt(String content) {
           try {
               String privateKey = Holders.config.qh.alipay.app.private_key;
               KeyFactory keyf = KeyFactory.getInstance("RSA");
               PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                       Base64.decode(privateKey));
               PrivateKey priKey = keyf.generatePrivate(priPKCS8);
               Cipher cipher = Cipher.getInstance("RSA");
               cipher.init(Cipher.DECRYPT_MODE, priKey);
               byte[] encryptedData = Base64Java.decodeBase64(content.getBytes())
               int inputLen = encryptedData.length;
               ByteArrayOutputStream out = new ByteArrayOutputStream();
               int offSet = 0;
               byte[] cache;
               int i = 0;
               // 对数据分段解密
               while (inputLen - offSet > 0) {
                   if (inputLen - offSet > 128) {
                       cache = cipher.doFinal(encryptedData, offSet, 128);
                   } else {
                       cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                   }
                   out.write(cache, 0, cache.length);
                   i++;
                   offSet = i * 128;
               }
               byte[] decryptedData = out.toByteArray();
               out.close();

               return new String(decryptedData);
           } catch (Exception e) {
               e.printStackTrace()
           }
       }*/
    /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>()

        if (sArray == null || sArray.size() <= 0) {
            return result
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key)
            if (value == null || value == ("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")) {
                continue
            }
            result.put(key, value)
        }
        return result
    }
    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet())
        Collections.sort(keys)

        String prestr = ""

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i)
            String value = params.get(key)
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value
            } else {
                prestr = prestr + key + "=" + value + "&"
            }
        }

        return prestr
    }
    /**
     * APP需要的格式 app需要对参数进行双引号
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public String createLinkStringApp(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet())
        Collections.sort(keys)

        String prestr = ""

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i)
            String value = params.get(key)
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=\"" + value + "\"";

            } else {
                prestr = prestr + key + "=\"" + value + "\"&"
            }
        }
        return prestr;
    }
}

