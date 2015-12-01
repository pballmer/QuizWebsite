package entities;

import java.util.ArrayList;
import java.util.Set;

public class FillBlank extends QuestionAbstract {

	public FillBlank(int questionID, int quizID, String question, ArrayList<String> answers) {
		super(questionID, quizID, question, answers);
		this.type = FILL_IN_BLANK;
	} 
	
}
