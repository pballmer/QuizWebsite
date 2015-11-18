package masterpackage;
import java.util.*;

public class Quiz {
	//TODO: Once this class is merged with the branch with the question class, replace String with Question for list
	String name;
	List<String> questions;
	String description;
	long startTime;
	long endTime;
	boolean random = false; //set these defaults for what I thought make sense
	boolean onePage = true;
	boolean immediateCorrection = false;
	boolean practiceMode = false;
	int score = -1;//sentinel value

	public Quiz(){//no param constructor, not sure if we'll need this but yeah
		this.name = "Test Quiz";
		this.description = "This quiz hasn't been set yet";
		this.questions = new ArrayList<String>();
		this.startTime = System.currentTimeMillis();
	}
	
	//constructor without other options being set
	public Quiz(String nameInput, String descInput, ArrayList<String> questionInput){
		this.name = nameInput;
		this.description = descInput;
		this.questions = new ArrayList<String>();
		questions.addAll(questionInput);
		this.startTime = System.currentTimeMillis();
	}
	
	//constructor with all options set
	public Quiz(String nameInput, String descInput, ArrayList<String> questionInput, boolean rand, boolean page, boolean correction, boolean practice){//constructor 
		this.name = nameInput;
		this.description = descInput;
		this.questions = new ArrayList<String>();
		questions.addAll(questionInput);//
		this.random = rand;
		if(random) randomize();
		this.onePage = page;
		this.immediateCorrection = correction;
		this.practiceMode = practice;
		this.startTime = System.currentTimeMillis();
	}
	
	public void addQuestion(String question){
		questions.add(question);
		//TODO: update database
		//TODO: add something to update front end instance? would we do that here or where this is called from?
	}
	
	//to randomize the thing
	public void randomize(){
		long seed = System.nanoTime();
		Collections.shuffle(questions, new Random(seed));
		//TODO: update FE/BE
	}
	
	public long getQuizTime(){
		endTime = System.currentTimeMillis();
		return endTime - startTime;
	}
}
