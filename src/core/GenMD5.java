/*
ID: lazydom1
LANG: JAVA
TASK: GenerateSecretCode.java
Created on: 2012-3-28-下午4:18:38
Author: lazydomino[AT]163.com(pisces)
*/

package core;

import java.security.MessageDigest;

/**
 * MD5 加密模块
 */
public class GenMD5 {

		public GenMD5()
		{
			
			
		}
		
		public static String  getMD5(char[] str)
		{
			passwd = "";
			for(char c:str)
			{
				passwd += c;
				
			}
			return md5(passwd);
		}
	
		public static String getMD5(String str)
		{
			return md5(str);
		}
		
		private static String md5(String string) 
		{
			char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };
			try {
				byte[] bytes = string.getBytes();
				MessageDigest messageDigest = MessageDigest.getInstance("MD5");
				messageDigest.update(bytes);
				byte[] updateBytes = messageDigest.digest();
				int len = updateBytes.length;
				char myChar[] = new char[len * 2];
				int k = 0;
				for (int i = 0; i < len; i++) {
					byte byte0 = updateBytes[i];
					myChar[k++] = hexDigits[byte0 >>> 4 & 0x0f];
					myChar[k++] = hexDigits[byte0 & 0x0f];
				}
			return new String(myChar);
			} catch (Exception e) {
			return null;
			}
		}

		
		private static String passwd = "";
}
