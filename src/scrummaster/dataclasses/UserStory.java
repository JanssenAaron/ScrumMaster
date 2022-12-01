package scrummaster.dataclasses;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import scrummaster.DBConnection;

public class UserStory extends ScrumMasterCommand {
    
    private int id;
    private int priority;
    private String description;
    private Date completedDate;
    private Date timeEstimate;
    private int projectId;

    public UserStory() {

    }

    public UserStory(int id, int priority, String description, Date completedDate, Date timeEstimate, int projectId) {
        this.id = id;
        this.priority = priority;
        this.description = description;
        this.completedDate = completedDate;
        this.timeEstimate = timeEstimate;
        this.projectId = projectId;
    }

    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public Date getTimeEstimate() {
        return timeEstimate;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public void setTimeEstimate(Date timeEstimate) {
        this.timeEstimate = timeEstimate;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public static UserStory findById(int id) throws SQLException {
        PreparedStatement pstmt = DBConnection.CONNECTION
                .prepareStatement("select * from user_story where id = ?");
        pstmt.setInt(0, id);
        ResultSet results = pstmt.executeQuery();
        if (results.next()) {
            UserStory us = new UserStory();
            us.setId(results.getInt("story_id"));
            us.setPriority(results.getInt("priority"));
            us.setDescription(results.getString("description"));
            us.setCompletedDate(results.getDate("completed_date"));
            us.setTimeEstimate(results.getDate("time_estimate"));
            us.setProjectId(results.getInt("project_id"));
            return us;
        }
        return null;
    }

    public static ArrayList<UserStory> findByProjectId(int id) throws SQLException {
        PreparedStatement pstmt = DBConnection.CONNECTION
                .prepareStatement("select * from user_story where project_id = ?");
        pstmt.setInt(0, id);
        ResultSet results = pstmt.executeQuery();
        ArrayList<UserStory> stories = new ArrayList<>();
        while (results.next()) {
            UserStory us = new UserStory();
            us.setId(results.getInt("story_id"));
            us.setPriority(results.getInt("priority"));
            us.setDescription(results.getString("description"));
            us.setCompletedDate(results.getDate("completed_date"));
            us.setTimeEstimate(results.getDate("time_estimate"));
            us.setProjectId(results.getInt("project_id"));
            stories.add(us);
        }
        return stories;
    }

    public static int saveUserStories(UserStory... stories) throws SQLException {
        PreparedStatement pstmt = DBConnection.CONNECTION
                .prepareStatement("insert into user_story (story_id, priority, description, completed_date, time_estimate, project_id) values (?, ?, ?, ?, ?, ?)");
        int count = 0;
        for (int i = 0; i < stories.length; i++) {
            UserStory story = stories[i];
            pstmt.setInt(0, story.getId());
            pstmt.setInt(1, story.getPriority());
            pstmt.setString(2, story.getDescription());
            pstmt.setDate(3, story.getCompletedDate());
            pstmt.setDate(4, story.getTimeEstimate());
            pstmt.setInt(5, story.getProjectId());
            count += pstmt.executeUpdate();
        }
        return count;
    }

}
