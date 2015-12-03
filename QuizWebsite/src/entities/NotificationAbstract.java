package entities; 

public abstract class NotificationAbstract {

	// made this an enum for clarity. they still correspond to 0, 1, and 2 - Colin
	public static enum NotificationTypes {
		FRIEND_REQUEST, CHALLENGE, NOTE
	}

	protected int type;
	protected int id;
	protected User from;
	protected User to;

	public NotificationAbstract(int type, int id, User from, User to) {
		this.type = type;
		this.id = id;
		this.from = from;
		this.to = to;
	}
	
	public String getSenderName() {
		return from.getUsername();
	}
	
	public String getRecipient()
	{
		return to.getUsername();
	}

}
