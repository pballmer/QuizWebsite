package entities;

import java.util.ArrayList;
import java.util.Set;

import entities.QuestionAbstract;

public class QuestionResponse extends QuestionAbstract{
	
	public QuestionResponse(int questionID, int quizID, String question, ArrayList<String> answers, int type, ArrayList<String >options){
		super(questionID, quizID, question, answers, type, options);
		
		if(type != QUESTION_RESPONSE) {
			throw new IllegalArgumentException("Int type is not correct Int for Question Response (" + QUESTION_RESPONSE + ")");
			}
	}
}
