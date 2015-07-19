package backend;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypt {
	public static String getSHA1(String sha1){
		byte [] convertme=sha1.getBytes();
		MessageDigest md = null;
	    try {
	        md = MessageDigest.getInstance("SHA-1");
	    }
	    catch(NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } 
	    return  byteArrayToHexString(md.digest(convertme));
	}
	public static String byteArrayToHexString(byte[] b) {
		  String result = "";
		  for (int i=0; i < b.length; i++) {
		    result +=
		          Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		  }
		  return result;
	}
}
