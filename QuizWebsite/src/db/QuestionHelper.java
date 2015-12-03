package db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import entities.QuestionAbstract;

import db.DBConnection;

import entities.*;


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
	
	
	//was this meant to get a question from 
	public static QuestionAbstract getQuestionFromRecord(ResultSet rs, int row, DBConnection conn)
	{
		QuestionAbstract question = null;
		try 
		{
			rs.absolute(row);
			
			//changes from QUIZ_NAME to QUestion_ID etc.
			String QuestionID = rs.getString("QUESTION_ID");
			String QuestionType = rs.getString("QUESTION_TYPE");
			ArrayList<String> answers = getAnswers(conn, QuestionID);
			ArrayList<String> options = getQuestionOptions(conn, QuestionID);
			//where is above quiz ID coming from and need to get question string
			//why isn't this instantiating?
			question = new QuestionAbstract(QuestionID, options.get(0), answers, QuestionType, options);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return question;
	}
	
	//gets a questionAbstract using the ID and calls getQuestioFromRecord to get the questionAbstract object form of question
	public static QuestionAbstract getQuestion(DBConnection conn, String QuestionID)
	{
		try
		{
			String query = "SELECT * From Question WHERE QuestionID = " + QuestionID + ";";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			
			//this checks if the results is populated by at least one item
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
	
	
	//Question: do we need this function? 
	public static ArrayList<QuestionAbstract> getAllQuestionsOfType(DBConnection conn, String QuestionType)
	{
		ArrayList<QuestionAbstract> questions = new ArrayList<QuestionAbstract>();
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
					QuestionAbstract question = new QuestionAbstract(QuestionID, quizID, questionString, answers, questionType);
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
	
	//returns the arraylist of the answers
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
	
	//since all question types except MC have their question text in their database, 
	//this will simply return question options size 1 for these types
	public static ArrayList<String> getQuestionOptions(DBConnection conn, String QuestionID)
	{
		QuestionAbstract question = null;
		
		try {
			String quesQuery = "SELECT * FROM Question WHERE QuestionID =" + QuestionID + ";";
			PreparedStatement ps = conn.getConnection().prepareStatement(quesQuery);
			ResultSet results = ps.executeQuery();
			if (results.isBeforeFirst())
			{
				question = getQuestionFromRecord(results, 1, conn);
			}
			
			if (question != null)
			{
				int QuestionType = question.getType();
				switch (QuestionType)
				{
					case MULTIPLE_CHOICE:
						String multQuery = "SELECT * FROM MultipleChoice WHERE QuestionID = " + QuestionID + ";";
						return getOptions(conn, multQuery);
					case QUESTION_RESPONSE:
						String respQuery = "SELECT * FROM QuestionReponse WHERE QuestionID = " + QuestionID + ";";
						return getOptions(conn, respQuery);
					case FILL_IN_BLANK:
						String fillQuery = "SELECT * FROM FillInBlank WHERE QuestionID = " + QuestionID + ";";
						return getOptions(conn, fillQuery);
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
	
	//returns the 'options' for a question. For every question except MC's this will be an arraylist of size 1 where
	//the 0 index will contain the question text
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
	//will return int later OR we will change it so it takes the ID as a parameter
	public static void addQuestion(DBConnection conn, int type)
	{
		String query = "INSERT INTO Question VALUES(NULL," + type + ");";
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ps.executeQuery();
			/* Use below to do rs.last(), increment and it should be the last question added according to TA
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection
					( "jdbc:mysql://" + server, account ,password);

			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT * FROM metropolises");
			*/
		}
		catch (SQLException ex)
		{
			System.err.println("Error occured when inserting user into database.");
			ex.printStackTrace();	
		}
	}
	
	//adds the arraylist of answers to the question
	public static void addAnswers(DBConnection conn, int id, ArrayList<String> answers)
	{
		try
		{
			for (int i = 0; i < answers.size(); i++)
			{
				String query = "INSERT INTO Answers VALUES(" + id + ", '" + answers.get(i) + "');";
				PreparedStatement ps = conn.getConnection().prepareStatement(query);
				ps.executeQuery();
			}
		}
		catch (SQLException ex)
		{
			System.err.println("Error occured when inserting user into database.");
			ex.printStackTrace();	
		}
	}

	public static void addFillBlank(DBConnection conn, FillBlank question)
	{

		int id = question.getQuestionID();
		String text = question.getQuestion();
		ArrayList<String> answers = question.getAnswers();
		
		addQuestion(conn, FILL_IN_BLANK);
		addAnswers(conn, id, answers);

		try
		{
			String query = "INSERT INTO FillInBlank VALUES(" + id + ", '" + text + "');";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ps.executeQuery();
		}
		catch (SQLException ex)
		{
			System.err.println("Error occured when inserting user into database.");
			ex.printStackTrace();	
		}
	}
	
	public static void addMultipleChoice(DBConnection conn, MultipleChoice question)
	{
		addQuestion(conn, MULTIPLE_CHOICE);
		int id = question.getQuestionID();
		//the first option here is the question
		ArrayList<String> options = question.getOptions();
		ArrayList<String> answers = question.getAnswers();
		addAnswers(conn, id, answers);
		
		try
		{
			
			for (int i = 0; i < options.size(); i++)
			{
				String query = "INSERT INTO FillInBlank|MultipleChoice VALUES(" + id + ", '" + options.get(i) + "');";
				PreparedStatement ps = conn.getConnection().prepareStatement(query);
				ps.executeQuery();				
			}

		}
		catch (SQLException ex)
		{
			System.err.println("Error occured when inserting user into database.");
			ex.printStackTrace();	
		}
	}
	
	public static void addPictureResponse(DBConnection conn, PictureResponse question)
	{
		addQuestion(conn, PICTURE_RESPONSE);
		int id = question.getQuestionID();
		String text = question.getQuestion();
		ArrayList<String> answers = question.getAnswers();
		addAnswers(conn, id, answers);
		try
		{
			
			String query = "INSERT INTO PictureResponse VALUES(" + id + ", '" + text + "');";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ps.executeQuery();	


		}
		catch (SQLException ex)
		{
			System.err.println("Error occured when inserting user into database.");
			ex.printStackTrace();	
		}
	}	
	
	public static void addQuestionResponse(DBConnection conn, QuestionResponse question)
	{
		addQuestion(conn, QUESTION_RESPONSE);
		int id = question.getQuestionID();
		String text = question.getQuestion();
		ArrayList<String> answers = question.getAnswers();
		addAnswers(conn, id, answers);
		
		try
		{
			String query = "INSERT INTO QuestionResponse VALUES(" + id + ", '" + text + "');";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ps.executeQuery();				
		}
		catch (SQLException ex)
		{
			System.err.println("Error occured when inserting user into database.");
			ex.printStackTrace();	
		}
	}	
}
