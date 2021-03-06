package entities;

import java.util.*;

import db.DBConnection;
import db.QuizHelper;

public class Quiz {
	private String name;
	private int id;
	private String link;
	private String creator;
	private List<QuestionAbstract> questions;
	private String description;
	private long startTime;
	private long endTime;
	private double score;
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
	
	public Quiz(int quizid, String quizname, String desc, String creator, ArrayList<QuestionAbstract> questions)
	{
		this.id = quizid;
		this.name = quizname;
		this.description = desc;
		this.creator = creator;
		this.questions = questions;
		tags = new ArrayList<String>();
	}
	
//	//constructor without other options being set
//	public Quiz(String nameInput, String descInput, ArrayList<QuestionAbstract> questionInput, User user, String link){
//		this.name = nameInput;
//		this.link = link;
//		this.description = descInput;
//		this.questions = new ArrayList<QuestionAbstract>();
//		questions.addAll(questionInput);
//		this.creator = user;
//		tags = new ArrayList<String>();
//	}
	
	//constructor with all options set
//	public Quiz(String nameInput, String descInput, ArrayList<QuestionAbstract> questionInput, User user, String link, boolean rand, boolean page, boolean correction){//constructor 
//		this.name = nameInput;
//		this.link = link;
//		this.description = descInput;
//		this.questions = new ArrayList<QuestionAbstract>();
//		questions.addAll(questionInput);
//		this.creator = user;
//		this.random = rand;
//		if(random) randomize();
//		this.onePage = page;
//		this.immediateCorrection = correction;
//		tags = new ArrayList<String>();
//	}
	
	public double doScore(ArrayList<String> responses){
		double score = 0;
		for(int i = 0; i < responses.size(); i++){
			//if(questions.get(i).checkAnswer(responses.get(i))) score++;
		}
		return (score/questions.size());//returns score as a percentage where each question is worth 1
	}
	
	public void addTag(String tag, DBConnection conn){
		tags.add(tag);
		QuizHelper.addTag(conn, id, tag);
	}
	
	public void addQuestion(QuestionAbstract question, DBConnection conn){
		QuizHelper.addQuizQuestion(conn, this.id, question.getQuestionID());
		questions.add(question);
	}
	
	//to randomize the thing
	public void randomize(){
		long seed = System.nanoTime();
		Collections.shuffle(questions, new Random(seed));
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

	public String getCreator() {
		return creator;
	}

	public List<QuestionAbstract> getQuestions() {
		return questions;
	}

	public String getDescription() {
		return description;
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
