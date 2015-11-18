import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;


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
			String achievement = rs.getString(ACHIEVEMENT);
			achievement = new Achievement(username, achievement);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return achievement;
		
	}
	private static queryBuilder(String UserID, String Password, String Admin)
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
		String query = "SELECT * From Users WHERE UserID='" + UserID + "';";
		PreparedStatement ps = conn.prepareStatement(query);
		
		ResultSet results = ps.executeQuery();
		if (results.isBeforeFirst())
		{
			return getUserFromRecord(results,1);
		}
	}
	
	public static User getUser(DBConnection conn, String UserID, String Password, String Admin)
	{
		String query = queryBuilder(UserID, Password, Admin);
		PreparedStatement ps = conn.prepareStatement(query);
		
		ResultSet results = ps.executeQuery();
		if (results.isBeforeFirst())
		{
			User user = getUserFromRecord(results, i);
			userList.add(user);
		}
	}
	
	public static ArrayList<User> getUsers(DBConnection conn, String UserID, String Password, String Admin)
	{
		String query = queryBuilder(UserID, Password, Admin);
		PreparedStatement ps = conn.prepareStatement(query);
		ArrayList<User> userList = new ArrayList<User>();
		
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
		return userList;
	}
	
	public static ArrayList<User> getAdmins(DBConnection conn)
	{
		String query = queryBuilder("", "", "true");
		PreparedStatement ps = conn.prepareStatement(query);
		ArrayList<User> userList = new ArrayList<User>();
		
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
		
		return userList;
	}
	
	public static String getPasswordByUserID(DBConnection conn, String UserID)
	{
		User user = getUserByID(conn, UserID);
		return user.getPassword();
	}
	
	public static String getAllUserIDs(DBConnection conn)
	{
		String query = queryBuilder("", "", "true");
		PreparedStatement ps = conn.prepareStatement(query);
		ArrayList<User> userList = new ArrayList<User>();
		
		ResultSet results = ps.executeQuery();
		if (results.isBeforeFirst())
		{
			ResultSet temp = results;
			temp.last();
			int numRows = temp.getRow();
			for (int i = 1; i <= numRows; i++)
			{
				User user = getUserFromRecord(results, i);
				userList.add(user.getPassword());
			}
		}
		
		return userList;
	}
	
	public static ArrayList<Achievements> getAchievements(DBConnection conn, String username)
	{
		String query = "SELECT * FROM Achievements WHERE Username='" + Username + "';";
		PreparedStatement ps = conn.prepareStatement(query);
		ArrayList<Achievement> achList = new ArrayList<Achievement>();
		
		ResultSet results = ps.executeQuery();
		if (results.isBeforeFirst())
		{
			ResultSet temp = results;
			temp.last();
			int numRows = temp.getRow();
			for (int i = 1; i <= numRows; i++)
			{
				Achievement acheivement = getAchievementFromRecord(results, i);
				achList.add(achievement);
			}
		}
		return achList;
	}
	
	public static HashMap<String, Double> getPastQuizPerformances(DBConnection conn, String Username)
	{
		String query = "SELECT * FROM QuizzesTaken WHERE Username='" + Username + "';";
		PreparedStatement ps = conn.prepareStatement(query);
		HashMap<String, Double> map = new HashMap<String. Double>();
		
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
		return map;
	}
	
	public static ArrayList<String> getFriends(DBConnection conn, String Username)
	{
		String query = "SELECT * FROM Notifications WHERE (Sender = '" + Username + "' OR Recipient = '" + Username + "') AND Status = " + ACCEPTED + ";";
		PreparedStatement ps = conn.prepareStatement(query);
		ArrayList<String> friends = new ArrayList<String>();
		
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
		return friends;
	}
}
