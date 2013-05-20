package com.zfx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

public class test {
	private static final String dataPath = "E:\\workspace_web\\tweetData\\predata\\066.content";
	public void SearchData(){
		File inFilePath = new File(dataPath);
		InputStream in = null;
		InputStreamReader inR =null;
		BufferedReader br = null;
		ArrayList<String> docs = new ArrayList<String>();
		Date d1 = new Date();
		System.out.println(d1.toString());
		try{
			in = new FileInputStream(inFilePath);
			inR = new InputStreamReader(in,"utf-8");
			br = new BufferedReader(inR);
			String indexLine;
			int i = 0;
			while((indexLine = br.readLine()) != null){
				docs.add(indexLine);
				
				i++;
				
			}
			System.out.println(i);
			Date d2 = new Date();
			System.out.println(d2.toString());
		} catch (Exception e){
			e.printStackTrace();
		} finally{
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
		}
	}
	public static void main(String[] args){
		test t = new test();
		t.SearchData();
	}
}
