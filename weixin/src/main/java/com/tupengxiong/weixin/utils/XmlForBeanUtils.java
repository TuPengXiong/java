package com.tupengxiong.weixin.utils;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.tupengxiong.weixin.bean.WxBase;
import com.tupengxiong.weixin.bean.WxText;

@Component
public class XmlForBeanUtils {
	private static final Logger logger = Logger.getLogger(XmlForBeanUtils.class);

	@Resource
	BeanUtils beanUtils;

	public WxText parseToWxText(String text) {
		WxText wxText = new WxText();
		try {
			Document document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();
			List<String> elementNames = beanUtils.beanFieldNames(WxText.class);
			for (int i = 0; i < elementNames.size(); i++) {
				Element element = root.element(beanUtils.indexChractarToUpperCase(elementNames.get(i)));
				beanUtils.fillfieldValueByNotRootElement(element, wxText);
			}
		} catch (DocumentException e) {
			logger.error(new StringBuilder("XmlForBeanUtils parseToWxText ").append("text = ").append(text));
		}
		return wxText;
	}

	public WxBase parseToWxBase(String text) {
		WxBase wxBase = new WxBase();
		try {
			Document document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();
			List<String> elementNames = beanUtils.beanFieldNames(WxText.class);
			for (int i = 0; i < elementNames.size(); i++) {
				Element element = root.element(beanUtils.indexChractarToUpperCase(elementNames.get(i)));
				beanUtils.fillfieldValueByNotRootElement(element, wxBase);
			}
		} catch (DocumentException e) {
			logger.error(new StringBuilder("XmlForBeanUtils parseToWxBase ").append("text = ").append(text));
		}
		return wxBase;
	}

}
