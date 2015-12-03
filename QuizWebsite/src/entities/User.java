package entities;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import db.DBConnection;
import db.NotificationsHelper;
import db.QuizHelper;
import db.UserHelper;

public class User {
	private String username;
	private String password;//will be stored as encrypted
	private boolean admin;
	private List<User> friends;
	private List<Quiz> quizzesMade;
	private Map<Integer, Integer> quizzesTaken;//maps quiz id to score
	private List<NotificationAbstract> notifications;
	private List<String> achievements;
	private boolean hasPracticed;
	
	// TODO should only initialize this once
	public static MessageDigest md;
	
	public User(String un, String pw){//pw is not encrypted
		this.username = un;
		this.admin = false;
		this.friends = new ArrayList<User>();
		this.quizzesMade = new ArrayList<Quiz>();
		this.quizzesTaken = new HashMap<Integer, Integer>();
		this.notifications = new ArrayList<NotificationAbstract>();
		this.achievements = new ArrayList<String>();
		this.hasPracticed = false;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		password = encryptPass(pw);
	}
	
	public User(String un, String pw, boolean admin, boolean encrypted){//pw is not encrypted
		this.username = un;
		this.admin = admin;
		this.friends = new ArrayList<User>();
		this.quizzesMade = new ArrayList<Quiz>();
		this.quizzesTaken = new HashMap<Integer, Integer>();
		this.notifications = new ArrayList<NotificationAbstract>();
		this.hasPracticed = false;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		password = pw;
	}

	
	public User(String un, String pw, boolean admin){//pw is not encrypted
		this.username = un;
		this.admin = admin;
		this.friends = new ArrayList<User>();
		this.quizzesMade = new ArrayList<Quiz>();
		this.quizzesTaken = new HashMap<Integer, Integer>();
		this.notifications = new ArrayList<NotificationAbstract>();
		this.hasPracticed = false;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		password = encryptPass(pw);
	}
	
	public void addQuizTaken(Quiz quiz, DBConnection conn, int score){
		quizzesTaken.put(quiz.getId(), score);
		QuizHelper.addQuizTaken(conn, quiz, this.getUsername(), score, String.valueOf(quiz.getStartTime()), String.valueOf(quiz.getEndTime()));
		if(quiz.isPracticeMode() && !achievements.contains("Practice Makes Perfect")){
			this.addAchievement("Practice Makes Perfect", conn);
		}
		if(quizzesTaken.size() == 10){
			this.addAchievement("Quiz Machine", conn);
		}
		if(score >= QuizHelper.getTopScore(conn, quiz.getId()) && !achievements.contains("I am the Greatest")){
			this.addAchievement("I am the Greatest", conn);
		}
	}
	
	public void addQuizMade(Quiz quiz, DBConnection conn){
		QuizHelper.addQuiz(conn, quiz);
		QuizHelper.addQuizMade(conn, quiz, username);
		quizzesMade.add(quiz);
		if(quizzesMade.size() == 1) {
			this.addAchievement("Amateur Author", conn);
		} else if(quizzesMade.size() == 5){
			this.addAchievement("Prolific Author", conn);
		} else if(quizzesMade.size() == 10){
			this.addAchievement("Prodigious Author", conn);
		}
	}
	
	public void addAchievement(String ach, DBConnection conn){
		UserHelper.addAchievement(conn, ach, username);
		achievements.add(ach);
	}
	
	public void addChallenge(Challenge challenge, DBConnection conn){
		NotificationsHelper.addChallenge(conn, challenge);
		notifications.add(challenge);
	}
	
	public void addNote(Note note, DBConnection conn){
		NotificationsHelper.addNote(conn, note);
		notifications.add(note);
	}
	
	//TODO: add friend request once DB help is added
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isAdmin() {
		return admin;
	}
	
	public Map<Integer, Integer> getQuizzesTaken() {
		return quizzesTaken;
	}

	public void addFriend(User friend){
		
		friends.add(friend);
	}
	
	public boolean passMatches(String pass) {
		String encrypted = encryptPass(pass);
		return encrypted.equals(this.password);
	}
	
	private static String encryptPass(String pw) {
		byte[] digest = null;
		md.update(pw.getBytes());
		digest = md.digest();
		return hexToString(digest);
	}
	
	//took from cracker (hw4)
	private static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}

}
