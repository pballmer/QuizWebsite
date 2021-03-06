package entities;

import java.util.ArrayList;
import java.util.Set;
import entities.QuestionAbstract;

public class MultipleChoice extends QuestionAbstract{
	
	private String text;
	private ArrayList<String> options;
	
	public MultipleChoice(int questionID, String text, ArrayList<String> options, String answer) {
		super(questionID, MULTIPLE_CHOICE, answer);
		this.text = text;
		this.options = options;
	}
	
	public String getText() {
		return text;
	}

	public ArrayList<String> getOptions() {
		return options;
	}
}
