package scrummaster.dataclasses;
import java.sql.*;

import scrummaster.DBConnection;
import scrummaster.enums.Request;

public class ScrumTeam extends ScrumMasterCommand{
    
    /**
     * @return the id
     */
    private int id;
    public ScrumTeam(){
    }
    public ScrumTeam(int id) {
        this.id = id;
    }


//----------------------get-----------------
public void getFunction( Request req) {
    System.out.println("get the id for Scrum Team");
    findSprint(getInt()).toString();
}
public ScrumTeam findSprint( int tableId) {
    String selectEmployee = "select * from sprint"
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
//-------------------------delete---------------------------------------
public void deleteFunction( Request req) {
    System.out.println("place the id you want to get rid of");
    deleteScrumTeam(getId()).toString();
}
public ScrumTeam deleteScrumTeam(int scID){
    String selectItem = "DELETE FROM scrum_team WHERE scrum_team_id  = ?;";
    try{
        PreparedStatement rs = DBConnection.CONNECTION.prepareStatement(selectItem);
        rs.setInt(scID, 1);
        ResultSet rsSprint = rs.executeQuery();
        return new ScrumTeam(rsSprint.getInt("scrum_team_id"));
    }
    catch(SQLException e){
        System.out.println(e);
    }
    return null;
}
//----------------create-------------------------------------
public void insertFunction(){
    System.out.println("place the id you want to get insert");
    insertScrumTeam(getId()).toString();
}
public ScrumTeam insertScrumTeam(int scID){
            //int scrumId, String summary
            String insert = "INSERT INTO scrum_team" + " (scrum_team_id ) values" + " (?);";
            try {
                PreparedStatement pstmt = DBConnection.CONNECTION.prepareStatement(insert);
                pstmt.setInt(1, scID);
    
                ResultSet rsSprint = pstmt.executeQuery();
                return new ScrumTeam(rsSprint.getInt("scrum_team_id") );
            } catch (SQLException e) {
                System.out.println(e);
            }
            return null;
}




























    public void FindById(int id) {
        this.setId(id);
    }

    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    public  void excuteCommandFuncation() {
        System.out.println("no support funcation");
    }
}
