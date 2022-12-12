package scrummaster.dataclasses;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

import scrummaster.DBConnection;
import scrummaster.enums.Request;

/**
 *
 * @author Jordan
 */
public class Sprint extends ScrumMasterCommand {
    private int id;
    private java.sql.Date startDate;
    private java.sql.Date endDate;
    private String notes;
    private int projectId;

    public Sprint() {
    }

    public Sprint(int tableId, java.sql.Date startDataAndTime, java.sql.Date endDataAndTime, String text,
            int projectID) {
        id = tableId;
        this.startDate = startDataAndTime;
        this.endDate = endDataAndTime;
        this.notes = text;
        this.projectId = projectID;
    }
    public void getFunction(Request req) {
        System.out.println(findSprint(getInt("what sprind id do you want:")));
    }
    public static Sprint findSprint( int tableId) {
        String selectEmployee = "select * from sprint"
                + " where sprint_id = ?";
        try {
            PreparedStatement pstmt = DBConnection.CONNECTION.prepareStatement(selectEmployee);
            pstmt.setInt(1, tableId);

            ResultSet rsSprint = pstmt.executeQuery();
            rsSprint.next();
            System.out.println(rsSprint.getDate("start_date"));
            return new Sprint(rsSprint.getInt("sprint_id"), rsSprint.getDate("start_date"),
                    rsSprint.getDate("end_date"), rsSprint.getString("notes"), rsSprint.getInt("project_id"));
        } catch (SQLException e) {
           System.out.println(e);
        }
        return null;
    }

    // ----------------------------------add sprint-----------------------------------------------------------------------------
    public static Sprint addSprint(java.sql.Time start, java.sql.Time end, String note, int proID) {
        String insert = "INSERT INTO SPRINT" + " (start_date,end_date, notes, project_id) values" + " (?,?,?,?);";
        try {
            PreparedStatement pstmt = DBConnection.CONNECTION.prepareStatement(insert);
            pstmt.setObject(1, start);
            pstmt.setObject(2, end);
            pstmt.setObject(3, note);
            pstmt.setInt(4, proID);

            ResultSet rsSprint = pstmt.executeQuery();
            return new Sprint(rsSprint.getInt("sprint_id"), rsSprint.getDate("start_date"),
                    rsSprint.getDate("end_date"), rsSprint.getString("notes"), rsSprint.getInt("project_id"));
        } catch (SQLException e) {

        }
        return null;
    }
    //select all 
    public void listFunction(Request req){
        LinkedList<Sprint>  listSprint = selectAllSprint();
        while(listSprint.size() != 0 ){
             System.out.println(listSprint.removeFirst());
        }
      }
    public static LinkedList<Sprint>  selectAllSprint(){
        LinkedList<Sprint> sprintTable = new LinkedList<>();
        String selectItem = "select * from sprint";
        try{
            ResultSet rsSprint = DBConnection.CONNECTION.prepareStatement(selectItem).executeQuery();
            while(rsSprint.next()){
                sprintTable.add(new Sprint(rsSprint.getInt("sprint_id"), rsSprint.getDate("start_date"),
                rsSprint.getDate("end_date"), rsSprint.getString("notes"), rsSprint.getInt("project_id")));
            }

        }
        catch(SQLException e){
            System.out.println(e);
        }
        return sprintTable;
    }
    
    ///----------------------------delete----------------------------------------
    public static void main(String[] args){
        Sprint x = new Sprint();
        x.deleteSprintById(1);
    }
    public void deleteFunction( Request req) {
        deleteSprintById(getInt("place an id to delete from sprint"));
    }
    private  Sprint  deleteSprintById(int sID ){
        ArrayList<Integer> sprintAllID = new ArrayList<Integer>();
        sprintAllID.add(sID);

        ArrayList<Integer> devteamAllID = getAllID("select dev_team_id FROM dev_team WHERE sprint_id = ",sprintAllID);

        // employee id must be changed so we must talk about

        deleteAllID(linkingSprintBacklogItemSprint, sprintAllID);

        deleteAllID(standupSprintItem, sprintAllID);
        
        updateEmployee(devteamAllID);

        deleteAllID(devTeamItemSprint, sprintAllID);
        deleteAllID(sprintItemSprintId, sprintAllID);
              ///look at the problem that certain key is not getting delete 
        return null;
    }
     // ------------------------basic 
    // -------------------------------get--------------------set------------------------------------------------------------------------------
    /**
     * @return the startDate
     */
    public java.sql.Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(java.sql.Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public java.sql.Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(java.sql.Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the projectId
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * @param projectId the projectId to set
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    public String toString(){//overriding the toString() method  
        return " sprint id: "+ id+ " start date: "+ startDate+" end date: "+ endDate + " notes: " + notes + " project_id " +projectId;
       }
}