package scrummaster.dataclasses;

import java.sql.*;
import java.util.LinkedList;

import scrummaster.DBConnection;
import scrummaster.enums.Request;

public class ScrumTeam extends ScrumMasterCommand {

    /**
     * @return the id
     */
    private int id;

    public ScrumTeam() {
    }

    public ScrumTeam(int id) {
        this.id = id;
    }

    // ----------------------get-----------------
    public void getFunction(Request req) {
        System.out.println("get the id for Scrum Team");
        findSprint(getInt()).toString();
    }

    public ScrumTeam findSprint(int tableId) {
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

    // -------------------------delete---------------------------------------
    public void deleteFunction(Request req) {
        System.out.println("place the id you want to get rid of");
        deleteScrumTeam(getId()).toString();
    }

    public ScrumTeam deleteScrumTeam(int scID) {
 
        LinkedList  scrumAllID = new LinkedList<Integer>();
        scrumAllID.add(scID);

            LinkedList projectAllID = getAllID("select project_id FROM project WHERE scrum_team_id = ", scrumAllID);
            LinkedList sprintAllID = getAllID("select sprint_id FROM sprint WHERE project_id = ", projectAllID);
            LinkedList devteamAllID = getAllID("select dev_team_id FROM sprint WHERE scrum_team_id = ", scrumAllID );
            // mergeLinkedList( devteamAllID, getAllID("select dev_team_id FROM sprint WHERE sprint_id = ", sprintAllID ) );
                        //employee id must be changed so we must talk about 
            LinkedList employeeID = getAllID("select employee_id FROM sprint WHERE dev_team_id = ", devteamAllID);
            
            LinkedList userStoryyID = getAllID("select user_story FROM sprint WHERE story_id = ", projectAllID);

            deleteAllID(linkingSprintBacklogItemStory, userStoryyID);
            deleteAllID(linkingSprintBacklogItemSprint, sprintAllID);

            deleteAllID(userStoryItem, projectAllID);
            deleteAllID(standupSprintItem, sprintAllID);

            deleteAllID(scrumMasterItemScrumTeam, projectAllID);

            deleteAllID(devTeamItemScrumTeam, scrumAllID);
            deleteAllID(devTeamItemSprint, sprintAllID);

            deleteAllID(sprintItem, projectAllID);

            deleteAllID(projectItem, scrumAllID);

            deleteAllID(scrumTeamItem, scrumAllID);



        return null;
    }

    // String linkingSprintBacklogItem = "DELETE FROM linking_sprint_backlog WHERE
    // scrum_team_id = ?;";
    // String userStoryItem = "DELETE FROM user_story WHERE sprint_id = ?;";
    // String standupSprintItem = "DELETE FROM standup WHERE sprint_id = ?;";
    // String scrumMasterItem = "DELETE FROM scrum_master WHERE sprint_id = ?;";
    // String employeeDevItem = "DELETE FROM employee WHERE sprint_id = ?;";
    // String devTeamItem = "DELETE FROM dev_team WHERE sprint_id = ?;";
    // String sprintItem = "DELETE FROM sprint WHERE sprint_id = ?;";
    // String projectItem = "select project_id FROM project WHERE scrum_team_id = "
    // + scrumId + ";";


    // ----------------create-------------------------------------
    public void insertFunction() {
        System.out.println("place the id you want to get insert");
        insertScrumTeam(getId()).toString();
    }

    public ScrumTeam insertScrumTeam(int scID) {
        // int scrumId, String summary
        String insert = "INSERT INTO scrum_team" + " (scrum_team_id ) values" + " (?);";
        try {
            PreparedStatement pstmt = DBConnection.CONNECTION.prepareStatement(insert);
            pstmt.setInt(1, scID);

            ResultSet rsSprint = pstmt.executeQuery();
            return new ScrumTeam(rsSprint.getInt("scrum_team_id"));
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

    public void excuteCommandFuncation() {
        System.out.println("no support funcation");
    }
}
