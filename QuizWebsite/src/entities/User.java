package entities;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class User {
	private String username;
	private String password;//will be stored as encrypted
	private boolean admin;
	private List<User> friends;
	
	// TODO should only initialize this once
	public static MessageDigest md;
	
	public User(String un, String pw){//pw is not encrypted
		this.username = un;
		this.admin = false;
		this.friends = new ArrayList<User>();
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		password = encryptPass(pw);
	}
	
	public User(String un, String pw, boolean admin){//pw is not encrypted
		this.username = un;
		this.admin = admin;
		this.friends = new ArrayList<User>();
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		password = encryptPass(pw);
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void addFriend(User friend){
		friends.add(friend);
	}
	
	public boolean passMatches(String pass) {
		String encrypted = encryptPass(pass);
		return encrypted.equals(this.password);
	}
	
	private static String encryptPass(String pw) {
		byte[] pass = pw.getBytes();
		byte[] bpw = new byte[20]; // Why do we assume this?
		System.arraycopy(md.digest(pass), 0, bpw, 0, bpw.length);
		return hexToString(bpw);
		
		/* Colin's strategy:
		byte[] digest = null;
		md.update(pw.getBytes());
		digest = md.digest();
		return hexToString(digest);
		 */
	}
	
	//took from cracker (hw4)
	private static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}

}
