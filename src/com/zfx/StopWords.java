package com.zfx;

import java.util.ArrayList;

public class StopWords {
	private String[] stopWords = {"i","you","is","am","are","we","they","them","he","her","she","him","it","the","these",
			"what","how","where","when","that","a","an","in","on","font","color='red'",""," "};
	public ArrayList removeStopWords(ArrayList<String> words){
		for(int i = 0;i < stopWords.length;i ++){
			if(words.indexOf(stopWords[i]) != -1){
				while(words.indexOf(stopWords[i]) != -1)
				words.remove(stopWords[i]);
			}
				
		}
		return words;
	}

}
