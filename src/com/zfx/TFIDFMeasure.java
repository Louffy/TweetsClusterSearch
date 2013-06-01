package com.zfx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class TFIDFMeasure {
	
	private int docsNum;
	private int termsNum;
	private String[] docs;
	//term and it's index
	private TreeMap<String,Integer> termsIndex;
	//frequency of term in one doc
	private int[][] termsFreq; //care:it just a &,not new int(),so if doesnot have place
	private float[][] termsWeight;
	private int[] maxTermsFreq;
	//frequency of term in all docs
	private int[] docsFreq;
	
	private StopWords rmStopWords;
	private String query;
	
	public TFIDFMeasure(String[] input,String q){
		//docs = new String[input.length];
		//for(int i = 0;i < input.length;i ++){
		//	docs[i] = new String(input[i]);
		//}
		docs = input;
		query = q;
	}
	public void getTermsSort(){
		int count = 0;
		Token token = new Token();
		HashMap<String,Integer> termsSort = new HashMap<String,Integer>();
		for(int i = 0; i < docs.length; i++ ){
			ArrayList tempWords = new ArrayList();
			String[] words = token.Partition(docs[i]);
			for(int k = 0; k<words.length;k ++){
				if(words[k].startsWith("@"))continue;
				else if(words[k].startsWith("http"))continue;
				else if(words[k].startsWith("//"))continue;
				else if(words[k].startsWith("com/"))continue;
				else if(words[k].startsWith("ly/"))continue;
				else if(words[k].length()>12)continue;
				//else if(!words[k].contentEquals(query))continue;
				else if(!words[k].contentEquals("")){
					if(words[k].charAt(0) >= '0' && words[k].charAt(0) <= '9')continue;
				}
				tempWords.add(words[k]);
			}
			tempWords = rmStopWords.removeStopWords(tempWords);
			String[] tWords = new String[tempWords.size()];
			tempWords.toArray(tWords);
			for(int j = 0; j<tWords.length; j++){
				if(termsSort.get(tWords[j]) == null)
					termsSort.put(tWords[j],1);
				else {
					count = termsSort.get(tWords[j]);
					termsSort.put(tWords[j], count + 1);
				}
			}
		}
		
		ArrayList<Map.Entry<String,Integer>> info = new ArrayList(termsSort.entrySet());
				
		Collections.sort(info, new Comparator<Map.Entry<String,Integer>>(){
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				// TODO Auto-generated method stub
				return (o2.getValue() - o1.getValue());
			}
		});
		int i = 0;
		for(Entry<String,Integer> e : info){
			if(e.getKey().equals(query))continue;
			if(i > info.size()*0.015)break;
			else i++;
			termsIndex.put(e.getKey(),e.getValue());
		}
		Set<String> keys = termsIndex.keySet();
		for(Iterator<String> it = keys.iterator(); it.hasNext();){
			String sw = it.next();
			System.out.println(sw + "  " + termsIndex.get(sw));
		}
	}
	public void generateTerms(){
		int c = 0;
		Set<String> keys = termsIndex.keySet();
		
		System.out.println(termsIndex.size());
		for(Iterator<String> it = keys.iterator(); it.hasNext(); ){
				String tw = it.next();
				termsIndex.put(tw,c);
				c++;	
		}
		//System.out.println(termsIndex.toString());
	}
	//get a word freq in a doc
	public HashMap<String, Integer> getWordFreq(String input){
		//input = input.toLowerCase();
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		Token token = new Token();
		String[] tempWords = token.Partition(input);
		ArrayList words = new ArrayList();
		for(int j = 0;j<tempWords.length;j++){
			if(words.indexOf(tempWords[j]) == -1)
				words.add(tempWords[j]);
		}
		words = rmStopWords.removeStopWords(words);
		String[] tWords = new String[words.size()];
		words.toArray(tWords);
		for(int i = 0;i < words.size();i ++){
			int c = count(tempWords,tWords[i]);
			result.put(tWords[i], c);
		}
		//System.out.println(result.toString());
		return result;
	}
	//count a word in a String[]
	public int count(String[] tempWords,String word){
		int count=0;
		for(int i = 0;i < tempWords.length;i ++){
			if(tempWords[i].contentEquals(word))
				count++;
		}
		return count;
	}
	//term-frequency
	public void generateTF(){
		for(int i = 0;i < docs.length;i ++){
			String curDoc = docs[i];
			HashMap<String, Integer> freq = getWordFreq(curDoc);
			Set<String> keys = freq.keySet();
			for(Iterator<String> it = keys.iterator();it.hasNext();){
				String word = it.next();
				int termIndex;
				int wordFreq = freq.get(word);
				if(termsIndex.get(word) != null){
					 termIndex = termsIndex.get(word);
				}
				else continue;
				termsFreq[termIndex][i] = wordFreq;
				docsFreq[termIndex]++;
				if(wordFreq > maxTermsFreq[i])
					maxTermsFreq[i] = wordFreq;
			}
			
			
		}
	}
	//get TF
	public float getTF(int term,int doc){
		int freq = termsFreq[term][doc];
		int maxFreq = maxTermsFreq[doc];
		return ((float)freq/(float)maxFreq);
	}
	//get IDF
	public double getIDF(int term){
		int df = docsFreq[term];
		return Math.log((double)docsNum / (double)df);
	}
	//generate TW
	public void generateTW(){
		for(int i = 0; i < termsNum; i++){
			for(int j = 0; j < docsNum; j++){
				termsWeight[i][j] = (float) (getTF(i,j)*getIDF(i));
				//if(termsWeight[i][j].isNaN()) termsWeight[i][j] = 0;
			}
		}
	}
	public void Init(){
		termsIndex = new TreeMap<String,Integer>();
		rmStopWords = new StopWords();
		//generate terms and it's index
		getTermsSort();
		generateTerms();
		termsNum = termsIndex.size();
		docsNum = docs.length;
		docsFreq = new int [termsNum];
		maxTermsFreq = new int[docs.length];
		termsFreq = new int[termsNum][];
		termsWeight = new float[termsNum][];
		for(int i = 0;i < termsNum; i++){
			termsFreq[i] = new int[docsNum];
			termsWeight[i] = new float[docsNum];
		}
		generateTerms();
		//System.out.println(termsIndex.toString());
		generateTF();
		/*for(int i = 0; i < termsNum; i++){
			for(int j = 0; j < docsNum; j++){
				System.out.print(termsFreq[i][j]);
				
			}
			System.out.println();
		}*/
		
		generateTW();
		/*for(int i = 0; i < termsNum; i++){
			for(int j = 0; j < docsNum; j++){
				System.out.print(termsWeight[i][j] + "  ");
				
			}
			System.out.println();
		}*/
		System.out.println(termsNum);
		System.out.println(docsNum);
		//System.out.println(termsWeight.length);
		Set<String> keys = termsIndex.keySet();
		/*for(Iterator<String> it = keys.iterator(); it.hasNext();){
			String sw = it.next();
		System.out.println(sw + "              " + termsIndex.get(sw));
		}*/
		
	}
	//get term-vectory of the doc
	public float[] getTermVector(int doc){
		float[] w = new float[termsNum];
		for(int i = 0; i < termsNum; i++){
			w[i] = termsWeight[i][doc];
			//System.out.print(w[i]);
		}
		
		return w;
	}
	//get kmeans data
	public float[][] getKmeansData(){
		//kmeans data of docs,x=term weight,y=doc 
		 float[][] data = new float[docsNum][termsNum];
		 for(int i = 0; i < docsNum; i++){
			 data[i] = getTermVector(i);
		 }
		 return data;
		 
	}
	public String[] getTermsIndex(){
		String [] term = new String[termsNum];
		Set<String> keys = termsIndex.keySet();
		int i = 0;
		for(Iterator<String> it = keys.iterator(); it.hasNext();){
			String t = it.next();
			term[i] = t;
			i++;
		}
		return term;
	}
	public static void main(String[] args){
		String[] docs = new String[5];
		docs[0] = "china is a big country,big big<font color='red'>Apple</font>country";
		docs[1] = new String("apple is a USA company");
		docs[2] = new String("zhang fei xue is a people in china,china is a country,very big big");
		docs[3] = "chai china you am apple";
		docs[4] = "apple store are usa";
		TFIDFMeasure tfidf = new TFIDFMeasure(docs,"apple");
		tfidf.Init();
		//tfidf.getTermsSort();
		/*float[][] data = tfidf.getKmeansData();
		for(int i = 0; i < data.length; i++){
			for(int j = 0; j < data[i].length; j++){
				System.out.print(data[i][j] + "  ");
				
			}
			System.out.println();
		}
		System.out.println("end");
		//tfidf.generateTerms();
		
		//HashMap<String,Integer> result = tfidf.getWordFreq("how are you i am fine you i you");
		//System.out.println(tfidf.toString());*/
	}
}
