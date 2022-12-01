package scrummaster.dataclasses;

import java.sql.*;

import scrummaster.DBConnection;

public class Employee extends ScrumMasterCommand {
    // TODO Aaron

    private int id;
    private String fName;
    private String lName;
    private int roleAssignment;
    private int devTeamId;
    public Employee() {
    }
    public Employee(int id, String fName, String lName, int roleAssignment, int devTeamId) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.roleAssignment = roleAssignment;
        this.devTeamId = devTeamId;
    }

    public int getID() {
        return id;
    }

    public String getFirstName() {
        return fName;
    }

    public String getLastName() {
        return lName;
    }

    public int getRoleAssignment() {
        return roleAssignment;
    }

    public int getDevTeamID() {
        return devTeamId;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setFirstName(String fName) {
        this.fName = fName;
    }

    public void setLastName(String lName) {
        this.lName = lName;
    }

    public void setRoleAssignment(int roleAssignment) {
        this.roleAssignment = roleAssignment;
    }

    public void setDevTeamID(int devTeamID) {
        this.devTeamId = devTeamID;
    }

    public static Employee findByID(int id) throws SQLException {

        Connection con = DBConnection.CONNECTION;

        PreparedStatement ps = con.prepareStatement("SELECT * FROM employee WHERE employee_id = ?");

        ps.setInt(0, id);

        ResultSet rs = ps.executeQuery();

        if (!rs.first()) {
            return null;
        }
        int eId = rs.getInt("employee_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        int role = rs.getInt("role_assignment");
        int devTeamID = rs.getInt("dev_team_id");

        Employee emp = new Employee(eId, firstName, lastName, role, devTeamID);

        return emp;
    }

    public static int SaveEmployees(Employee... emps) throws SQLException {

        Connection con = DBConnection.CONNECTION;

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO employee (first_name, last_name, role_assignment, dev_team_id) VALUES (?, ?, ?, ?)");
        int count = 0;
        for (int i = 0; i < emps.length; i++) {
            ps.setString(0, emps[i].getFirstName());
            ps.setString(1, emps[i].getLastName());
            ps.setInt(2, emps[i].getRoleAssignment());
            ps.setInt(3, emps[i].getDevTeamID());
            count++;
        }
        return count;
    }

}
