package scrummaster.dataclasses;

import java.sql.*;
import java.util.ArrayList;

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
        ArrayList<Sprint>  listSprint = selectAllSprint();
        listInput((Sprint[]) listSprint.toArray());
      }
    public static  ArrayList<Sprint>  selectAllSprint(){
        ArrayList<Sprint> sprintTable = new ArrayList<>();
        String selectItem = "select * from sprint";
        try{
            ResultSet rsSprint = DBConnection.CONNECTION.prepareStatement(selectItem).executeQuery();
            while(rsSprint.next()){
                sprintTable.add(new Sprint(rsSprint.getInt("sprint_id"), rsSprint.getTime("start_date"),
                rsSprint.getTime("end_date"), rsSprint.getNString("notes"), rsSprint.getInt("project_id")));
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
    private static Sprint  deleteSprintById(int id ){
        String selectItem = "DELETE FROM sprint WHERE sprint_id = ?;";
        try{
            DBConnection.CONNECTION.prepareStatement(linkingSprintBacklogItemSprint).executeQuery();
            DBConnection.CONNECTION.prepareStatement("update dev_team set sprint_id = null where sprint_id = " + id ).executeQuery();
            PreparedStatement rs = DBConnection.CONNECTION.prepareStatement(selectItem);
            rs.setInt(id, 1);
            ResultSet rsSprint = rs.executeQuery();
            rsSprint.next();
            return new Sprint(rsSprint.getInt("sprint_id"), rsSprint.getTime("start_date"),
            rsSprint.getTime("end_date"), rsSprint.getNString("notes"), rsSprint.getInt("project_id"));
   
        }
        catch(SQLException e){
            System.out.println(e);
        }
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

}
