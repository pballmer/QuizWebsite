package entities;

import java.util.ArrayList;
import java.util.Set;

import entities.QuestionAbstract;

public class QuestionResponse extends QuestionAbstract{
	
	public QuestionResponse(int questionID, int quizID, String question, ArrayList<String> answers){
		super(questionID, quizID, question, answers);
		this.type = QUESTION_RESPONSE;
	}
}
