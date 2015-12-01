package entities;

import java.util.ArrayList;
import java.util.Set;

public class PictureResponse extends QuestionAbstract{
	/*This is the constructor for the picture response
	 * */
	public PictureResponse(int questionID, int quizID, String question, ArrayList<String> answers) {
		super(questionID, quizID, question, answers);
		this.type = PICTURE_RESPONSE;
	}
}
