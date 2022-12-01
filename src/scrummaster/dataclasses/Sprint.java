package scrummaster.dataclasses;


import java.sql.*;

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
    public Sprint(){
    }
    
    public Sprint(int tableId,java.sql.Time startDataAndTime,java.sql.Time endDataAndTime,String text, int projectID ){
        id = tableId;
        this.startDate = startDataAndTime;
        this.endDate = endDataAndTime;
        this.notes = text;
        this.projectId = projectID;
    }
    public Sprint findSprint(Connection con, int tableId ){
        String selectEmployee = "select * from sprint"
                + " where id = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(selectEmployee);
            pstmt.setInt(1, tableId);
            
            ResultSet rsSprint = pstmt.executeQuery();
            return new Sprint(rsSprint.getInt("sprint_id"), rsSprint.getTime("start_date"),
             rsSprint.getTime("end_date"), rsSprint.getNString("notes"), rsSprint.getInt("project_id"));
        }
        catch(SQLException e ){
            
        }
        return null;  
    }

    
      
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
