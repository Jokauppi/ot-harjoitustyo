package productivetime.domain;

import productivetime.dao.ActivityDao;
import java.sql.SQLException;

public class ActivityInsertControl {
    private ActivityDao activityDB;

    public ActivityInsertControl(ActivityDao activityDB) {
        try {
            this.activityDB = activityDB;
            this.activityDB.initializeDB();
        } catch (SQLException e) {
            System.out.println("database creation unsuccessful");
        }
    }

    public void addActivity(String type) {
        try {
            Activity last = activityDB.readLast();
            if (last != null) {
                activityDB.update(last);
            }
            Activity next = new Activity(type);
            activityDB.create(next);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
