package com.tupengxiong.weixin.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.tupengxiong.weixin.bean.Sn;
import com.tupengxiong.weixin.bean.WxText;
import com.tupengxiong.weixin.bean.mapper.SnMapper;
import com.tupengxiong.weixin.bean.mapper.WxTextMapper;
import com.tupengxiong.weixin.service.WxService;
import com.tupengxiong.weixin.utils.TuLingUtils;
import com.tupengxiong.weixin.utils.XmlForBeanUtils;

@Service
@RequestMapping("/test")
public class TestController {

	private static final Logger logger = Logger.getLogger(TestController.class);
	@Resource
	TuLingUtils tuLingTools;
	@Resource
	XmlForBeanUtils xmlForBeanUtils;
	@Resource
	private SnMapper snMapper;
	@Resource
	private WxTextMapper wxTextMapper;
	@Resource
	private RestTemplate restTemplate;

	@RequestMapping("/test")
	public void test(HttpServletResponse response, String msg) {
		WxText wxText = new WxText();
		wxText.setFromUserName("fromUserName");
		wxText.setToUserName("toUserName");
		wxText.setCreateTime(new Date());
		String content = "11";
		if (msg != null) {
			content = msg;
		}
		wxText.setContent(content);
		String resp = tuLingTools.getWxResp(wxText);
		wxText = xmlForBeanUtils.parseToWxText(resp);
		wxTextMapper.insert(wxText);
		try {
			response.getWriter().write(resp);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping("/sn")
	public void sn(HttpServletResponse response, String msg) {
		Sn sn = new Sn();
		sn.setCreateTime(new Date());
		sn.setType("test");
		snMapper.insert(sn);
		try {
			response.getWriter().write("success");
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/taobao", method = RequestMethod.GET)
	@ResponseBody
	public String index(String itemId, String sellerId) {
		if (itemId == null) {
			itemId = "555682960129";
		}
		if (sellerId == null) {
			sellerId = "2957592081";
		}

		HttpMethod httpMethod = HttpMethod.GET;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Referer", "https://item.taobao.com/item.htm?id=" + itemId);
		headers.add("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
		HttpEntity<?> httpEntity = new HttpEntity<String>(null, headers);
		String url = "https://detailskip.taobao.com/service/getData/1/p1/item/detail/sib.htm?itemId=" + itemId
				+ "&sellerId=" + sellerId
				+ "&modules=dynStock,qrcode,viewer,price,duty,xmpPromotion,delivery,activity,fqg,zjys,couponActivity,soldQuantity,originalPrice,tradeContract";
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, String.class);
		logger.info(responseEntity.getBody());
		JSONObject jsonObject = new JSONObject(responseEntity.getBody());
		JSONObject dataJson = jsonObject.getJSONObject("data");
		JSONObject promotionJson = dataJson.getJSONObject("promotion");
		JSONObject promoDataJson = promotionJson.getJSONObject("promoData");
		Iterator iterator = promoDataJson.keySet().iterator();
		String price = "0.00";
		JSONArray priceJson;
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			logger.info(key);
			String value = promoDataJson.get(key).toString();
			logger.info(value);
			priceJson = new JSONArray(value);
			if (priceJson.length() >= 1) {
				price = priceJson.getJSONObject(priceJson.length() - 1).getString("price");
			}

		}
		return itemId + ":" + price;
	}

	public static void main(String[] args) {

	}

}
