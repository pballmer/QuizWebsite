package entities;

import java.util.ArrayList;
import java.util.Set;

public class MultipleChoice extends QuestionAbstract{

	public MultipleChoice(int questionID, int quizID, String question, Set<ArrayList<String>> answers) {
		super(questionID, quizID, question, answers);
		this.type = questionTypes[0];
	}
	

}
