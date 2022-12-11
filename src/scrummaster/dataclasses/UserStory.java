package scrummaster.dataclasses;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;

import scrummaster.DBConnection;
import scrummaster.enums.Request;

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
                .prepareStatement("select * from user_story where story_id = ?");
        pstmt.setInt(1, id);
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
        pstmt.setInt(1, id);
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

    public static ArrayList<UserStory> listUserStories() throws SQLException {
        PreparedStatement pstmt = DBConnection.CONNECTION
                .prepareStatement("select * from user_story");
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
                .prepareStatement(
                        "insert into user_story (story_id, priority, description, completed_date, time_estimate, project_id) values (?, ?, ?, ?, ?, ?)");
        int count = 0;
        for (int i = 0; i < stories.length; i++) {
            UserStory story = stories[i];
            pstmt.setInt(1, story.getId());
            pstmt.setInt(2, story.getPriority());
            pstmt.setString(3, story.getDescription());
            pstmt.setDate(4, story.getCompletedDate());
            pstmt.setDate(5, story.getTimeEstimate());
            pstmt.setInt(6, story.getProjectId());
            count += pstmt.executeUpdate();
        }
        return count;
    }

    public static int deleteById(int id) throws SQLException {
        PreparedStatement pstmt = DBConnection.CONNECTION
                .prepareStatement("delete from user_story where id = ?");
        pstmt.setInt(0, id);
        return pstmt.executeUpdate();
    }

    public void getFunction(Request req) {
        try {
            System.out.println("Find by:\n(0) User Story Id\n(1) Project Id");
            int choice;
            do {
                choice = getInt();
            } while (choice != 0 && choice != 1);
            switch (choice) {
                case 0:
                    System.out.println(findById(getInt()));
                    break;
                case 1:
                    findByProjectId(getInt()).forEach(System.out::println);
                    break;
            }
        } catch (SQLException e) {
            System.out.println("Could not get items from the database");
        }
    }

    public void deleteFunction(Request req) {
        try {
            System.out.printf("Deleted %d entries from the user story table\n", deleteById(getInt()));
        } catch (SQLException e) {
            System.out.println("Could not delete items from database");
        }
    }

    public void insertFunction(Request req) {
        try {
            UserStory us = new UserStory(getInt(), getInt(), getString(), readDate(), readDate(), getInt());
            int num = saveUserStories(us);
            System.out.printf("Saved %d entities in the database\n", num);
        } catch (SQLException e) {
            System.out.println("Could not save item to database");
        }
    }

    public void listFunction(Request req) {
        try {
            listUserStories().forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Could not read items from the database");
        }
    }

    @Override
    public String toString() {
        DateFormat dateFormat = DateFormat.getDateInstance();
        String completedString = completedDate == null ? "" : dateFormat.format(completedDate);
        String estimateString = dateFormat.format(timeEstimate);
        return String.format(
                "User Story:\nId = %d\nPriority = %d\nDescription = %s\nDate Completed = %s\nEstimated Completion = %s\nProject Id = %d",
                id, priority, description, completedString, estimateString, projectId);
    }

}
