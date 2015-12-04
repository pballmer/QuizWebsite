package entities;

import db.QuestionHelper;

import java.util.ArrayList;

import db.DBConnection;

public class QuestionAbstract {
	
	protected static final int MULTIPLE_CHOICE = 0;
	protected static final int QUESTION_RESPONSE = 1;
	protected static final int FILL_IN_BLANK = 2;
	protected static final int PICTURE_RESPONSE = 3;	

	private int questionID;
	private int quizID;
	private int type;
	private String question;
	private ArrayList<String> answers = new ArrayList<String>(); 
	private ArrayList<String> options = new ArrayList<String>();
	
	 /*Constructor for QuestionAbstract
	 * NOTE** IF QUIZ ID == -1 Then the question was not created with knowledge of it's quiz id. Just a heads up
	 * */
	public QuestionAbstract(int questionID, int quizID, String question, ArrayList<String> answers, int type, ArrayList<String> options){
		this.questionID =  questionID;
		this.type = type;
		DBConnection dbConn = new DBConnection();
		//Question helper will return int OR group decide something else. TO BE FIXED
		this.quizID = QuestionHelper.addQuestion(dbConn, this.type);
		this.question = question;
		this.answers = answers;	
		this.options = options;
		//add answers
		QuestionHelper.addAnswers(dbConn, this.questionID, this.answers);
	}
	
//	/* This will add the question into the correct table for it's type and add it's answers as well, regardless of type, however you must insert type
//	 * */
//	public void addQuestionAbstract(DBConnection conn, QuestionAbstract question){
//		//question and answers were added in the constructor, so now just add to type correct table
//		switch (question.getType())
//		{
//			case MULTIPLE_CHOICE:
//				QuestionHelper.addMultipleChoice(conn, (MultipleChoice) question); 
//				return;
//			case QUESTION_RESPONSE:
//				QuestionHelper.addQuestionResponse(conn, (QuestionResponse) question);
//				return;
//			case FILL_IN_BLANK:
//				QuestionHelper.addFillBlank(conn, (FillBlank) question);
//				return;
//			case PICTURE_RESPONSE:
//				QuestionHelper.addPictureResponse(conn, (PictureResponse) question);
//				return;
//			default: break;
//		}
//		
//	}
	
	public ArrayList<String> getOptions(){	
		return this.options;
	}
	public int getType(){
		return this.type;
	}
	
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
