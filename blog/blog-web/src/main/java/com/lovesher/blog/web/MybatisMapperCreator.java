package com.lovesher.blog.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自动生成MyBatis的实体类、实体映射XML文件、Mapper
 * ClassName: MybatisMapperCreator <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2018年3月5日 下午4:51:14 <br/> 
 * 
 * @author tupengxiong 
 * @version  
 * @since JDK 1.7
 */
public class MybatisMapperCreator {

	/**
	 ********************************** 使用前必读*******************
	 ** 
	 ** 使用前请将moduleName更改为自己模块的名称即可（一般情况下与数据库名一致），其他无须改动。
	 ** 
	 *********************************************************** 
	 */

	private final String type_char = "char";

	private final String type_date = "date";

	private final String type_timestamp = "timestamp";

	private final String type_int = "int";

	private final String type_bigint = "bigint";

	private final String type_text = "text";

	private final String type_bit = "bit";
	
	private final String type_double = "double";
	
	private final String type_float = "float";

	private final String type_decimal = "decimal";

	private final String type_blob = "blob";

	private final String moduleName = "resposity"; // 对应模块名称（根据自己模块做相应调整!!!务必修改^_^）

	private final String bean_path = "D:/work/git-repo/java/blog/blog-service/src/main/java/com/lovesher/blog/resposity/bean";
	
	private final String bean_dto_path = "D:/work/git-repo/java/blog/blog-common/src/main/java/com/lovesher/blog/bean";

	private final String mapper_path = "D:/work/git-repo/java/blog/blog-service/src/main/java/com/lovesher/blog/resposity/mapper";

	private final String xml_path = "D:/work/git-repo/java/blog/blog-web/src/main/resources/ibatis";
	
	private final String mapper_define_xml_path = "D:/work/git-repo/java/blog/blog-web/src/main/resources";
	
	private final String dao_path = "D:/work/git-repo/java/blog/blog-service/src/main/java/com/lovesher/blog/resposity/dao";
	
	private final String service_path = "D:/work/git-repo/java/blog/blog-common/src/main/java/com/lovesher/blog/service";
	
	private final String service_impl_path = "D:/work/git-repo/java/blog/blog-service/src/main/java/com/lovesher/blog/service/impl";
	
	private final String dao_impl_path = "D:/work/git-repo/java/blog/blog-service/src/main/java/com/lovesher/blog/resposity/dao/impl";

	private final String bean_package = "com.lovesher.blog." + moduleName + ".bean";
	
	private final String bean_dto_package = "com.lovesher.blog.bean";

	private final String mapper_package = "com.lovesher.blog." + moduleName+ ".mapper";
	
	private final String dao_package = "com.lovesher.blog." + moduleName+ ".dao";
	
	private final String dao_impl_package = "com.lovesher.blog." + moduleName+ ".dao.impl";
	
	private final String service_package = "com.lovesher.blog.service";
	
	private final String service_impl_package = "com.lovesher.blog.service.impl";

	private final String driverName = "com.mysql.jdbc.Driver";

	private final String user = "root";

	private final String password = "mysql";

	private final String url = "jdbc:mysql://127.0.0.1:3306/db_blog?characterEncoding=utf8";

	private String tableName = null;

	private String beanName = null;
	
	private String beanDTOName = null;

	private String mapperName = null;
	
	private String _mapperName = null;
	
	private String daoName = null;
	
	private String daoImplName = null;
	
	private String serviceName = null;
	
	private String serviceImplName = null;
	
	private Connection conn = null;

	private void init() throws ClassNotFoundException, SQLException {
		Class.forName(driverName);
		conn = DriverManager.getConnection(url, user, password);
	}

	/**
	 * 获取所有的表
	 * 
	 * @return
	 * @throws SQLException
	 */
	private List<String> getTables() throws SQLException {
		List<String> tables = new ArrayList<String>();
		PreparedStatement pstate = conn.prepareStatement("show tables");
		ResultSet results = pstate.executeQuery();
		while (results.next()) {
			String tableName = results.getString(1);
			// if ( tableName.toLowerCase().startsWith("yy_") ) {
			tables.add(tableName);
			// }
		}
		return tables;
	}

