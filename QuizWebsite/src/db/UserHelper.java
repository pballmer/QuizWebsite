package db;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import entities.Quiz;
import entities.User;

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
			user = new User(username, password, admin);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return user;
	}
	
	private static Achievement getAchievementFromRecord(ResultSet rs, int row)
	{
		Achievement achievement = null;
		try 
		{
			rs.absolute(row);
			String username = rs.getString(USERNAME);
			String achieve = rs.getString(ACHIEVEMENT);
			achievement = new Achievement(username, achieve);
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
			String query = "SELECT * From Users WHERE UserID='" + UserID + "';";
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
	
	public static ArrayList<Achievements> getAchievements(DBConnection conn, String username)
	{
		ArrayList<Achievement> achList = new ArrayList<Achievement>();
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
					Achievement achievement = getAchievementFromRecord(results, i);
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
	
	public static Set<User> getFriends(DBConnection conn, String Username)
	{
		Set<User> friends = new HashSet<User>();
		try
		{
			String query = "SELECT * FROM Notifications WHERE (Sender = '" + Username + "' OR Recipient = '" + Username + "') AND Status = " + ACCEPTED + ";";
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
					User friend = getUserByID(conn, friendname);
					friends.add(friend);
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
	
	public static void addUser(DBConnection conn, User user) {
		String name = user.getUsername();
		String pass = user.getPassword();
		boolean admin = user.isAdmin();
		String command = "INSERT INTO Users VALUES(\"" + name + "\",\"" 
						+ pass + "\"," + admin + ");";
		try {
			PreparedStatement ps = conn.getConnection().prepareStatement(command);
			ps.executeQuery(); // TODO is this right?
		} catch (SQLException e) {
			System.err.println("Error occured when inserting user into database.");
			e.printStackTrace();
		}
	}
	
	
}
