package scrummaster.dataclasses;
import java.sql.*;

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

    public ScrumTeam findSprint(Connection con, int tableId) {
        String selectEmployee = "select * from sprint"
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
