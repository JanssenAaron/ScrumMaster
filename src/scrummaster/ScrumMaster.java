
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
        Scanner scan = new Scanner(System.in);
        while (run) {
            ScrumClasses requestClass = ScrumClasses.scrumClassesRequest();
            while(run){
              Request req = Request.setRequest();
              requestClass.excute(req);
              System.out.println(  "would you like to request something else say yes to keep going or anything else to go back ");
              String hold = scan.next();
              run = "yes".equals(hold);
            }
            
            System.out.println(  "would you like to request something else say yes to keep going or anything else to stop ");
            String hold = scan.next();
            run = "yes".equals(hold);
            
        }
        scan.close();

    }
   
    /*
    
     */
    public static void requestData(ScrumClasses scrum) {

        Request.commands();
        Request req = Request.setRequest();
        scrum.excute(req);

    }

}
