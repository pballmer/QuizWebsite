import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.User;


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
		
		if (!UserID.isEmpty())
		{
			needAnd = true;	
			query += " WHERE QuizID=" + QuizID; 
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
			
			query += "QuizName = '" + QuizName + "'";
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
			
			query += "Description = " + Description;
		}
		
		query += ";";
		return query;
	}
	
	public static Quiz getQuizByID(DBConnection conn, String QuizID)
	{
		String query = "SELECT * FROM Quiz WHERE QuizID=" + QuizID + ";";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet results = ps.executeQuery();
		
		if (results.isBeforeFirst())
		{
			return getQuizFromRecord(results, 1);
		}
	}
	
	public static Quiz getQuiz(DBConnection conn, String QuizID, String QuizName, String Description)
	{
		String query = queryBuilder(QuizID, QuizName, Description);
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet results = ps.executeQuery();
		
		if (results.isBeforeFirst())
		{
			return getQuizFromRecord(results, 1);
		}
	}
	
	public static ArrayList<Quiz> getQuizzes(DBConnection conn, String QuizID, String QuizName, String Description)
	{
		String query = queryBuilder(QuizID, QuizName, Description);
		PreparedStatement ps = conn.prepareStatement(query);
		ArrayList<User> quizList = new ArrayList<User>();
		
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
		return quizList;
	}
	
	public static User getQuizMaker(DBConnection conn, String QuizID)
	{
		String query = "SELECT * FROM QuizzesMade WHERE QuizID = " + QuizID + ";";
		PreparedStatement ps = conn.prepareStatement(query);
	
		ResultSet results = ps.executeQuery();
		User user = null;
		if (results.isBeforeFirst())
		{
			try
			{
				results.absolute(1);
				String username = results.getString(USER_ID);
				user = UserHelper.getUserByID(conn, username);
			}
			catch (SQLException ex)
			{
				ex.printStackTrace();
				System.err.println("Error occured when accessing database.");
			}
		}
		return user;
	}
	
	public static ArrayList<Quiz> getQuizzesMade(DBConnection conn, String Username)
	{
		String query = "SELECT * FROM QuizzesMade WHERE Username = '" + Username + "';";
		PreparedStatement ps = conn.prepareStatement(query);
		
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
		
		return quizList;
	}
	
	public static ArrayList<Question> getQuizQuestions(DBConnection conn, String QuizID)
	{
		String query = "SELECT * FROM QuizQuestions WHERE QuizID = " + QuizID + ";";
		PreparedStatement ps = conn.prepareStatement(query);
		
		ResultSet results = ps.executeQuery();
		ArrayList<Question> questionList = new ArrayList<Question>();
		
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
		return questionList;
	}
	
	public static HashMap<String, Double> getTopScorers(DBConnection conn, String QuizID)
	{
		String query = "SELECT * FROM QuizzesTaken WHERE QuizID = " + QuizID + " ORDER BY Score LIMIT 10;";
		PreparedStatement ps = conn.prepareStatement(query);
		
		ResultSet results = ps.UseruteQuery();
		HashMap<String, Double> map = new HashMap<String, Double>();
		
		if (results.isBeforeAfter())
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
		return map;
	}
	
	public static ArrayList<Quiz> getQuizzesTaken(DBConnection conn, String Username)
	{
		String query = "SELECT * FROM QuizzesTaken WHERE Username = '" + Username + "';";
		PreparedStatement ps = conn.prepareStatement(query);
		
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
		
		return quizList;
	}
}
