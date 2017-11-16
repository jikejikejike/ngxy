package com.thinkgem.jeesite.test;

public class Test {

	public static void main(String[] args) {
		System.out.println(Test.conver("齐鲁石化危化品管理系统"));

	}
	public static String conver(String str) {
		String result = "";
		for(int i = 0; i < str.length(); i++) {
			String temp = "";
			int strInt = str.charAt(i);
			if(strInt > 127) {
				temp += "\\u" + Integer.toHexString(strInt);
			} else {
				temp = String.valueOf(str.charAt(i));
			}

			result += temp;
		}
		return result;
	}


}
