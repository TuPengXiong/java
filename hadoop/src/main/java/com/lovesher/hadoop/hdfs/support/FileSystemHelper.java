package com.lovesher.hadoop.hdfs.support;

import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;

public class FileSystemHelper {

	/**
	 * 关闭文件系统
	 * @Title: close   
	 * @Description: 关闭文件系统   
	 * @param: @param fileSystem      
	 * @return: void      
	 * @throws
	 */
	public static void close(FileSystem fileSystem){
		if (null != fileSystem) {
			try {
				fileSystem.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
