package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBConnection;
import entities.*;

public class NotificationsHelper 
{
	private static final int NOTIFICATION_ID = 1;
	private static final int CHECKED = 2;
	private static final int NOTIFICATION_TYPE = 3;
	
	private static final int SENDER = 2;
	private static final int RECIPIENT = 3;
	private static final int STATUS = 4;
	
	private static final int QUIZ_ID = 4;
	private static final int LINK = 5;
	private static final int SCORE = 6;
	
	private static final int NOTE = 4;
	
	public static final int PENDING = 0;
	public static final int ACCEPTED = 1;
	public static final int REJECTED = 2;
	
	public static final int CHALLENGE = 1;
	public static final int NOTE_TYPE = 2;
	

	
	private static Notification getNotificationFromRecord(ResultSet rs, int row)
	{
		Notification notification = null;
		try 
		{
			rs.absolute(row);
			int NotificationID = rs.getInt(NOTIFICATION_ID);
			boolean Checked = rs.getBoolean(CHECKED);
			int NotificationType = rs.getInt(NOTIFICATION_TYPE);
			notification = new Notification(NotificationID, Checked, NotificationType);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return notification;
	}
	
	private static FriendRequest getFriendRequestFromRecord(ResultSet rs, int row)
	{
		FriendRequest request = null;
		try 
		{
			rs.absolute(row);
			int NotificationID = rs.getInt(NOTIFICATION_ID);
			boolean Sender = rs.getBoolean(SENDER);
			int Recipient = rs.getInt(RECIPIENT);
			int Status = rs.getInt(STATUS);
			request = new FriendRequest(NotificationID, Sender, Recipient, Status);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return request;
		
	}
	
	private static Challenge getChallengeFromRecord(ResultSet rs, int row)
	{
		Challenge request = null;
		try 
		{
			rs.absolute(row);
			int NotificationID = rs.getInt(NOTIFICATION_ID);
			boolean Sender = rs.getBoolean(SENDER);
			int Recipient = rs.getInt(RECIPIENT);
			int QuizID = rs.getInt(QUIZ_ID);
			String link = rs.getString(LINK);
			double score = rs.getDouble(SCORE);
			request = new Challenge(NotificationID, Sender, Recipient, QuizID, link, score);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return request;
	}
	
	private static Note getNoteFromRecord(ResultSet rs, int row)
	{
		Note request = null;
		try 
		{
			rs.absolute(row);
			int NotificationID = rs.getInt(NOTIFICATION_ID);
			boolean Sender = rs.getBoolean(SENDER);
			int Recipient = rs.getInt(RECIPIENT);
			String note = rs.getString(NOTE);
			request = new Note(NotificationID, Sender, Recipient, note);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return request;	
	}
	
	
	public static Notification getNotification(DBConnection conn, int NotificationID)
	{
		try
		{
			String query = "SELECT * FROM Notifications WHERE NotificationID = " + NotificationID + ";";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			
			if (results.isBeforeFirst())
			{
				return getNotificationFromRecord(results, 1);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return null;
	}
	
	public static Note getNote(DBConnection conn, int NotificationID)
	{
		try
		{
			String query = "SELECT * FROM Notes WHERE NotificationID = " + NotificationID + ";";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			
			if (results.isBeforeFirst())
			{
				return getNoteFromRecord(results, 1);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return null;
	}
	
	public static ArrayList<Note> getUnreadNotes(DBConnection conn, String recipient)
	{
		ArrayList<Note> notes = new ArrayList<Note>();
		try 
		{
			String query = "SELECT * FROM Notes WHERE Recipient='" + recipient + "' AND Checked=0";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				for (int i = 1; i <= numRows; i++)
				{
					notes.add(getNoteFromRecord(results, i));
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return null;		
	}
	
	public static Challenge getChallenge(DBConnection conn, int NotificationID)
	{
		try 
		{
			String query = "SELECT * FROM Challenge WHERE NotificationID = " + NotificationID + ";";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			
			if (results.isBeforeFirst())
			{
				return getChallengeFromRecord(results, 1);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		
		return null;
	}
	
	
	public static ArrayList<String> getPendingFrendRequests(DBConnection conn, String username)
	{
		ArrayList<String> friends = new ArrayList<String>();
		try
		{
			String query = "SELECT * FROM Friends WHERE (Recipient = '" + username + "') AND Status = " + PENDING + ";";
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
					String friend = results.getString(2);
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
	
	public static ArrayList<Note> getNotesBySender(DBConnection conn, String username)
	{
		ArrayList<Note> notes = new ArrayList<Note>();
		String query = "SELECT * FROM Notes WHERE Sender = '" + username + "';";
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();

			
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				for (int i = 1; i <= numRows; i++)
				{
					Note note = getNoteFromRecord(results, i);
					notes.add(note);
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return notes;
	}
	
	public static ArrayList<Note> getNotesByRecipient(DBConnection conn, String username)
	{
		String query = "SELECT * FROM Notes WHERE Recipient = '" + username + "';";
		ArrayList<Note> notes = new ArrayList<Note>();
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();

			
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				for (int i = 1; i <= numRows; i++)
				{
					Note note = getNoteFromRecord(results, i);
					notes.add(note);
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return notes;
	}
	
	public static ArrayList<Challenge> getChallengeBySender(DBConnection conn, String username)
	{
		String query = "SELECT * FROM Challenge WHERE Sender = '" + username + "';";
		ArrayList<Challenge> challenges = new ArrayList<Challenge>();
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();

			
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				for (int i = 1; i <= numRows; i++)
				{
					Challenge note = getChallengeFromRecord(results, i);
					challenges.add(note);
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return challenges;	
	}
	
	public static ArrayList<Challenge> getChallengeByRecipient(DBConnection conn, String username)
	{
		String query = "SELECT * FROM Challenge WHERE Recipient = '" + username + "';";
		ArrayList<Challenge> challenges = new ArrayList<Challenge>();
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();

			
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				for (int i = 1; i <= numRows; i++)
				{
					Challenge note = getChallengeFromRecord(results, i);
					challenges.add(note);
				}
			}	
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return challenges;	
	}
	
	public static int addNotification(DBConnection conn, int type, boolean checked)
	{
		String query = "INSERT INTO Notifications VALUES(NULL," + checked+ ", " + type + ");";
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ps.execute();
			
			String query_id = "SELECT NotificationID FROM Notifications ORDER BY NotificationID DESC";
			ps = conn.getConnection().prepareStatement(query_id);
			ResultSet results = ps.executeQuery();
			
			if (results.isBeforeFirst())
			{
				results.absolute(1);
				return results.getInt(1);
			}

		}
		catch (SQLException ex)
		{
			System.err.println("Error occured when inserting notification into database.");
			ex.printStackTrace();	
		}
		return -1;
	}
	
	public static void addChallenge(DBConnection conn, Challenge challenge)
	{
		String sender = challenge.getSenderName();
		String recipient = challenge.getRecipient();
		int QuizID = challenge.getQuizID();
		String link = challenge.getQuizLink();
		double score = challenge.getScore();
		
		int id = addNotification(conn, CHALLENGE, false);
		
		String query = "INSERT INTO Challenge VALUES(" + id + ", '" + sender + "', '" + recipient +"'," +
							QuizID + ", '" + link +"', " + score + ");";
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ps.execute();
		}
		catch (SQLException e)
		{
			System.err.println("Error occured when inserting challenge into database.");
			e.printStackTrace();	
		}
	}
	
	public static void addFriendRequest(DBConnection conn, String sender, String recipient, int status)
	{

		String query = "INSERT INTO Friends VALUES('" + sender + "', '" + recipient +"'," +
							status + ");";
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ps.execute();
		}
		catch (SQLException e)
		{
			System.err.println("Error occured when inserting friend request into database.");
			e.printStackTrace();	
		}
	}
	
	public static void addNote(DBConnection conn, Note note)
	{
		String sender = note.getSenderName();
		String recipient = note.getRecipient();
		String message = note.getText();
		
		int id = addNotification(conn, NOTE_TYPE, false);
		
		String query = "INSERT INTO Notes VALUES(" + id + ", '" + sender + "', '" + recipient +"', '" +
							message + "');";
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ps.execute();
		}
		catch (SQLException e)
		{
			System.err.println("Error occured when inserting note into database.");
			e.printStackTrace();	
		}
	}
	
	public static void respondToFriendRequest(DBConnection conn, String Sender, String Recipient, int response)
	{
		String query = "UPDATE Friends SET Status = " + response + "WHERE Sender = '" + Sender + "' AND Recipient = '" + Recipient + "';";
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ps.execute();
		}
		catch (SQLException e)
		{
			System.err.println("Error occured when responding to friend request");
			e.printStackTrace();	
		}
	}
	
}
