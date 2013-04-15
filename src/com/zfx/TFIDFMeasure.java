package com.zfx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
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
	
	public TFIDFMeasure(){
		//for(int i = 0;i < 3;i ++){
		//	docs[i] = new String();
		//}
		docs = new String[5];
		docs[0] = "china is a big country,big big<font color='red'>Apple</font>country";
		docs[1] = new String("apple is a USA company");
		docs[2] = new String("zhang fei xue is a people in china,china is a country,very big big");
		docs[3] = "chai china you am apple";
		docs[4] = "apple store are usa";
	}
	public void generateTerms(){
		int count = 0;
		Token token = new Token();
		
		for(int i = 0; i<docs.length; i++ ){
			ArrayList tempWords = new ArrayList();
			String[] words = token.Partition(docs[i]);
			for(int k = 0; k<words.length;k ++){
				if(tempWords.indexOf(words[k]) == -1)
					tempWords.add(words[k]);
			}
			tempWords = rmStopWords.removeStopWords(tempWords);
			String[] tWords = new String[tempWords.size()];
			tempWords.toArray(tWords);
			for(int j = 0; j<tWords.length; j++){
				if(termsIndex.get(tWords[j]) == null){
					termsIndex.put(tWords[j],count);
					count++;
				}
			}
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
			}
		}
	}
	public void Init(){
		termsIndex = new TreeMap<String,Integer>();
		rmStopWords = new StopWords();
		//generate terms and it's index
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
		System.out.println(termsIndex.toString());
		generateTF();
		for(int i = 0; i < termsNum; i++){
			for(int j = 0; j < docsNum; j++){
				System.out.print(termsFreq[i][j]);
				
			}
			System.out.println();
		}
		
		generateTW();
		for(int i = 0; i < termsNum; i++){
			for(int j = 0; j < docsNum; j++){
				System.out.print(termsWeight[i][j] + "  ");
				
			}
			System.out.println();
		}
		System.out.println(termsWeight.length);
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
	public static void main(String[] args){
		TFIDFMeasure tfidf = new TFIDFMeasure();
		tfidf.Init();
		float[][] data = tfidf.getKmeansData();
		for(int i = 0; i < data.length; i++){
			for(int j = 0; j < data[i].length; j++){
				System.out.print(data[i][j] + "  ");
				
			}
			System.out.println();
		}
		System.out.println("end");
		//tfidf.generateTerms();
		
		//HashMap<String,Integer> result = tfidf.getWordFreq("how are you i am fine you i you");
		//System.out.println(tfidf.toString());
	}
}
