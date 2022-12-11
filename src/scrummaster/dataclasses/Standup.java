package scrummaster.dataclasses;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.sql.Date;

import scrummaster.DBConnection;
import scrummaster.enums.Request;

public class Standup extends ScrumMasterCommand {

    private String note;
    private Date date;
    private int sprintId;

    public Standup() {

    }

    public Standup(String note, Date date, int sprintId) {
        this.note = note;
        this.date = date;
        this.sprintId = sprintId;
    }

    public String getNote() {
        return note;
    }

    public Date getDate() {
        return date;
    }

    public int getSprintId() {
        return sprintId;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSprintId(int sprintId) {
        this.sprintId = sprintId;
    }

    public static ArrayList<Standup> findBySprintId(int sprintId) throws SQLException {
        PreparedStatement pstmt = DBConnection.CONNECTION
                .prepareStatement("select * from standup where sprint_id = ?");
        pstmt.setInt(1, sprintId);
        ResultSet results = pstmt.executeQuery();
        ArrayList<Standup> list = new ArrayList<>();
        while (results.next()) {
            Standup st = new Standup();
            st.setNote(results.getString("note"));
            st.setDate(results.getDate("date_of_standup"));
            st.setSprintId(results.getInt("sprint_id"));
            list.add(st);
        }
        return list;
    }

    public static ArrayList<Standup> listStandups() throws SQLException {
        PreparedStatement pstmt = DBConnection.CONNECTION
                .prepareStatement("select * from standup");
        ResultSet results = pstmt.executeQuery();
        ArrayList<Standup> list = new ArrayList<>();
        while (results.next()) {
            Standup st = new Standup();
            st.setNote(results.getString("note"));
            st.setDate(results.getDate("date_of_standup"));
            st.setSprintId(results.getInt("sprint_id"));
            list.add(st);
        }
        return list;
    }

    public static int saveStandups(Standup... sts) throws SQLException {
        PreparedStatement pstmt = DBConnection.CONNECTION
                .prepareStatement("insert into standup (note, date_of_standup, sprint_id) values (?, ?, ?)");
        int count = 0;
        for (int i = 0; i < sts.length; i++) {
            Standup st = sts[i];
            pstmt.setString(1, st.getNote());
            pstmt.setDate(2, st.getDate());
            pstmt.setInt(3, st.getSprintId());
            count += pstmt.executeUpdate();
        }
        return count;
    }

    public static int delete(int sprintId, Date date) throws SQLException {
        PreparedStatement pstmt = DBConnection.CONNECTION
                .prepareStatement("delete from standup where sprint_id = ? and date_of_standup = ?");
        pstmt.setInt(1, sprintId);
        pstmt.setDate(2, date);
        return pstmt.executeUpdate();
    }

    public void insertFunction(Request req) {
        Standup st = new Standup(getString(), readDate(), getInt());
        try {
            saveStandups(st);
        } catch (SQLException e) {
            System.out.println("Could not save standup to database");
        }
    }

    public void deleteFunction(Request req) {
        try {
            int num = delete(getInt(), readDate());
            System.out.printf("Deleted %d entries from standup table\n", num);
        } catch (SQLException e) {
            System.out.println("Could not delete from the database");
        }
    }

    public void getFunction(Request req) {
        try {
            ArrayList<Standup> sts = findBySprintId(getInt());
            sts.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Could not retrieve items from database");
        }
    }

    public void listFunction(Request req) {
        try {
            ArrayList<Standup> sts = listStandups();
            sts.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Could not retrieve items from database");
        }
    }

    @Override
    public String toString() {
        DateFormat dateFormat = DateFormat.getDateInstance();
        return String.format("Standup:\nSprint Id = %d\nNotes = %s\nDate = %s", sprintId, note,
                dateFormat.format(date));
    }

}
