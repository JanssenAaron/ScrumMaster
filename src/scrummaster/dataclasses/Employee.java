package scrummaster.dataclasses;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

import scrummaster.DBConnection;
import scrummaster.enums.Request;

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

    private ArrayList<Employee> getAllEmployees() {
        try {
            ResultSet emps = DBConnection.CONNECTION.createStatement().executeQuery("SELECT * FROM EMPLOYEE");
            ArrayList<Employee> employees = new ArrayList<>();
            while (emps.next()) {
                int id = emps.getInt(1);
                String fName = emps.getString(2);
                String lName = emps.getString(3);
                int ra = emps.getInt(4);
                int devId = emps.getInt(5);
                Employee newGuy = new Employee(id, fName, lName, ra, devId);
                employees.add(newGuy);
            }
            return employees;

        } catch (SQLException sqlE) {
            System.out.println(sqlE.getMessage());
            return null;
        }

    }

    public void listFunction(Request req) {

        ArrayList<Employee> emps = getAllEmployees();
        for (Employee emp : emps) {
            System.out.println(emp);
        }

    }

    public void deleteFunction(Request req) {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print("Employee ID to delete: ");
            while (!scan.hasNextInt()) {
                System.out.print("Employee ID to delete: ");
                scan.next();
            }
            int id = scan.nextInt();
            PreparedStatement psDelScrumM = DBConnection.CONNECTION
                    .prepareStatement("DELETE FROM scrum_master WHERE scrum_master_id = ?");
            PreparedStatement psDelEmpl = DBConnection.CONNECTION
                    .prepareStatement("DELETE FROM employee WHERE employee_id = ?");
            psDelScrumM.setInt(1, id);
            psDelEmpl.setInt(1, id);
            psDelScrumM.executeUpdate();
            psDelEmpl.executeUpdate();
            System.out.println("Deleted employee ID: " + Integer.toString(id));

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            System.out.println("Could not delete employee");
        }
    }

    public void insertFunction(Request req) {
        // int id = getInt();
        String fName = getString();
        String lName = getString();
        int ra = getInt();
        int devID = getInt();
        try {

            PreparedStatement ps = DBConnection.CONNECTION.prepareStatement(
                    "INSERT INTO employee(first_name, last_name, role_assignment, dev_team_id) VALUES (?, ?, ?, ?)");
            ps.setString(1, fName);
            ps.setString(2, lName);
            ps.setInt(3, ra);
            ps.setInt(4, devID);
            ps.executeUpdate();

        } catch (SQLException sqlE) {
            System.out.println(sqlE.getMessage());
        }
    }

    public String toString() {
        return "ID: " + getID() + " Name: " + getLastName() + ", " + getFirstName() + " Role: " + getRoleAssignment();
    }

}
