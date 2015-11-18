package entities;

import java.util.ArrayList;
import java.util.Set;

public class PictureResponse extends QuestionAbstract{

	public PictureResponse(int questionID, int quizID, String question, Set<ArrayList<String>> answers) {
		super(questionID, quizID, question, answers);
	}
}
