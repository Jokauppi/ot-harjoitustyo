package productivetime;

import productivetime.dao.ActivityDao;
import productivetime.domain.Activity;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("test");
        try {
            ActivityDao db = new ActivityDao();
            Activity a = new Activity("testi");
            db.create(a);
        } catch (SQLException e){
            System.out.println(e);
        }

    }
}
