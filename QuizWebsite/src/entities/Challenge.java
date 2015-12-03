package entities;

public class Challenge extends NotificationAbstract{
	private Quiz quiz;
	private String link;
	
	public Challenge(int type, int id, User from, User to, Quiz q, String link){
		super(type, id, from, to);
		this.quiz = q;
		this.link = link;
	}
	
	public int getQuizID(){
		return quiz.getId();
	}
	
	public String getQuizLink(){
		return link;
	}
	
	public int getScore(){
		//return from.getQuizzesTaken().get(quiz.getId());
		return 10;
	}

}
