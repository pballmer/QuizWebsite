package entities;

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
	
	
	public QuestionAbstract(int questionID, int quizID, String question, ArrayList<String> answers){
		this.questionID =  questionID;
		this.quizID = quizID;
		this.question = question;
		this.answers = answers;
		
		DBConnection dbConn = new DBConnection();
		addQuestions(dbConn.getConnection(), 
		
	}
	
	/*This gets the question itself
	 * */
	String getQuestion() {
		return question;
	}

	/*This gets an arraylist of answers
	 * */
	ArrayList<String> getAnswers() {
		return answers;
	}

	
	public int getQuizID() {
		return quizID;
	}
	
	public int getQuestionID() {
		return questionID;
	}
	
	public boolean checkAnswer(ArrayList<String> answer){
		for(int i = 0; i < answers.size();i ++){
			if(answers.get(i).equals(answer))
				return true;
		}
		return false;
	}
}
