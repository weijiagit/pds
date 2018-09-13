package com.fykj.util;

public class WordExportUtils {
	
	private static final int LINE_COUNT = 48;
	
	public static String getBlankStr(String str) {
    	int count = LINE_COUNT;
    	if(str != null && !"".equals(str)){
    		char cc[] = str.toCharArray();
    		for(char c: cc){
    			count--;
    			if(isChinese(c)){
    				count--;
    			}
    		}
    	}
    	if(count > 0){
        	StringBuilder builder = new StringBuilder();
    		for(int i = 0;i<count;i++){
    			builder.append(" ");
    		}
    		return builder.toString();
    	}
    	return "";
    }
    
	public static boolean isChinese(char c) {   
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);  
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A  
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {  
            return true;  
        }  
        return false;  
    }  

}
