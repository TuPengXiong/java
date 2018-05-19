package com.lovesher.hadoop.hdfs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;
import org.mortbay.util.ajax.JSON;

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
	 * 获取某用户的文件系统 @Title: getFileSystem @Description: 获取文件系统 @param: @param
	 * uri @param: @param conf @param: @param user
	 * 文件的拥有者 @param: @return @param: @throws IOException @param: @throws
	 * InterruptedException @return: FileSystem @throws
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
		Logger logger = Logger.getLogger(FileSystemFactory.class);
		try {
			fileSystem = FileSystemFactory.getFileSystem(new URI("hdfs://192.168.152.130:9000"),
					ConfigurationFactory.getConfiguration(), "tpx");

			Path userPath = new Path("/");
			FileStatus status[] = fileSystem.listStatus(userPath);
			for (int i = 0; i < status.length; i++) {
				FileStatus fileStatus = status[i];
				String path = fileStatus.getPath().toString();
				String owner = fileStatus.getOwner();
				String group = fileStatus.getGroup();
				long accessTime = fileStatus.getAccessTime();
				logger.info("path:" + path + "|owner:" + owner + "|group:" + group + "|accessTime:" +accessTime);
			}

			Path path = fileSystem.getHomeDirectory();
			//上传文件到hdfs
			Path hdfsPath = new Path(path, new Path("hdfs"));
			if(fileSystem.exists(hdfsPath)){
				logger.info("hdfsPath： " + hdfsPath.toString() + " exist ");
			}else{
				URI hdfsUri = FileSystemFactory.class.getResource("/hdfs-default.xml").toURI();
				fileSystem.copyFromLocalFile(new Path(hdfsUri), hdfsPath);
			}
			
			URI coreUri = FileSystemFactory.class.getResource("/core-default.xml").toURI();
			fileSystem.copyFromLocalFile(new Path(coreUri), new Path(path, new Path("core")));

			URI mapredUri = FileSystemFactory.class.getResource("/mapred-default.xml").toURI();
			fileSystem.copyFromLocalFile(new Path(mapredUri), new Path(path, new Path("mapred")));

			
			URI yarnUri = FileSystemFactory.class.getResource("/yarn-default.xml").toURI();
			fileSystem.copyFromLocalFile(new Path(yarnUri), new Path(path, new Path("yarn")));
			
			//获取文件到本地
			URI localUri = FileSystemFactory.class.getResource("/").toURI();
			logger.info(localUri);
			Path dst = new Path(new URI(localUri.toString() + "download/hdfs-default.xml"));
			fileSystem.copyToLocalFile(hdfsPath , dst);
			
			//删除文件
			//fileSystem.deleteOnExit(hdfsPath);

		} catch (IOException | InterruptedException | URISyntaxException e) {
			logger.error(e);
		} finally {
			FileSystemHelper.close(fileSystem);
		}
	}
}
