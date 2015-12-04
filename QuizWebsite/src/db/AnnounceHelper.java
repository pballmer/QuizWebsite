package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnnounceHelper 
{
	
	private static final int TEXT = 2;

	public static ArrayList<String> getAnnouncements(DBConnection conn, int num)
	{
		ArrayList<String> announcements = new ArrayList<String>();
		try
		{
			String query = "SELECT * FROM Announcement ORDER BY AnnouncementID DESC;";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				int total = (numRows > num) ? num : numRows;
				for (int i =1; i <= total; i++)
				{
					results.absolute(i);
					announcements.add(results.getString(TEXT));
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return announcements;
	}
	
	
	public static int addAnnouncement(DBConnection conn, String text)
	{
		String query = "INSERT INTO Announcement VALUES(NULL, \"" + text + "\");";
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ps.execute();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when adding announcement.");
			return -1;
		}
		return 1;
	}
	
	public static String getAnnouncement(DBConnection conn, int ID)
	{
		String text = "";
		try
		{
			String query = "SELECT Text FROM Announcement WHERE AnnouncementID = " + ID + ");";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			if (results.isBeforeFirst())
			{
				results.absolute(1);
				text = results.getString(TEXT);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return text;
	}
}
