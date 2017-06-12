package com.tupengxiong.weixin.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tupengxiong.weixin.bean.WxText;
import com.tupengxiong.weixin.utils.TuLingTools;

@Service
@RequestMapping("/test")
public class TestController {

	@Resource
	TuLingTools tuLingTools;

	@RequestMapping("/test")
	public void test(HttpServletResponse response) throws IOException {
		WxText wxText = new WxText();
		wxText.setFromUserName("fromUserName");
		wxText.setToUserName("toUserName");
		wxText.setCreateTime(10000l);
		String resp = tuLingTools.getWxResp(wxText);
		response.getWriter().write(resp);
		response.getWriter().flush();
		response.getWriter().close();
	}

}
