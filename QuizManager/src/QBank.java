/**
 * The question bank class contains an array of questions,
 * as well as several methods that allow the user to add, edit
 * or delete questions as well as allowing students to take a quiz
 * @author emb109 (virgil)
 * @version 1.0 27/03/2024
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class QBank {
    private String bankID;
    private float score;
    private ArrayList<Boolean> quizAnswers = new ArrayList<>();
    private long time;
    private ArrayList<Question> questions = new ArrayList<>();

    /**
     * default constructor
     */
    public QBank(){
        this.bankID = "unnamed";
        score = 0;
    }

    /**
     * has user enter information for initializing a question base from the keyboard
     */
    public void readKeyboard(){
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the name of the question bank");
        bankID = in.nextLine();
        if(bankID.length() > 15){
            bankID = null; //clear bank id value
            System.out.println("Error: Bank ID has a character limit of 15");
            readKeyboard(); //ask for another value
        }
    }

    //get number of questions in the questions array
    public int getNumQ(){
        return questions.size();
    }

    //get the id/name of the question bank
    public String getName(){
        return bankID;
    }

    /**
     * uses a text menu to give user several options for editing, deleting,
     * and saving/loading the questions and the question bank
     * @throws FileNotFoundException if the save/load methods are unable to find the file
     */
    public void editQBank() throws FileNotFoundException {
        String option;
        Scanner input = new Scanner(System.in);
        do {
            System.out.println(questions.toString());
            System.out.println("a - add question \n d - delete question \n s - save bank \n l - load bank \n q - quit");
            option = input.nextLine().toUpperCase();
            switch(option){
                case "A":
                    //determine if question is fill in the blank or multiple choice
                    System.out.println("Enter 'f' for fill-in-the-blanks or 'm' for a multiple choice question");
                    String type = input.nextLine().toUpperCase();
                    Question q;
                    if(type.equals("F")){
                        q = new FITB();
                    } else if (type.equals("M")) {
                        q = new MCQ();
                    }else{
                        System.out.print("Invalid response");
                        break;
                    }
                    //add new question
                    q.readKeyboard();
                    questions.add(q);
                    break;
                case "D":
                    //delete question
                    //list all questions with index
                    for(int i = 0; i < questions.size(); i++){
                        System.out.print(i);
                        System.out.print(". ");
                        Question qList = questions.get(i);
                        qList.printQuestion();
                    }
                    //have user enter index of question to be deleted
                    Scanner in = new Scanner(System.in);
                    System.out.println("Please enter the number of the question you'd like to delete:");
                    try{
                        int index = in.nextInt();
                        questions.remove(index);
                    }catch(InputMismatchException e){
                        System.out.println("Input must be a number");
                    }catch(IndexOutOfBoundsException e) {
                        System.out.println("That question does not exist");
                    }
                    break;
                case "S":
                    //save bank to file
                    save();
                    break;
                case "L":
                    //load bank from file
                    load();
                    break;
                case "Q":
                    //do nothing, exit loop
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }while(!option.equals("Q")); //loop while the user has not selected quit
    }

    /**
     * allows user to take a quiz with the questions in the array list. questions are
     * displayed one at a time and questions are numbered. score and time taken is displayed
     * at the end of the quiz
     * @param qNum number of questions in quiz
     */
    public void takeQuiz(int qNum){
        //start a timer when quiz begins
        long start = System.currentTimeMillis();
        //display questions in a random order
        Collections.shuffle(questions);
        score = 0; //set score to zero
        //if qNum is greater than number of questions, set it to bank size
        if(qNum > questions.size()){
            qNum = questions.size();
        }
        int counter = 0; //keeps track of which question user is on
        Question currentQ;
        Scanner in = new Scanner(System.in);
        String option;
        //display first question outside of loop for counter accuracy and ease of use
        currentQ = questions.get(counter);
        System.out.println("Question " + counter);
        boolean a = currentQ.display();
        if(a){
            score++;
            quizAnswers.add(counter, true); //record result of this question
        }else{
            quizAnswers.add(counter, false);
        }
        do{
            System.out.println("n - next question \n p - previous question \n q - quit");
            option = in.nextLine().toUpperCase();
            switch(option){
                case "N":
                    //display next question
                    counter++;
                    currentQ = questions.get(counter);
                    System.out.println("Question " + counter);
                    a = currentQ.display();
                    //if question is right, add to score
                    if(a){
                        score++;
                        quizAnswers.add(counter, true);
                    }else{
                        quizAnswers.add(counter, false);
                    }
                    break;
                case "P":
                    //display previous question
                    if(counter != 0){
                        counter--;
                        currentQ = questions.get(counter);
                        System.out.println("Question " + counter);
                        a = currentQ.display();
                        if(a && !quizAnswers.get(counter)){
                            score++;
                            quizAnswers.add(counter, true);
                        }else{
                            quizAnswers.add(counter, false);
                        }
                    }else{
                        System.out.println("There is no previous question.");
                    }
                    break;
                case"Q":
                    break; //do nothing, exit loop
            }
        }while(!option.equals("Q") && counter < qNum -1);
        //get end time and calculate time elapsed
        long finish = System.currentTimeMillis();
        time =  (finish - start) / 1000;

        //print information about the test
        printStats(counter, qNum);
    }

    /**
     * at the end of a quiz, prints various statistics about the
     * quiz for the user, including time taken to complete, percent
     * correct answers, and number of unanswered questions
     * @param counter which question the user was on when the quiz ended
     * @param qNum the number of questions in the quiz
     */
    public void printStats(int counter, int qNum){
        //calculate percent of answers correct
        float percent = score/questions.size();
        percent *= 100;
        //print info
        System.out.println("Score: " + percent + "%"); //percent answers correct
        System.out.println(qNum - 1 - counter + " unanswered questions."); //number of unanswered questions
        System.out.println("Time: " + time/60 + " minutes and " + time%60 +" seconds."); //time taken to complete test
    }

    /**
     * loads a question bank from a text document into the program
     */
    public void load() throws FileNotFoundException{
        //load question bank from text file
        File qBank = new File("M:\\Java\\questionBank.txt");
        Scanner infile = new Scanner(new BufferedReader(new FileReader(qBank)));
        infile.useDelimiter("\r?\n|\r");
        while(infile.hasNextLine()) {
            Question newQ;
            if(infile.nextLine().equals("MCQ")){
                newQ = new MCQ();
                newQ.setqStem(infile.nextLine());
                ((MCQ) newQ).setAnswerList(infile.nextLine());
                ((MCQ) newQ).setCorrectAnswer(infile.nextLine());
                questions.add(newQ);
            }else{
                newQ = new FITB();
                newQ.setqStem(infile.nextLine());
                ((FITB) newQ).setAnswer(infile.nextLine());
                questions.add(newQ);
            }
        }
        infile.close();
    }

    /**
     * saves the current question bank into a text document
     */
    public void save() {
        File qBank = new File("M:\\Java\\questionBank.txt");
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(qBank));
            for (Question q : questions) {
                //info to help parse text
                if (q.getClass().equals(MCQ.class)) { //record question type
                    out.println("MCQ");
                } else {
                    out.println("FITB");
                }
                //print question
                out.println(q.getqStem());
                out.println(q.getAnswer());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        out.close();
    }

    public String toString(){
        return bankID;
    }
}
