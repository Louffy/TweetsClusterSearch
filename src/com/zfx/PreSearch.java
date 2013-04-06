package com.zfx;

import java.io.*;

public class PreSearch {
	private static final String predataPath = "E:\\workspace_web\\WeiboSearch\\WebContent\\predata";
	private static final String dataPath = "E:\\workspace_web\\WeiboSearch\\WebContent\\data\\data.content";
	public PreSearch(){
		
	}
	public void SearchData(){
		File inFilePath = new File(predataPath);
		File[] inFile = inFilePath.listFiles();
		InputStream in = null;
		InputStreamReader inR =null;
		BufferedReader br = null;
		FileWriter outW =null;
		BufferedWriter bw = null;
		File outFile = new File(dataPath);
		outFile.delete();
		try {
			outFile.createNewFile();
		} catch (IOException e5) {
			// TODO Auto-generated catch block
			e5.printStackTrace();
		}
		
		try{
			long b = 0;
			for(int a = 0;a < inFile.length;a++){
				in = new FileInputStream(inFile[a]);
				inR = new InputStreamReader(in,"utf-8");
				br = new BufferedReader(inR);
				outW = new FileWriter(outFile,true);
				bw = new BufferedWriter(outW);
				String indexLine;
				String contentLine;
				while((indexLine = br.readLine()) != null){
					int i = indexLine.indexOf("[user]: ");
					if(i == -1)continue;
					contentLine = br.readLine();
					int j = indexLine.indexOf("[is_retweeted]: ");
					String index = indexLine.substring(i+8, j);
					String content = contentLine.substring(11);
					System.out.println(index);
					System.out.println(b + "  "  + content);
					b++;
					bw.write(index);
					bw.newLine();
					bw.write(content);
					bw.newLine();
				}
				System.out.println(inFile.length);
			}
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
		

		System.out.println("Success!");
		
	}
	public static void main(String[] args){
		PreSearch preSearch = new PreSearch();
		preSearch.SearchData();
		
	}
	

}
