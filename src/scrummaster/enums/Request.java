package scrummaster.enums;

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
        boolean run  = true; 
        Request value = INVALID; 
        Scanner scan = new Scanner(System.in);
        while(run) {

            try{
              value = Request.valueOf(scan.nextLine().toUpperCase());
              run = false;
            }
            catch(Exception e){
              value = INVALID; 
            }
        }
        scan.close();
        return value; 

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
}