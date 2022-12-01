package scrummaster.enums;

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
        hold.excuteCommandFuncation(hold , req);
    }
    public static ScrumClasses scrumClassesRequest() {
        boolean run  = true; 
        ScrumClasses value = INVALID; 
        Scanner scan = new Scanner(System.in);
        while(run) {
            try {
                value =  ScrumClasses.valueOf(scan.nextLine().toUpperCase());
                run = false;
            } catch (Exception e) {
                value = INVALID;
            }
        }
        scan.close();
        return value; 

    }
}
