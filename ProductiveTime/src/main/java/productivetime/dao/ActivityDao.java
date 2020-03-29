package productivetime.dao;

import productivetime.domain.Activity;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ActivityDao implements Dao<Activity, Integer> {

    private Connection connection;
    private PreparedStatement stmt;
    private Statement s;

    public ActivityDao() throws SQLException {
        startConnection();
        stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Activities (id INTEGER PRIMARY KEY, type TEXT, start INTEGER, duration INTEGER)");
        stmt.executeUpdate();
        closeConnection();
    }

    private void startConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:activity.db" );
    }

    private void closeConnection() throws SQLException {
        connection.close();
    }

    @Override
    public void create(Activity object) throws SQLException {
        startConnection();
        stmt = connection.prepareStatement("INSERT INTO Activities (type, start) VALUES (?,(SELECT strftime('%s','now')))");
        stmt.setString(1, object.getType());
        stmt.executeUpdate();
        closeConnection();
    }

    @Override
    public Activity read(Integer key) throws SQLException {
        return null;
    }

    @Override
    public Activity update(Activity object) throws SQLException {
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {

    }

    @Override
    public List<Activity> list() throws SQLException {
        return null;
    }
}
