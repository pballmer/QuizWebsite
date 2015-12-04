package entities;

import java.util.ArrayList;

import entities.QuestionAbstract;

public class QuestionResponse extends QuestionAbstract{
	
	private String text;
	
	public QuestionResponse(int questionID, String text, String answer) {
		super(questionID, QUESTION_RESPONSE, answer);
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
