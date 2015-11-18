package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class QuestionHelper
{
	private static final int QUESTION_ID = 1;
	private static final int QUESTION_TYPE = 2;
	private static final int ANSWER = 2;
	private static final int OPTIONS = 2;
	
	private static final int MULTIPLE_CHOICE = 0;
	private static final int QUESTION_RESPONSE = 1;
	private static final int FILL_IN_BLANK = 2;
	private static final int PICTURE_RESPONSE = 3;
	
	public static Question getQuestionFromRecord(ResultSet rs, int row, DBConnection conn)
	{
		Question question = null;
		try 
		{
			rs.absolute(row);
			String QuestionID = rs.getString(QUIZID);
			String QuestionType = rs.getString(QUIZ_NAME);
			ArrayList<String> answers = getAnswers(conn, QuestionID);
			question = new Question(QuestionID, QuestionID, answers);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return question;
	}
	
	public static Question getQuestion(DBConnection conn, String QuestionID)
	{
		try
		{
			String query = "SELECT * From Question WHERE QuestionID = " + QuestionID + ";";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			
			if (results.isBeforeFirst())
			{
				return getQuestionFromRecord(results, 1, conn);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return null;
	}
	
	public static ArrayList<Question> getAllQuestionsOfType(DBConnection conn, String QuestionType)
	{
		ArrayList<Question> questions = new ArrayList<Question>();
		try 
		{
			String query = "SELECT * FROM Question WHERE QuestionType = " + QuestionType + ";";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			
			if (results.isBeforeFirst())
			{
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				for (int i =1; i <= numRows; i++)
				{
					results.absolute(i);
					String QuestionID = results.getString(QUESTION_ID);
					ArrayList<String> answers = getAnswers(conn, QuestionID);
					Question question = new Question(QuestionID, QuestionType, answers);
					questions.add(question);
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		
		return questions;
	}
	
	public static ArrayList<String> getAnswers(DBConnection conn, String QuestionID)
	{
		ArrayList<String> answers = new ArrayList<String>();
		try {
			String query = "SELECT * FROM Answers WHERE QuestionID = " + QuestionID + ";";
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
					String answer = results.getString(ANSWER);
					answers.add(answer);
				}
			}	
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return answers;
	}
	
	public static ArrayList<String> getQuestionOptions(DBConnection conn, String QuestionID)
	{
		Question question = null;
		
		try {
			String quesQuery = "SELECT * FROM Question WHERE QuestionID =" + QuestionID + ";";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			if (results.isBeforeFirst())
			{
				question = getQuestionFromRecord(results, 1, conn);
			}
			
			if (question != null)
			{
				int QuestionType = Integer.parseInt(question.getQuestionType());
				switch (QuestionType)
				{
					case MULTIPLE_CHOICE:
						String multQuery = "SELECT * FROM MultipleChoice WHERE QuestionID = " + QuestionID + ";";
						return getOptions(conn, multQuery);
						break;
					case QUESTION_RESPONSE:
						String respQuery = "SELECT * FROM QuestionReponse WHERE QuestionID = " + QuestionID + ";";
						return getOptions(conn, respQuery);
						break;
					case FILL_IN_BLANK:
						String fillQuery = "SELECT * FROM FillInBlank WHERE QuestionID = " + QuestionID + ";";
						return getOptions(conn, fillQuery);
						break;
					case PICTURE_RESPONSE:
						String picQuery = "SELECT * FROM PictureResponse WHERE QuestionID = " + QuestionID + ";";
						return getOptions(conn, picQuery);
					default: break;
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return new ArrayList<String>();
	}
	
	private static ArrayList<String> getOptions(DBConnection conn, String query)
	{
		ArrayList<String> options = new ArrayList<String>();
		try {
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
					String option = results.getString(OPTIONS);
					options.add(option);
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return options;
	}

}