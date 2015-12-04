package entities;

import java.util.*;

import db.DBConnection;
import db.QuizHelper;

public class Quiz {
	private String name;
	private int id;
	private String link;
	private User creator;
	private List<QuestionAbstract> questions;
	private String description;
	private Date startTime;
	private Date endTime;
	private boolean random = false; //set these defaults for what I thought make sense
	private boolean onePage = true;
	private boolean immediateCorrection = false;
	private List<String> tags;
	
	// default constructor used when creating a new quiz with no info yet
	public Quiz()
	{
		this.id = -1;
		this.name = "";
		this.description = "";
	}
	
	public Quiz(int quizid, String quizname, String desc)
	{
		this.id = quizid;
		this.name = quizname;
		this.description = desc;
		questions = new ArrayList<QuestionAbstract>();
		tags = new ArrayList<String>();
	}
	//constructor without other options being set
	public Quiz(String nameInput, String descInput, ArrayList<QuestionAbstract> questionInput, User user, String link){
		this.name = nameInput;
		this.link = link;
		this.description = descInput;
		this.questions = new ArrayList<QuestionAbstract>();
		questions.addAll(questionInput);
		this.creator = user;
		this.startTime = new Date();
		tags = new ArrayList<String>();
	}
	
	//constructor with all options set
	public Quiz(String nameInput, String descInput, ArrayList<QuestionAbstract> questionInput, User user, String link, boolean rand, boolean page, boolean correction){//constructor 
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
		this.startTime = new Date();
		tags = new ArrayList<String>();
	}
	
	public double doScore(ArrayList<String> responses){
		double score = 0;
		for(int i = 0; i < responses.size(); i++){
			if(questions.get(i).checkAnswer(responses.get(i))) score++;
		}
		return (score/questions.size());//returns score as a percentage where each question is worth 1
	}
	
	public void addTag(String tag, DBConnection conn){
		tags.add(tag);
		QuizHelper.addTag(conn, this, tag);
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String n){
		
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

	public Date getStartTime() {
		return startTime;
	}
	
	public void setEndTime(){
		endTime = new Date();
	}

	public Date getEndTime() {
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
