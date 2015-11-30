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
	
	/**
	 * Creates a new account with the requested username and password
	 * @param name Desired username
	 * @param pass Desired password (unencrypted)
	 * @return User object for the new profile or null if account existed
	 */
	public User createAccount(String name, String pass) {
		User newUser = null;
		if (!accountExists(name)) {
			newUser = new User(name, pass);
			accounts.put(name, newUser);
		}
		return newUser;
	}

}
