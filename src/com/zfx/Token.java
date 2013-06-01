package com.zfx;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Token {
	//String[] stopWords = {"i","you","the","is","are"};
	public String[] Partition(String input)
	{
		Pattern pattern = Pattern.compile("([{}(): +\\|.~,;<>*\'$&%\"!?-_^/=#])");
		input = input.toLowerCase();
		int i = input.indexOf(' ');
		input = input.substring(i + 1);
		int j = input.indexOf(' ');
		input = input.substring(j + 1);
		String[] filter = input.split(pattern.toString());
		//System.out.println(pattern.toString());
		return filter;
		
		
	}
	public static void main(String[] args){
		Token token = new Token();
		String input = "hello,world.who are you.You are me.? http \"zhang #fei @df@xueeeeee http://bit.ly/g0ayUN";
		String s[] = token.Partition(input);
		System.out.println(input);
		
		for(int i = 0;i < s.length;i++)
			System.out.println(s[i]);
		
	}

}
