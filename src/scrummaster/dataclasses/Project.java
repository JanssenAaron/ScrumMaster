package scrummaster.dataclasses;

import java.sql.*;
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
    //--------------------------------------get--------------------------------------------
    public void getFunction( Request req) {
        Scanner scan = new Scanner(System.in);
        System.out.println("options: find scrum team 'project_id' or 'scrum_id'  ");
        System.out.println(scan.next());
        if ("project_id".equalsIgnoreCase(scan.next())) 
            findProjectById( getInt()).toString();
        else if("scrum_id".equalsIgnoreCase(scan.next()))
            findByScrumTeamId( getInt()).toString();
        
    }
    public ScrumTeam findProjectById( int tableId) {
        String selectEmployee = "select * from project"
                + " where id = ?";
        try {
            PreparedStatement pstmt = DBConnection.CONNECTION.prepareStatement(selectEmployee);
            pstmt.setInt(1, tableId);
            ResultSet rsscrumteam = pstmt.executeQuery();
            return new ScrumTeam(rsscrumteam.getInt("employee_id"));
        } catch (SQLException e) {
            return null;
        }
    }
    
    public ScrumTeam findByScrumTeamId( int tableId) {
        String selectEmployee = "select * from project"
                + " inner join scrum_team "
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
        createProject(getInt(), getString() );
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
        String selectItem = "DELETE FROM project WHERE sprint_id = ?;";
        try{
            PreparedStatement rs = DBConnection.CONNECTION.prepareStatement(selectItem);
            rs.setInt(pID, 1);
            ResultSet rsSprint = rs.executeQuery();
            return new Project(rsSprint.getInt("project_id"), rsSprint.getInt("scrum_team_id"),
             rsSprint.getNString("description"));
   
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return null;
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
    
}
