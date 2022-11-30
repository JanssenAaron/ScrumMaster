package scrummaster.dataclasses;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import scrummaster.DBConnection;

public class Standup {
    
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

    public static Standup findById(int id) throws SQLException {
        PreparedStatement pstmt = DBConnection.CONNECTION
                .prepareStatement("select * from standup where id = ?");
        pstmt.setInt(0, id);
        ResultSet results = pstmt.executeQuery();
        if (results.next()) {
            Standup st = new Standup();
            st.setNote(results.getString("note"));
            st.setDate(results.getDate("date_of_standup"));
            st.setSprintId(results.getInt("sprint_id"));
            return st;
        }
        return null;
    }

    public static int saveStandups(Standup... sts) throws SQLException {
        PreparedStatement pstmt = DBConnection.CONNECTION
                .prepareStatement("insert into standup (note, date_of_standup, sprint_id) values (?, ?, ?)");
        int count = 0;
        for (int i = 0; i < sts.length; i++) {
            Standup st = sts[i];
            pstmt.setString(0, st.getNote());
            pstmt.setDate(1, st.getDate());
            pstmt.setInt(2, st.getSprintId());
            count += pstmt.executeUpdate();
        }
        return count;
    }

}
