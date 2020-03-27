package productivetime.dao;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ActivityDatabase {

    private Connection db;
    private Statement s;

    public ActivityDatabase() throws SQLException {
        this.db = DriverManager.getConnection("jdbc:sqlite:activity.db" );
        this.s = db.createStatement();
    }

    public void initialize(){
        System.out.println("database created");
    }

}
