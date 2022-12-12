package scrummaster.dataclasses;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

import scrummaster.DBConnection;
import scrummaster.enums.Request;

public class ScrumTeam extends ScrumMasterCommand {

    /**
     * @return the id
     */
    private int id;
    private int scrum_master_id;
    private Project joinProject; 
    public ScrumTeam() {
    }

    public ScrumTeam(int id) {
        this.id = id;
    }
    public ScrumTeam(Project id) {
        joinProject = id; 

    }

    // ----------------------get-----------------
    public void getFunction(Request req) {
        System.out.println(findSprint(getInt("Enter id: ")));
    }

    public LinkedList<ScrumTeam> findSprint(int tableId) {
        String selectEmployee = "select * from project inner join scrum_team on scrum_team.scrum_team_id = project.scrum_team_id where project.scrum_team_id = ?;";

        LinkedList<ScrumTeam> scrumList = new LinkedList<>();
        try {
            PreparedStatement pstmt = DBConnection.CONNECTION.prepareStatement(selectEmployee);
            pstmt.setInt(1, tableId);
            ResultSet rsscrumteam = pstmt.executeQuery();
            while(rsscrumteam.next())
                scrumList.add(new ScrumTeam(new Project(rsscrumteam.getInt("project_id"), rsscrumteam.getInt("scrum_team_id"),
                rsscrumteam.getString("description"))));

            return scrumList;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }



    // -------------------------delete---------------------------------------
    public void deleteFunction(Request req) {
        deleteScrumTeam(getInt("place the id to delete from scrum team"));
    }

    public ScrumTeam deleteScrumTeam(int scID) {

        ArrayList<Integer> scrumAllID = new ArrayList<Integer>();
        scrumAllID.add(scID);

        ArrayList<Integer> projectAllID = getAllID("select project_id FROM project WHERE scrum_team_id = ", scrumAllID);

        ArrayList<Integer> sprintAllID = getAllID("select sprint_id FROM sprint WHERE project_id = ", projectAllID);
        ArrayList<Integer> devteamAllID = getAllID("select dev_team_id FROM dev_team WHERE scrum_team_id = ",scrumAllID);
        ArrayList<Integer> devTeam = getAllID("select dev_team_id FROM dev_team WHERE sprint_id = ",sprintAllID);


        // employee id must be changed so we must talk about
        ArrayList<Integer> employeeID = getAllID("select employee_id FROM employee WHERE dev_team_id = ", devteamAllID);

        ArrayList<Integer> userStoryyID = getAllID("select story_id FROM user_story WHERE project_id = ", projectAllID);
       
        deleteAllID(linkingSprintBacklogItemStory, userStoryyID);
        deleteAllID(linkingSprintBacklogItemSprint, sprintAllID);

        deleteAllID(userStoryItem, projectAllID);
        deleteAllID(standupSprintItem, sprintAllID);
       //look at dev team forgot to pull scrum and sprint
        deleteAllID(scrumMasterItemScrumTeam, devteamAllID);
        
        updateEmployee(devteamAllID);
        updateEmployee(devTeam);

        deleteAllID(devTeamItemScrumTeam, scrumAllID);
        deleteAllID(devTeamItemSprint, sprintAllID);
        System.out.println(projectAllID.get(0));
        deleteAllID(sprintItem, projectAllID);

        ///look at the problem that certain key is not getting delete 
        deleteAllID(projectItem, scrumAllID);

        deleteAllID(scrumTeamItem, scrumAllID);

        return null;
    }
    // ----------------create-------------------------------------
    public void insertFunction(Request req) {
        System.out.println("place the id you want to get insert");
        insertScrumTeam();

    }

    public ScrumTeam insertScrumTeam() {
        // int scrumId, String summary
        String insert = "INSERT INTO scrum_team (scrum_team_id) VALUES (DEFAULT)";
        try {
            int pstmt = DBConnection.CONNECTION.prepareStatement(insert).executeUpdate();

            System.out.println(pstmt);
            return new ScrumTeam( );
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

    public String toString() {// overriding the toString() method
        if(joinProject != null)
           return  joinProject + "";
        return " "+id + "  scrum master id:  " + scrum_master_id;
    }
}
