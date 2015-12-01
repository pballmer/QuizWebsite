package entities;

public abstract class NotificationAbstract {
	//0 is friend request, 1 is challenge, 2 is note
	public static final int[] NotificationTypes = {0, 1, 2};
	public int type;
	public int notificationID;
	User from;
	User to;
	
	public NotificationAbstract(int type, int id, User from, User to){
		this.type = type;
		this.notificationID = id;
		this.from = from;
		this.to = to;
	}
	
	public int getType(){
		return type;
	}
	
	public int getID(){
		return notificationID;
	}
	
	public String getSender(){
		return from.getUsername();
	}
	
	public String getRecipient(){
		return to.getUsername();
	}
}
