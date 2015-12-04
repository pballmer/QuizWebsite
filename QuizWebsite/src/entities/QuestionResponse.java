package entities;

import java.util.ArrayList;

import entities.QuestionAbstract;

public class QuestionResponse extends QuestionAbstract{
	
	public QuestionResponse(int questionID, int quizID, String question, ArrayList<String> answers, int type, ArrayList<String >options){
		super(questionID, quizID, question, answers, QUESTION_RESPONSE, options);
	}
}
