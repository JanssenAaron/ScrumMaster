package scrummaster.dataclasses;

import java.sql.*;
import java.util.LinkedList;
import java.util.Scanner;

import scrummaster.DBConnection;
import scrummaster.enums.*;;

/**
 *
 * @author Jordan
 */
public class Project extends ScrumMasterCommand {
    
    private int id;
    private int scrumTeamId;
    private String description;
    
    public Project(int tableId, int scrumId, String summary) {
        id = tableId;
        scrumTeamId = scrumId;
        description = summary;
    }
    
    public Project() {
    }
    public void listFunction(Request req){
        
      }
    //--------------------------------------get--------------------------------------------
    public void getFunction( Request req) {
        Scanner scan = new Scanner(System.in);
        System.out.println("options: find scrum team 'project_id' or 'scrum_id'  ");
        String command = scan.next();
        if ("project_id".equalsIgnoreCase(command)) 
            System.out.println(findProjectById( getInt("Enter project id: ")));
        else if("scrum_id".equalsIgnoreCase(command))
            findByScrumTeamId( getInt("Enter scrum team id: ")).toString();        
    }
    public Project findProjectById( int tableId) {
        String selectEmployee = "select * from project where project_id = ?;";
        try {
            PreparedStatement pstmt = DBConnection.CONNECTION.prepareStatement(selectEmployee);
            pstmt.setInt(1, tableId);
            ResultSet rsscrumteam = pstmt.executeQuery();
            rsscrumteam.next();
            return new Project(rsscrumteam.getInt("project_id"), rsscrumteam.getInt("scrum_team_id"), rsscrumteam.getString("description"));
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
    
    public ScrumTeam findByScrumTeamId( int tableId) {
        String selectEmployee = "select * from project"
                + " inner join scrum_team on "
                + "scrum_id.scrum_team_id = project.scrum_team_id"
                + "where project.scrum_team_id = ?";
        try {
            PreparedStatement pstmt = DBConnection.CONNECTION.prepareStatement(selectEmployee);
            pstmt.setInt(1, tableId);
            ResultSet rsscrumteam = pstmt.executeQuery();
            return new ScrumTeam(rsscrumteam.getInt("employee_id"));
        } catch (SQLException e) {
            return null;
        }
    }
    
    //-----------------------------------create-----------------------------------------------------------
    public void insertFunction( Request req) {
        createProject(getInt("Enter scrum team id: "), getString("Enter summary: ") );
    }
    public Project createProject(int scrum_Id, String summarys){
        //int scrumId, String summary
        String insert = "INSERT INTO SPRINT" + " (scrum_team_id, description ) values" + " (?,?);";
        try {
            PreparedStatement pstmt = DBConnection.CONNECTION.prepareStatement(insert);
            pstmt.setInt(1, scrum_Id);
            pstmt.setObject(2, summarys);

            ResultSet rsSprint = pstmt.executeQuery();
            return new Project(rsSprint.getInt("project_id"), rsSprint.getInt("scrum_team_id"),
             rsSprint.getNString("description"));
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    //------------------delete-------------------------
    public void deleteFunction( Request req) {
        System.out.println("place an id to delete");
        deleteProject(getId()).toString();
    }
    public Project deleteProject(int pID){
        LinkedList  projectAllID = new LinkedList<Integer>();
        projectAllID.add(pID);
        LinkedList sprintAllID = getAllID("select sprint_id FROM sprint WHERE project_id = ", projectAllID);
        LinkedList devteamAllID = getAllID("select dev_team_id FROM sprint WHERE scrum_team_id = ", scrumAllID );
        mergeLinkedList( devteamAllID, getAllID("select dev_team_id FROM sprint WHERE sprint_id = ", sprintAllID ) );
                    //employee id must be changed so we must talk about 
        LinkedList employeeID = getAllID("select employee_id FROM sprint WHERE dev_team_id = ", devteamAllID);
        
        LinkedList userStoryyID = getAllID("select user_story FROM sprint WHERE story_id = ", projectAllID);

        deleteAllID(linkingSprintBacklogItemStory, userStoryyID);
        deleteAllID(linkingSprintBacklogItemSprint, sprintAllID);

        deleteAllID(userStoryItem, projectAllID);
        deleteAllID(standupSprintItem, sprintAllID);

        deleteAllID(scrumMasterItemScrumTeam, projectAllID);

        deleteAllID(devTeamItemScrumTeam, scrumAllID);
        deleteAllID(devTeamItemSprint, sprintAllID);

        deleteAllID(sprintItem, projectAllID);

        deleteAllID(projectItem, scrumAllID);

        deleteAllID(scrumTeamItem, scrumAllID);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @return the scrumTeamId
     */
    public int getScrumTeamId() {
        return scrumTeamId;
    }

    /**
     * @param scrumTeamId the scrumTeamId to set
     */
    public void setScrumTeamId(int scrumTeamId) {
        this.scrumTeamId = scrumTeamId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    public String toString(){//overriding the toString() method  
        return id+ " "+ scrumTeamId+" "+ description;
       }
    
}
