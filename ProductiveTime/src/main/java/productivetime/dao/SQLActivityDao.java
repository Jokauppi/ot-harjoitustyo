package productivetime.dao;

import productivetime.domain.Activity;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class offers various methods to store activities into an SQLite database and list the stored activities.
 *
 * @see Activity
 */
public class SQLActivityDao implements Dao<Activity> {

    private final String dbName;
    private Connection connection;
    private PreparedStatement stmt;
    private Statement s;

    /**
     * Creates new database and initializes the database table to be used.
     *
     * @param dbName Name of database to be used
     * @throws SQLException if database table creation is unsuccessful.
     */
    public SQLActivityDao(String dbName) throws SQLException {
        this.dbName = dbName;
        initializeDB();
    }

    private void initializeDB() throws SQLException {
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

    /**
     * Inserts a new activity into the database.
     * @param activity Activity to be added
     * @param start Start time associated with the activity in seconds since Unix epoch
     * @throws SQLException if for example table doesn't exist or database is locked.
     */
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

    /**
     * Gives an activity with the corresponding key.
     *
     * @param key key to be searched for
     * @return activity associated with the given key, null if no such activity exists.
     * @throws SQLException if for example table doesn't exist or database is locked.
     */
    @Override
    public Activity read(Integer key) throws SQLException {
        startConnection();
        stmt = connection.prepareStatement("SELECT * FROM Activities WHERE id = ?;");
        stmt.setInt(1, key);
        ResultSet activity = stmt.executeQuery();
        if (activity.next()) {
            Integer duration = activity.getInt("duration");
            if (activity.wasNull()) {
                duration = null;
            }
            Activity lastActivity = new Activity(activity.getInt("id"), activity.getString("type"), activity.getInt("start"), duration);
            stmt.close();
            closeConnection();
            return lastActivity;
        } else {
            stmt.close();
            closeConnection();
            return null;
        }
    }

    /**
     * Gives latest activity in the database.
     *
     * @return the newest activity in the database, null if database is empty
     * @throws SQLException if for example table doesn't exist or database is locked.
     */
    @Override
    public Activity readLast() throws SQLException {
        startConnection();
        ResultSet lastItem = s.executeQuery("SELECT * FROM Activities ORDER BY id DESC LIMIT 1;");
        if (lastItem.next()) {
            Integer duration = lastItem.getInt("duration");
            if (lastItem.wasNull()) {
                duration = null;
            }
            Activity lastActivity = new Activity(lastItem.getInt("id"), lastItem.getString("type"), lastItem.getInt("start"), duration);
            closeConnection();
            return lastActivity;
        } else {
            closeConnection();
            return null;
        }
    }

    /**
     * Assigns a duration to the given activity.
     * @param activity The activity to be updated.
     * @param end The end time that is used to calculate the duration in seconds since Unix epoch
     * @return the updated activity.
     * @throws SQLException if for example table doesn't exist or database is locked.
     */
    @Override
    public Activity update(Activity activity, long end) throws SQLException {

        Activity newActivity = null;

        if (activity != null) {
            startConnection();
            stmt = connection.prepareStatement("UPDATE Activities SET duration = ? WHERE id = ?;");
            stmt.setLong(1, end - activity.getStart());
            stmt.setInt(2, activity.getId());
            stmt.executeUpdate();
            stmt.close();
            closeConnection();
            newActivity = read(activity.getId());
        }

        return newActivity;
    }

    /**
     * Changes the type of an activity in the database.
     *
     * @param activity Activity to be changed.
     * @param newType New type to be given to the activity
     * @return the updated activity.
     * @throws SQLException if for example table doesn't exist or database is locked.
     */
    @Override
    public Activity retype(Activity activity, String newType) throws SQLException {

        Activity newActivity = null;

        if (activity != null) {
            startConnection();
            stmt = connection.prepareStatement("UPDATE Activities SET type = ? WHERE id = ?;");
            stmt.setString(1, newType);
            stmt.setInt(2, activity.getId());
            stmt.executeUpdate();
            stmt.close();
            closeConnection();
            newActivity = read(activity.getId());
        }

        return newActivity;
    }

    /**
     * Deletes the activity associated with the given key.
     *
     * @param key Key of the activity to be deleted.
     * @throws SQLException if for example table doesn't exist or database is locked.
     */
    @Override
    public void delete(Integer key) throws SQLException {
        startConnection();
        stmt = connection.prepareStatement("DELETE FROM Activities WHERE id = ?;");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        stmt.close();
        closeConnection();
    }

    /**
     * Deletes the latest activity in the database.
     *
     * @throws SQLException if for example table doesn't exist or database is locked.
     */
    @Override
    public void deleteLast() throws SQLException {
        Activity lastItem = readLast();
        if (lastItem != null) {
            delete(lastItem.getId());
        }
    }

    /**
     * Gives a list of all activities stored in the database.
     *
     * @return list of activities
     * @throws SQLException if for example table doesn't exist or database is locked.
     */
    @Override
    public List<Activity> list() throws SQLException {
        startConnection();
        ResultSet rs = s.executeQuery("SELECT * FROM Activities;");
        List<Activity> activityList = listFromResultSet(rs);
        closeConnection();
        return activityList;
    }

    /**
     * Gives a list of activities stored in the database that are entirely or partly in the given time frame.
     * Also ongoing activities that have started before the end of the given time frame are included.
     * @param beginning Start of the time frame in seconds since Unix epoch.
     * @param end Start of the time frame in seconds since Unix epoch.
     * @return list of activities
     * @throws SQLException if for example table doesn't exist or database is locked.
     */
    @Override
    public List<Activity> list(long beginning, long end) throws SQLException {
        startConnection();
        stmt = connection.prepareStatement("SELECT * FROM Activities WHERE (start+duration >= ? AND start <= ?) OR (start+duration > ? AND start+duration <= ?)" +
                " OR (start >= ? AND start < ?) OR (duration IS NULL AND start <= ?);");
        stmt.setLong(1, end);
        stmt.setLong(2, beginning);
        stmt.setLong(3, beginning);
        stmt.setLong(4, end);
        stmt.setLong(5, beginning);
        stmt.setLong(6, end);
        stmt.setLong(7, end);
        ResultSet rs = stmt.executeQuery();
        List<Activity> activityList = listFromResultSet(rs);
        stmt.close();
        closeConnection();
        return activityList;
    }

    private List<Activity> listFromResultSet(ResultSet rs) throws SQLException {
        List<Activity> activityList = new ArrayList<>();
        while (rs.next()) {
            Integer duration = rs.getInt("duration");
            if (rs.wasNull()) {
                duration = null;
            }
            activityList.add(new Activity(rs.getInt("id"), rs.getString("type"), rs.getInt("start"), duration));
        }
        return activityList;
    }

    /**
     * Clears the database and initializes the needed tables again.
     * @throws SQLException
     */
    @Override
    public void clear() throws SQLException {
        startConnection();
        s.execute("DROP TABLE IF EXISTS Activities;");
        closeConnection();
        initializeDB();
    }

}
