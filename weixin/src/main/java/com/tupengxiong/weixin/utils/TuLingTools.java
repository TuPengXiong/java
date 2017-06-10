package com.tupengxiong.weixin.utils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.tupengxiong.weixin.bean.WxArticle;
import com.tupengxiong.weixin.bean.WxText;

/**
 * 图灵机器人
 */
@Component
public class TuLingTools {

	private static final String api_ket = "4aa238fb3ff04fc4bb964060c8485e74";
	private static final String api_secret = "3ed93627ba6ee059";
	private static final String url = "http://www.tuling123.com/openapi/api";
	private static final Logger logger = Logger.getLogger(XmlForBeanUtils.class);

	@Resource
	private ResponseMsg responseMsg;

	/**
	 * 获取图灵的返回信息
	 * 
	 * @param userId
	 * @param info
	 * @param defultString
	 * @return
	 */
	private Map<String, Object> getResponseFromTuLing(String userId, String info) {
		Map<String, Object> map = null;
		URI uri = null;
		try {
			uri = UriComponentsBuilder.fromHttpUrl(url).build().encode("UTF-8").toUri();
		} catch (UnsupportedEncodingException e) {
			logger.error("TuLingTools  getResponseFromTuLing UnsupportedEncodingException");
		}
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("key", api_ket);
		jsonObject.put("info", info);
		jsonObject.put("userId", userId);
		HttpEntity<String> reqEntity = new HttpEntity<String>(jsonObject.toString(), headers);
		ResponseEntity<String> respEntity = restTemplate.exchange(uri, HttpMethod.POST, reqEntity, String.class);
		if (null != respEntity) {
			if (!StringUtils.isBlank(respEntity.getBody().toString())) {
				JSONObject object = new JSONObject(respEntity.getBody().toString());
				map = new HashMap<String, Object>();
				// 文本类
				map.put("code", object.getInt("code"));
				map.put("body", new String(respEntity.getBody()));
			}
		}
		return map;
	}

	public String getWxResp(WxText wxText) {
		String resp = "success";
		String fromUserName = wxText.getFromUserName();
		String toUserName = wxText.getToUserName();
		Map<String, Object> map = null;
		try {
			map = getResponseFromTuLing(wxText.getFromUserName(), wxText.getContent());
		} catch (Exception exception) {
			return resp;
		}
		wxText.setToUserName(fromUserName);
		wxText.setFromUserName(toUserName);

		if (map.get("code") == null) {
			return resp;
		}
		Integer code = (Integer) map.get("code");
		JSONObject object = new JSONObject((String)map.get("body"));

		List<WxArticle> articles = new ArrayList<WxArticle>();
		System.out.println(object);
		switch (code) {
		case 100000:
			// 文本类
			wxText.setContent(object.getString("text"));
			return responseMsg.text(wxText);
		case 200000:
			// 链接类
			String title = object.getString("text");
			String url = object.getString("url");
			WxArticle wxArticle = new WxArticle(title, title, url, url);
			articles.add(wxArticle);
			return responseMsg.articles(articles, wxText);
		case 302000:

		case 308000:
			// 新闻类
			resp = object.getString("text");
			JSONArray news = object.getJSONArray("list");
			WxArticle wxArticleNews = null;
			for (int i = 0; i < news.length(); i++) {
				JSONObject json = new JSONObject(news.get(i).toString());
				String article = json.getString("article");
				String source = json.getString("source");
				String icon = json.getString("icon");
				String detailurl = json.getString("detailurl");
				wxArticleNews = new WxArticle(article, source, icon, detailurl);
				articles.add(wxArticleNews);
			}
			return responseMsg.articles(articles, wxText);
		case 313000:
			// 儿歌类

		case 314000:
			// 诗词类
			String text = object.getString("text");
			String function = object.getString("function");
			JSONObject json = new JSONObject(function.toString());
			wxText.setContent(text + "\r\n" + json.toString());
			return responseMsg.text(wxText);
		default:
			// 文本类
			wxText.setContent(object.getString("text"));
			return responseMsg.text(wxText);
		}
	}

}
