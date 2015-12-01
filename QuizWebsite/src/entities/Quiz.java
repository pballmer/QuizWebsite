import java.util.*;

import db.QuizHelper;

public class Quiz {
	private String name;
	private int id;
	private String link;
	private User creator;
	private List<QuestionAbstract> questions;
	private String description;
	private long startTime;
	private long endTime;
	private boolean random = false; //set these defaults for what I thought make sense
	private boolean onePage = true;
	private boolean immediateCorrection = false;
	private boolean practiceMode = false;
	private int score = -1;//sentinel value
	
	//constructor without other options being set
	public Quiz(String nameInput, String descInput, ArrayList<QuestionAbstract> questionInput, User user, String link){
		this.name = nameInput;
		this.link = link;
		this.description = descInput;
		this.questions = new ArrayList<QuestionAbstract>();
		questions.addAll(questionInput);
		this.creator = user;
		this.startTime = System.currentTimeMillis();
	}
	
	//constructor with all options set
	public Quiz(String nameInput, String descInput, ArrayList<QuestionAbstract> questionInput, User user, String link, boolean rand, boolean page, boolean correction, boolean practice){//constructor 
		this.name = nameInput;
		this.link = link;
		this.description = descInput;
		this.questions = new ArrayList<QuestionAbstract>();
		questions.addAll(questionInput);
		this.creator = user;
		this.random = rand;
		if(random) randomize();
		this.onePage = page;
		this.immediateCorrection = correction;
		this.practiceMode = practice;
		this.startTime = System.currentTimeMillis();
	}
	
	public void addQuestion(QuestionAbstract question, DBConnection conn){
		QuizHelper.addQuizQuestion(conn, this, question);
		questions.add(question);
	}
	
	//to randomize the thing
	public void randomize(){
		long seed = System.nanoTime();
		Collections.shuffle(questions, new Random(seed));
		//TODO: update BE
	}
	
	public long getQuizTime(){
		endTime = System.currentTimeMillis();
		return endTime - startTime;
	}
	
	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int val){
		id = val;
	}

	public User getCreator() {
		return creator;
	}

	public List<QuestionAbstract> getQuestions() {
		return questions;
	}

	public String getDescription() {
		return description;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public boolean isRandom() {
		return random;
	}

	public boolean isOnePage() {
		return onePage;
	}

	public boolean isImmediateCorrection() {
		return immediateCorrection;
	}

	public boolean isPracticeMode() {
		return practiceMode;
	}

	public int getScore() {
		return score;
	}
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	@Override
	public boolean equals(Object o){
		Quiz q = (Quiz)o;
		return (q.getId() == this.getId());
	}
}
