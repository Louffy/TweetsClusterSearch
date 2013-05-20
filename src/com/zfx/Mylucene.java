package com.zfx;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.TotalHitCountCollector;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
public class Mylucene{
	//private static final File indexPath = new File("D:\\index");
	//private static final String dataPath = "D:\\english_tweets\\content_english\\20110208\\000.content";
	private static final String dataPath = "E:\\workspace_web\\tweetData\\data\\data.content.006";
	private static final File indexPath = new File("E:\\workspace_web\\tweetData\\index");
	private static final Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
	public IndexReader indexReader;
	public IndexSearcher searcher;
	public QueryParser parser;
	public void Mylucene(){
	}
	public ArrayList<String> ReadFile(File file){
		InputStream in = null;
		InputStreamReader inR = null;
		BufferedReader br = null;
		ArrayList<String> words = new ArrayList<String>();
		
		try{
			in = new FileInputStream(file);
			inR = new InputStreamReader(in,"utf-8");
			br = new BufferedReader(inR);
			String indexLine;
			String contentLine;
			while((indexLine = br.readLine()) != null){
				if(indexLine.trim().indexOf(' ') == -1)continue;
				contentLine = br.readLine();
				if(contentLine.trim().length() < 25)continue;
				String temp = new String("<font color='blue'>");
				int user = indexLine.indexOf(' ');
				indexLine = indexLine.substring(user + 1);
				//if
				temp = temp.concat(indexLine.trim());
				temp = temp.concat("</font>: ");
				
				temp = temp.concat(contentLine.trim());
				words.add(temp);
				
			}
			return words;
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}finally {
			try{
				if(in != null)
					in.close();
				if(inR != null)
					inR.close();
				if(br != null)
					br.close();
			}catch (Exception e){
				e.printStackTrace();
				return null;
			}
		}
	}
	@SuppressWarnings("deprecation")
	public void CreateIndex(){
		File readFile = new File(dataPath);
		ArrayList<String> words = ReadFile(readFile);
		Document doc = null;
		if(words != null){
			try{
				IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36,analyzer);
				IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexPath),config);
				for(int i = 0; i < words.size(); i++){
					String word = words.get(i); 
					doc = new Document();
					Field index = new Field("index",word,Field.Store.YES,Field.Index.ANALYZED);//存储，分词建索引
					doc.add(index);
					indexWriter.addDocument(doc);
					//System.out.println("\n index: " + key + "\n content: " + words.get(key));
				}
				indexWriter.optimize();
				indexWriter.close();
				System.out.println("索引建立成功");
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		else
			System.out.println("文件读取错误");
	}
	
	public boolean NoIndex(){
		File[] indexs = indexPath.listFiles();
		if(indexs.length == 0){
			return true;
		} else{
			return false;
		}
	}
	public String[] WSearch(String queryStr){
		//ArrayList<String> wResult = new ArrayList<String>();
		String[] wResult = null;
		try{
			Query query = parser.parse(queryStr);
			TopScoreDocCollector collector = TopScoreDocCollector.create(10000, true);
			searcher.search(query, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;
			
			//高亮
			Formatter formatter = new SimpleHTMLFormatter("<font color='red'>","</font>");
			Scorer fragmentScore = new QueryScorer(query);
			Highlighter highlighter = new Highlighter(formatter,fragmentScore);
			Fragmenter fragmenter = new SimpleFragmenter(100);
			highlighter.setTextFragmenter(fragmenter);
			
			if(hits.length > 0){
				//System.out.println("检索词："+queryStr+"\t共找到  "+hits.length+"条记录");
				//wResult.put("<br> 检索词："+queryStr,"共找到  "+hits.length+"条记录");
				int length;
				if(hits.length > 200)length = 200;
				else length = hits.length;
				wResult = new String[length];
				for(int i = 0; i < length; i++){    //从索引中获取数据是一件很耗时间的事情，你最好只获取用户需要的数据
					Document result = searcher.doc(hits[i].doc);
					String content = result.get("index");
					String highlighterContent = highlighter.getBestFragment(analyzer, "index", content);
					//System.out.println((i+1) + ")" + "\n index:" + result.get("index") 
					//		+ "\n content:" + highlighterContent);
					wResult[i] = new String(highlighterContent);
				}
				return wResult;
			}else {
				//System.out.println("未找到结果");
				//wResult = new String[1];
				//wResult[0] = new String("Not find");
				return wResult;
			}
		}catch (Exception e){
			//System.out.println("Exception");
			//wResult = new String[1];
			//wResult[0] = new String("Exception!");
			return wResult;
		}
	}
	public void Init(){
		try {
			indexReader = IndexReader.open(FSDirectory.open(indexPath));
			searcher = new IndexSearcher(indexReader);
			parser = new QueryParser(Version.LUCENE_36,"index",analyzer);
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void Search(String queryStr){
		try{
			
			
			Query query = parser.parse(queryStr);
			//TopScoreDocCollector collector = TopScoreDocCollector.create(10000, true);
			//searcher.search(query, collector);
			//TopScoreDocCollector collector = TopScoreDocCollector.create(10000, true);
			TopDocs topDoc = searcher.search(query, 2000);
			ScoreDoc[] hits = topDoc.scoreDocs;
			
			//ArrayList<Document> docs = new ArrayList<Document>();
			//高亮
			Formatter formatter = new SimpleHTMLFormatter("<font color='red'>","</font>");
			Scorer fragmentScore = new QueryScorer(query);
			Highlighter highlighter = new Highlighter(formatter,fragmentScore);
			Fragmenter fragmenter = new SimpleFragmenter(100);
			highlighter.setTextFragmenter(fragmenter);
			System.out.println("-------search-------");
			Date s = new Date();
			System.out.println(s.toString());
			
			
			if(hits.length > 0){
				System.out.println("检索词："+queryStr+"\t共找到  "+hits.length+"条记录");
				for(int i = 0; i < 100; i++){
					Document result = searcher.doc(hits[i].doc);
					//docs.add(result);
					String content = result.get("index");
					String highlighterContent = highlighter.getBestFragment(analyzer, "index", content);
					System.out.println((i+1)
							+ "content:" + highlighterContent);
					//docs[i] = new String(content);
					//System.out.println(docs[i]);
				}
			Date s1 = new Date();
			System.out.println(s1.toString());
			System.out.println("-------search-------");
			//return docs;
			}else {
				System.out.println("未找到结果");
			}
		}catch (Exception e){
			System.out.println("Exception");
		}
		//return null;
		
	}

	public static void main(String[] args){
		Mylucene mylucene = new Mylucene();
		/*
		 mylucene.Init();
		
		Date d = new Date();
		System.out.println(d.toString());
		if(mylucene.NoIndex()){
			mylucene.CreateIndex();
		}
		Date t = new Date();
		System.out.println(t.toString());
		
		mylucene.Search("apple");
		
		Date s = new Date();
		System.out.println(s.toString());
		System.out.println("--------------");
		Date t1 = new Date();
		//System.out.println(t1.toString());
		
		String[] qu = mylucene.WSearch("apple");
		Date s1 = new Date();
		System.out.println(s1.toString());
		*/
		mylucene.CreateIndex();
	}	
}

