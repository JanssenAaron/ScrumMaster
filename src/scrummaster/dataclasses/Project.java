package scrummaster.dataclasses;

import java.sql.*;
import java.util.ArrayList;
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
    private ScrumTeam scrumgroup;
    
    public Project(int tableId, int scrumId, String summary) {
        id = tableId;
        scrumTeamId = scrumId;
        description = summary;
    }
    public Project(int tableId, int scrumId, String summary,ScrumTeam scrum) {
        id = tableId;
        scrumTeamId = scrumId;
        description = summary;
        scrumgroup = scrum;
    }
    
    public Project() {
    }
    public void listFunction(Request req){
        String sql = "select * from project";
        try{
             ResultSet x =   DBConnection.CONNECTION.prepareStatement(sql).executeQuery();
             while(x.next()){
                System.out.println(new Project(x.getInt("project_id"), x.getInt("scrum_team_id"), x.getString("description") ));
             }
        }
        catch(Exception e){
            System.out.println(e);
        }
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
    
    public Project findByScrumTeamId( int tableId) {
        String selectEmployee = "select * from project"
                + " inner join scrum_team on "
                + "scrum_id.scrum_team_id = project.scrum_team_id"
                + "where project.scrum_team_id = ?";
        try {
            PreparedStatement pstmt = DBConnection.CONNECTION.prepareStatement(selectEmployee);
            pstmt.setInt(1, tableId);
            ResultSet rsscrumteam = pstmt.executeQuery();
            rsscrumteam.next();
            return new Project(rsscrumteam.getInt("employee_id"), 
            rsscrumteam.getInt("scrum_team_id"),rsscrumteam.getString("description"), 
            new ScrumTeam(rsscrumteam.getInt("scrum_team_id") ) );
            
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
        String insert = "INSERT INTO project" + " (scrum_team_id, description ) values" + " (?,?);";
        try {
            PreparedStatement pstmt = DBConnection.CONNECTION.prepareStatement(insert);
            pstmt.setInt(1, scrum_Id);
            pstmt.setObject(2, summarys);

            ResultSet rsSprint = pstmt.executeQuery();
            return new Project(rsSprint.getInt("project_id"), rsSprint.getInt("scrum_team_id"),
             rsSprint.getString("description"));
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    //------------------delete-------------------------
    public void deleteFunction( Request req) {

        deleteProject(getInt("place an id to delete from project"));
    }
    public static void main(String[] args){
        Project x = new Project();
        x.deleteProject(1);
    }
    public void deleteProject(int pID){
        ArrayList<Integer> projectAllID = new ArrayList<Integer>();
        projectAllID.add(pID);

        ArrayList<Integer> sprintAllID = getAllID("select sprint_id FROM sprint WHERE project_id = ", projectAllID);
        ArrayList<Integer> devteamAllID = getAllID("select dev_team_id FROM dev_team WHERE sprint_id = ",sprintAllID);

        // employee id must be changed so we must talk about
        ArrayList<Integer> employeeID = getAllID("select employee_id FROM employee WHERE dev_team_id = ", devteamAllID);

        ArrayList<Integer> userStoryyID = getAllID("select story_id FROM user_story WHERE project_id = ", projectAllID);
       
        deleteAllID(linkingSprintBacklogItemStory, userStoryyID);
        deleteAllID(linkingSprintBacklogItemSprint, sprintAllID);

        deleteAllID(userStoryItem, projectAllID);
        deleteAllID(standupSprintItem, sprintAllID);
        
        updateEmployee(devteamAllID);

        deleteAllID(devTeamItemSprint, sprintAllID);
              ///look at the problem that certain key is not getting delete 

        deleteAllID(sprintItem, projectAllID);

        deleteAllID(projectItemProjectId, projectAllID);
    }
    //get 
    

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
        if(scrumgroup != null)
            return id+ " "+ scrumTeamId+" "+ description + scrumgroup;
        return id+ " "+ scrumTeamId+" "+ description;
       }
    
}
