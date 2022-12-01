
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
     */
    public static void main(String[] args) {
        classes();
        ScrumClasses requestClass = ScrumClasses.scrumClassesRequest();

        
        if (requestClass != ScrumClasses.INVALID) {
            requestData(requestClass);
        } else
            System.out.println("sorry the command was not right");


    }
    /*
    
     */
    public static void requestData(ScrumClasses scrum) {

        commands();
        System.out.println("request something ");
        Request req = Request.setRequest();
        scrum.excute( req);

    }

    // define option of nevagatign the menu
    public static  void commands() {
        EnumSet.allOf(Request.class).forEach(action -> System.out.println(action));
    }
    public static  void classes() {
    }

}
