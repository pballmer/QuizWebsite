package entities;

import java.util.ArrayList;
import java.util.Set;

public class FillBlank extends QuestionAbstract {

	public FillBlank(int questionID, int quizID, String question, ArrayList<String> answers, int type, ArrayList<String> options) {
		super(questionID, quizID, question, answers, FILL_IN_BLANK, options);
	} 
	
}
