package com.lovesher.freemaker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

public class Test {

	public static Configuration cfg = new Configuration(new Version("2.3.0"));

	public static void main(String[] args) throws IOException, TemplateException {
		@SuppressWarnings("serial")
		Map<String, Object> data = new HashMap<String, Object>() {
			{
				put("name", "tupx");
				put("addtime", new Date());
			}
		};
		System.out.println(getString(data, "hello ${name} ${addtime?string('yyyy-MM-dd HH:mm:ss')}"));
		System.out.println(Test.class.getResource("/freemakers/test.ftl").getPath());
		System.out.println(getString(data,Test.class.getResource("/freemakers").getPath(),"test.ftl"));
		
		generateFile(data,Test.class.getResource("/freemakers").getPath(),"test.ftl");
	}

	/**
	 * 获取模板填充数据
	 * getString:(获取模板填充数据). <br/> 
	 * @author tupengxiong
	 * @param data
	 * @param stringformat
	 * @return
	 * @throws IOException
	 * @throws TemplateException 
	 * @since JDK 1.7
	 */
	public static String getString(Map<String, Object> data, String stringformat)
			throws IOException, TemplateException {
		Template template = new Template(null, new StringReader(stringformat), cfg);
		StringWriter w = new StringWriter();
		template.getConfiguration();
		template.process(data, w);
		return w.getBuffer().toString();
	}
	
	/**
	 * 生成模板填充文件
	 * generateFile:(生成模板填充文件). <br/> 
	 * @author tupengxiong
	 * @param data
	 * @param stringformat
	 * @return
	 * @throws IOException
	 * @throws TemplateException 
	 * @since JDK 1.7
	 */
	public static void generateFile(Map<String, Object> data, String directoty,String fileName)
			throws IOException, TemplateException {
		cfg.setDirectoryForTemplateLoading(new File(directoty));
		Template template = cfg.getTemplate(fileName);
		FileWriter fileWriter = new FileWriter(directoty + "_" + UUID.randomUUID() + "_" + fileName);
		template.getConfiguration();
		template.process(data, fileWriter);
		fileWriter.flush();
	}
	
	/**
	 * 获取模板填充数据
	 * getString:(获取模板填充数据). <br/> 
	 * @author tupengxiong
	 * @param data
	 * @param stringformat
	 * @return
	 * @throws IOException
	 * @throws TemplateException 
	 * @since JDK 1.7
	 */
	public static String getString(Map<String, Object> data, String directoty,String fileName)
			throws IOException, TemplateException {
		cfg.setDirectoryForTemplateLoading(new File(directoty));
		Template template = cfg.getTemplate(fileName);
		StringWriter w = new StringWriter();
		template.getConfiguration();
		template.process(data, w);
		return w.getBuffer().toString();
	}
}