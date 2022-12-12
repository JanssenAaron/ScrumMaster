
package scrummaster.dataclasses;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import scrummaster.DBConnection;
import scrummaster.enums.*;

public class ScrumMasterCommand {
    protected static String linkingSprintBacklogItemSprint = "DELETE FROM linking_sprint_backlog WHERE sprint_id  =";
    protected String linkingSprintBacklogItemStory = "DELETE FROM linking_sprint_backlog WHERE story_id  =";

    protected String userStoryItem = "DELETE FROM user_story  WHERE project_id = ";
    protected String standupSprintItem = "DELETE FROM standup WHERE sprint_id = ";

    protected String scrumMasterItemScrumTeam = "DELETE FROM scrum_master  WHERE scrum_team_id = ";
    protected String scrumMasterItemSprint = "DELETE FROM scrum_master  WHERE sprint_id = ";

    protected String devTeamItemSprint = "DELETE FROM dev_team WHERE sprint_id = ";
    protected String devTeamItemScrumTeam = "DELETE FROM dev_team WHERE scrum_team_id = ";

    protected String sprintItem = "DELETE FROM sprint WHERE project_id = ";
    protected String sprintItemSprintId = "DELETE FROM sprint WHERE sprint_id = ";
    protected String projectItem = "DELETE FROM project WHERE scrum_team_id = ";
    protected String projectItemProjectId = "DELETE FROM project WHERE scrum_team_id = ";
    protected String scrumTeamItem = "DELETE FROM scrum_team WHERE scrum_team_id = ";

    // public void excuteCommandFuncation(ScrumMasterCommand scrum, Request req) {
    // req.callMethod(req, scrum);
    // }
    // this method should be used for get 1 element
    public void getFunction(Request req) {
        System.out.println("no support for get  hello ");
    }

    // this method should be used for adding elements to the database
    public void deleteFunction(Request req) {
        System.out.println("no support create function");
    }

    // this method should be used for select all the element
    public void insertFunction(Request req) {
        System.out.println("no support create function");
    }

    // this method should be used for select all the element
    public void updateFunction(Request req) {
        System.out.println("no support create function");
    }

    // this method should be used for select all the element
    public void listFunction(Request req) {
        System.out.println("no support create function");
    }

    public void listInput(ScrumMasterCommand[] rows) {
        for (ScrumMasterCommand x : rows)
            x.toString();
    }

    public int getInt(String prompt) {
        Scanner scan = new Scanner(System.in);
        System.out.print(prompt);
        while (!scan.hasNextInt()) {
            System.out.println("Not a valid integer");
            System.out.print(prompt);
            scan.next();
        }
        int input = scan.nextInt();
        return input;
    }

    public String getString(String prompt) {
        Scanner scan = new Scanner(System.in);
        System.out.print(prompt);
        String input = scan.nextLine();
        return input;
    }

    public Date readDate(String prompt) {
        Scanner scan = new Scanner(System.in);
        System.out.print(prompt);
        while (true) {
            try {
                String input = scan.nextLine();
                return Date.valueOf(input);
            } catch (IllegalArgumentException ex) {
                System.out.println("Not a valid date");
                System.out.print(prompt);
            }
        }

    }

    public void mergeLinkedList(LinkedList<Integer> mergeTo, LinkedList<Integer> merge) {
        while (merge.size() != 0) {
            mergeTo.add(merge.removeFirst());
        }
    }

    public ArrayList<Integer> getAllID(String scrum, ArrayList<Integer> scrumId) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        try {
            for (int i = 0; i < scrumId.size(); i++) {
                ResultSet rs = DBConnection.CONNECTION.prepareStatement(scrum + scrumId.get(i) + ";").executeQuery();
                while (rs.next()) {
                    list.add(rs.getInt(1));
                }
                
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e);
            return list;
        }
    }

    public void deleteAllID(String scrum, ArrayList<Integer> scrumId) {
        try {
            for (int i = 0; i < scrumId.size(); i++) {
                DBConnection.CONNECTION.createStatement().executeUpdate(scrum + scrumId.get(i) + ";");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void updateEmployee(ArrayList<Integer> scrumId){
        try {
            for (int i = 0; i < scrumId.size(); i++) {
                DBConnection.CONNECTION.createStatement().executeUpdate("update Employee set dev_team_id = null where dev_team_id = " + scrumId.get(i) + ";");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}