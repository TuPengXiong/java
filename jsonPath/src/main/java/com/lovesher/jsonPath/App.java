package com.lovesher.jsonPath;

import static com.jayway.jsonpath.Criteria.where;
import static com.jayway.jsonpath.Filter.filter;
import static com.jayway.jsonpath.JsonPath.parse;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

/**
 * 
 * ClassName: App <br/> 
 * Function: JsonPath. <br/> 
 * Reason: JsonPath. <br/> 
 * date: 2018年7月12日 上午10:26:21 <br/> 
 * @see https://github.com/json-path/JsonPath
 * @author tupengxiong 
 * @version  
 * @since JDK 1.7
 */
public class App {
	private static Logger logger = LoggerFactory.getLogger(App.class);
	public static void main(String[] args) {
		String path = App.class.getClassLoader().getResource("test.json").getPath();
		try {
			logger.info(path);
			String json = FileUtils.readFileToString(new File(path), "UTF-8");
			logger.info(json);

			Filter cheapFictionFilter = filter(where("category").is("fiction").and("price").lte(10));

			List<Map<String, Object>> books = parse(json).read("$.store.book[?]", cheapFictionFilter);
			
			logger.info("cheapFictionFilter books:" + books);
			
			List<String> bookLt10 = JsonPath.read(json, "$.store.book[?(@.price < 10)]");
			logger.info("$.store.book[?(@.price < 10)]" + bookLt10);

			JSONArray jsonStr = JsonPath.read(json, "$..author");
			logger.info("$..author:" + jsonStr);
			List<String> authors = JsonPath.read(json, "$.store.book[*].author");
			logger.info("$.store.book[*].author:" + authors);
			
			
			Filter fooOrBar = filter(where("foo").exists(true)).or(where("bar").exists(true));
			List<Map<String, Object>> booksOr = parse(json).read("$.store.book[?]", fooOrBar);
			logger.info("$.store.book[?] booksOr" + booksOr);
			
			Filter fooAndBar = filter(where("foo").exists(true)).and(where("bar").exists(true));
			List<Map<String, Object>> booksAnd = parse(json).read("$.store.book[?]", fooAndBar);
			logger.info("$.store.book[?] booksAnd" + booksAnd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
