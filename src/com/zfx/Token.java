package com.zfx;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Token {
	//String[] stopWords = {"i","you","the","is","are"};
	public String[] Partition(String input)
	{
		Pattern pattern = Pattern.compile("([ \\t{}(),:;<>/.!?\n])");
		input = input.toLowerCase();
		String[] filter = input.split(pattern.toString());
		
		return filter;
		
		
	}
	public static void main(String[] args){
		Token token = new Token();
		String input = "hello,world.who are you.You are me.?";
		String s[] = token.Partition(input);
		System.out.println(input);
		
		for(int i = 0;i < s.length;i++)
			System.out.println(s[i]);
		
	}

}
