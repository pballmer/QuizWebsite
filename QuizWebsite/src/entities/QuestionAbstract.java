package src.entities;



public abstract class QuestionAbstract {
	

	/*Per Peter's seemingly nonsensical request, instead of String, types will be in int
	 * 0: Multiple Choice (MC)
	 * 1:Fill in the Blank (FB)
	 * 2:Picture Response (PR)
	 * 3:Question Response (QR)
	 * */
	public static final int[] questionTypes = new int[]{0, 1, 2, 3};
	private int questionID;
	private int quizID;
	public int type;
	public String question;
	public Set<ArrayList<String>> answers = new HashSet<ArrayList<String>>(); 
	
	
	public QuestionAbstract(int questionID, int quizID, String question, Set<ArrayList<String>> answers){
		this.questionID =  questionID;
		this.quizID = quizID;
		this.question = question;
		this.answers = answers;
	}
	
	/*This gets the question itself
	 * */
	String getQuestion() {
		return question;
	}

	/*This gets an arraylist of answers
	 * */
	Set<ArrayList<String>> getAnswers() {
		return answers;
	}

	
	int getQuizID() {
		return quizID;
	}
	
	int getQuestionID() {
		return questionID;
	}
	
	public boolean checkAnswer(ArrayList<String> answer){
		for(ArrayList<String> curr: answers){
			if(curr.equals(answer))
				return true;
		}
		return false;
	}
}
