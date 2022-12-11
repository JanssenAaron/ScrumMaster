package scrummaster.dataclasses;

import java.sql.*;
import scrummaster.DBConnection;
import scrummaster.enums.Request;

public class EmployeeRole extends ScrumMasterCommand {
    // TODO Aaron
    private int id;
    private String desc;

    public EmployeeRole() {
    }

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

        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
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
            ps.setString(1, roles[i].getDescription());
            count++;
        }
        return count;
    }

    public void insertFunction(Request req) {
        String role = getString("Enter a role description");
        try {
            PreparedStatement ps = DBConnection.CONNECTION
                    .prepareStatement("INSERT INTO employee_role(role_desc) VALUES (?)");
            ps.setString(1, role);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listFunction(Request req) {
        try {
            PreparedStatement ps = DBConnection.CONNECTION
                    .prepareStatement("SELECT role_desc FROM employee_role");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Role description: " + rs.getString(1));
            }
        } catch (SQLException sqlE) {
            System.out.println(sqlE.getMessage());
        }
    }

    public void getFunction(Request req) {
        try {
            System.out.println(findByID(getInt("Enter role assign id: ")));

        } catch (SQLException sqlE) {
            System.out.println(sqlE.getMessage());
        }
    }

    public String toString() {
        return "Role description: " + getDescription();
    }

    public void deleteFunction(Request req) {
        try {
            PreparedStatement psEmps = DBConnection.CONNECTION
                    .prepareStatement("UPDATE employee SET role_assignment = null WHERE role_assignment = ?");
            int id = getInt("Enter role id: ");
            psEmps.setInt(1, id);
            psEmps.executeUpdate();
            PreparedStatement ps = DBConnection.CONNECTION
                    .prepareStatement("DELETE FROM employee_role WHERE emp_role_id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException sqlE) {
            System.out.println(sqlE.getMessage());
        }
    }
}
