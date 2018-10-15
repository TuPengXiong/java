/** 
 * Project Name:excel 
 * File Name:Test.java 
 * Package Name:com.loevsher.excel 
 * Date:2018年6月19日下午1:48:08 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.lovesher.excel;

/**
 * ClassName:Test <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年6月19日 下午1:48:08 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class WriterTest {

	public static void main(String[] args) throws Exception {

		String file = "D:/导出测试数据.xlsx";

		ExcelWriter writer = new ExcelWriter() {
			public void generate() throws Exception {

				// 电子表格开始
				this.beginSheet();

				for (int rownum = 0; rownum < 100000; rownum++) {
					// 插入新行
					this.insertRow(rownum);

					// 建立新单元格,索引值从0开始,表示第一列
					this.createCell(0, "第 " + rownum + " 行");
					this.createCell(1, 34343.123456789);
					this.createCell(2, "23.67%");
					this.createCell(3, "12:12:23");
					this.createCell(4, "2014-10-11 12:12:23");
					this.createCell(5, "true");
					this.createCell(6, "false");

					// 结束行
					this.endRow();
				}

				// 电子表格结束
				this.endSheet();
			}
		};
		writer.process(file, "1");
	}
}
