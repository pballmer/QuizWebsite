
package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import entities.*;


public class QuizHelper 
{
	private static final int QUIZID = 1;
	private static final int QUESTION_ID = 2;
	private static final int QUIZ_TAKEN_ID = 1;
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
			String QuizID = rs.getString(QUIZID);
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
	
	private static String queryBuilder(String QuizID, String QuizName, String Description)
	{
		String query = "SELECT * FROM Quiz";
		boolean needAnd = false;
		
		if (!QuizID.isEmpty())
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
	
	public static Quiz getQuizByID(DBConnection conn, String QuizID)
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
	
	public static Quiz getQuiz(DBConnection conn, String QuizID, String QuizName, String Description)
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
	
	public static ArrayList<Quiz> getQuizzes(DBConnection conn, String QuizID, String QuizName, String Description)
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
	
	public static User getQuizMaker(DBConnection conn, String QuizID)
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
	
	public static ArrayList<Quiz> getQuizzesMade(DBConnection conn, String Username)
	{
		ArrayList<Quiz> quizList = new ArrayList<Quiz>();
		try
		{
			String query = "SELECT * FROM QuizzesMade WHERE Username = '" + Username + "';";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			
			ResultSet results = ps.executeQuery();
			ArrayList<Quiz> quizList = new ArrayList<Quiz>();
			
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				for (int i = 1; i <= numRows; i++)
				{
					results.absolute(i);
					String QuizID = results.getString(QUIZ_TAKEN_ID);
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
	
	public static ArrayList<Question> getQuizQuestions(DBConnection conn, String QuizID)
	{
		ArrayList<Question> questionList = new ArrayList<Question>();
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
					Question question = QuestionHelper.getQuestion(conn, questionID);
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
	
	public static HashMap<String, Double> getTopScorers(DBConnection conn, String QuizID)
	{
		HashMap<String, Double> map = new HashMap<String, Double>();
		try {
			String query = "SELECT * FROM QuizzesTaken WHERE QuizID = " + QuizID + " ORDER BY Score LIMIT 10;";
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
	
	public static ArrayList<Quiz> getQuizzesTaken(DBConnection conn, String Username)
	{
		ArrayList<Quiz> quizList = new ArrayList<Quiz>();
		try {
			String query = "SELECT * FROM QuizzesTaken WHERE Username = '" + Username + "';";
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
					String QuizID = results.getString(QUIZ_TAKEN_ID);
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
	
	public static void addQuiz(DBConnection conn, Quiz quiz)
	{
		String QuizName = quiz.getQuizname();
		String Description = quiz.getDescription();
		String command = "INSERT INTO Quiz VALUES(NULL,'" 
						+ QuizName + "', '" + Description + "');";
		try {
			PreparedStatement ps = conn.getConnection().prepareStatement(command);
			ps.executeQuery(); // TODO is this right?
		} catch (SQLException e) {
			System.err.println("Error occured when inserting user into database.");
			e.printStackTrace();
		}
	}
	
	public static void addQuizMade(DBConnection conn, Quiz quiz, String user, int Status)
	{
		String QuizID = quiz.getQuizID();
		String query = "INSERT INTO QuizzesMade VALUES('" + user + "', " 
						+ QuizID + "," + Status + ");";
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
		int QuizID = quiz.getQuizID();
		String query = "INSERT INTO QuizzesTaken VALUES('" + user + "'," + QuizID + ", " + score + ", '" + start + "', '" + end + "');"; 
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
	
	public static void addQuizQuestion(DBConnection conn, Quiz quiz, Question question)
	{
		int QuizID = quiz.getQuizID();
		String questionID = question.getQuestionID();
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
