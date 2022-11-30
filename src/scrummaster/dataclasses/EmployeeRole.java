package scrummaster.dataclasses;

import java.sql.*;
import scrummaster.DBConnection;

public class EmployeeRole {
    // TODO Aaron
    private int id;
    private String desc;

    public EmployeeRole(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public int getID() {
        return id;
    }

    public String getDescription() {
        return desc;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static EmployeeRole findByID(int id) throws SQLException {

        Connection con = DBConnection.CONNECTION;

        PreparedStatement ps = con.prepareStatement("SELECT * FROM employee_role WHERE emp_role_id = ?");

        ps.setInt(0, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.first()) {
            return null;
        }
        int erID = rs.getInt("emp_role_id");
        String erDesc = rs.getString("role_desc");

        EmployeeRole team = new EmployeeRole(erID, erDesc);
        return team;
    }

    public static int SaveEmployeeRoles(EmployeeRole... roles) throws SQLException {

        Connection con = DBConnection.CONNECTION;

        PreparedStatement ps = con.prepareStatement("INSERT INTO employee_role (role_desc) VALUES (?)");
        int count = 0;
        for (int i = 0; i < roles.length; i++) {
            ps.setString(0, roles[i].getDescription());
            count++;
        }
        return count;
    }

}
