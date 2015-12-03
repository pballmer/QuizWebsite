package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import entities.*;


public class QuizHelper 
{
	private static final int QUIZID = 1;
	private static final int QUESTION_ID = 2;
	private static final int QUIZ_TAKEN_ID = 2;
	private static final int QUIZ_NAME = 2;
	private static final int DESCRIPTION = 3;
	private static final int USER_ID = 1;
	private static final int SCORE = 3;
	
	public static Quiz getQuizFromRecord(ResultSet rs, int row)
	{
		Quiz quiz = null;
		try 
		{
			rs.absolute(row);
			int QuizID = rs.getInt(QUIZID);
			String QuizName = rs.getString(QUIZ_NAME);
			String Description = rs.getString(DESCRIPTION);
			quiz = new Quiz(QuizID, QuizName, Description);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return quiz;
	}
	
	private static String queryBuilder(int QuizID, String QuizName, String Description)
	{
		String query = "SELECT * FROM Quiz";
		boolean needAnd = false;
		
		if (QuizID != -1)
		{
			needAnd = true;	
			query += " WHERE QuizID=" + QuizID; 
		}
		
		if (!QuizName.isEmpty())
		{
			if (needAnd)
			{
				query += " AND ";
			} 
			else 
			{
				query += " WHERE ";
			}
			
			query += "QuizName = '" + QuizName + "'";
			needAnd = true;
		}
		
		if (!Description.isEmpty())
		{
			if (needAnd)
			{
				query += " AND ";
			}
			else
			{
				query += " WHERE ";
			}
			
			query += "Description = " + Description;
		}
		
		query += ";";
		return query;
	}
	
	public static double getScore(DBConnection conn, int QuizID, String Username)
	{
		double result = 0;
		try
		{
			String query = "SELECT Score FROM QuizzesTaken WHERE QuizID = " + QuizID + " AND Username='" + Username + "';";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			
			if (results.isBeforeFirst())
			{
				results.absolute(1);
				result = results.getDouble(1);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return result;
	}
	
	public static String getStartTime(DBConnection conn, int QuizID, String Username)
	{
		String result = "";
		try
		{
			String query = "SELECT StartTime FROM QuizzesTaken WHERE QuizID = " + QuizID + " AND Username='" + Username + "';";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			
			if (results.isBeforeFirst())
			{
				results.absolute(1);
				result = results.getString(1);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return result;
	}
	
	public static String getEndTime(DBConnection conn, int QuizID, String Username)
	{
		String result = "";
		try
		{
			String query = "SELECT EndTime FROM QuizzesTaken WHERE QuizID = " + QuizID + " AND Username='" + Username + "';";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			
			if (results.isBeforeFirst())
			{
				results.absolute(1);
				result = results.getString(1);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return result;
	}
	
	public static Quiz getQuizByID(DBConnection conn, int QuizID)
	{
		try 
		{
			String query = "SELECT * FROM Quiz WHERE QuizID=" + QuizID + ";";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			
			if (results.isBeforeFirst())
			{
				return getQuizFromRecord(results, 1);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return null;
	}
	
	public static Quiz getQuiz(DBConnection conn, int QuizID, String QuizName, String Description)
	{
		try 
		{
			String query = queryBuilder(QuizID, QuizName, Description);
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			
			if (results.isBeforeFirst())
			{
				return getQuizFromRecord(results, 1);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return null;
	}
	
	public static ArrayList<Quiz> getQuizzes(DBConnection conn, int QuizID, String QuizName, String Description)
	{
		ArrayList<Quiz> quizList = new ArrayList<Quiz>();
		try 
		{
			String query = queryBuilder(QuizID, QuizName, Description);
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			
			ResultSet results = ps.executeQuery();
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				for (int i = 1; i <= numRows; i++)
				{
					Quiz quiz = getQuizFromRecord(results, i);
					quizList.add(quiz);
				}
			}	
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return quizList;
	}
	
	public static User getQuizMaker(DBConnection conn, int QuizID)
	{
		User user = null;
		try {
			String query = "SELECT * FROM QuizzesMade WHERE QuizID = " + QuizID + ";";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
		
			ResultSet results = ps.executeQuery();
			if (results.isBeforeFirst())
			{
				results.absolute(1);
				String username = results.getString(USER_ID);
				user = UserHelper.getUserByID(conn, username);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return user;
	}
	
	public static void setQuizName(DBConnection conn, int QuizID, String newName)
	{
		try {
			String query = "UPDATE QuizzesMade SET QuizName = '" + newName + "' WHERE QuizID = " + QuizID + ";";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ps.execute();
			
		} catch (SQLException ex) {
				ex.printStackTrace();
				System.err.println("Error occured when accessing database.");
			}
	}
	
	public static ArrayList<Quiz> getPopularQuizzes(DBConnection conn, int num)
	{
		ArrayList<Quiz> quizList = new ArrayList<Quiz>();
		try
		{
			String query = "SELECT Q.QuizID, QuizName, Description FROM Quiz Q JOIN QuizzesTaken T ON T.QuizID = Q.QuizID GROUP BY Q.QuizID ORDER BY COUNT(*) DESC;";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);		
			ResultSet results = ps.executeQuery();
			
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				int total = (numRows > num) ? num: numRows;
				for (int i = 1; i <= total; i++)
				{
					results.absolute(i);
					Quiz quiz = getQuizFromRecord(results, i);
					quizList.add(quiz);
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return quizList;
	}
	
	public static ArrayList<Quiz> getRecentQuizzes(DBConnection conn, int num)
	{
		ArrayList<Quiz> quizList = new ArrayList<Quiz>();
		try
		{
			String query = "SELECT * FROM Quiz ORDER BY QuizID DESC;";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);		
			ResultSet results = ps.executeQuery();
			
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				int total = (numRows > num) ? num: numRows;
				for (int i = 1; i <= total; i++)
				{
					results.absolute(i);
					Quiz quiz = getQuizFromRecord(results, i);
					quizList.add(quiz);
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return quizList;
	}
	
	public static int getTotalNumQuizzes(DBConnection conn)
	{
		int num = 0; 
		try
		{
			String query = "SELECT COUNT(*) FROM Quiz";
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
	
	public static int removeQuizHistory(DBConnection conn, int id)
	{
		String query = "DELETE FROM Quiz WHERE QuizId=" + id + ";";
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ps.execute();
			
			query = "DELETE FROM QuizzesTaken WHERE QuizID=" + id + ";";
			ps = conn.getConnection().prepareStatement(query);
			ps.execute();
			
			query = "DELETE FROM QuizzesMade WHERE QuizID=" + id + ";";
			ps = conn.getConnection().prepareStatement(query);
			ps.execute();
		}
		catch (SQLException e) {
			System.err.println("Error occured when inserting user into database.");
			e.printStackTrace();
			return -1;
		}
	
		return 1;
	}
	
	public static int removeQuiz(DBConnection conn, int id)
	{
		String query = "DELETE FROM Quiz WHERE QuizId=" + id + ";";
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ps.execute();
		}
		catch (SQLException e) {
			System.err.println("Error occured when inserting user into database.");
			e.printStackTrace();
			return -1;
		}
	
	return 1;
	}
	
	public static ArrayList<Quiz> getQuizzesMade(DBConnection conn, String Username, int num)
	{
		ArrayList<Quiz> quizList = new ArrayList<Quiz>();
		ArrayList<Integer> idList = new ArrayList<Integer>();
		try
		{
			String query = "SELECT M.QuizID FROM Quiz Q JOIN QuizzesMade M ON M.Username='" + Username +"' GROUP BY QuizID;";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			
			ResultSet results = ps.executeQuery();
	
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				int total = (numRows > num && num != -1) ? num: numRows;
				for (int i = 1; i <= total; i++)
				{
					results.absolute(i);
					idList.add(results.getInt(1));
				}
			}
			
			for (int i = 0; i < idList.size(); i++)
			{
				String second_query = "SELECT * FROM Quiz WHERE QuizID = " + idList.get(i) + ";";
				ps = conn.getConnection().prepareStatement(second_query);
				
				ResultSet quizzes = ps.executeQuery();
				if (quizzes.isBeforeFirst())
				{
					quizzes.absolute(1);
					Quiz quiz = getQuizFromRecord(quizzes, 1);
					quizList.add(quiz);
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		
		return quizList;
	}
	
	public static ArrayList<QuestionAbstract> getQuizQuestions(DBConnection conn, int QuizID)
	{
		ArrayList<QuestionAbstract> questionList = new ArrayList<QuestionAbstract>();
		try
		{
			String query = "SELECT * FROM QuizQuestions WHERE QuizID = " + QuizID + ";";
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
					String questionID = results.getString(QUESTION_ID);
					QuestionAbstract question = QuestionHelper.getQuestion(conn, questionID);
					questionList.add(question);
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return questionList;
	}
	
	public static HashMap<String, Double> getTopScorers(DBConnection conn, int QuizID)
	{
		HashMap<String, Double> map = new HashMap<String, Double>();
		try {
			String query = "SELECT * FROM QuizzesTaken WHERE QuizID = " + QuizID + " ORDER BY Score;";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			
			ResultSet results = ps.executeQuery();
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				int total = (numRows > 10) ? 10 : numRows;
				for (int i = 1; i <= total; i++)
				{
					results.absolute(i);
					double score = results.getDouble(SCORE);
					String Username = results.getString(USER_ID);
					map.put(Username, score);
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
	
	public static ArrayList<Quiz> getQuizzesTaken(DBConnection conn, String Username, int num)
	{
		ArrayList<Integer> idList = new ArrayList<Integer>();
		ArrayList<Quiz> quizList = new ArrayList<Quiz>();
		try {
			String query = "SELECT T.QuizID FROM Quiz Q JOIN QuizzesTaken T ON T.Username='" + Username + "' GROUP BY QuizID;";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			
			ResultSet results = ps.executeQuery();
	
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				int total = (numRows > num && num != -1) ? num: numRows;
				for (int i = 1; i <= total; i++)
				{
					results.absolute(i);
					idList.add(results.getInt(1));
				}
			}
			
			for (int i = 0; i < idList.size(); i++)
			{
				String second_query = "SELECT * FROM Quiz WHERE QuizID = " + idList.get(i) + ";";
				ps = conn.getConnection().prepareStatement(second_query);
				
				ResultSet quizzes = ps.executeQuery();
				if (quizzes.isBeforeFirst())
				{
					quizzes.absolute(1);
					Quiz quiz = getQuizFromRecord(quizzes, 1);
					quizList.add(quiz);
				}
			}
			
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		
		return quizList;
	}
	
	public static int addQuiz(DBConnection conn, Quiz quiz)
	{
		String QuizName = quiz.getName();
		String Description = quiz.getDescription();
		String command = "INSERT INTO Quiz VALUES(NULL,'" 
						+ QuizName + "', '" + Description + "');";
		try {
			PreparedStatement ps = conn.getConnection().prepareStatement(command);
			ps.executeQuery(); // TODO is this right?
			// Use below to do rs.last(), increment and it should be the last question added according to TA
			Statement stmt = conn.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Quiz");
			rs.last();
			int lastID = Integer.parseInt(rs.getString("QuizID"));
			return lastID;
		} catch (SQLException e) {
			System.err.println("Error occured when inserting user into database.");
			e.printStackTrace();
		}
		//this is so it can compile
		return -1;
	}
	
	public static void addQuizMade(DBConnection conn, Quiz quiz, String user)
	{
		int QuizID = quiz.getId();
		String query = "INSERT INTO QuizzesMade VALUES(\"" + user + "\", " + QuizID + ");";
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ps.executeQuery();
		}
		catch (SQLException e)
		{
			System.err.println("Error occured when inserting user into database.");
			e.printStackTrace();
		}
	}
	
	public static void addQuizTaken(DBConnection conn, Quiz quiz, String user, double score, String start, String end)
	{
		int QuizID = quiz.getId();
		String query = "INSERT INTO QuizzesTaken VALUES(\"" + user + "\"," + QuizID + ", " + score + ", \"" + start + "\", \"" + end + "\");"; 
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ps.execute();
		}
		catch (SQLException e)
		{
			System.err.println("Error occured when inserting user into database.");
			e.printStackTrace();			
		}
	}
	
	public static void addQuizQuestion(DBConnection conn, Quiz quiz, QuestionAbstract question)
	{
		int QuizID = quiz.getId();
		String questionID = Integer.toString(question.getQuestionID());
		String query = "INSERT INTO QuizQuestion VALUES(" + QuizID + ", " + questionID + ");";
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ps.execute();
		}
		catch (SQLException e)
		{
			System.err.println("Error occured when inserting user into database.");
			e.printStackTrace();	
		}
	}
	
	
}
