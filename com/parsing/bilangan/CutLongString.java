package com.parsing.bilangan;


public class CutLongString {
	
	public static String CutString( String input  , int maxlength){
		String result = "";
		StringBuilder builder = new StringBuilder(); 
		String[] temp = input.split("\\s+");  
		int i = 0 , n = 0;
		while( i <  temp.length ){
			int panjang = temp[i].length();  
			if( n + panjang < maxlength ){
				n += panjang; 
				builder.append(temp[i]);
				builder.append(" "); 
				i++; 
			}else{
				builder.append( "\n" ); 
				n = 0; 
 			}
		}
		
		result= builder.toString(); 
		return result;
	}

}
