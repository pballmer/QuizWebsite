package entities;

import java.util.ArrayList;
import java.util.Set;
import entities.QuestionAbstract;

public class MultipleChoice extends QuestionAbstract{

	public MultipleChoice(int questionID, int quizID, String question, ArrayList<String> answers, int type) {
		super(questionID, quizID, question, answers, type);
		
		if(type != MULTIPLE_CHOICE) {
			throw new IllegalArgumentException("Int type is not correct Int for Question Response (" + MULTIPLE_CHOICE + ")");
			}
	}
}