	private void processTable(String table) {
		StringBuffer sb = new StringBuffer(table.length());
		String tableNew = table.toLowerCase();
		String[] tables = tableNew.split("_");
		String temp = null;
		for (int i = 1; i < tables.length; i++) {
			temp = tables[i].trim();
			sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
		}
		beanName = sb.toString();
		beanDTOName = beanName+"DTO";
		mapperName = beanName + "Mapper";
		_mapperName = shotFirst(mapperName);
		daoName = beanName + "Dao";
		daoImplName = beanName + "DaoImpl";
		serviceName = beanName + "Service";
		serviceImplName = serviceName + "Impl";
	}

	/**
	 * 首字母转小写
	 * 
	 * @return
	 */
	private String shotFirst(String str){
		char[] chars=new char[1];  
        chars[0]=str.charAt(0);  
        String temp=new String(chars);  
        if(chars[0]>='A'  &&  chars[0]<='Z'){  
        	str = str.replaceFirst(temp,temp.toLowerCase());  
        }
        return str;
	}
	
	private String processType(String type) {
		if (type.indexOf(type_char) > -1) {
			return "java.lang.String";
		} else if (type.indexOf(type_bigint) > -1) {
			return "java.lang.Long";
		} else if (type.indexOf(type_int) > -1) {
			return "java.lang.Integer";
		} else if (type.indexOf(type_date) > -1) {
			return "java.util.Date";
		} else if (type.indexOf(type_text) > -1) {
			return "java.lang.String";
		} else if (type.indexOf(type_timestamp) > -1) {
			return "java.util.Date";
		} else if (type.indexOf(type_bit) > -1) {
			return "java.lang.Boolean";
		} else if (type.indexOf(type_decimal) > -1) {
			return "java.math.BigDecimal";
		} else if (type.indexOf(type_blob) > -1) {
			return "byte[]";
		} else if (type.indexOf(type_double) > -1) {
			return "java.lang.Double";
		} else if (type.indexOf(type_float) > -1) {
			return "java.lang.Float";
		}
		
		return null;
	}

	private String processField(String field) {
		StringBuffer sb = new StringBuffer(field.length());
		// field = field.toLowerCase();
		String[] fields = field.split("_");
		String temp = null;
		sb.append(fields[0]);
		for (int i = 1; i < fields.length; i++) {
			temp = fields[i].trim();
			sb.append(temp.substring(0, 1).toUpperCase()).append(
					temp.substring(1));
		}
		return sb.toString();
	}

	/**
	 * 将实体类名首字母改为小写
	 * 
	 * @param beanName
	 * @return
	 */
	@SuppressWarnings("unused")
	private String processResultMapId(String beanName) {
		return beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
	}

	/**
	 * 构建类上面的注释
	 * 
	 * @param bw
	 * @param text
	 * @return
	 * @throws IOException
	 */
	private BufferedWriter buildClassComment(BufferedWriter bw, String text)
			throws IOException {
		bw.newLine();
		bw.newLine();
		bw.write("/**");
		bw.newLine();
		bw.write(" * ");
		bw.newLine();
		bw.write(" * " + text);
		bw.newLine();
		bw.write(" * ");
		bw.newLine();
		bw.write(" **/");
		return bw;
	}

	/**
	 * 构建方法上面的注释
	 * 
	 * @param bw
	 * @param text
	 * @return
	 * @throws IOException
	 */
	private BufferedWriter buildMethodComment(BufferedWriter bw, String text)
			throws IOException {
		bw.newLine();
		bw.write("\t/**");
		bw.newLine();
		bw.write("\t * ");
		bw.newLine();
		bw.write("\t * " + text);
		bw.newLine();
		bw.write("\t * ");
		bw.newLine();
		bw.write("\t **/");
		return bw;
	}

