package entities;

public class Challenge extends NotificationAbstract{
	private String text;
	private Quiz quiz;
	
	public Challenge(int type, int id, User from, User to, String text, Quiz q){
		super(type, id, from, to);
		this.text = text;
		this.quiz = q;
	}

	public String getText(){
		return text;
	}
	
	public int getQuizID(){
		return quiz.getId();
	}
	
	public String getQuizLink(){
		return quiz.getLink();
	}
	
	public int getScore(){
		return from.getQuizzesTaken().get(quiz.getId());
	}
}
