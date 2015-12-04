package entities;

import java.util.ArrayList;
import java.util.Set;

public class PictureResponse extends QuestionAbstract{
	
	private String text;
	
	public PictureResponse(int questionID, String text, String answer) {
		super(questionID, PICTURE_RESPONSE, answer);
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
