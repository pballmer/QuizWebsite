package entities;

public class FriendRequest extends NotificationAbstract {
	public static final int REJECTED = 0; 
	public static final int ACCEPTED = 1;
	public static final int PENDING = 2;
	
	
	public FriendRequest(int type, int id, String from, String to){
		super(type, id, from, to);
	}
}
