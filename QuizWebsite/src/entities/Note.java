package entities;

public class Note extends NotificationAbstract {
	String text;
	
	public Note(int type, int id, String from, String to, String text){
		super(type, id, from, to);
		this.text = text;
	}
	
	public String getText(){
		return text;
	}
}
