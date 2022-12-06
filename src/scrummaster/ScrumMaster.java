
package scrummaster;

import java.util.EnumSet;
import java.util.Scanner;

import scrummaster.enums.Request;
import scrummaster.enums.ScrumClasses;

/**
 *
 * @author aaronjanssen
 */
public class ScrumMaster {

    /**
     * @param args the command line arguments
     *             ScrumClasses requestClass = ScrumClasses.scrumClassesRequest();
            if (requestClass != ScrumClasses.INVALID) {
                requestData(requestClass);
            } else{
                System.out.println("sorry the command was not right");
            }
     */
    public static void main(String[] args) {
        boolean run = true;
        
        while (run) {
            ScrumClasses requestClass = ScrumClasses.scrumClassesRequest();
        


            Scanner scan = new Scanner(System.in);
            System.out.println("enter an integer");
            System.out.println(  "would you like to request something else say yes to keep going or anything else to stop ");
            String hold = scan.nextLine();
            System.out.println(hold);
            run = "yes".equals(hold);
            scan.close();
        }
        try{
        DBConnection.CONNECTION.close();
        }
        catch(Exception e ){
        }
    }
   
    /*
    
     */
    public static void requestData(ScrumClasses scrum) {

        Request.commands();
        Request req = Request.setRequest();
        scrum.excute(req);

    }

}
