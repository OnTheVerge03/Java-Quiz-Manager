/**
 * class for fill in the blank questions, child class of Question
 * allows user to enter a question stem that contains a blank and
 * asks the test-taker to fill in the blank with appropriate word
 * @author emb109 (virgil)
 * @version 1.0 04/04/24
 */

import java.util.Scanner;
public class FITB extends Question{
    private String answer;

    /**
     * allows user to create new questions with input from the keyboard
     */
    @Override
    public void readKeyboard(){
        super.readKeyboard();
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter your answer and press enter: ");
        answer = in.nextLine().toUpperCase();
    }

    /**
     * setter method for answer
     * @param answer word that fills in the blank in the question stem
     */
    public void setAnswer(String answer) {
        this.answer = answer.toUpperCase();
    }

    /**
     * getter method for answer
     * @return answer as a string
     */
    @Override
    public String getAnswer() {
        return answer;
    }

    /**
     * prints the answer
     */
    @Override
    public void printAnswer() {
        System.out.println(answer);
    }

    /**
     * displays question while taking quiz and increases
     * score if the answer is correct
     */
    @Override
    public boolean display(){
        super.printQuestion();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the word in the blank: ");
        String a = in.nextLine().toUpperCase();
        return a.equals(answer);
    }
}
