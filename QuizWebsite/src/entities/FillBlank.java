package entities;

import java.util.ArrayList;
import java.util.Set;

public class FillBlank extends QuestionAbstract {

	public FillBlank(int questionID, int quizID, String question, ArrayList<String> answers, int type) {
		super(questionID, quizID, question, answers, type);
		
		if(type != FILL_IN_BLANK) {
			throw new IllegalArgumentException("Int type is not correct Int for Question Response (" + FILL_IN_BLANK + ")");
			}
	} 
	
}
