package entities;

public class Challenge extends NotificationAbstract{
	private int quizID;
	private String link;
	private double score;
	
	public Challenge(int type, int id, String from, String to, int quizID, String link, double score){
		super(type, id, from, to);
		this.quizID = quizID;
		this.link = link;
		this.score = score;
	}
	
	public int getQuizID(){
		return quizID;
	}
	
	public String getQuizLink(){
		return link;
	}

	public double getScore(){
		//return from.getQuizzesTaken().get(quiz.getId());
		return score;
	}

}
