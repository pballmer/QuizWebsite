package entities;

import java.util.ArrayList;
import java.util.Set;

public class PictureResponse extends QuestionAbstract{
	/*This is the constructor for the picture response
	 * */
	public PictureResponse(int questionID, int quizID, String question, ArrayList<String> answers, int type) {
		try{
			if(type != PICTURE_RESPONSE) {
				throw new IllegalArgumentException("Int type is not correct Int for Picture Response (3)");
				}
			super(questionID, quizID, question, answers, type);
			this.type = PICTURE_RESPONSE;
		}catch()
	}
}
