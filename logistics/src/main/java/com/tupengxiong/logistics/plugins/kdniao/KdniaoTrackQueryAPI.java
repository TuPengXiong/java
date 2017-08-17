package com.tupengxiong.logistics.plugins.kdniao;

import java.util.HashMap;
import java.util.Map; 

/**
 *
 * 快递鸟物流轨迹即时查询接口
 *
 * @技术QQ群: 456320272
 * @see: http://www.kdniao.com/YundanChaxunAPI.aspx
 * @copyright: 深圳市快金数据技术服务有限公司
 *
 * DEMO中的电商ID与私钥仅限测试使用，正式环境请单独注册账号
 * 单日超过500单查询量，建议接入我方物流轨迹订阅推送接口
 * 
 * ID和Key请到官网申请：http://www.kdniao.com/ServiceApply.aspx
 */

public class KdniaoTrackQueryAPI {
	
	//DEMO
	public static void main(String[] args) {
		KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
		try {
			String result = api.getOrderTracesByJson("ANE", "210001633605");
			System.out.print(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//电商ID
	private String EBusinessID="请到快递鸟官网申请http://www.kdniao.com/ServiceApply.aspx";
	//电商加密私钥，快递鸟提供，注意保管，不要泄漏
	private String AppKey="请到快递鸟官网申请http://www.kdniao.com/ServiceApply.aspx";
	//请求url
	private String ReqURL="http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";	
 
	/**
     * Json方式 查询订单物流轨迹
	 * @throws Exception 
     */
	public String getOrderTracesByJson(String expCode, String expNo) throws Exception{
		String requestData= "{'OrderCode':'','ShipperCode':'" + expCode + "','LogisticCode':'" + expNo + "'}";
		Map<String, String> params = new HashMap<String, String>();
		params.put("RequestData", KdNiaoUtils.urlEncoder(requestData, "UTF-8"));
		params.put("EBusinessID", EBusinessID);
		params.put("RequestType", "1002");
		String dataSign=KdNiaoUtils.encrypt(requestData, AppKey, "UTF-8");
		params.put("DataSign", KdNiaoUtils.urlEncoder(dataSign, "UTF-8"));
		params.put("DataType", "2");
		String result=KdNiaoUtils.sendPost(ReqURL, params);
		//根据公司业务处理返回的信息......
		return result;
	}
}
