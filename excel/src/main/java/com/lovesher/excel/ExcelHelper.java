package com.lovesher.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.log4j.Logger;

import jxl.Workbook;
import jxl.WorkbookSettings;

/**
 * 导出报表
 * ClassName: ExcelHelper 
 * Function: 导出报表
 * Reason: 导出报表
 * date: 2018年6月19日 下午2:47:38 <br/> 
 * 
 * @author tupengxiong 
 * @version  
 * @since JDK 1.7
 */
public class ExcelHelper {

	private static Logger logger = Logger.getLogger(ExcelHelper.class);
	
	public static final int MAX_COLUMN = 65535;
	public static boolean isBusy(){
		return false;
	}
	
	//return code
	//0:success 1:busy -1:error
	@SuppressWarnings("rawtypes")
	public synchronized static int writeExcel(String file,List list,Class clazz,List<String> fields,List<String> titles) throws Exception {
		try{
			OutputStream os=getOutputStream(file);
			jxl.WorkbookSettings workbookSettings = new WorkbookSettings();
			workbookSettings.setGCDisabled(true);
			workbookSettings.setInitialFileSize(1024000);
			workbookSettings.setInitialFileSize(512000);
			int sheetNum = list.size()%MAX_COLUMN ==0?list.size()/MAX_COLUMN:list.size()/MAX_COLUMN+1;
			jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(os,workbookSettings);
			int count = 0;
			for(int sheet=0;sheet<sheetNum;sheet++){
				jxl.write.WritableSheet ws = wwb.createSheet("Sheet"+(sheet+1), sheet);
				jxl.write.Label label=null;
				if(titles!=null&&titles.size()>0){
					for(int j=0;j<titles.size();j++){ 
						label=new jxl.write.Label(j,0,titles.get(j));
						ws.addCell(label);
					}
				}
				for(int i=count;i<list.size();i++){
					Object o=list.get(i);
					for(int j=0;j<fields.size();j++){
						String field = fields.get(j);
						Object object = ReflectUtils.invokeGetMethod(clazz, o, field);
						label=new jxl.write.Label(j,i%MAX_COLUMN+1, object.toString()); 
						ws.addCell(label);
					}
					count++;
					if(count%MAX_COLUMN==0){
						break;
					}
				}
			}
			wwb.write();
			wwb.close();
			return 0;
		}catch(Exception e){
			logger.error("导出报表出错！"+fields.toString(),e);
			return -1;
		}
		
	}
	
	
	public static OutputStream getOutputStream(String file) throws Exception{
		File f = new File(file);
		f.createNewFile();
		OutputStream os=new FileOutputStream(f);
		return os;
	}
}
