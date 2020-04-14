package productivetime.domain;

import productivetime.dao.ActivityDao;
import java.sql.SQLException;

public class ActivityInsertControl {
    private ActivityDao activityDB;
    private TimeService timeService;

    public ActivityInsertControl(ActivityDao activityDB) {
        this.timeService = new TimeService();
        try {
            this.activityDB = activityDB;
            this.activityDB.initializeDB();
        } catch (SQLException e) {
            System.out.println("database creation unsuccessful");
        }
    }

    public void addActivity(String type) {
        long now = TimeService.nowSeconds();
        try {
            Activity last = activityDB.readLast();
            if (last != null) {
                activityDB.update(last, now);
            }
            Activity next = new Activity(type);
            activityDB.create(next, now);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
