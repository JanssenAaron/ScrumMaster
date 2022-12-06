package scrummaster.enums;

import java.util.EnumSet;
import java.util.Scanner;
import scrummaster.dataclasses.ScrumMasterCommand;

/**
 *
 * @author Jordan
 */
// connect to the scrumMaster Comnmand line defualt option
public enum Request {
    LS,
    GET,
    INSERT,
    DELETE,
    UPDATE,
    INVALID;

    public static Request setRequest() {
        commands();
        Scanner scan = new Scanner(System.in);
        boolean run = true;
        Request scrum = INVALID;
        while(run){
            try{
                String hold = scan.nextLine();
                scrum = valueOf(hold.toUpperCase());
                run = false; 
            }   
            catch(Exception e){
                System.out.println("do");
            }       
        }
        return scrum ;
    }

    // ask brandon
    public void callMethod(Request req, ScrumMasterCommand scrum) {
        if (req == Request.INSERT) {
            scrum.insertFunction(req);

        } else if (req == Request.GET)
            scrum.getFunction(req);
        else if (req == Request.DELETE)
            scrum.deleteFunction(req);
        else if (req == Request.LS)
            scrum.listFunction(req);
        else if (req == Request.UPDATE)
            scrum.updateFunction(req);
        else if (req == Request.INSERT) 
           scrum.insertFunction(req);
    }
    public static  void commands() {
        EnumSet.allOf(Request.class).forEach(action -> System.out.println(action));
    }
}
