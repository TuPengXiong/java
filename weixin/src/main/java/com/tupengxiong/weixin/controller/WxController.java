package com.tupengxiong.weixin.controller;

import java.io.DataInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tupengxiong.weixin.bean.WxText;
import com.tupengxiong.weixin.bean.mapper.WxTextMapper;
import com.tupengxiong.weixin.service.WxService;
import com.tupengxiong.weixin.task.MessageTransferTask;
import com.tupengxiong.weixin.utils.TuLingUtils;
import com.tupengxiong.weixin.utils.XmlForBeanUtils;

@Controller("WxController")
@RequestMapping("/api")
public class WxController {

	private static final Logger logger = Logger.getLogger(WxController.class);
	@Resource
	private WxService wxService;

	@Resource
	private XmlForBeanUtils xmlForBeanUtils;

	@Resource
	private TuLingUtils tuLingTools;

	@Resource
	private WxTextMapper wxTextMapper;

	@Resource
	private MessageTransferTask messageTransferTask;

	private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

	@RequestMapping("/wxNotify")
	public void wxNotify(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		Map<String, String> params = new HashMap<String, String>();
		@SuppressWarnings("rawtypes")
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement().toString();
			String value = request.getParameter(name);
			params.put(name, value);
		}
		logger.info(params);
		params.put("token", "bNjTgyE0IFFkrsFdHp8YNcOwkvoWTAZ0");
		String resp = "success";
		String reqcontent = null;
		DataInputStream in = null;
		try {
			// 验证签名
			if (null == params.get("echostr") && wxService.getSignature(params)) {
				// 得到请求消息的数据输入流
				in = new DataInputStream(request.getInputStream());
				byte[] bytes = new byte[request.getContentLength()];
				in.readFully(bytes); // 根据长度，将消息实体的内容读入字节数组bytes中
				in.close(); // 关闭数据流
				reqcontent = new String(bytes); // 从字节数组中得到表示实体的字符串
			} else if (null != params.get("echostr") && !wxService.getSignature(params)) {
				resp = "error";
			} else if(null != params.get("echostr") && wxService.getSignature(params)){
				resp = params.get("echostr");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		if (null != reqcontent) {
			String type = xmlForBeanUtils.parseToMsgType(reqcontent);
			if (type.equals("text")) {
				WxText wxText = xmlForBeanUtils.parseToWxText(reqcontent);
				//防重
				Integer count = wxTextMapper.wxTextTotalMsgIdCount(wxText.getMsgId());
				if(count == 0){
					wxTextMapper.insert(wxText);
					fixedThreadPool.execute(messageTransferTask);
					resp = tuLingTools.getWxResp(wxText);
				}
			}
		}

		try {
			response.getWriter().write(resp);
			response.getWriter().flush();
			response.getWriter().close();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
