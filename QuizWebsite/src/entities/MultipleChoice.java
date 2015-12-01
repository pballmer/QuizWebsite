package entities;

import java.util.ArrayList;
import java.util.Set;
import entities.QuestionAbstract;

public class MultipleChoice extends QuestionAbstract{

	public MultipleChoice(int questionID, int quizID, String question, ArrayList<String> answers) {
		super(questionID, quizID, question, answers);
		this.type = MULTIPLE_CHOICE;
	}
}
