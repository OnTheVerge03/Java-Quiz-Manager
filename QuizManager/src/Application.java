/**
 * this program allows teachers to create, organize and maintain banks
 * of questions, and for students to take quizzes with those questions.
 * Questions are organized into banks, which are organized into modules.
 * @author emb109 (virgil)
 * @version 1.0 04/04/24
 */

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class Application {

    private ArrayList<Module> mods = new ArrayList<>();

    /**
     * main method, runs the application
     * @param args n/a
     * @throws FileNotFoundException for save and load functions in qBank
     */
    public static void main(String[] args) throws FileNotFoundException {
        Application app = new Application();
        app.runStartMenu();
    }

    /**
     * first menu user encounters, select whether they want
     * options for student or for teacher
     */
    private void runStartMenu() throws FileNotFoundException {
        String option;
        Scanner input = new Scanner(System.in);
        do {
            printStartMenu();
            option = input.nextLine().toUpperCase();
            switch (option){
                case "S":
                    //give user options for student
                    runMenuStudent();
                    break;
                case "T":
                    //give user options for teacher
                    runMenuTeacher();
                    break;
                case("Q"):
                    //quit
                    break;
                default:
                    System.out.println("Invalid entry, select again.");
                    break;
            }
        }while(!option.equals("Q"));
    }

    /**
     *prints start menu in runStartMenu method
     */
    private void printStartMenu(){
        System.out.println("Options: \n s - I am a student \n t - I am a teacher \n q -quit");
    }

    /**
     * if user selects student, they will be given the options
     * available to students -list question banks, take quiz, quit
     */
    private void runMenuStudent(){
        String option;
        Scanner input = new Scanner(System.in);
        do {
            printMenuStudent();
            option = input.nextLine().toUpperCase();
            switch (option) {
                case("M"):
                    //list modules in application class
                    System.out.println(this);
                    break;
                case("B"):
                    //list question banks in modules class
                    Module m;
                    m = selectMod();
                    try{
                        String s = m.toString();
                        System.out.println(s);
                    }catch(NullPointerException e){
                        System.out.println("Module does not exist.");
                    }
                    break;
                case("T"):
                    //take quiz
                    Module module1 = selectMod();
                    try{
                        QBank qBank1 = module1.selectQBank();
                        System.out.println("How many questions would you like to answer: ");
                        Scanner in = new Scanner(System.in);
                        try{
                            int q = in.nextInt();
                            qBank1.takeQuiz(q);
                        }catch(InputMismatchException e){
                            System.out.println("Input must be a number");
                            break;
                        }
                    }catch(NullPointerException e){
                        System.out.println("Module does not exist.");
                    }
                    break;
                case("Q"):
                    //quit
                    break;
                default:
                    System.out.println("Invalid entry, select again.");
                    break;
            }
        }while(!option.equals("Q"));
    }

    /**
     * print student menu in runMenuStudent method
     */
    private void printMenuStudent(){
        System.out.println("Options: \n m - list modules \n b - list question banks \n t - take quiz \n q - quit");
    }

    /**
     * if the user selects teacher, this menu will give them all the available
     * options for a teacher
     */
    private void runMenuTeacher() throws FileNotFoundException {
        String option;
        Scanner input = new Scanner(System.in);
        do {
            printMenuTeacher();
            option = input.nextLine().toUpperCase();
            switch (option) {
                case ("N"):
                    //create new module and add to array list
                    System.out.println("Enter module name:");
                    Module n = new Module(input.nextLine());
                    if(n.getName().length() <= 7) {
                        mods.add(n);
                    }else{
                        System.out.println("Error: Module ID must contain less than 8 characters.");
                    }
                    break;
                case("C"):
                    //select module
                    Module mod = selectMod();
                    try{
                        //add new question bank
                        mod.addQBank();
                    }catch(NullPointerException e){
                        System.out.println("Module does not exist.");
                    }
                    break;
                case("D"):
                    //select module
                    Module t = selectMod();
                    try{
                        //select question bank
                        QBank d = t.selectQBank();
                        //delete if there are no questions in the module
                        t.deleteQBank(d);
                    }catch(NullPointerException e){
                        System.out.println("Module does not exist.");
                    }
                    break;
                case("E"):
                    //select module
                    Module m;
                    m = selectMod();
                    try{
                        //select question bank
                        QBank b;
                        b = m.selectQBank();
                        b.editQBank();
                    }catch(NullPointerException e){
                        System.out.println("Module or question bank t does not exist.");
                    }
                    break;
                case("M"):
                    //list modules in application class
                    System.out.println(this);
                    break;
                case("B"):
                    //list question banks in modules class
                    Module module1;
                    module1 = selectMod();
                    try{
                        String s = module1.toString();
                        System.out.println(s);
                    }catch(NullPointerException e){
                        System.out.println("Module does not exist.");
                    }
                    break;
                case("Q"):
                    //quit
                    break;
                default:
                    System.out.println("Invalid entry, select again.");
                    break;
            }
        }while(!option.equals("Q"));
    }

    /**
     * print menu for runMenuTeacher method
     */
    private void printMenuTeacher(){
        System.out.println("Options: \n n - create new module \n c - create new question bank \n d - delete question bank \n e - edit question bank \n m - list modules \n b - list question banks \n q - quit");
    }

    /**
     * print menu for runMenuTeacher method
     * @return Module that matches name user enters
     */
    public Module selectMod(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the name of the module: ");
        String bankName = input.nextLine();
        //iterate through array until names match
        for (Module mod : mods) {
            Module module1;
            module1 = mod;
            if (bankName.equals(module1.getName())) {
                return module1;
            }
        }
        //if module does not exist
        return null;
    }

    /**
     * print modules or message if no modules exist
     * @return String with module info
     */
    public String toString(){
        if(mods != null){
            return mods.toString();
        }else{
            return "There are no available modules.";
        }
    }
}
