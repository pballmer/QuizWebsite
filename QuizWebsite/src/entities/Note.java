package entities;

public class Note extends NotificationAbstract {
	String text;
	
	public Note(int type, int id, User from, User to, String text){
		super(type, id, from, to);
		this.text = text;
	}
	
	public String getText(){
		return text;
	}
}
