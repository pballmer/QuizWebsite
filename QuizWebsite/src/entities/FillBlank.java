package entities;

import java.util.ArrayList;
import java.util.Set;

public class FillBlank extends QuestionAbstract {

	private String textBefore;
	private String textAfter;
	
	public FillBlank(int questionID, String textBefore, String textAfter, String answer) {
		super(questionID, FILL_IN_BLANK, answer);
		this.textBefore = textBefore;
		this.textAfter = textAfter;
	}

	public String getTextBefore() {
		return textBefore;
	}

	public String getTextAfter() {
		return textAfter;
	}
}
