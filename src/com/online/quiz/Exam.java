package com.online.quiz;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.online.quiz.CreateDOM;
import com.online.quiz.CreateDOM;

/*
 * Exam Class
 * 1. For setting questions for test
 * 2. Getting QuestionList
 * 3. Getting Answers List
 * 4. Getting Marked Questions
 * 5. Setting Question Marked
 * 6. Calculating Result
 * 7. Getting Current Question No.
 * 8. Getting Total No. of Questions
 */

public class Exam {
	Document dom;
	public int currentQuestion=0;
	public int totalNumberOfQuestions=0;
	public int quizDuration=0;
	String testName;
	ArrayList<String> answered = new ArrayList<String>();
	ArrayList<String> marked = new ArrayList<String>();
	
	public List<Integer> quizSelectionsList=new ArrayList<Integer>();
	
	
	public List<Integer> getQuizSelectionsList() {
		return quizSelectionsList;
	}

	public Map<Integer,Integer> selections=new LinkedHashMap<Integer,Integer>();
	
	public ArrayList<QuizQuestion> questionList;
	
	//Initializing Exam class
	public Exam(String test, String id,int totalNumberOfQuestions) throws SAXException,ParserConfigurationException,IOException, URISyntaxException, JSONException{
		dom=CreateDOM.getDOM(test,id);
		testName=test;
		/*
		 * For Initializing
		 * 1. User selections
		 * 2. User Answer List
		 * 3. Marked Questions List
		 */
		for(int i=0;i<totalNumberOfQuestions;i++){
			selections.put(i,-1);
			answered.add("-1");
			marked.add("-1");
		}
		questionList=new ArrayList<QuizQuestion>(totalNumberOfQuestions);
		System.out.println("number"+totalNumberOfQuestions);
	}
	
	//Getting Exam Title
	public String getTitle(){
		return testName;
	}
	
	//getting Current DOM Object
	public Document getDom(){
		return dom;
	}

	// Setting Questions For Current Exam
	public void setQuestion(int i)
	{ 
		int number=i;
		String options[]=new String[4];
	    String question=null;
	    int correct=0;
	  
		NodeList qList=dom.getElementsByTagName("question");
		    NodeList childList=qList.item(i).getChildNodes();
	    
	
	    int counter=0;
	    
	    for (int j = 0; j < childList.getLength(); j++) {
            Node childNode = childList.item(j);
            if ("answer".equals(childNode.getNodeName()))
            {
                options[counter]=childList.item(j).getTextContent();
                counter++;
            }
            else if("quizquestion".equals(childNode.getNodeName()))
            {
            	question=childList.item(j).getTextContent();
            	
            }
            else if("correct".equals(childNode.getNodeName()))
            {
            	correct=Integer.parseInt(childList.item(j).getTextContent());
            }
            
        }	  
		
		QuizQuestion q=new QuizQuestion();
		q.setQuestionNumber(number);
		q.setQuestion(question);
		q.setCorrectOptionIndex(correct);
		q.setQuestionOptions(options);	
		//System.out.println("Adding");
		//questionList.put(number,q);
                while(questionList.size() != getTotalNumberOfQuestions()){
                    questionList.add(number,q);
                }
		//if(questionList.size() != getTotalNumberOfQuestions()){
		//	questionList.add(number,q);
		//}
		//else{
			questionList.set(number, q);
		//}
		//System.out.println("Question list"+questionList);
		
	}
	
	//Getting Questions List
	public ArrayList<QuizQuestion> getQuestionList(){
		return this.questionList;
	}
	
	//Getting User Selections List
	public ArrayList<String> getAnswerList(){
		return this.answered;
	}
	
	//Setting current Answered
	public void setAnswered(int i){
		answered.set(i, "0");
	}
	
        //Setting current UnAnswered
	public void setUnAnswered(int i){
		answered.set(i, "-1");
	}
        
	//Getting user questions Marked List
	public ArrayList<String> getMarkedList(){
		return this.marked;
	}
	
	//Setting Current Question Marked
	public void setMarked(int i){
		marked.set(i, "0");
	}
	
	//Setting Current Question Unmarked
	public void setUnMarked(int i){
		marked.set(i, "-1");
	}
	
	//Getting whether current question Marked Status
	public String getMark(int i){
		return marked.get(i);
	}
	
	//Getting Current Question
	public int getCurrentQuestion(){return currentQuestion;}
	
	//Getting User Selections List
	public Map<Integer,Integer> getSelections(){
		return this.selections;
	}
	
	//Calculating Exam Result
	public int calculateResult(Exam exam,int taken){
		int totalCorrect=0;
		Map<Integer,Integer> userSelectionsMap=exam.selections;		
		List<Integer> userSelectionsList=new ArrayList<Integer>();
		for (Map.Entry<Integer, Integer> entry :userSelectionsMap.entrySet()) {
			userSelectionsList.add(entry.getValue());
		}
		
		//quizSelectionsList=userSelectionsList;
		//System.out.println(quizSelectionsList);
		
		List<QuizQuestion> questionList=exam.questionList;
		//System.out.println(questionList);
		
		List<Integer> correctAnswersList=new ArrayList<Integer>();
		for(QuizQuestion question: questionList){
			correctAnswersList.add(question.getCorrectOptionIndex());
		}
		
		// Checking Answers
		for(int i=0;i<taken;i++){
			//System.out.println(userSelectionsList.get(i));
			System.out.println("Correct"+correctAnswersList.get(i));
			if((userSelectionsList.get(i)-1)==correctAnswersList.get(i)){
				totalCorrect++;
			}
		}
		
		return totalCorrect;
	}
	
	public int getUserSelectionForQuestion(int i){
		Map<Integer,Integer> map=getSelections();
		
		return (Integer) map.get(i);
	}	
	
	//getting total No. Of Questions
	public int getTotalNumberOfQuestions(){
		return totalNumberOfQuestions;
	}
   
	//Setting Questions Count For Current Exam
	public void setTotalNumberOfQuestions(int i){
		this.totalNumberOfQuestions=i;
		
	}
	
	
}
