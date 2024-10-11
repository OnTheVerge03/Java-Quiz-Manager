/**
 * multiple choice question, child class of Question. has teacher enter a question
 * stem and a list of answers and the student answers with the letter that corresponds to
 * the correct answer in the list
 * @author emb109 (virgil)
 * @version 1.0 04/04/24
 */

import java.util.Scanner;
public class MCQ extends Question{
    private String answerList;
    private String correctAnswer;

    /**
     * allows user to create new question using input from keyboard
     */
    @Override
    public void readKeyboard(){
        super.readKeyboard();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your answer options then press enter: ");
        answerList = in.nextLine();
        System.out.println("Enter the letter that corresponds to the correct answer: ");
        correctAnswer = in.next().toUpperCase();
    }

    /**
     * prints question stem and the list of possible answers
     * overrides print question method in parent class
     */
    @Override
    public void printQuestion(){
        System.out.println(super.qStem);
        System.out.println(answerList);
    }

    /**
     * prints the letter corresponding to the correct
     * answer in the answer list. overrides print answer
     * method in parent class
     */
    @Override
    public void printAnswer() {
        System.out.println(correctAnswer);
    }

    /**
     * setter method for answer list
     * @param answerList the list of possible answers to the question
     */
    public void setAnswerList(String answerList) {
        this.answerList = answerList;
    }

    /**
     * getter method for answer
     * @return answer as a string
     */
    @Override
    public String getAnswer() {
        return correctAnswer;
    }

    /**
     * getter method for question stem including answer list
     * @return stem and answer list as string
     */
    @Override
    public String getqStem() {
        return super.getqStem() + "\n" + answerList;
    }


    /**
     * setter method for correct answer
     * @param correctAnswer the letter corresponding with the correct answer in the answer list
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * displays question and answer list while taking a quiz
     * and adds to score if the answer is correct
     */
    @Override
    public boolean display(){
        printQuestion();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the letter corresponding to the correct answer: ");
        String a = in.next().toUpperCase();
        return a.equals(correctAnswer);
    }
}
