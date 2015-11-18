import java.sql.ResultSet;


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
	
	private static final int PENDING = 0;
	private static final int ACCEPTED = 1;
	private static final int REJECTED = 2;
	
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
		String query = "SELECT * FROM Notifications WHERE NotificationID = " + NotificationID + ";";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet results = ps.executeQuery();
		
		if (results.isBeforeFirst())
		{
			return getNotificationFromRecord(results, 1);
		}
		
		return null;
	}
	
	public static Note getNote(DBConnection conn, int NotificationID)
	{
		String query = "SELECT * FROM Notes WHERE NotificationID = " + NotificationID + ";";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet results = ps.executeQuery();
		
		if (results.isBeforeFirst()))
		{
			return getNoteFromRecord(results, 1);
		}
		
		return null;
	}
	
	public static Challenge getChallenge(DBConnection conn, int NotificationID)
	{
		String query = "SELECT * FROM Challenge WHERE NotificationID = " + NotificationID + ";";
		preparedStatement ps = conn.prepareStatement(query);
		ResultSet results = ps.executeQuery();
		
		if (results.isBeforeFirst())
		{
			return getChallengeFromRecord(results, 1);
		}
		
		return null;
	}
	
	public static FriendRequest getFriendRequest(DBConnection conn, int NotificationID)
	{
		String query = "SELECT * FROM Friends WHERE NotificationID = " + NotificationID + ";";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet results = ps.executeQuery();
		
		if (results.isBeforeFirst())
		{
			return getFriendRequestFromRecord(results, 1);
		}
		
		return null;
	}
	
	public static ArrayList<FriendRequest> getPendingFrendRequests(DBConnection conn, String username)
	{
		String query = "SELECT * FROM Friends WHERE (Sender = '" + username + "' OR Recipient = '" + username + "') AND Status = " + PENDING + ";";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet results = ps.executeQuery();
		ArrayList<FriendRequest> friends = new ArrayList<FriendRequest>();
		
		if (results.isBeforeFirst())
		{
			ResultSet temp = results;
			temp.last();
			int numRows = temp.getRow();
			for (int i = 1; i <= numRows; i++)
			{
				FriendRequest req = getFriendRequestFromRecord(results, i);
				friends.add(req);
			}
		}
		
		return friends;
	}
	
	public static ArrayList<Note> getNotesBySender(DBConnection conn, String username)
	{
		String query = "SELECT * FROM Notes WHERE Sender = '" + username + "';";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet results = ps.executeQuery();
		ArrayList<Note> notes = new ArrayList<Note>();
		
		if (results.isBeforeFirst())
		{
			ResultSet temp = results;
			temp.last());
			int numRows = temp.getRow();
			for (int i = 1; i <= numRows; i++)
			{
				Note note = getNoteFromRecord(results, i);
				notes.add(note);
			}
		}
		return notes;
	}
	
	public static ArrayList<Note> getNotesByRecipient(DBConnection conn, String username)
	{
		String query = "SELECT * FROM Notes WHERE Recipient = '" + username + "';";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet results = ps.executeQuery();
		ArrayList<Note> notes = new ArrayList<Note>();
		
		if (results.isBeforeFirst())
		{
			ResultSet temp = results;
			temp.last());
			int numRows = temp.getRow();
			for (int i = 1; i <= numRows; i++)
			{
				Note note = getNoteFromRecord(results, i);
				notes.add(note);
			}
		}
		return notes;
	}
	
	public static ArrayList<Challenge> getChallengeBySender(DBConnection conn, String username)
	{
		String query = "SELECT * FROM Challenge WHERE Sender = '" + username + "';";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet results = ps.executeQuery();
		ArrayList<Challenge> challenges = new ArrayList<Challenge>();
		
		if (results.isBeforeFirst())
		{
			ResultSet temp = results;
			temp.last());
			int numRows = temp.getRow();
			for (int i = 1; i <= numRows; i++)
			{
				Challenge note = getChallengeFromRecord(results, i);
				challenges.add(note);
			}
		}
		return challenges;	
	}
	
	public static ArrayList<Challenge> getChallengeByRecipient(DBConnection conn, String username)
	{
		String query = "SELECT * FROM Challenge WHERE Recipient = '" + username + "';";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet results = ps.executeQuery();
		ArrayList<Challenge> challenges = new ArrayList<Challenge>();
		
		if (results.isBeforeFirst())
		{
			ResultSet temp = results;
			temp.last());
			int numRows = temp.getRow();
			for (int i = 1; i <= numRows; i++)
			{
				Challenge note = getChallengeFromRecord(results, i);
				challenges.add(note);
			}
		}
		return challenges;	
	}
}
