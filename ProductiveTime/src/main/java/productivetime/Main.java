package productivetime;

import productivetime.dao.ActivityDatabase;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("test");
        try {
            ActivityDatabase db = new ActivityDatabase();
            db.initialize();
        } catch (SQLException e){
            System.out.println(e);
        }

    }
}
