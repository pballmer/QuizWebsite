package entities;

import db.QuestionHelper;

import java.util.ArrayList;

import db.DBConnection;

public abstract class QuestionAbstract {
	

	/*Per Peter's seemingly nonsensical request, instead of String, types will be in int
	 * 0: Multiple Choice (MC)
	 * 1:Fill in the Blank (FB)
	 * 2:Picture Response (PR)
	 * 3:Question Response (QR)
	 * */
	static final int MULTIPLE_CHOICE = 0;
	protected static final int QUESTION_RESPONSE = 1;
	static final int FILL_IN_BLANK = 2;
	static final int PICTURE_RESPONSE = 3;	
	private int questionID;
	private int quizID;
	public int type;
	public String question;
	public ArrayList<String> answers = new ArrayList<String>(); 
	
	
	public QuestionAbstract(int questionID, int quizID, String question, ArrayList<String> answers, int  type){
		this.questionID =  questionID;
		this.type = type;
		DBConnection dbConn = new DBConnection();
		//Question helper will return int, Kim to change this later, then this will be correct
		this.quizID = QuestionHelper.addQuestion(dbConn, this.type);
		this.question = question;
		this.answers = answers;	
	}
	
	/* This will add the question into the correct table and add it's answers as well, regardless of type, however you must insert type
	 * */
	public void addQuestionAbstract(String question, ArrayList<String> answers, DBConnection conn, int type)
	
	
	/*This gets the question itself
	 * */
	public String getQuestion() {
		//this is a change
		return question;
	}

	/*This gets an arraylist of answers
	 * */
	public ArrayList<String> getAnswers() {
		return answers;
	}

	//check answer
	public boolean checkAnswer(String answer){
		return this.answers.contains(answer);
	}
	
	public int getQuizID() {
		return quizID;
	}
	
	public int getQuestionID() {
		return questionID;
	}
}
