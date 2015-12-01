package entities;
import java.util.*;

public class Quiz {
	//TODO: Once this class is merged with the branch with the question class, replace String with Question for list
	String name;
	int id;
	User creator;
	List<QuestionAbstract> questions;
	String description;
	long startTime;
	long endTime;
	boolean random = false; //set these defaults for what I thought make sense
	boolean onePage = true;
	boolean immediateCorrection = false;
	boolean practiceMode = false;
	int score = -1;//sentinel value
	
	//constructor without other options being set
	public Quiz(String nameInput, String descInput, ArrayList<QuestionAbstract> questionInput, User user){
		this.name = nameInput;
		this.description = descInput;
		this.questions = new ArrayList<QuestionAbstract>();
		questions.addAll(questionInput);
		this.creator = user;
		this.id = getDBid()//TODO integrate this with kims database shit
		this.startTime = System.currentTimeMillis();
		
	}
	
	//constructor with all options set
	public Quiz(String nameInput, String descInput, ArrayList<QuestionAbstract> questionInput, User user, boolean rand, boolean page, boolean correction, boolean practice){//constructor 
		this.name = nameInput;
		this.description = descInput;
		this.questions = new ArrayList<QuestionAbstract>();
		questions.addAll(questionInput);
		this.creator = user;
		this.id = getDBid()//TODO integrate this with kims database shit
		this.random = rand;
		if(random) randomize();
		this.onePage = page;
		this.immediateCorrection = correction;
		this.practiceMode = practice;
		this.startTime = System.currentTimeMillis();
	}
	
	public void addQuestion(QuestionAbstract question){
		questions.add(question);
		//TODO: update database
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
}
