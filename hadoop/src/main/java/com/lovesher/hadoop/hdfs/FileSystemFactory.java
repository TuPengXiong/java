package com.lovesher.hadoop.hdfs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import com.lovesher.hadoop.hdfs.support.FileSystemHelper;

/**
 * 获取文件系统
 * 
 * @ClassName: FileSystemFactory
 * @Description: 获取文件系统
 * @author: tupengxiong tupengxiong@qq.com
 * @date: 2018年5月18日 下午10:34:21
 * @version V1.0
 */
public class FileSystemFactory {

	public static FileSystem getFileSystem(Configuration conf) throws IOException {
		FileSystem fileSystem = FileSystem.get(conf);
		return fileSystem;
	}

	/**
	 * 获取某用户的文件系统
	 * @Title: getFileSystem   
	 * @Description: 获取文件系统   
	 * @param: @param uri
	 * @param: @param conf
	 * @param: @param user 文件的拥有者
	 * @param: @return
	 * @param: @throws IOException
	 * @param: @throws InterruptedException      
	 * @return: FileSystem      
	 * @throws
	 */
	public static FileSystem getFileSystem(URI uri, Configuration conf, String user)
			throws IOException, InterruptedException {
		FileSystem fileSystem = FileSystem.get(uri, conf, user);
		return fileSystem;
	}

	public static FileSystem getFileSystem(URI uri, Configuration conf) throws IOException {
		FileSystem fileSystem = FileSystem.get(uri, conf);
		return fileSystem;
	}
	
	public static void main(String[] args) {
		FileSystem fileSystem = null;
		try {
			fileSystem = FileSystemFactory.getFileSystem(new URI("hdfs://master:9000"),
					ConfigurationFactory.getConfiguration(), "hadoop");
		} catch (IOException | InterruptedException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			FileSystemHelper.close(fileSystem);
		}
	}
}
