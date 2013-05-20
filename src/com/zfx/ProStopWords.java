package com.zfx;

import java.io.*;
import java.util.ArrayList;

public class ProStopWords {
	private static final String dataPath = "E:\\workspace_web\\tweetData\\stop_words_eng.txt";
	private static final String pdataPath = "E:\\workspace_web\\tweetData\\p_stop_words_eng.txt";
	public void getStopWords(){
		File input = new File(dataPath);
		InputStream in = null;
		InputStreamReader inR = null;
		BufferedReader br = null;
		File out = new File(pdataPath);
		FileWriter outW = null;
		BufferedWriter bw = null;
		try {
			
			in = new FileInputStream(input);
			inR = new InputStreamReader(in);
			br = new BufferedReader(inR);
			outW = new FileWriter(out);
			bw = new BufferedWriter(outW);
			String con;
			
			try {
				while((con = br.readLine()) != null){
					bw.write(con+"\",\"");
					//bw.newLine();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			if(in != null)
				try {
					in.close();
				} catch (IOException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}
			if(inR != null)
				try {
					inR.close();
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
			if(br != null)
				try {
					br.close();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			//先关闭FileWriter再关闭BufferedWriter
			if(bw != null){
				
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(outW != null)
				try {
					outW.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		
		
		
	}
	
	public static void main(String[] args){
		ProStopWords pro = new ProStopWords();
		pro.getStopWords();
		System.out.println("end");
	}

}
