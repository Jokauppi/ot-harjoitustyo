package productivetime;

import productivetime.dao.ActivityDao;
import productivetime.domain.Activity;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("test");
        try {
            ActivityDao db = new ActivityDao();
            Activity a = new Activity("testi");
            db.create(a);
            TimeUnit.SECONDS.sleep(5);
            Activity last = db.readLast();
            System.out.println(last);
            System.out.println(db.update(last));
            Activity b = new Activity("sleep");
            db.create(b);
        } catch (SQLException | InterruptedException e){
            System.out.println(e);
        }

    }
}
