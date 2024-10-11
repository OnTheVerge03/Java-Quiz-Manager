/**
 * this is the parent class for all other question classes. it contains basic methods
 * to define, store and retrieve questions that are elaborated on depending on
 * what type of question it is.
 * @author emb109 (virgil)
 * @version 1.0 04/04/24
 */

import java.util.Scanner;
abstract class Question {
    String qStem;

    /**
     * default constructor method for the questions class
     */
    public Question(){
        qStem = "unknown";
    }

    /**
     * prints the question stem
     */
    public void printQuestion(){
        System.out.println(qStem);
    }

    /**
     * displays question and returns a boolean that represents
     * a right or wrong answer.
     * @return true for correct answer, false for incorrect
     */
    abstract boolean display();

    /**
     * setter method for the question stem property
     * @param qStem the body of the question
     */
    public void setqStem(String qStem) {
        this.qStem = qStem;
    }

    /**
     * getter method for question stem
     * @return question stem as a string
     */
    public String getqStem() {
        return qStem;
    }

    /**
     * blank method that is overridden in child classes
     * @return answer as a string
     */
    abstract String getAnswer();

    /**
     * allows user to define question stem from the keyboard
     */
    public void readKeyboard(){
        Scanner in  = new Scanner(System.in);
        System.out.println("Please enter your question stem here and press enter:");
        qStem = in.next();
    }

    /**
     * prints the answer to the question-
     * it's blank here but overridden in child class
     */
    abstract void printAnswer();
}
