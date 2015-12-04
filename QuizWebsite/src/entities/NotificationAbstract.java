package entities; 

public abstract class NotificationAbstract {

	// made this an enum for clarity. they still correspond to 0, 1, and 2 - Colin
	public static enum NotificationTypes {
		FRIEND_REQUEST, CHALLENGE, NOTE
	}

	protected int type;
	protected int id;
	protected String from;
	protected String to;

	public NotificationAbstract(int type, int id, String from, String to) {
		this.type = type;
		this.id = id;
		this.from = from;
		this.to = to;
	}
	
	public String getSenderName() {
		return from;
	}
	
	public String getRecipient()
	{
		return to;
	}

}
