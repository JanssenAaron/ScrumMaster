package scrummaster.enums;

import java.util.EnumSet;
import java.util.Scanner;
import scrummaster.dataclasses.*;
/**
 *
 * @author Jordan
 */

public enum ScrumClasses {
    PROJECT( new Project()),
    SCRUM_TEAM(new ScrumTeam()),
    SPRINT(new Sprint()),
    DEVTEAM(new DevTeam()),
    EMPLOYEE(new Employee()),
    EMPLOYEE_ROLE(new EmployeeRole()),
    STAND_UP(new Standup()),
    USER_STORY(new UserStory()),
    INVALID(null);
   // ScrumMasterCommand x = (ScrumMasterCommand) new Project();
    private ScrumMasterCommand hold; 
    private ScrumClasses(ScrumMasterCommand item) {
        hold = item ;
    }
    
    public void excute(Request req){
        req.callMethod(req, hold);
        // hold.excuteCommandFuncation(hold , req);
    }
    public static ScrumClasses scrumClassesRequest() {
        commands();
        Scanner scan = new Scanner(System.in);
        boolean run = true;
        ScrumClasses scrum = INVALID;
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
    public static  void commands() {
        EnumSet.allOf(ScrumClasses.class).forEach(action -> System.out.println(action));
    }
}
// boolean run  = true; 
// ScrumClasses value = INVALID; 
// Scanner myObj = new Scanner(System.in);  // Create a Scanner object
// System.out.println("Enter username");
// commands();
// while(run) {
//     try {
//         System.out.print(myObj);
//         value =  ScrumClasses.valueOf("".toUpperCase());
//         run = false;
//     } catch (Exception e) {
//         System.out.println("not a valid input");
//     }
// }
// myObj.close();
// return value; 