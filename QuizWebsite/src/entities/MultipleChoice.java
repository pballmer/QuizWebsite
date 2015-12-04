package entities;

import java.util.ArrayList;
import java.util.Set;
import entities.QuestionAbstract;

public class MultipleChoice extends QuestionAbstract{
	
	private ArrayList<String> options;
	
	public MultipleChoice(int questionID, ArrayList<String> options, String answer) {
		super(questionID, MULTIPLE_CHOICE, answer);
		this.options = options;
	}

	public ArrayList<String> getOptions() {
		return options;
	}
}
