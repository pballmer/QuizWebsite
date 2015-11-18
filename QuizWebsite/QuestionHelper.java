import java.sql.PreparedStatement;
import java.sql.ResultSet;


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
		String query = "SELECT * From Question WHERE QuestionID = " + QuestionID + ";";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet results = ps.executeQuery();
		
		if (results.isBeforeFirst())
		{
			return getQuestionFromRecord(results, 1, conn);
		}
	}
	
	public static ArrayList<Question> getAllQuestionsOfType(DBConnection conn, String QuestionType)
	{
		String query = "SELECT * FROM Question WHERE QuestionType = " + QuestionType + ";";
		PreparedStatement ps = conn.prepareStatement(query);
		ArrayList<Question> questions = new ArrayList<Questions>();
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
		
		return questions;
	}
	
	public static ArrayList<String> getAnswers(DBConnection conn, String QuestionID)
	{
		ArrayList<String> answers = new ArrayList<String> answers;
		String query = "SELECT * FROM Answers WHERE QuestionID = " + QuestionID + ";";
		PreparedStatement ps = conn.prepareStatement(query);
		
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
		return answers;
	}
	
	public static ArrayList<String> getQuestionOptions(DBConnection conn, String QuestionID)
	{
		String query = "SELECT * FROM Question WHERE QuestionID =" + QuestionID + ";";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet results = ps.executeQuery();
		Question question = null;
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
					String query = "SELECT * FROM MultipleChoice WHERE QuestionID = " + QuestionID + ";";
					return getOptions(conn, query);
					break;
				case QUESTION_RESPONSE:
					String query = "SELECT * FROM QuestionReponse WHERE QuestionID = " + QuestionID + ";";
					return getOptions(conn, query);
					break;
				case FILL_IN_BLANK:
					String query = "SELECT * FROM FillInBlank WHERE QuestionID = " + QuestionID + ";";
					return getOptions(conn, query);
					break;
				case PICTURE_RESPONSE:
					String query = "SELECT * FROM PictureResponse WHERE QuestionID = " + QuestionID + ";";
					return getOptions(conn, query);
				default: break;
			}
		}
		return new ArrayList<String>();
	}
	
	private static ArrayList<String> getOptions(DBConnection conn, String query)
	{
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet results = ps.executeQuery();
		ArrayList<String> options = new ArrayList<String>();
		
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

}
