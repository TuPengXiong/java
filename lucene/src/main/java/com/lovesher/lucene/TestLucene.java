package com.lovesher.lucene;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class TestLucene {

	@Test
	public void add() throws IOException {

		Article article = new Article();
		article.setId(1L);
		article.setTitle("Lucene全文检索");
		article.setContent(
				"Lucene是apache软件基金会4 jakarta项目组的一个子项目，是一个开放源代码的全文检索引擎工具包，但它不是一个完整的全文检索引擎，而是一个全文检索引擎的架构，提供了完整的查询引擎和索引引擎，部分文本分析引擎（英文与德文两种西方语言）。");

		final Path path = Paths.get("./article/");

		Directory directory = FSDirectory.open(path);
		Analyzer analyzer = new StandardAnalyzer();

		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

		Document document = new Document();
		document.add(new TextField("id", article.getId().toString(), Field.Store.YES));
		document.add(new TextField("title", article.getTitle(), Field.Store.YES));
		document.add(new TextField("content", article.getContent(), Field.Store.YES));

		indexWriter.addDocument(document);
		indexWriter.close();

	}

	@Test
	public void SearchFiles() throws IOException, org.apache.lucene.queryparser.classic.ParseException {
		// String queryString = "全文检索";
		String queryString = "1";

		// 多条件
		// Query q = MultiFieldQueryParser.parse(new String[]{},new
		// String[]{},new StandardAnalyzer());

		final Path path = Paths.get("./article/");
		Directory directory = FSDirectory.open(path);
		Analyzer analyzer = new StandardAnalyzer();

		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		// 单条件
		QueryParser queryParser = new QueryParser("id", analyzer);
		Query query = queryParser.parse(queryString);

		TopDocs topDocs = indexSearcher.search(query, 10);

		long conut = topDocs.totalHits;
		System.out.println("检索总条数：" + conut);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			Document document = indexSearcher.doc(scoreDoc.doc);
			System.out.print("相关度：" + scoreDoc.score);
			System.out.println(document.get("id"));
			System.out.println(document.get("title"));
			System.out.println(document.get("content"));
		}
	}
}
