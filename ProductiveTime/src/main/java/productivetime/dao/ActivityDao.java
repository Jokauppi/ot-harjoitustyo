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
        initializeDB();
    }

    public void initializeDB() throws SQLException {
        startConnection();
        s.execute("CREATE TABLE IF NOT EXISTS Activities (id INTEGER PRIMARY KEY, type TEXT NOT NULL, start INTEGER, duration INTEGER);");
        closeConnection();
    }

    private void startConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:activity.db");
        s = connection.createStatement();
    }

    private void closeConnection() throws SQLException {
        s.close();
        connection.close();
    }

    @Override
    public void create(Activity object) throws SQLException {
        startConnection();
        stmt = connection.prepareStatement("INSERT INTO Activities (type, start) VALUES (?,(SELECT strftime('%s','now')));");
        stmt.setString(1, object.getType());
        stmt.executeUpdate();
        stmt.close();
        closeConnection();
    }

    @Override
    public Activity read(Integer key) throws SQLException {
        startConnection();
        stmt = connection.prepareStatement("SELECT * FROM Activities WHERE id = ?;");
        stmt.setInt(1, key);
        ResultSet activity = stmt.executeQuery();
        if (activity.next()) {
            Activity lastActivity = new Activity(activity.getInt("id"), activity.getString("type"), activity.getInt("start"), activity.getInt("duration"));
            stmt.close();
            closeConnection();
            return lastActivity;
        } else {
            stmt.close();
            closeConnection();
            return null;
        }
    }

    @Override
    public Activity readLast() throws SQLException {
        startConnection();
        ResultSet lastItem = s.executeQuery("SELECT * FROM Activities ORDER BY id DESC LIMIT 1;");
        if (lastItem.next()) {
            Activity lastActivity = new Activity(lastItem.getInt("id"), lastItem.getString("type"), lastItem.getInt("start"), lastItem.getInt("duration"));
            closeConnection();
            return lastActivity;
        } else {
            closeConnection();
            return null;
        }
    }

    @Override
    public Activity update(Activity activity) throws SQLException {
        startConnection();
        if (activity != null) {
            stmt = connection.prepareStatement("UPDATE Activities SET duration = (SELECT strftime('%s','now')-?) WHERE id = ?;");
            stmt.setLong(1, activity.getStart());
            stmt.setInt(2, activity.getId());
            stmt.executeUpdate();
            stmt.close();
            closeConnection();
            return readLast();
        }
        closeConnection();
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        startConnection();
        stmt = connection.prepareStatement("DELETE FROM Activities WHERE id = ?;");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        stmt.close();
        closeConnection();
    }

    @Override
    public boolean deleteLast() throws SQLException {
        Activity lastItem = readLast();
        startConnection();
        if (lastItem != null) {
            stmt = connection.prepareStatement("DELETE FROM Activities WHERE id = ?;");
            stmt.setInt(1, lastItem.getId());
            stmt.executeUpdate();
            stmt.close();
            return true;
        }
        closeConnection();
        return false;
    }

    @Override
    public List<Activity> list() throws SQLException {
        startConnection();
        List<Activity> activityList = new ArrayList<>();
        ResultSet rs = s.executeQuery("SELECT * FROM Activities;");
        while (rs.next()) {
            activityList.add(new Activity(rs.getInt("id"), rs.getString("type"), rs.getInt("start"), rs.getInt("duration")));
        }
        closeConnection();
        return activityList;
    }

    @Override
    public List<Activity> list(long beginning, long end) throws SQLException {
        startConnection();
        List<Activity> activityList = new ArrayList<>();
        stmt = connection.prepareStatement("SELECT * FROM Activities WHERE start+duration > ? OR start < ? OR (start < ? AND start+duration > ?);");
        stmt.setLong(1, beginning);
        stmt.setLong(2, end);
        stmt.setLong(3, beginning);
        stmt.setLong(4, end);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            activityList.add(new Activity(rs.getInt("id"), rs.getString("type"), rs.getInt("start"), rs.getInt("duration")));
        }
        stmt.close();
        closeConnection();
        return activityList;
    }

    @Override
    public void clear() throws SQLException {
        startConnection();
        s.execute("DROP TABLE IF EXISTS Activities;");
        closeConnection();
        initializeDB();
    }

}
