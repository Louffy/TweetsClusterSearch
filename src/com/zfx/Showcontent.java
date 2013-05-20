package com.zfx;
import net.sf.ezmorph.Morpher;
import net.sf.ezmorph.MorpherRegistry;
import net.sf.ezmorph.bean.BeanMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.beanutils.PropertyUtils;
public class Showcontent {
	private String[][] terms;
	private String[][] docClusters;
	private String query;
	private long time;
	
	
	
	public Showcontent(){
		
	}
	public void Init(String[] docs,Cluster[] c,String q,long t){
		query = q;
		time = t;
		docClusters = new String[c.length][];
		terms = new String[c.length][];
		for(int i = 0; i < c.length; i++){
			int k = c[i].getCurrentMember().size();
			int h = c[i].getInfo().size();
			docClusters[i] = new String[k];
			terms[i] = new String[h];
			for(int j = 0; j < h; j++){
				terms[i][j] = new String(c[i].getInfo().get(j));
			}
			for(int j = 0; j < k; j++){
				docClusters[i][j] = new String(docs[c[i].getCurrentMember().get(j)]);
			}
		}
	}
	
	public String[][] getDocs(){
		return docClusters;
	}
	public String[][] getTerms(){
		return terms;
	}
	public String getQuery(){
		return query;
	}
	public long getTime(){
		return time;
	}
	
	public static void main(String[] args){
		Webcontent w = new Webcontent();
		Showcontent s = new Showcontent();
		String q = new String("china");
		w.Init(q);
		s.Init(w.getDocs(), w.getClusters(), q,w.getTime());
		String[][] docs = s.getDocs();
		String[][] terms = s.getTerms();
		for(int i = 0; i < terms.length; i++){
			
			for(int j = 0; j < terms[i].length; j++){
				System.out.println(terms[i][j]);
			}
			for(int j = 0; j < docs[i].length; j++){
				System.out.println(docs[i][j]);
			}
		}
	}
}
