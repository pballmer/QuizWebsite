package entities;

import java.util.HashMap;

public class AccountManager {
	
	private HashMap<String, User> accounts;
	
	public AccountManager() {
		accounts = new HashMap<String, User>();
	}
	
	public boolean accountExists(String name) {
		return accounts.containsKey(name);
	}
	
	public boolean passwordMatches(String name, String pass) {
		if (!accountExists(name)) return false;
		User user = accounts.get(name);
		return user.passMatches(pass);
	}
	
	public void createAccount(String name, String pass) {
		if (!accountExists(name)) {
			accounts.put(name, new User(name, pass));
		}
	}

}
