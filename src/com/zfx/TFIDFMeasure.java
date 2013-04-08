package com.zfx;

import java.util.ArrayList;

public class TFIDFMeasure {
	private int docNum;
	private int termNum;
	private String[] docs;
	private String[] terms;
	private int[][] termFreq;
	private float[][] termWeight;
	private int[] maxTermFreq;
	private int[] docFreq;
	
	public void generateTerms(){
		Token token = new Token();
		for(int i = 0; i<docs.length; i++ ){
			String[] words = token.Partition(docs[i]);
			
		}
	}
	public TFIDFMeasure(ArrayList docs,ArrayList terms){
		
	}

}