	/**
	 * 生成实体类
	 * 
	 * @param columns
	 * @param types
	 * @param comments
	 * @throws IOException
	 */
	private void buildEntityBean(List<String> columns, List<String> types,List<String> comments, String tableComment) throws IOException {
		File folder = new File(bean_path);
		if (!folder.exists()) {
			folder.mkdir();
		}

		File beanFile = new File(bean_path, beanName + ".java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
		bw.write("package " + bean_package + ";");
		bw.newLine();
		bw.write("import java.io.Serializable;");
		bw.newLine();
		bw = buildClassComment(bw, tableComment);
		bw.newLine();
		bw.write("@SuppressWarnings(\"serial\")");
		bw.newLine();
		bw.write("public class " + beanName + " implements Serializable {");
		bw.newLine();
		bw.newLine();
		int size = columns.size();
		for (int i = 0; i < size; i++) {
			bw.write("\t/**" + comments.get(i) + "**/");
			bw.newLine();
			bw.write("\tprivate " + processType(types.get(i)) + " "
					+ processField(columns.get(i)) + ";");
			bw.newLine();
			bw.newLine();
		}
		bw.newLine();
		String tempField = null;
		String _tempField = null;
		String tempType = null;
		for (int i = 0; i < size; i++) {
			tempType = processType(types.get(i));
			_tempField = processField(columns.get(i));
			tempField = _tempField.substring(0, 1).toUpperCase()+ _tempField.substring(1);
			bw.newLine();
			bw.write("\tpublic void set" + tempField + "(" + tempType + " "
					+ _tempField + "){");
			bw.newLine();
			bw.write("\t\tthis." + _tempField + " = " + _tempField + ";");
			bw.newLine();
			bw.write("\t}");
			bw.newLine();
			bw.newLine();
			bw.write("\tpublic " + tempType + " get" + tempField + "(){");
			bw.newLine();
			bw.write("\t\treturn this." + _tempField + ";");
			bw.newLine();
			bw.write("\t}");
			bw.newLine();
		}
		bw.newLine();
		bw.write("}");
		bw.newLine();
		bw.flush();
		bw.close();
	}
	
	/**
	 * 生成实体类DTO
	 * 
	 * @param columns
	 * @param types
	 * @param comments
	 * @throws IOException
	 */
	private void buildEntityDTOBean(List<String> columns, List<String> types,List<String> comments, String tableComment) throws IOException {
		File folder = new File(bean_dto_path);
		if (!folder.exists()) {
			folder.mkdir();
		}

		File beanFile = new File(bean_dto_path, beanDTOName + ".java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
		bw.write("package " + bean_dto_package + ";");
		bw.newLine();
		bw.write("import java.io.Serializable;");
		bw.newLine();
		bw = buildClassComment(bw, tableComment);
		bw.newLine();
		bw.write("@SuppressWarnings(\"serial\")");
		bw.newLine();
		bw.write("public class " + beanDTOName + " implements Serializable {");
		bw.newLine();
		bw.newLine();
		int size = columns.size();
		for (int i = 0; i < size; i++) {
			bw.write("\t/**" + comments.get(i) + "**/");
			bw.newLine();
			bw.write("\tprivate " + processType(types.get(i)) + " "
					+ processField(columns.get(i)) + ";");
			bw.newLine();
			bw.newLine();
		}
		bw.newLine();
		String tempField = null;
		String _tempField = null;
		String tempType = null;
		for (int i = 0; i < size; i++) {
			tempType = processType(types.get(i));
			_tempField = processField(columns.get(i));
			tempField = _tempField.substring(0, 1).toUpperCase()+ _tempField.substring(1);
			bw.newLine();
			bw.write("\tpublic void set" + tempField + "(" + tempType + " "
					+ _tempField + "){");
			bw.newLine();
			bw.write("\t\tthis." + _tempField + " = " + _tempField + ";");
			bw.newLine();
			bw.write("\t}");
			bw.newLine();
			bw.newLine();
			bw.write("\tpublic " + tempType + " get" + tempField + "(){");
			bw.newLine();
			bw.write("\t\treturn this." + _tempField + ";");
			bw.newLine();
			bw.write("\t}");
			bw.newLine();
		}
		bw.newLine();
		bw.write("}");
		bw.newLine();
		bw.flush();
		bw.close();
	}
	
	/**
	 * 构建DAO文件
	 * 
	 * @throws IOException
	 */
	private void buildDao() throws IOException {
		File folder = new File(dao_path);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		File daoFile = new File(dao_path, daoName + ".java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(daoFile), "utf-8"));
		bw.write("package " + dao_package + ";");
		bw.newLine();
		bw.newLine();
		bw.write("import " + bean_package + "." + beanName + ";");
		bw.newLine();
		bw = buildClassComment(bw, daoName);
		bw.newLine();
		bw.newLine();
		bw.write("public interface " + daoName + "{");
		bw.newLine();
		bw.newLine();
		bw = buildMethodComment(bw, "查询（根据主键ID查询）");
		bw.newLine();
		bw.write("\t" + beanName + "  selectById (Long id );");
		bw.newLine();
		bw = buildMethodComment(bw, "删除（根据主键ID删除）");
		bw.newLine();
		bw.write("\t" + "int deleteById (Long id );");
		bw.newLine();
		bw = buildMethodComment(bw, "添加");
		bw.newLine();
		bw.write("\t" + "long insert(" + beanName + " record );");
		bw.newLine();
		bw = buildMethodComment(bw, "修改");
		bw.newLine();
		bw.write("\t" + "int update (" + beanName + " record );");
		bw.newLine();
		bw.newLine();
		bw.write("}");
		bw.flush();
		bw.close();
	}
	
	/**
	 * 构建DAOImpl文件
	 * 
	 * @throws IOException
	 */
	private void buildDaoImpl() throws IOException {
		File folder = new File(dao_impl_path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		File daoFile = new File(dao_impl_path, daoImplName + ".java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(daoFile), "utf-8"));
		bw.write("package " + dao_impl_package + ";");
		bw.newLine();
		bw.newLine();
		bw.write("import " + bean_package + "." + beanName + ";");
		bw.newLine();
		bw.write("import " + mapper_package + "." + mapperName + ";");
		bw.newLine();
		bw.write("import " + dao_package + "." + daoName + ";");
		bw.newLine();
		bw.write("import javax.annotation.Resource;");
		bw.newLine();
		bw.write("import org.springframework.stereotype.Service;");
		bw.newLine();
		bw = buildClassComment(bw, daoImplName);
		bw.newLine();
		bw.write("@Service(\""+this.shotFirst(daoName)+"\")");
		bw.newLine();
		bw.write("public class " + daoImplName + " implements "+daoName+"{");
		bw.newLine();
		bw.newLine();
		bw.write("\t@Resource");
		bw.newLine();
		bw.write("\t private "+ mapperName +" "+ _mapperName +";");
		bw.newLine();
		bw = buildMethodComment(bw, "查询（根据主键ID查询）");
		bw.newLine();
		bw.write("\t@Override");
		bw.newLine();
		bw.write("\t public " + beanName + "  selectById (Long id ){");
		bw.newLine();
		bw.write("\t\t return this."+_mapperName+".selectById(id);");
		bw.newLine();
		bw.write("\t}");
		bw.newLine();
		bw = buildMethodComment(bw, "删除（根据主键ID删除）");
		bw.newLine();
		bw.write("\t@Override");
		bw.newLine();
		bw.write("\t" + "public int deleteById (Long id ){");
		bw.newLine();
		bw.write("\t\t return this."+_mapperName+".deleteById(id);");
		bw.newLine();
		bw.write("\t}");
		bw = buildMethodComment(bw, "添加");
		bw.newLine();
		bw.write("\t@Override");
		bw.newLine();
		bw.write("\t" + "public long insert(" + beanName + " record ){");
		bw.newLine();
		bw.write("\t\t return this."+_mapperName+".insert(record);");
		bw.newLine();
		bw.write("\t}");
		bw = buildMethodComment(bw, "修改");
		bw.newLine();
		bw.write("\t@Override");
		bw.newLine();
		bw.write("\t" + "public int update (" + beanName + " record ){");
		bw.newLine();
		bw.write("\t\t return this."+_mapperName+".update(record);");
		bw.newLine();
		bw.write("\t}");
		bw.newLine();
		bw.write("}");
		bw.flush();
		bw.close();
	}
	
	/**
	 * 构建Service文件
	 * 
	 * @throws IOException
	 */
	private void buildService() throws IOException {
		File folder = new File(service_path);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		File serviceFile = new File(service_path, serviceName + ".java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(serviceFile), "utf-8"));
		bw.write("package " + service_package + ";");
		bw.newLine();
		bw.newLine();
		bw.write("import " + bean_dto_package + "." + beanDTOName + ";");
		bw.newLine();
		bw = buildClassComment(bw, serviceName);
		bw.newLine();
		bw.newLine();
		bw.write("public interface " + serviceName + "{");
		bw.newLine();
		bw.newLine();
		bw = buildMethodComment(bw, "查询（根据主键ID查询）");
		bw.newLine();
		bw.write("\t" + beanDTOName + " selectById (Long id );");
		bw.newLine();
		bw = buildMethodComment(bw, "删除（根据主键ID删除）");
		bw.newLine();
		bw.write("\t" + "int deleteById (Long id );");
		bw.newLine();
		bw = buildMethodComment(bw, "添加");
		bw.newLine();
		bw.write("\t" + "long insert(" + beanDTOName + " record );");
		bw.newLine();
		bw = buildMethodComment(bw, "修改");
		bw.newLine();
		bw.write("\t" + "int update (" + beanDTOName + " record );");
		bw.newLine();
		bw.newLine();
		bw.write("}");
		bw.flush();
		bw.close();
	}
	
	/**
	 * 构建ServiceImpl文件
	 * 
	 * @throws IOException
	 */
	private void buildServiceImpl() throws IOException {
		File folder = new File(service_impl_path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		File daoFile = new File(service_impl_path, serviceImplName + ".java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(daoFile), "utf-8"));
		bw.write("package " + service_impl_package + ";");
		bw.newLine();
		bw.newLine();
		bw.write("import " + bean_dto_package + "." + beanDTOName + ";");
		bw.newLine();
		bw.write("import " + dao_package + "." + daoName + ";");
		bw.newLine();
		bw.write("import javax.annotation.Resource;");
		bw.newLine();
		bw.write("import org.springframework.stereotype.Service;");
		bw.newLine();
		bw.write("import "+ service_package + "." + serviceName+";");
		bw.newLine();
		bw.write("import org.springframework.beans.BeanUtils;");
		bw.newLine();
		bw.write("import " + bean_package + "." + beanName + ";");
		bw.newLine();
		bw = buildClassComment(bw, serviceName);
		bw.newLine();
		bw.write("@Service(\""+this.shotFirst(serviceName)+"\")");
		bw.newLine();
		bw.write("public class " + serviceImplName + " implements "+serviceName+"{");
		bw.newLine();
		bw.newLine();
		bw.write("\t@Resource");
		bw.newLine();
		bw.write("\t private "+ daoName +" "+ this.shotFirst(daoName) +";");
		bw.newLine();
		bw = buildMethodComment(bw, "查询（根据主键ID查询）");
		bw.newLine();
		bw.write("\t@Override");
		bw.newLine();
		bw.write("\tpublic " + beanDTOName + " selectById(Long id){");
		bw.newLine();
		bw.write("\t\t"+ beanDTOName +" "+ this.shotFirst(beanDTOName) +" = new "+ beanDTOName +"();");
		bw.newLine();
		bw.write("\t\t"+ beanName +" "+ this.shotFirst(beanName) +" = this."+ this.shotFirst(daoName) +".selectById(id);");
		bw.newLine();
		bw.write("\t\tif(null != "+ this.shotFirst(beanName) +"){");
		bw.newLine();
		bw.write("\t\t\tBeanUtils.copyProperties("+ this.shotFirst(beanName) +","+ this.shotFirst(beanDTOName) +");");
		bw.newLine();
		bw.write("\t\t\treturn "+ this.shotFirst(beanDTOName) +";");
		bw.newLine();
		bw.write("\t\t}");
		bw.newLine();
		bw.write("\t\treturn null;");
		bw.newLine();
		bw.write("\t}");
		bw.newLine();
		bw = buildMethodComment(bw, "删除（根据主键ID删除）");
		bw.newLine();
		bw.write("\t@Override");
		bw.newLine();
		bw.write("\t" + "public int deleteById(Long id){");
		bw.newLine();
		bw.write("\t\treturn this."+ this.shotFirst(daoName) +".deleteById(id);");
		bw.newLine();
		bw.write("\t}");
		bw = buildMethodComment(bw, "添加");
		bw.newLine();
		bw.write("\t@Override");
		bw.newLine();
		bw.write("\t" + "public long insert(" + beanDTOName + " record){");
		bw.newLine();
		bw.write("\t\t"+ beanName +" "+ this.shotFirst(beanName) +" = new "+ beanName +"();");
		bw.newLine();
		bw.write("\t\tif(null != record){");
		bw.newLine();
		bw.write("\t\t\tBeanUtils.copyProperties(record, "+ this.shotFirst(beanName) +");");
		bw.newLine();
		bw.write("\t\t\treturn this."+ this.shotFirst(daoName) +".insert("+ this.shotFirst(beanName) +");");
		bw.newLine();
		bw.write("\t\t}");
		bw.newLine();
		bw.write("\t\treturn 0;");
		bw.newLine();
		bw.write("\t}");
		bw = buildMethodComment(bw, "修改");
		bw.newLine();
		bw.write("\t@Override");
		bw.newLine();
		bw.write("\t" + "public int update(" + beanDTOName + " record){");
		bw.newLine();
		bw.write("\t\t"+ beanName +" "+ this.shotFirst(beanName) +" = new "+ beanName +"();");
		bw.newLine();
		bw.write("\t\tif(null != record){");
		bw.newLine();
		bw.write("\t\t\tBeanUtils.copyProperties(record, "+ this.shotFirst(beanName) +");");
		bw.newLine();
		bw.write("\t\t\treturn this."+ this.shotFirst(daoName) +".update("+ this.shotFirst(beanName) +");");
		bw.newLine();
		bw.write("\t\t}");
		bw.newLine();
		bw.write("\t\treturn 0;");
		bw.newLine();
		bw.write("\t}");
		bw.newLine();
		bw.write("}");
		bw.flush();
		bw.close();
	}

	/**
	 * 构建Mapper文件
	 * 
	 * @throws IOException
	 */
	private void buildMapper() throws IOException {
		File folder = new File(mapper_path);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		File mapperFile = new File(mapper_path, mapperName + ".java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(mapperFile), "utf-8"));
		bw.write("package " + mapper_package + ";");
		bw.newLine();
		bw.newLine();
		bw.write("import " + bean_package + "." + beanName + ";");
		bw.newLine();
		bw = buildClassComment(bw, mapperName + "数据库操作接口类");
		bw.newLine();
		bw.newLine();
		bw.write("public interface " + mapperName + "{");
		bw.newLine();
		bw.newLine();
		bw = buildMethodComment(bw, "查询（根据主键ID查询）");
		bw.newLine();
		bw.write("\t" + beanName + "  selectById (Long id );");
		bw.newLine();
		bw = buildMethodComment(bw, "删除（根据主键ID删除）");
		bw.newLine();
		bw.write("\t" + "int deleteById (Long id );");
		bw.newLine();
		bw = buildMethodComment(bw, "添加");
		bw.newLine();
		bw.write("\t" + "long insert(" + beanName + " record );");
		bw.newLine();
		bw = buildMethodComment(bw, "修改");
		bw.newLine();
		bw.write("\t" + "int update (" + beanName + " record );");
		bw.newLine();
		bw.newLine();
		bw.write("}");
		bw.flush();
		bw.close();
	}

	/**
	 * 构建实体类映射XML文件
	 * 
	 * @param columns
	 * @param types
	 * @param comments
	 * @throws IOException
	 */
	private void buildMapperXml(List<String> columns, List<String> types,List<String> comments) throws IOException {
		File folder = new File(xml_path);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		File mapperXmlFile = new File(xml_path, mapperName + ".xml");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(mapperXmlFile)));
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bw.newLine();
		bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" ");
		bw.newLine();
		bw.write("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
		bw.newLine();
		bw.write("<mapper namespace=\"" + mapper_package + "." + mapperName
				+ "\">");
		bw.newLine();
		bw.newLine();

		buildSQL(bw, columns, types);

		bw.write("</mapper>");
		bw.flush();
		bw.close();
	}

	
	private void buildMapperDefinitionXml(BufferedWriter bw) throws IOException {
//		<bean id="userMapper" class="org.mybatis.spring.mapper.MapperFactoryBean"> 
//		<property name="mapperInterface" value="com.lovesher.blog.resposity.mapper.UserMapper" />
//		<property name="sqlSessionFactory" ref="sqlSessionFactory" /> 
//		</bean>
		bw.append("\t<bean id=\""+mapper_package+"."+mapperName+"\" class=\"org.mybatis.spring.mapper.MapperFactoryBean\">");
		bw.newLine();
		bw.append("\t\t<property name=\"mapperInterface\" value=\""+mapper_package+"."+mapperName+"\" />");
		bw.newLine();
		bw.append("\t\t<property name=\"sqlSessionFactory\" ref=\"sqlSessionFactory\" />");
		bw.newLine();
		bw.append("\t</bean>");
		bw.newLine();
		bw.newLine();
	}
	private void buildSQL(BufferedWriter bw, List<String> columns,List<String> types) throws IOException {
		int size = columns.size();
		String tempField = null;
		String _tempField = null;
//		String tempType = null;
		bw.write("\t<!-- 结果映射 resultMap-->");
		bw.newLine();
		bw.write("\t<resultMap id=\"BaseResultMap\" type=\"com.lovesher.blog.resposity.bean."+ beanName +"\">");
		for (int i = 0; i < size; i++) {
//			tempType = processType(types.get(i));
			_tempField = processField(columns.get(i));
			tempField = _tempField.substring(0, 1).toUpperCase()+ _tempField.substring(1);
			bw.newLine();
//			bw.write("\t\t <result column=\""+columns.get(i)+"\" property=\""+this.shotFirst(tempField)+"\" jdbcType=\""+tempType+"\"/>");
			bw.write("\t\t <result column=\""+columns.get(i)+"\" property=\""+this.shotFirst(tempField)+"\"/>");
			if (i == size - 1) {
				bw.newLine();
				bw.write("\t</resultMap>");
			}
		}
		// 通用结果列
		bw.newLine();
		bw.newLine();
		bw.write("\t<!-- 通用查询结果列-->");
		bw.newLine();
		bw.write("\t<sql id=\"Base_Column_List\">");
		bw.newLine();

		bw.write("\t\t");
		for (int i = 0; i < size; i++) {
			bw.write("`");
			bw.write(columns.get(i));
			bw.write("`");
			if (i != size - 1) {
				bw.write(",");
			}
		}

		bw.newLine();
		bw.write("\t</sql>");
		bw.newLine();
		bw.newLine();

		// 查询（根据主键ID查询）
		bw.write("\t<!-- 查询（根据主键ID查询） -->");
		bw.newLine();
		bw.write("\t<select id=\"selectById\" resultMap=\"BaseResultMap\">");
		bw.newLine();
		bw.write("\t\t SELECT");
		bw.newLine();
		bw.write("\t\t <include refid=\"Base_Column_List\" />");
		bw.newLine();
		bw.write("\t\t FROM " + tableName);
		bw.newLine();
		bw.write("\t\t WHERE `" + columns.get(0) + "` = #{"
				+ processField(columns.get(0)) + "}");
		bw.newLine();
		bw.write("\t</select>");
		bw.newLine();
		bw.newLine();
		// 查询完

		// 删除（根据主键ID删除）
		bw.write("\t<!--删除：根据主键ID删除-->");
		bw.newLine();
		bw.write("\t<delete id=\"deleteById\">");
		bw.newLine();
		bw.write("\t\t DELETE FROM " + tableName);
		bw.newLine();
		bw.write("\t\t WHERE `" + columns.get(0) + "` = #{"
				+ processField(columns.get(0)) + "}");
		bw.newLine();
		bw.write("\t</delete>");
		bw.newLine();
		bw.newLine();
		// 删除完

		// 添加insert方法
		bw.write("\t<!-- 添加 -->");
		bw.newLine();
		bw.write("\t<insert id=\"insert\" parameterType=\""
				+ bean_package + "."+ beanName + "\">");
		bw.newLine();
		bw.write("\t\t INSERT INTO " + tableName);
		bw.newLine();
		bw.write(" \t\t(");
		for (int i = 1; i < size; i++) {
			bw.write("`");
			bw.write(columns.get(i));
			bw.write("`");
			if (i != size - 1) {
				bw.write(",");
			}
		}
		bw.write(") ");
		bw.newLine();
		bw.write("\t\t VALUES ");
		bw.newLine();
		bw.write(" \t\t(");
		for (int i = 1; i < size; i++) {
			bw.write("#{" + processField(columns.get(i)) + "}");
			if (i != size - 1) {
				bw.write(",");
			}
		}
		bw.write(") ");
		bw.newLine();
		bw.write("\t</insert>");
		bw.newLine();
		bw.newLine();
		// 添加insert完

		// ----- 修改
		bw.write("\t<!-- 修 改-->");
		bw.newLine();
		bw.write("\t<update id=\"update\" parameterType=\""
				+ bean_package + "."+ beanName + "\">");
		bw.newLine();
		bw.write("\t\t UPDATE " + tableName);
		bw.newLine();
		bw.write("\t\t SET ");

		bw.newLine();
		tempField = null;
		for (int i = 1; i < size; i++) {
			tempField = processField(columns.get(i));
			bw.write("\t\t\t <if test=\""+tempField+" !=null\"> `" + columns.get(i) + "` = #{" + tempField + "},</if>");
			bw.newLine();
		}
		bw.write("\t\t `"+columns.get(0)+"` = `"+columns.get(0)+"`");
		bw.write("\t\t WHERE `" + columns.get(0) + "` = #{"
				+ processField(columns.get(0)) + "}");
		bw.newLine();
		bw.write("\t</update>");
		bw.newLine();
		bw.newLine();
	}

	/**
	 * 获取所有的数据库表注释
	 * 
	 * @return
	 * @throws SQLException
	 */
	private Map<String, String> getTableComment() throws SQLException {
		Map<String, String> maps = new HashMap<String, String>();
		PreparedStatement pstate = conn.prepareStatement("show table status");
		ResultSet results = pstate.executeQuery();
		while (results.next()) {
			String tableName = results.getString("NAME");
			String comment = results.getString("COMMENT");
			maps.put(tableName, comment);
		}
		return maps;
	}

	public void generate() throws ClassNotFoundException, SQLException,
			IOException {
		init();
		String prefix = "show full fields from ";
		List<String> columns = null;
		List<String> types = null;
		List<String> comments = null;
		PreparedStatement pstate = null;
		List<String> tables = getTables();
		Map<String, String> tableComments = getTableComment();
		
		File folder = new File(mapper_define_xml_path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		File mapperXmlFile = new File(mapper_define_xml_path,"mapperDefinition.xml");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(mapperXmlFile)));
		bw.append("<!-- mapper define begin -->");
		bw.newLine();
		for (String table : tables) {
			columns = new ArrayList<String>();
			types = new ArrayList<String>();
			comments = new ArrayList<String>();
			pstate = conn.prepareStatement(prefix + table);
			ResultSet results = pstate.executeQuery();
			while (results.next()) {
				columns.add(results.getString("FIELD"));
				types.add(results.getString("TYPE"));
				comments.add(results.getString("COMMENT"));
			}
			tableName = table;
			processTable(table);
			String tableComment = tableComments.get(tableName);
			buildEntityBean(columns, types, comments, tableComment);
			buildMapper();
			buildMapperXml(columns, types, comments);
			buildDao();
			buildDaoImpl();
			buildEntityDTOBean(columns, types, comments, tableComment);
			buildService();
			buildServiceImpl();
			buildMapperDefinitionXml(bw);
		}
		bw.append("<!-- mapper define end -->");
		bw.flush();
		bw.close();
		conn.close();
	}


	public static void main(String[] args) {
		try {
			new MybatisMapperCreator().generate();
			// 自动打开生成文件的目录
			Runtime.getRuntime().exec("cmd /c start explorer D:\\");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}