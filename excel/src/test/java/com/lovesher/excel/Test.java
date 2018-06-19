/** 
 * Project Name:excel 
 * File Name:Test.java 
 * Package Name:com.loevsher.excel 
 * Date:2018年6月19日下午1:48:08 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.lovesher.excel;

import java.util.ArrayList;
import java.util.List;

/** 
 * ClassName:Test <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年6月19日 下午1:48:08 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public class Test {

	@SuppressWarnings("serial")
	public static void main(String[] args) {
		List<VO> lists = new ArrayList<VO>();
		for(int i=0;i<100000;i++){
			VO vo = new VO();
			vo.setPhone("手机号"+i);
			lists.add(vo);
		}
		List<String> fields =  new ArrayList<String>(){{
			add("phone");
		}};
		List<String> titles =  new ArrayList<String>(){{
			add("手机号");
		}};
		try {
			System.out.println(ExcelHelper.writeExcel("D://1.xls", lists, VO.class, fields, titles));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
  