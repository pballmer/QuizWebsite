package entities;

import java.util.ArrayList;
import java.util.Set;
import entities.QuestionAbstract;

public class MultipleChoice extends QuestionAbstract{
private ArrayList<String> options = new ArrayList<String>();
	public MultipleChoice(int questionID, int quizID, String question, ArrayList<String> answers, int type, ArrayList<String> options) {
		super(questionID, quizID, question, answers, MULTIPLE_CHOICE, options);
		if(type != MULTIPLE_CHOICE) {
			throw new IllegalArgumentException("Int type is not correct Int for Question Response (" + MULTIPLE_CHOICE + ")");
			}
	}
}
