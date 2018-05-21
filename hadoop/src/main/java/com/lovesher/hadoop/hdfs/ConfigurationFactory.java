package com.lovesher.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;

/**
 * hadoop配置
 * @ClassName:  ConfigurationFactory   
 * @Description: hadoop配置   
 * @author: tupengxiong tupengxiong@qq.com
 * @date:   2018年5月18日 下午10:14:09   
 * @version V1.0
 */
public class ConfigurationFactory {

	public static Configuration getConfiguration() {
		return new Configuration();
	}

	public static void main(String[] args) {
		Configuration configuration = ConfigurationFactory.getConfiguration();
		configuration.set("fs.defaultFS", "hdfs://node1:9000");
	}
}
