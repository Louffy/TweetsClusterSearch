package com.zfx;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
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
	private static final String dataPath = "E:\\workspace_web\\WeiboSearch\\WebContent\\data\\data.content";
	private static final File indexPath = new File("E:\\workspace_web\\WeiboSearch\\WebContent\\index");
	private static final Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
	public void Mylucene(){
	}
	public HashMap<String,String> ReadFile(File file){
		InputStream in = null;
		InputStreamReader inR = null;
		BufferedReader br = null;
		HashMap<String,String> wordsMap = new HashMap<String,String>();
		try{
			in = new FileInputStream(file);
			inR = new InputStreamReader(in,"utf-8");
			br = new BufferedReader(inR);
			String indexLine;
			String contentLine;
			while((indexLine = br.readLine()) != null&&(contentLine = br.readLine()) != null){
				wordsMap.put(indexLine.trim(), contentLine.trim());
			}
			return wordsMap;
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
	public void CreateIndex(){
		File readFile = new File(dataPath);
		HashMap<String,String> words = ReadFile(readFile);
		Document doc = null;
		if(words != null){
			try{
				IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36,analyzer);
				IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexPath),config);
				Set<String> keys = words.keySet();
				for(Iterator<String> it = keys.iterator(); it.hasNext();){
					String key = it.next();
					doc = new Document();
					Field index = new Field("index",key,Field.Store.YES,Field.Index.ANALYZED);//存储，分词建索引
					Field content = new Field("content",words.get(key),Field.Store.YES,Field.Index.ANALYZED);
					doc.add(index);
					doc.add(content);
					indexWriter.addDocument(doc);
					//System.out.println("\n index: " + key + "\n content: " + words.get(key));
				}
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
	public TreeMap<String,String> WSearch(String queryStr){
		TreeMap<String,String> wResult = new TreeMap<String,String>();
		try{
			IndexReader indexReader = IndexReader.open(FSDirectory.open(indexPath));
			IndexSearcher searcher = new IndexSearcher(indexReader);
			QueryParser parser = new QueryParser(Version.LUCENE_36,"content",analyzer);
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
				for(int i = 0; i < hits.length; i++){
					Document result = searcher.doc(hits[i].doc);
					String content = result.get("content");
					String highlighterContent = highlighter.getBestFragment(analyzer, "content", content);
					//System.out.println((i+1) + ")" + "\n index:" + result.get("index") 
					//		+ "\n content:" + highlighterContent);
					wResult.put(result.get("index"), highlighterContent);
				}
				return wResult;
			}else {
				//System.out.println("未找到结果");
				return null;
			}
		}catch (Exception e){
			//System.out.println("Exception");
			//wResult.put("Exception","");
			return null;
		}
	}
	public void Search(String queryStr){
		try{
			IndexReader indexReader = IndexReader.open(FSDirectory.open(indexPath));
			IndexSearcher searcher = new IndexSearcher(indexReader);
			QueryParser parser = new QueryParser(Version.LUCENE_36,"content",analyzer);
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
				System.out.println("检索词："+queryStr+"\t共找到  "+hits.length+"条记录");
				for(int i = 0; i < hits.length; i++){
					Document result = searcher.doc(hits[i].doc);
					String content = result.get("content");
					String highlighterContent = highlighter.getBestFragment(analyzer, "content", content);
					System.out.println((i+1) + ")" + "\n index:" + result.get("index") 
							+ "\n content:" + highlighterContent);
				}
			}else {
				System.out.println("未找到结果");
			}
		}catch (Exception e){
			System.out.println("Exception");
		}
	}

	public static void main(String[] args){
		Mylucene mylucene = new Mylucene();
		//if(mylucene.NoIndex()){
		//	mylucene.CreateIndex();
		//}
		mylucene.Search("hello");
	}	
}

