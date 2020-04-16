package productivetime.dao;

import productivetime.domain.Activity;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivityDao implements Dao<Activity> {

    private final String dbName;
    private Connection connection;
    private PreparedStatement stmt;
    private Statement s;

    public ActivityDao(String dbName) throws SQLException {
        this.dbName = dbName;
        initializeDB();
    }

    public void initializeDB() throws SQLException {
        startConnection();
        s.execute("CREATE TABLE IF NOT EXISTS Activities (id INTEGER PRIMARY KEY, type TEXT NOT NULL, start INTEGER, duration INTEGER);");
        closeConnection();
    }

    private void startConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
        s = connection.createStatement();
    }

    private void closeConnection() throws SQLException {
        s.close();
        connection.close();
    }

    @Override
    public void create(Activity activity, long start) throws SQLException {
        startConnection();
        stmt = connection.prepareStatement("INSERT INTO Activities (type, start) VALUES (?,?);");
        stmt.setString(1, activity.getType());
        stmt.setLong(2, start);
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
    public Activity update(Activity activity, long end) throws SQLException {
        startConnection();
        if (activity != null) {
            stmt = connection.prepareStatement("UPDATE Activities SET duration = ? WHERE id = ?;");
            stmt.setLong(1, end - activity.getStart());
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
        stmt = connection.prepareStatement("SELECT * FROM Activities WHERE (start+duration >= ? AND start <= ?)" +
                " OR (start+duration > ? AND start+duration <= ?)" +
                " OR (start >= ? AND start < ?)" +
                " OR (duration IS NULL AND start <= ?);");
        stmt.setLong(1, end);
        stmt.setLong(2, beginning);
        stmt.setLong(3, beginning);
        stmt.setLong(4, end);
        stmt.setLong(5, beginning);
        stmt.setLong(6, end);
        stmt.setLong(7, end);
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
