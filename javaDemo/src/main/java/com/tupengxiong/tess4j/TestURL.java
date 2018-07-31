package com.tupengxiong.tess4j;

import java.awt.Rectangle;
import java.io.File;
/**
 * 通过URL来获取网络上的资源下载
 */
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

public class TestURL {
	public static void main(String[] args) throws TesseractException {
		FileOutputStream fos = null;
		String path = "C:\\Users\\fangjunlong\\Desktop\\b.png";
		String uri = "https://a.cnaidai.com/webjr/login.cci?w=160&h=40&f=34&o=60&q=60&t=0.5090717565574998";
		/*try {
			// 创建URL对象并指定所对应的地址。
			URL url = new URL(uri);
			// 建立输入流获取url的数据。
			InputStream is = url.openStream();
			// 建立文件输出流并新建b.png来存储资源。
			fos = new FileOutputStream(path);
			int i;
			// 循环判断如果is.read()的值不等于-1，则说明字节流中还存在数值，用fos进行输出。
			while ((i = is.read()) != -1) {
				fos.write(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 最后流程结束后进行自动fos的关闭。
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}*/
		Tesseract instance = new Tesseract();
		File tessDataFolder = LoadLibs.extractTessResources("tessdata");
		//Set the tessdata path
		instance.setDatapath(tessDataFolder.getAbsolutePath());
		instance.setLanguage("eng");//chi_sim ：简体中文， eng	根据需求选择语言库
		String decrease = instance.doOCR(new File(path))
		        .replace(" ",".").replace(",","");
		System.out.println(decrease);
	}
}