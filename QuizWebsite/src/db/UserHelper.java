package db;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import entities.*;

public class UserHelper 
{
	private static final int USERNAME = 1;
	private static final int ACHIEVEMENT = 2;
	private static final int PASSWORD = 2;
	private static final int ADMIN = 3;
	private static final int FIRST_ROW = 1;
	private static final int QUIZ_ID = 2;
	private static final int SCORE = 3;
	private static final int ACCEPTED = 1;
	private static final int SENDER = 2;
	private static final int RECIPIENT = 3;
	
	public static User getUserFromRecord(ResultSet rs, int row)
	{
		User user = null;
		try 
		{
			rs.absolute(row);
			String username = rs.getString(USERNAME);
			String password = rs.getString(PASSWORD);
			boolean admin = rs.getBoolean(ADMIN);
			user = new User(username, password, admin, true);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return user;
	}
	
	public static int getTotalNumUsers(DBConnection conn)
	{
		int num = 0; 
		try
		{
			String query = "SELECT COUNT(*) FROM Users";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);		
			ResultSet results = ps.executeQuery();
			
			if (results.isBeforeFirst())
			{
				results.absolute(1);
				num = results.getInt(1);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return num;
	}
	
	private static String getAchievementFromRecord(ResultSet rs, int row)
	{
		String achievement = "";
		try 
		{
			rs.absolute(row);
			achievement = rs.getString(ACHIEVEMENT);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return achievement;
		
	}
	private static String queryBuilder(String UserID, String Password, String Admin)
	{
		String query = "SELECT * FROM Users";
		boolean needAnd = false;
		
		if (!UserID.isEmpty())
		{
			needAnd = true;	
			query += " WHERE Username='" + UserID + "'"; 
		}
		
		if (!Password.isEmpty())
		{
			if (needAnd)
			{
				query += " AND ";
			} 
			else 
			{
				query += " WHERE ";
			}
			
			query += "Password = '" + Password + "'";
			needAnd = true;
		}
		
		if (!Admin.isEmpty())
		{
			if (needAnd)
			{
				query += " AND ";
			}
			else
			{
				query += " WHERE ";
			}
			
			query += "Admin = " + Admin;
		}
		
		query += ";";
		return query;
	}
	
	public static User getUserByID(DBConnection conn, String UserID)
	{
		try
		{
			String query = "SELECT * From Users WHERE Username='" + UserID + "';";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			
			ResultSet results = ps.executeQuery();
			if (results.isBeforeFirst())
			{
				return getUserFromRecord(results,1);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return null;
	}
	
	// TODO purpose of this method?
	public static User getUser(DBConnection conn, String UserID, String Password, String Admin)
	{
		try
		{
			String query = queryBuilder(UserID, Password, Admin);
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			
			ResultSet results = ps.executeQuery();
			if (results.isBeforeFirst())
			{
				return getUserFromRecord(results, 1);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		
		return null;
	}
	
	public static ArrayList<User> getUsers(DBConnection conn, String UserID, String Password, String Admin)
	{
		ArrayList<User> userList = new ArrayList<User>();
		try
		{
			String query = queryBuilder(UserID, Password, Admin);
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			
			ResultSet results = ps.executeQuery();
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				for (int i = 1; i <= numRows; i++)
				{
					User user = getUserFromRecord(results, i);
					userList.add(user);
				}
			}	
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return userList;
	}
	
	// TODO seems like we would use the above method, passing in "", "", true
	public static ArrayList<User> getAdmins(DBConnection conn)
	{
		ArrayList<User> userList = new ArrayList<User>();
		try
		{
			String query = queryBuilder("", "", "true");
			PreparedStatement ps = conn.getConnection().prepareStatement(query);

			ResultSet results = ps.executeQuery();
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				for (int i = 1; i <= numRows; i++)
				{
					User user = getUserFromRecord(results, i);
					userList.add(user);
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		
		return userList;
	}
	
	public static String getPasswordByUserID(DBConnection conn, String UserID)
	{
		User user = getUserByID(conn, UserID);
		return user.getPassword();
	}
	
	//AL<String>?
	public static ArrayList<String> getAllUserIDs(DBConnection conn)
	{
		ArrayList<String> userList = new ArrayList<String>();
		try
		{
			String query = queryBuilder("", "", "true");
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			
			ResultSet results = ps.executeQuery();
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				for (int i = 1; i <= numRows; i++)
				{
					User user = getUserFromRecord(results, i);
					userList.add(user.getUsername()); // TODO getUsername?
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		
		return userList;
	}
	
	public static ArrayList<String> getAchievements(DBConnection conn, String username)
	{
		ArrayList<String> achList = new ArrayList<String>();
		try
		{
			String query = "SELECT * FROM Achievements WHERE Username='" + username + "';";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			
			ResultSet results = ps.executeQuery();
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				for (int i = 1; i <= numRows; i++)
				{
					String achievement = getAchievementFromRecord(results, i);
					achList.add(achievement);
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return achList;
	}
	
	public static void addAchievement(DBConnection conn, String ach, String username){
		String command = "INSERT INTO Achievements VALUES(\"" + username + "\",\"" + ach + "\");";
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(command);
			ps.execute();
		} catch (SQLException e) {
			System.err.println("Error occured when inserting achievement into database.");
			e.printStackTrace();
		}
	}
	
	public static HashMap<String, Double> getPastQuizPerformances(DBConnection conn, String Username)
	{
		HashMap<String, Double> map = new HashMap<String, Double>();
		
		try
			{
			String query = "SELECT * FROM QuizzesTaken WHERE Username='" + Username + "';";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			
			ResultSet results = ps.executeQuery();
			
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				for (int i = 1; i <= numRows; i++)
				{
					results.absolute(i);
					String QuizID = results.getString(QUIZ_ID);
					double score = results.getDouble(SCORE);
					map.put(QuizID, score);
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return map;
	}
	
	public static ArrayList<String> getFriends(DBConnection conn, String Username)
	{
		ArrayList<String> friends = new ArrayList<String>();
		try
		{
			String query = "SELECT * FROM Friends WHERE (Sender = '" + Username + "' OR Recipient = '" + Username + "') AND Status = " + ACCEPTED + ";";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			
			ResultSet results = ps.executeQuery();
			
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				for (int i = 1; i <= numRows; i++)
				{
					results.absolute(i);
					String sender = results.getString(SENDER);
					String recipient = results.getString(RECIPIENT);
					String friendname = (Username.equals(sender)) ? recipient : sender;
					friends.add(friendname);
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return friends;
	}
	
	public static int addUser(DBConnection conn, User user) {
		String name = user.getUsername();
		String pass = user.getPassword();
		boolean admin = user.isAdmin();
		String command = "INSERT INTO Users VALUES(\"" + name + "\",\"" 
						+ pass + "\"," + admin + ");";
		try {
			PreparedStatement ps = conn.getConnection().prepareStatement(command);
			ps.execute();
		} catch (SQLException e) {
			System.err.println("Error occured when inserting user into database.");
			e.printStackTrace();
			return -1;
		}
		
		return 1;
	}
	
	public static int makeAdmin(DBConnection conn, String username)
	{
		String query = "UPDATE Users SET Admin=1 WHERE Username='" + username + "';";
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ps.execute();
		}
		catch (SQLException e) {
			System.err.println("Error occured when updating user in database.");
			e.printStackTrace();
			return -1;
		}
	
	return 1;	
		
	}
	public static int removeUser(DBConnection conn, String username)
	{
		String query = "DELETE FROM Users WHERE Username='" + username + "';";
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ps.execute();
		}
		catch (SQLException e) {
			System.err.println("Error occured when deleting user into database.");
			e.printStackTrace();
			return -1;
		}
	
	return 1;
	}
	
	
}
