package com.zfx;
import net.sf.ezmorph.Morpher;
import net.sf.ezmorph.MorpherRegistry;
import net.sf.ezmorph.bean.BeanMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.beanutils.PropertyUtils;

public class json {
	
	private JSONArray a = new JSONArray();
	private Webcontent w = new Webcontent();
	private Showcontent s = new Showcontent();
	private String[][] terms;
	private String[][] docs;
	public json(){
		
	}
	public void Init(String q){
		w.Init(q);
		s.Init(w.getDocs(), w.getClusters(), q,w.getTime());
		a.clear();
		terms = s.getTerms();
		
		
		docs = s.getDocs();
		for(int i = 0; i < terms.length; i++){
			JSONObject o = new JSONObject();
			JSONArray t = new JSONArray();
			JSONArray d = new JSONArray();
			for(int j = 0; j < terms[i].length; j++){
				
				t.add(terms[i][j]);
			}
			o.put("terms", t);
			for(int j = 0; j < docs[i].length; j++){
				d.add(docs[i][j]);
			}
			o.put("docs", d);
			a.add(o);
		}
		
	}
	public JSONArray getJson(){
		return a;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		json s = new json();
		s.Init("apple");
		JSONArray a = s.a;
		System.out.println(a.toString());	
	}

}