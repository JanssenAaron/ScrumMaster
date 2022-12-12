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
    private java.sql.Time startDate;
    private java.sql.Time endDate;
    private String notes;
    private int projectId;

    public Sprint() {
    }

    public Sprint(int tableId, java.sql.Time startDataAndTime, java.sql.Time endDataAndTime, String text,
            int projectID) {
        id = tableId;
        this.startDate = startDataAndTime;
        this.endDate = endDataAndTime;
        this.notes = text;
        this.projectId = projectID;
    }

    public static Sprint findSprint( int tableId) {
        String selectEmployee = "select * from sprint"
                + " where id = ?";
        try {
            PreparedStatement pstmt = DBConnection.CONNECTION.prepareStatement(selectEmployee);
            pstmt.setInt(1, tableId);

            ResultSet rsSprint = pstmt.executeQuery();
            return new Sprint(rsSprint.getInt("sprint_id"), rsSprint.getTime("start_date"),
                    rsSprint.getTime("end_date"), rsSprint.getNString("notes"), rsSprint.getInt("project_id"));
        } catch (SQLException e) {

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
            return new Sprint(rsSprint.getInt("sprint_id"), rsSprint.getTime("start_date"),
                    rsSprint.getTime("end_date"), rsSprint.getNString("notes"), rsSprint.getInt("project_id"));
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
        listInput((Sprint[]) listSprint.toArray());
      }
    public static LinkedList<Sprint>  selectAllSprint(){
        LinkedList<Sprint> sprintTable = new LinkedList<>();
        String selectItem = "select * from sprint";
        try{
            ResultSet rsSprint = DBConnection.CONNECTION.prepareStatement(selectItem).executeQuery();
            while(rsSprint.next()){
                sprintTable.add(new Sprint(rsSprint.getInt("sprint_id"), rsSprint.getTime("start_date"),
                rsSprint.getTime("end_date"), rsSprint.getString("notes"), rsSprint.getInt("project_id")));
            }

        }
        catch(SQLException e){
            System.out.println(e);
        }
        return sprintTable;
    }
    ///----------------------------delete----------------------------------------
    public void deleteFunction( Request req) {
        deleteSprintById(1);
    }
    private  Sprint  deleteSprintById(int sID ){
        ArrayList<Integer> sprintAllID = new ArrayList<Integer>();
        sprintAllID.add(sID);

        ArrayList<Integer> devteamAllID = getAllID("select dev_team_id FROM dev_team WHERE sprint_id = ",sprintAllID);

        // employee id must be changed so we must talk about
        ArrayList<Integer> employeeID = getAllID("select employee_id FROM employee WHERE dev_team_id = ", devteamAllID);

        deleteAllID(linkingSprintBacklogItemSprint, sprintAllID);

        deleteAllID(standupSprintItem, sprintAllID);
        
        updateEmployee(devteamAllID);

        deleteAllID(devTeamItemSprint, sprintAllID);
              ///look at the problem that certain key is not getting delete 
        return null;
    }
     // ------------------------basic 
    // -------------------------------get--------------------set------------------------------------------------------------------------------
    /**
     * @return the startDate
     */
    public java.sql.Time getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(java.sql.Time startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public java.sql.Time getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(java.sql.Time endDate) {
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
        return id+ " "+ startDate+" "+ endDate + " " + notes + " " +projectId;
       }
}