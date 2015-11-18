package entities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
	String username;
	String password;//will be stored as encrypted
	
	public static MessageDigest md;
	
	//took from cracker (hw4)
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	public User(String un, String pw){//pw is not encrypted
		this.username = un;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] pass = pw.getBytes();
		byte[] bpw = new byte[20];
		System.arraycopy(md.digest(pass), 0, bpw, 0, bpw.length);
		password = hexToString(bpw);
	}

}
