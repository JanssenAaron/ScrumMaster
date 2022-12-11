
package scrummaster.dataclasses;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Scanner;

import scrummaster.DBConnection;
import scrummaster.enums.*;

public class ScrumMasterCommand {
    protected String linkingSprintBacklogItemSprint = "DELETE FROM linking_sprint_backlog WHERE sprint_id  =";
    protected String linkingSprintBacklogItemStory = "DELETE FROM linking_sprint_backlog WHERE story_id  =";

    protected String userStoryItem = "DELETE FROM user_story  WHERE project_id = ";
    protected String standupSprintItem = "DELETE FROM standup WHERE sprint_id = ";

    protected String scrumMasterItemScrumTeam = "DELETE FROM scrum_master  WHERE scrum_team_id = ";
    protected String scrumMasterItemSprint = "DELETE FROM scrum_master  WHERE sprint_id = ";

    protected String devTeamItemSprint = "DELETE FROM dev_team WHERE sprint_id = ?;";
    protected String devTeamItemScrumTeam = "DELETE FROM dev_team WHERE scrum_team_id = ";


    protected String sprintItem = "DELETE FROM sprint WHERE sprint_id = ?;";
    protected String projectItem = "DELETE FROM project WHERE sprint_id = ";
    protected String scrumTeamItem = "DELETE FROM scrum_team WHERE scrum_team_id = ?;";
    // public void excuteCommandFuncation(ScrumMasterCommand scrum,  Request req) {
    //     req.callMethod(req, scrum);
    // }
    //this method should be used for get 1 element
    public void getFunction( Request req) {
        System.out.println("no support for get  hello ");
    }
    //this method should be used for adding elements to the database
    public void deleteFunction( Request req) {
        System.out.println("no support create function");
    }
    //this method should be used for select all the element
    public void insertFunction(Request req){
        System.out.println("no support create function");
    }
     //this method should be used for select all the element
     public void updateFunction(Request req){
         System.out.println("no support create function");
    }
    //this method should be used for select all the element
     public void listFunction(Request req){
       System.out.println("no support create function");
     }
     public void listInput(ScrumMasterCommand[] rows){
              for (ScrumMasterCommand x: rows)
                  x.toString();
     }
     public int getInt(){
        Scanner scan = new Scanner(System.in);
        while(!scan.hasNextInt()){
            System.out.println("Enter an int:");
            scan.next();
        }
        int input = scan.nextInt();
        return input;
     }
     public String getString(){;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a string:");
        String input = scan.next();
        return input;
     }
     public Date readDate() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a Date (yyyy-mm-dd):");
        String input = scan.next();
        return Date.valueOf(input);
     }
     public void  mergeLinkedList(LinkedList<Integer> mergeTo, LinkedList<Integer> merge){
        while(merge.size() != 0){
            mergeTo.add(merge.removeFirst());
        }
     }
     public LinkedList getAllID(String scrum, LinkedList<Integer> scrumId) {
        LinkedList list = new LinkedList<Integer>();
        try {
            while (scrumId.size() != 0) {
                ResultSet rs = DBConnection.CONNECTION.prepareStatement(scrum + scrumId.removeFirst() + ";")
                        .executeQuery();
                //list.add(rs.getInt(0));
                while (rs.next()) {
                    list.add(rs.getInt(0));
                }
            }
            return list;
        } catch (SQLException e) {
            return list;
        }
       // return list;
    }

    public void deleteAllID(String scrum, LinkedList scrumId) {
        try {
            while (scrumId.size() != 0) {
                ResultSet rs = DBConnection.CONNECTION.prepareStatement(scrum + scrumId.removeFirst() + ";")
                        .executeQuery();
            }
        } catch (SQLException e) {
        }
    }
}