/**
 * module class contains a list of question banks and an ID
 * allows user to access and organize the question banks for
 * ease of use
 * @author emb109 (virgil)
 * @version 1.0 04/04/24
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Module {
    private String moduleID;
    private ArrayList<QBank> banks = new ArrayList<>();

    /**
     * constructor for the module class
     * @param moduleID the name of the module
     */
    public Module(String moduleID){
        this.moduleID = moduleID;
    }

    /**
     * has user enter an ID of a question bank then returns the
     * corresponding question bank object
     * @return QBank with correct id
     */
    public QBank selectQBank(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the Question Bank ID: ");
        String bankName = input.nextLine();
        //iterate through array until names match
        for (QBank bank : banks) {
            QBank qb1;
            qb1 = bank;
            if (bankName.equals(qb1.getName())) {
                return qb1;
            }
        }
        //if question bank does not exist
        return null;
    }


    /**
     * to add question banks to the module
     */
    public void addQBank(){
        QBank n = new QBank();
        n.readKeyboard();
        banks.add(n);
    }

    /**
     * delete a question bank from a module only if that#
     * question bank has no questions in it
     * @param qb the QBank to be deleted
     */
    public void deleteQBank(QBank qb){
        if(qb.getNumQ() == 0){
            banks.remove(qb);
        }else{
            System.out.println("You cannot delete a question bank with questions in it.");
            System.out.println("Please delete all the questions from this bank and try again.");
        }

    }

    /**
     * get the name/id of the module
     * @return moduleID the unique id of the module
     */
    public String getName() {
        return moduleID;
    }

    /**
     * to String method for module class, returns string with
     * all important information about the module object
     * @return String containing info for module class
     */
    @Override
    public String toString() {
        return "Module{" +
                "moduleID='" + moduleID + '\'' +
                ", banks=" + banks.toString() +
                '}';
    }
}
