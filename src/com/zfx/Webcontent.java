package com.zfx;

import java.util.Date;

public class Webcontent {
	private Mylucene mylucene;
	private TFIDFMeasure tfidf;
	private Kmeans mykmeans;
	private String[] docs;
	private Cluster[] clusters;
	private String[] terms;
	private long time;
	public Webcontent(){
		mylucene = new Mylucene();
		mylucene.Init();
	}
	public void Init(String q){
		Date d1 = new Date();
		docs = mylucene.WSearch(q);
		if(docs == null){
			docs = new String[1];
			docs[0] = new String("Not find!!!");
		}
		tfidf = new TFIDFMeasure(docs,q);
		tfidf.Init();
		terms = tfidf.getTermsIndex();
		int t;
		if(terms.length <= 20)t = terms.length;
		else t = 20;
		mykmeans = new Kmeans(tfidf.getKmeansData(),t);
		mykmeans.start();
		mykmeans.end(terms);
		clusters = mykmeans.getClusters();
		Date d2 = new Date();
		time = d2.getTime() - d1.getTime();
	}
	public String[] getDocs(){
		return docs;
	}
	public Cluster[] getClusters(){
		return clusters;
	}
	public long getTime(){
		return time;
	}
	public static void main(String[] args){
		Webcontent w = new Webcontent();
		w.Init("apple");
		String[] docs = w.getDocs();
		Cluster[] c = w.getClusters();
		for(int i = 0; i < c.length; i++){
			System.out.println("*******************************************************************");
			System.out.println(i);
			System.out.println("******************************************************************************************************");
			
			for(int k = 0; k < c[i].getInfo().size(); k++){
				System.out.print(c[i].getInfo().get(k) + "    ");
			}
			System.out.println();
			
			System.out.println("******************************************************************************************************");
			for(int j = 0;j < c[i].getCurrentMember().size(); j++){
				System.out.println(docs[(int) c[i].getCurrentMember().get(j)]);
			}
		}
	}
}
