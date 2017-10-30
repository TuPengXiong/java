package com.tupengxiong.elasticsearch.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ElasticSearchDruidDataSourceFactory;

public class SqlEs {

	public static void main(String[] args) throws Exception {
		Properties properties = new Properties();
		properties.put("url", "jdbc:elasticsearch://127.0.0.1:9300/");
		DruidDataSource dds = (DruidDataSource) ElasticSearchDruidDataSourceFactory
		        .createDataSource(properties);
		dds.setInitialSize(1);
		Connection connection = dds.getConnection();
		String sql2 = "select * FROM test";
		PreparedStatement ps = connection.prepareStatement(sql2);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
		    //sql对应输出
		    System.out.println(resultSet.getString("字段名") );

		}
		ps.close();
		connection.close();
		dds.close();
	}
}
