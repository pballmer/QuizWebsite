package entities;

public class FriendRequest extends NotificationAbstract {

	public FriendRequest(int type, int id, User from, User to){
		super(type, id, from, to);
	}
}
