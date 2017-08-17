package com.tupengxiong.logistics.plugins.kdniao;

import java.util.HashMap;
import java.util.Map;

/**
*
* 快递鸟在线下单接口
*
* @技术QQ: 4009633321
* @技术QQ群: 200121393
* @see: http://www.kdniao.com/api-order
* @copyright: 深圳市快金数据技术服务有限公司
* 
* ID和Key请到官网申请：http://www.kdniao.com/ServiceApply.aspx
*/

public class KdApiCreateOrderAPI {
	
	//电商ID
	private String EBusinessID="请到快递鸟官网申请http://www.kdniao.com/ServiceApply.aspx";	
	//电商加密私钥，快递鸟提供，注意保管，不要泄漏
	private String AppKey="请到快递鸟官网申请http://www.kdniao.com/ServiceApply.aspx";	
	//测试请求url
    private String ReqURL = "http://testapi.kdniao.cc:8081/api/oorderservice";
	//正式请求url
	//private string ReqURL = "http://api.kdniao.cc/api/OOrderService";

	/**
     * Json方式 在线下单
	 * @throws Exception 
     */
	public String orderOnlineByJson() throws Exception{
		String requestData= "{'OrderCode': '012657700312'," +
                                  "'ShipperCode':'YTO'," +
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
                                  "'AddService':" +
                                  "[{" +
                                  "'Name':'COD','Value':'1020'}]," +
                                  "'Weight':1.0," +
                                  "'Quantity':1," +
                                  "'Volume':0.0," +
                                  "'Remark':'小心轻放'," +
                                  "'Commodity':" +
                                  "[{" +
                                  "'GoodsName':'鞋子'," +
                                  "'Goodsquantity':1," +
                                  "'GoodsWeight':1.0}]" +
                                  "}";
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("RequestData", KdNiaoUtils.urlEncoder(requestData, "UTF-8"));
		params.put("EBusinessID", EBusinessID);
		params.put("RequestType", "1001");
		String dataSign=KdNiaoUtils.encrypt(requestData, AppKey, "UTF-8");
		params.put("DataSign", KdNiaoUtils.urlEncoder(dataSign, "UTF-8"));
		params.put("DataType", "2");
		
		String result=KdNiaoUtils.sendPost(ReqURL, params);
		
		//根据公司业务处理返回的信息......
		
		return result;
	}
}
