package com.tupengxiong.logistics.plugins.kdniao;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 快递鸟电子面单接口
 *
 * @技术QQ群: 340378554
 * @see: http://kdniao.com/api-eorder
 * @copyright: 深圳市快金数据技术服务有限公司
 * 
 * ID和Key请到官网申请：http://kdniao.com/reg
 */
 
public class KdApiEOrderAPI {
	
	//电商ID
	private String EBusinessID="请到快递鸟官网申请http://kdniao.com/reg";	
	//电商加密私钥，快递鸟提供，注意保管，不要泄漏
	private String AppKey="请到快递鸟官网申请http://kdniao.com/reg";	
	//请求url, 正式环境地址：http://api.kdniao.cc/api/Eorderservice    测试环境地址：http://testapi.kdniao.cc:8081/api/EOrderService
	private String ReqURL="http://testapi.kdniao.cc:8081/api/Eorderservice";	
	

	/**
     * Json方式 电子面单
	 * @throws Exception 
     */
	public String orderOnlineByJson() throws Exception{
		String requestData= "{'OrderCode': '012657700387'," +
                "'ShipperCode':'SF'," +
                "'PayType':1," +
                "'ExpType':1," +
                "'Cost':1.0," +
                "'OtherCost':1.0," +
                "'Sender':" +
                "{" +
                "'Company':'LV','Name':'Taylor','Mobile':'15018442396','ProvinceName':'上海','CityName':'上海','ExpAreaName':'青浦区','Address':'明珠路73号'}," +
                "'Receiver':" +
                "{" +
                "'Company':'GCCUI','Name':'Yann','Mobile':'15018442396','ProvinceName':'北京','CityName':'北京','ExpAreaName':'朝阳区','Address':'三里屯街道雅秀大厦'}," +
                "'Commodity':" +
                "[{" +
                "'GoodsName':'鞋子','Goodsquantity':1,'GoodsWeight':1.0}]," +
                "'Weight':1.0," +
                "'Quantity':1," +
                "'Volume':0.0," +
                "'Remark':'小心轻放'," +
                "'IsReturnPrintTemplate':1}";
		Map<String, String> params = new HashMap<String, String>();
		params.put("RequestData", KdNiaoUtils.urlEncoder(requestData, "UTF-8"));
		params.put("EBusinessID", EBusinessID);
		params.put("RequestType", "1007");
		String dataSign=KdNiaoUtils.encrypt(requestData, AppKey, "UTF-8");
		params.put("DataSign", KdNiaoUtils.urlEncoder(dataSign, "UTF-8"));
		params.put("DataType", "2");
		
		String result=KdNiaoUtils.sendPost(ReqURL, params);
		
		//根据公司业务处理返回的信息......
		
		return result;
	}
}
