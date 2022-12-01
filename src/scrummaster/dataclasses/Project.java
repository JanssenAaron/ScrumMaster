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
    
    public ScrumTeam findProjectById(Connection con, int tableId) {
        String selectEmployee = "select * from project"
                + " where id = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(selectEmployee);
            pstmt.setInt(1, tableId);
            ResultSet rsscrumteam = pstmt.executeQuery();
            return new ScrumTeam(rsscrumteam.getInt("employee_id"));
        } catch (SQLException e) {
            return null;
        }
    }
    
    public ScrumTeam findByScrumTeamId(Connection con, int tableId) {
        String selectEmployee = "select * from project"
                + " inner join scrum_team "
                + "scrum_id.scrum_team_id = project.scrum_team_id"
                + "where project.scrum_team_id = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(selectEmployee);
            pstmt.setInt(1, tableId);
            ResultSet rsscrumteam = pstmt.executeQuery();
            return new ScrumTeam(rsscrumteam.getInt("employee_id"));
        } catch (SQLException e) {
            return null;
        }
    }
    


    public void getFuncation(Scanner scan, Request req) {
        System.out.println("options: find scrum team id ");
        System.out.println(scan.next());
        if ("find".equalsIgnoreCase(scan.next())) {
            int hold =0 ;
            System.out.println("enter an int");
            while(!scan.hasNextInt())
                hold = scan.nextInt();
            findByScrumTeamId(DBConnection.CONNECTION, hold);
        }
        
    }
    
    public void createFunction(Scanner scan, Request req) {
        System.out.println("no support create funcation we are here fjkaldsjf kla");
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
