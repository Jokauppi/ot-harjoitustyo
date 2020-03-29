package productivetime.dao;

import productivetime.domain.Activity;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivityDao implements Dao<Activity, Integer> {

    private Connection connection;
    private PreparedStatement stmt;
    private Statement s;

    public ActivityDao() throws SQLException {
        startConnection();
        s = connection.createStatement();
        s.execute("CREATE TABLE IF NOT EXISTS Activities (id INTEGER PRIMARY KEY, type TEXT NOT NULL, start INTEGER, duration INTEGER);");
    }

    private void startConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:activity.db" );
    }

    private void closeConnection() throws SQLException {
        connection.close();
    }

    @Override
    public void create(Activity object) throws SQLException {
        stmt = connection.prepareStatement("INSERT INTO Activities (type, start) VALUES (?,(SELECT strftime('%s','now')));");
        stmt.setString(1, object.getType());
        stmt.executeUpdate();
        stmt.close();
    }

    @Override
    public Activity read(Integer key) throws SQLException {
        return null;
    }

    public Activity readLast() throws SQLException {
        ResultSet rs = s.executeQuery("SELECT * FROM Activities ORDER BY id DESC LIMIT 1;");
        if (rs.next()){
            return new Activity(rs.getInt("id"), rs.getString("type"), rs.getInt("start"), rs.getInt("duration"));
        } else {
            return null;
        }
    }

    @Override
    public Activity update(Activity activity) throws SQLException {
        if (activity != null){
            stmt = connection.prepareStatement("UPDATE Activities SET duration = (SELECT strftime('%s','now')-?) WHERE id = ?;");
            stmt.setInt(1, activity.getStart());
            stmt.setInt(2, activity.getId());
            stmt.executeUpdate();
            stmt.close();
            return readLast();
        }
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {

    }

    @Override
    public List<Activity> list() throws SQLException {
        List<Activity> activityList = new ArrayList<>();
        startConnection();
        ResultSet rs = s.executeQuery("SELECT * FROM Activities;");
        while (rs.next()){
            activityList.add(new Activity(rs.getInt("id"), rs.getString("type"), rs.getInt("start"), rs.getInt("duration")));
        }
        closeConnection();
        return activityList;
    }


}
