package com.tupengxiong.weixin.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;


import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Component;

import com.tupengxiong.weixin.bean.WxArticle;
import com.tupengxiong.weixin.bean.WxText;

@Component
public class ResponseMsg {

	/**
	 * 被动回复文本消息
	 * 
	 * @param text
	 *            发送的文本
	 * @return String xml格式的数据
	 */
	public String text(WxText text) {
		String returnStr = "success";
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("xml");
		root.addElement("ToUserName").setText(text.getToUserName());
		root.addElement("FromUserName").setText(text.getFromUserName());
		root.addElement("CreateTime").setText(text.getCreateTime().toString());
		root.addElement("MsgType").setText("text");
		root.addElement("Content").setText(text.getContent());
		StringWriter strWtr = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter xmlWriter = new XMLWriter(strWtr, format);
		try {
			xmlWriter.write(document);
		} catch (IOException e) {
			return returnStr;
		}
		returnStr = strWtr.toString();
		return returnStr;
	}

	/**
	 * 被动回复图文消息
	 * 
	 * @param articles
	 *            发送的图文(不可超过10个)
	 * @return String xml格式的数据
	 */
	public String articles(List<WxArticle> articles, WxText text) {
		String returnStr = "success";
		// 图文消息限制10条以内
		if (articles.size() > 10)
			return returnStr;

		// 填充回复内容
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("xml");
		root.addElement("ToUserName").setText(text.getToUserName());
		root.addElement("FromUserName").setText(text.getFromUserName());
		root.addElement("CreateTime").setText(text.getCreateTime().toString());
		root.addElement("MsgType").setText("news");
		root.addElement("ArticleCount").setText(String.valueOf(articles.size()));

		// 填充图文信息
		Element fXML = root.addElement("Articles");

		for (int i = 0; i <= articles.size(); i++) {
			Element mXML = fXML.addElement("item");
			mXML.addElement("Title").setText(articles.get(i).getTitle());
			mXML.addElement("Description").setText((articles.get(i).getDescription()));
			mXML.addElement("PicUrl").setText(articles.get(i).getPicUrl());
			mXML.addElement("Url").setText(articles.get(i).getUrl());
		}
		StringWriter strWtr = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter xmlWriter = new XMLWriter(strWtr, format);
		try {
			xmlWriter.write(document);
		} catch (IOException e) {
			return returnStr;
		}
		returnStr = strWtr.toString();
		return returnStr;
	}

}