package com.tupengxiong.logistics.plugins.kdniao;

import java.util.HashMap;
import java.util.Map; 

/**
 *
 * 快递鸟单号识别接口
 *
 * @技术QQ群: 456320272
 * @copyright: 深圳市快金数据技术服务有限公司
 *
 * 
 * ID和Key请到官网申请：http://www.kdniao.com/ServiceApply.aspx
 */

public class KdApiOrderDistinguish {
	
	//DEMO
	public static void main(String[] args) {
		KdApiOrderDistinguish api = new KdApiOrderDistinguish();
		try {
			String result = api.getOrderTracesByJson("3967950525457");
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
     * Json方式 单号识别
	 * @throws Exception 
     */
	public String getOrderTracesByJson(String expNo) throws Exception{
		String requestData= "{'LogisticCode':'" + expNo + "'}";
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("RequestData", KdNiaoUtils.urlEncoder(requestData, "UTF-8"));
		params.put("EBusinessID", EBusinessID);
		params.put("RequestType", "2002");
		String dataSign=KdNiaoUtils.encrypt(requestData, AppKey, "UTF-8");
		params.put("DataSign", KdNiaoUtils.urlEncoder(dataSign, "UTF-8"));
		params.put("DataType", "2");
		
		String result=KdNiaoUtils.sendPost(ReqURL, params);
		
		//根据公司业务处理返回的信息......
		
		return result;
	}
}
