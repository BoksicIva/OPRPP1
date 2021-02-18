package hr.fer.oprpp1.hw05.crypto;

import javax.swing.text.html.HTMLDocument.HTMLReader.CharacterAction;

public class Util {
	
	/**
	 * Method converts String representing hex value to byte array
	 * @param keyText String representing hex value
	 * @return converted byte array
	 */
	public static byte[] hextobyte(String keyText) {
		boolean negative=false;
		int length=keyText.length();
		if(keyText==null) throw new NullPointerException("KeyText is null reference and it cannot be converted into byte array");
		if(length%2==1) throw new IllegalArgumentException("keyText String should have length as even number!");
		int index=0;
		byte[] bytearray=new byte[length/2];
		while(index < length) {
			bytearray[index/2]=convert(keyText.charAt(index),keyText.charAt(index+1));
			index+=2;
		}
		return bytearray;
		
	}
	
	/**
	 * Method converts given chars to byte value
	 * @param first
	 * @param second
	 * @return
	 */
	private static byte convert(Character first,Character second) {
		Integer firstNum=toNumber(first);
		Integer secondNum=toNumber(second);
		Integer number=firstNum*10+secondNum;
		byte result=(byte) (firstNum*16+secondNum);
		if(result>127)
			result=(byte) - (256-result);
		return result;
	}
	
	
	/**
	 * Method converts given char to number value of decimal number
	 * @param s
	 * @return
	 */
	private static Integer toNumber(Character s) {
		Integer number = null;
		if(Character.isAlphabetic(s)) {
			switch(Character.toLowerCase(s)){
				case 'a':   number=10;
						  	break;
				case 'b':   number=11;
			  				break;
				case 'c':   number=12;
			  				break;
				case 'd':   number=13;
			  				break;
				case 'e':   number=14;
			  				break;
				case 'f':   number=15;
  							break;
  				default:  throw new IllegalArgumentException("Character is not hex value. ");
			}
		}else if(Character.isDigit(s)){
			number=Integer.parseInt(String.valueOf(s));
		}
		return number;
	}
	
	/**
	 * Method checks if given Character is number 
	 * @param val
	 * @return
	 */
	private static boolean isInteger(Character val) {
	    if (val == null) {
	        return false;
	    }
	    try {
	        Integer number = Integer.parseInt(val+"");
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	/**
	 * Method converts byte values of given array into hex values
	 * @param bytearray array of bytes
	 * @return String of hex values
	 */
	public static String bytetohex(byte[] bytearray) {
		if(bytearray.length==0) return "";
		int decimal;
		String hex;
		StringBuilder resultSB = new StringBuilder();
		for(int i=0;i<bytearray.length;i++) {
			resultSB.append(Character.forDigit((bytearray[i] >> 4) & 0xF, 16));
			resultSB.append(Character.forDigit(bytearray[i] & 0xF, 16));
			
		}
		return resultSB.toString();
		
	}
	
	
	
	/**
	 * Method converts number to hex value
	 * @param s number that need to be converted
	 * @return string of hex value
	 */
	private static String convertToString(int s) {
		String string = null;
		if(s>=10) {
			switch(s){
				case 10 :   string="a";
						  	break;
				case 11 :   string="b";
			  				break;
				case 12 :   string="c";
			  				break;
				case 13:    string="d";
			  				break;
				case 14 :   string="e";
			  				break;
				case 15 :   string="f";
  							break;
  				default:  throw new IllegalArgumentException("Character is not hex value. ");
			}
		}else if(s<10 && s>=0){
			string=String.valueOf(s);
		}
		return string;
	}
	
	


}
