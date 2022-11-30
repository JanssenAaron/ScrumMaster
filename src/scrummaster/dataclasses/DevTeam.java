package scrummaster.dataclasses;

import java.util.ArrayList;
import java.sql.*;
import scrummaster.DBConnection;

public class DevTeam {
    // TODO Aaron
    private int id;
    private int scrumTeamId;
    private int sprintId;

    public DevTeam(int id, int scrumTeamId, int sprintId) {
        this.id = id;
        this.scrumTeamId = scrumTeamId;
        this.sprintId = sprintId;
    }

    public int getID() {
        return id;
    }

    public int getSprintID() {
        return sprintId;
    }

    public int getScrumTeamId() {
        return scrumTeamId;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setSprintID(int sprintID) {
        this.sprintId = sprintID;
    }

    public void setScrumTeamID(int scrumTeamId) {
        this.scrumTeamId = scrumTeamId;
    }

    public static DevTeam findByID(int id) throws SQLException {
        Connection con = DBConnection.CONNECTION;

        PreparedStatement ps = con.prepareStatement("SELECT * FROM dev_team WHERE dev_team_id = ?");

        ps.setInt(0, id);
        ResultSet rs = ps.executeQuery();
        if (!rs.first()) {
            return null;
        }
        int dtID = rs.getInt("dev_team_id");
        int scID = rs.getInt("scrum_team_id");
        int sprtID = rs.getInt("sprint_id");

        DevTeam team = new DevTeam(dtID, scID, sprtID);
        return team;

    }

    public static ArrayList<DevTeam> findByScrumTeamID(int scrumTeamId) throws SQLException {
        Connection con = DBConnection.CONNECTION;

        PreparedStatement ps = con.prepareStatement("SELECT * FROM dev_team WHERE scrum_team_id = ?");

        ps.setInt(0, scrumTeamId);
        ResultSet rs = ps.executeQuery();
        ArrayList<DevTeam> teams = new ArrayList<>();
        while (rs.next()) {
            int dtID = rs.getInt("dev_team_id");
            int scID = rs.getInt("scrum_team_id");
            int sprtID = rs.getInt("sprint_id");
            teams.add(new DevTeam(dtID, scID, sprtID));
        }

        return teams;
    }

    public static ArrayList<DevTeam> findBySprintID(int sprintID) throws SQLException {
        Connection con = DBConnection.CONNECTION;

        PreparedStatement ps = con.prepareStatement("SELECT * FROM dev_team WHERE sprint_id = ?");

        ps.setInt(0, sprintID);
        ResultSet rs = ps.executeQuery();
        ArrayList<DevTeam> teams = new ArrayList<>();
        while (rs.next()) {
            int dtID = rs.getInt("dev_team_id");
            int scID = rs.getInt("scrum_team_id");
            int sprtID = rs.getInt("sprint_id");
            teams.add(new DevTeam(dtID, scID, sprtID));
        }

        return teams;
    }

    public static int saveDevTeams(DevTeam... teams) throws SQLException {

        Connection con = DBConnection.CONNECTION;

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO employee (scrum_team_id, sprint_id) VALUES (?, ?)");
        int count = 0;
        for (int i = 0; i < teams.length; i++) {
            ps.setInt(0, teams[i].getScrumTeamId());
            ps.setInt(1, teams[i].getSprintID());
            count++;
        }
        return count;
    }
}
