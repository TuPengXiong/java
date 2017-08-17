package com.tupengxiong.logistics.plugins.kdniao;

import java.util.HashMap;
import java.util.Map;

/**
 * 快递鸟智选物流
 *
 * @技术QQ群: 456320272
 * @copyright: 深圳市快金数据技术服务有限公司
 * <p>
 * <p>
 * ID和Key请到官网申请：http://www.kdniao.com/ServiceApply.aspx
 */

public class KdApiExpRecommend {

    //DEMO
    public static void main(String[] args) {
        KdApiExpRecommend api = new KdApiExpRecommend();
        try {
            String result = api.getExpRecommendByJson();
            System.out.print(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //电商ID
    private String EBusinessID = "请到快递鸟官网申请http://www.kdniao.com/ServiceApply.aspx";
    //电商加密私钥，快递鸟提供，注意保管，不要泄漏
    private String AppKey = "请到快递鸟官网申请http://www.kdniao.com/ServiceApply.aspx";
    //请求url
    private String ReqURL = "http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";

    /**
     * Json方式 智选物流
     *
     * @throws Exception
     */
    public String getExpRecommendByJson() throws Exception {
        String requestData = "{'MemberID':'123456','WarehouseID':'1','Detail':[{'OrderCode':'12345','IsCOD':0,'Sender':{'ProvinceName':'广东省','CityName':'广州','ExpAreaName':'龙岗区','Subdistrict':'布吉街道','Address':'518000'},'Receiver':{'ProvinceName':'广东','CityName':'梅州','ExpAreaName':'丰顺','Subdistrict':'布吉街道','Address':'518000'},'Goods':[{'ProductName':'包','Volume':'','Weight':'1'}]},{'OrderCode':'12346','IsCOD':0,'Sender':{'ProvinceName':'广东省','CityName':'广州','ExpAreaName':'龙岗区','Subdistrict':'布吉街道','Address':'518000'},'Receiver':{'ProvinceName':'湖南','CityName':'长沙','ExpAreaName':'龙岗区','Subdistrict':'布吉街道','Address':'518000'},'Goods':[{'ProductName':'包','Volume':'','Weight':'1'}]}]}";

        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", KdNiaoUtils.urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", "2006");
        String dataSign = KdNiaoUtils.encrypt(requestData, AppKey, "UTF-8");
        params.put("DataSign", KdNiaoUtils.urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");

        String result = KdNiaoUtils.sendPost(ReqURL, params);

        //根据公司业务处理返回的信息......

        return result;
    }


    /**
     * Json方式 导入运费模板
     *
     * @throws Exception
     */
    public String importCostTemplateByJson() throws Exception {
        String requestData = "{'MemberID':'123456','Detail':[{'ShipperCode':'YD','SendProvince':'广东','SendCity':'广州','SendExpArea':'天河','ReceiveProvince':'湖南','ReceiveCity':'长沙','ReceiveExpArea':'龙岗','FirstWeight':'1','FirstFee':'8','AdditionalWeight':'1','AdditionalFee':'10','WeightFormula':''},{'ShipperCode':'YD','SendProvince':'广东','SendCity':'广州','SendExpArea':'天河','ReceiveProvince':'湖南','ReceiveCity':'长沙','ReceiveExpArea':'雨花','FirstWeight':'1','FirstFee':'8','AdditionalWeight':'1','AdditionalFee':'10','WeightFormula':'{{w-0}-0.4}*{{{1000-w}-0.4}+1}*4.700+ {{w-1000}-0.6}*[(w-1000)/1000]*4.700）','ShippingType':'1','IntervalList':[{'StartWeight': 1.0,'EndWeight': 2.0, 'Fee': 3.0}]}]}";

        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", KdNiaoUtils.urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", "2004");
        String dataSign = KdNiaoUtils.encrypt(requestData, AppKey, "UTF-8");
        params.put("DataSign", KdNiaoUtils.urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");

        String result = KdNiaoUtils.sendPost(ReqURL, params);

        //根据公司业务处理返回的信息......

        return result;
    }

}
