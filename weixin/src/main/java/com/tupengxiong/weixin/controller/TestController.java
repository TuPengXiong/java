package com.tupengxiong.weixin.controller;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tupengxiong.weixin.bean.Sn;
import com.tupengxiong.weixin.bean.WxText;
import com.tupengxiong.weixin.bean.mapper.SnMapper;
import com.tupengxiong.weixin.bean.mapper.WxTextMapper;
import com.tupengxiong.weixin.utils.TuLingUtils;
import com.tupengxiong.weixin.utils.XmlForBeanUtils;

@Service
@RequestMapping("/test")
public class TestController {

	@Resource
	TuLingUtils tuLingTools;
	@Resource
	XmlForBeanUtils xmlForBeanUtils;
	@Resource
	private SnMapper snMapper;
	@Resource
	private WxTextMapper wxTextMapper;

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
}
