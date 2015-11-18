package entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class QuestionAbstract {

	
	private int questionID;
	private int quizID;
	public String question;
	public Set<ArrayList<String>> answers = new HashSet<ArrayList<String>>(); 
	
	
	public QuestionAbstract(int questionID, int quizID, String question, Set<ArrayList<String>> answers){
		this.questionID =  questionID;
		this.quizID = quizID;
		this.question = question;
		this.answers = answers;
	}
	String getQuestion() {
		return question;
	}

	Set<ArrayList<String>> getAnswers() {
		return answers;
	}

	int getQuizID() {
		return quizID;
	}

	int getQuestionID() {
		return questionID;
	}
	public boolean checkAnswer(ArrayList<String> answer){
		for(ArrayList<String> curr: answers){
			if(curr.equals(answer))
				return true;
		}
		return false;
	}
}
