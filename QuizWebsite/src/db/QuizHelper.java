package db;

import java.util.Date;
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
	
	public static ArrayList<Double> getScores(DBConnection conn, int QuizID, String Username)
	{
		ArrayList<Double> scores = new ArrayList<Double>();
		try
		{
			String query = "SELECT Score FROM QuizzesTaken WHERE QuizID = " + QuizID + " AND Username='" + Username + "';";
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
					scores.add(results.getDouble(1));
				}
			}
		}
		catch (SQLException ex)
		{
			
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return scores;
	}
	
	public static double getScore(DBConnection conn, int QuizID, String Username)
	{
		double score = 0;
		try
		{
			String query = "SELECT Score FROM QuizzesTaken WHERE QuizID = " + QuizID + " AND Username='" + Username + "' ORDER BY EndTime DESC;";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			
			if (results.isBeforeFirst())
			{
				results.absolute(1);
				score = results.getDouble(1);
			}
		}
		catch (SQLException ex)
		{
			
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return score;
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
	
	public static Quiz getFullQuizByID(DBConnection conn, int QuizID)
	{
		Quiz quiz = null;
		String quizName = "";
		String quizDesc = "";
		String creator = "";
		boolean random = false;
		boolean onePage = false;
		boolean autoScore = false;
		try 
		{
			String query = "SELECT * FROM Quiz WHERE QuizID=" + QuizID + ";";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			if (rs.isBeforeFirst())
			{
				rs.absolute(1);
				quizName = rs.getString(QUIZ_NAME);
				quizDesc = rs.getString(DESCRIPTION);
			}
			
			query = "SELECT * FROM QuizzesMade WHERE QuizID=" + QuizID + ";";
			ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			
			if (results.isBeforeFirst())
			{
				results.absolute(1);
				creator = rs.getString(1);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		quiz = new Quiz(QuizID, quizName, quizDesc, creator, getQuizQuestions(conn, QuizID));
		return quiz;
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
			String query = "UPDATE Quiz SET QuizName = '" + newName + "' WHERE QuizID = " + QuizID + ";";
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ps.execute();
			
		} catch (SQLException ex) {
				ex.printStackTrace();
				System.err.println("Error occured when accessing database.");
			}
	}
	
	public static void setQuizDesc(DBConnection conn, int QuizID, String newDesc)
	{
		try {
			String query = "UPDATE Quiz SET Description = '" + newDesc + "' WHERE QuizID = " + QuizID + ";";
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
	
	public static double getTopScore(DBConnection conn, int id){
		String query = "SELECT * FROM QuizzesTaken WHERE QuizID = " + id + ";";
		double currScore = -1;//sentinel
		try {
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			
			if(results.isBeforeFirst()){
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				for(int i = 1; i <= numRows; i++){
					results.absolute(i);
					if(results.getInt(3) > currScore) currScore = results.getInt(3);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.err.println("Error occured when accessing top score.");
		}
		return currScore;
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
		Quiz quiz = QuizHelper.getQuizByID(conn, id);
		if (quiz==null) return -1;
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
		Quiz quiz = QuizHelper.getQuizByID(conn, id);
		if (quiz==null) return -1;
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
	
	public static ArrayList<HashMap<String, Double>> getTopScorers(DBConnection conn, int QuizID)
	{
		ArrayList<HashMap<String, Double>> map = new ArrayList<HashMap<String, Double>>();
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
					HashMap<String, Double> tempMap = new HashMap<String, Double>();
					tempMap.put(Username, score);
					map.add(tempMap);
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
	
	public static ArrayList<Double> getAllScores(DBConnection conn, int QuizID)
	{
		ArrayList<Double> scores = new ArrayList<Double>();
		try {
			String query = "SELECT * FROM QuizzesTaken WHERE QuizID = " + QuizID + ";";
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
					scores.add(score);
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		return scores;
	}
	
	public static ArrayList<String> getTagsForQuiz(DBConnection conn, Quiz quiz){
		int QuizID = quiz.getId();
		ArrayList<String> tags = new ArrayList<String>();
		String query = "SELECT * FROM Tags WHERE QuizID = " + QuizID + ";";
		try {
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			
			if(results.isBeforeFirst()){
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				for(int i = 1; i <= numRows; i++){
					results.absolute(i);
					tags.add(results.getString(2));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.err.println("Error occured when getting tags.");
		}
		return tags;
	}
	
	public static ArrayList<Integer> getQuizIDsFromTag(DBConnection conn, String tag){
		ArrayList<Integer> quizIDs = new ArrayList<Integer>();
		String query = "SELECT * FROM Tags WHERE Tag = \"" + tag + "\";";
		try {
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();
			
			if(results.isBeforeFirst()){
				ResultSet temp = results;
				temp.last();
				int numRows = temp.getRow();
				for(int i = 1; i <= numRows; i++){
					results.absolute(i);
					quizIDs.add(results.getInt(1));
				}
			}
		} catch (SQLException ex){
			ex.printStackTrace();
			System.err.println("Error occured when getting quizzes from tag.");
		}
		return quizIDs;
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
	
	public static ArrayList<HashMap<String, Double>> getDailyTopScorers(DBConnection conn, int quizID, String today, String tomorrow)
	{
		ArrayList<HashMap<String, Double>> scores = new ArrayList<HashMap<String, Double>>();
		try {
			String query = "SELECT Username, Score FROM QuizzesTaken WHERE QuizID =" + quizID + " AND EndTime > '" + today + "' AND EndTime < '" + tomorrow + "' ORDER BY Score DESC;";
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
					HashMap<String, Double> tempMap = new HashMap<String, Double>();
					tempMap.put(results.getString(1), results.getDouble(2));
					scores.add(tempMap);
				}
			}
			
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		
		return scores;
	}
	
	public static ArrayList<HashMap<String, Double>> getRecentDailyScorers(DBConnection conn, int quizID, String today, String tomorrow)
	{
		ArrayList<HashMap<String, Double>> scores = new ArrayList<HashMap<String, Double>>();
		try {
			String query = "SELECT Username, Score FROM QuizzesTaken WHERE QuizID =" + quizID + " AND EndTime > '" + today + "' AND EndTime <'" + tomorrow + "' ORDER BY EndTime DESC;";
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
					HashMap<String, Double> tempMap = new HashMap<String, Double>();
					tempMap.put(results.getString(1), results.getDouble(2));
					scores.add(tempMap);
				}
			}
			
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.err.println("Error occured when accessing database.");
		}
		
		return scores;
	}
	
	public static int addQuiz(DBConnection conn, Quiz quiz)
	{
		String QuizName = quiz.getName();
		String Description = quiz.getDescription();
		String command = "INSERT INTO Quiz VALUES(NULL,'" 
						+ QuizName + "', '" + Description + "');";
		try {
			PreparedStatement ps = conn.getConnection().prepareStatement(command);
			ps.execute();
			// Use below to do rs.last(), increment and it should be the last question added according to TA
			Statement stmt = conn.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Quiz");
			rs.last();
			int lastID = Integer.parseInt(rs.getString("QuizID"));
			command = "INSERT INTO QuizProperties (QuizID) VALUES (" + lastID + ");";
			ps = conn.getConnection().prepareStatement(command);
			ps.execute();
			return lastID;
		} catch (SQLException e) {
			System.err.println("Error occured when inserting user into database.");
			e.printStackTrace();
		}
		//this is so it can compile
		return -1;
	}
	
	public static int addTag(DBConnection conn, int id, String tag){
		String command = "INSERT INTO Tags VALUES (" + id + ", \"" + tag + "\");";
		try {
			PreparedStatement ps = conn.getConnection().prepareStatement(command);
			ps.execute();
		} catch (SQLException e){
			System.err.println("Error occured when inserting tag into database.");
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	
	public static void addQuizMade(DBConnection conn, Quiz quiz, String user)
	{
		int QuizID = quiz.getId();
		
		String query = "INSERT INTO QuizzesMade VALUES(\"" + user + "\", " + QuizID + ", -1, NOW());";
		// TODO use real values for status and time ^
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
	
//	public static void addQuizTaken(DBConnection conn, Quiz quiz, String user, double score, String start, String end)
//	{
//		int QuizID = quiz.getId();
//		String query = "INSERT INTO QuizzesTaken VALUES(\"" + user + "\"," + QuizID + ", " + score + ", \"" + start + "\", \"" + end + "\");"; 
//		try
//		{
//			PreparedStatement ps = conn.getConnection().prepareStatement(query);
//			ps.execute();
//		}
//		catch (SQLException e)
//		{
//			System.err.println("Error occured when inserting user into database.");
//			e.printStackTrace();			
//		}
//	}
	
	//call when user starts to take a quiz
	public static void addQuizToTake(DBConnection conn, Quiz quiz, String user){ 
		int QuizID = quiz.getId();
		String command = "INSERT INTO QuizzesTaken (Username, QuizID, StartTime)"
				+ " VALUES(\"" + user + "\"," + QuizID + ", NOW());"; 
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(command);
			ps.execute();
		}
		catch (SQLException e)
		{
			System.err.println("Error occured when adding quiz to take.");
			e.printStackTrace();			
		}
	}
	
	public static void addEndTime(DBConnection conn, Quiz quiz, String user) {
		int QuizID = quiz.getId();
		Date date = new Date();
		int year = date.getYear()+1900;
		int month = date.getMonth()+1;
		int day = date.getDate();
		int hours = date.getHours();
		int minutes = date.getMinutes();
		int seconds = date.getSeconds();
		
		String command = "SELECT StartTime FROM QuizzesTaken WHERE Username='" + user + "' AND QuizID=" + QuizID + " ORDER BY StartTime DESC;";
		String time = "";
		try
		{
			PreparedStatement p = conn.getConnection().prepareStatement(command);
			ResultSet result = p.executeQuery();
			if (result.isBeforeFirst())
			{
				result.absolute(1);
				time = result.getString(1);
			}
		}
		catch (SQLException e)
		{
			System.err.println("Error occured when adding end time.");
			e.printStackTrace();			
		}
	
		String monthString = (month < 10) ? "0" + month : "" + month;
		String dayString = (day < 10) ? "0" + day : "" + day;
		String hourString = (hours < 10) ? "0" + hours : "" + hours;
		String minString = (minutes < 10) ? "0" + minutes : "" + minutes;
		String secondsString = (seconds < 10) ? "0" + seconds : "" + seconds;
		String now = year + "-" + monthString + "-" + dayString + " " + hourString + ":" + minString + ":" + secondsString;
		
		 command = "UPDATE QuizzesTaken"
				+ " SET EndTime= '" + now
				+ "' WHERE QuizID=" + QuizID + " AND Username='" + user + "' AND StartTime='" + time + "';"; 
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(command);
			ps.execute();
		}
		catch (SQLException e)
		{
			System.err.println("Error occured when adding end time.");
			e.printStackTrace();			
		}
	}
	
	public static void setScore(DBConnection conn, Quiz quiz, String user, double score) {
		int QuizID = quiz.getId();
		String command = "UPDATE QuizzesTaken SET Score=" + score
				+ " WHERE QuizID=" + QuizID + " AND Username='" + user + "';"; 
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(command);
			ps.execute();
		}
		catch (SQLException e)
		{
			System.err.println("Error occured when adding end time.");
			e.printStackTrace();			
		}
	}
	
	public static long getTimeDiff(DBConnection conn, int QuizID, String user){
		long diff = -1;
		String query = "SELECT StartTime, EndTime FROM QuizzesTaken WHERE QuizID=" + QuizID + " AND Username='" + user + "' ORDER BY EndTime DESC;"; 
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(query);
			ResultSet results = ps.executeQuery();

			results.absolute(1);
			Date before = (Date)results.getTimestamp(1);
			Date after = (Date)results.getTimestamp(2);
			diff = after.getTime() - before.getTime();
		}
		catch (SQLException e)
		{
			System.err.println("Error occured when getting time diff.");
			e.printStackTrace();			
		}
		return diff;
	}
	
	public static void addQuizQuestion(DBConnection conn, int quizID, int questionID)
	{
		String query = "INSERT INTO QuizQuestions VALUES(" + quizID + ", " + questionID + ");";
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

	public static void setProperties(DBConnection conn, Integer quizID,
			boolean isRandom, boolean onePage, boolean immediate) {
		String query = "UPDATE QuizProperties SET Random=" + isRandom + ", Page =" 
						+ onePage + ", Immediate=" + immediate + " WHERE QuizID =" + quizID + ";";
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
