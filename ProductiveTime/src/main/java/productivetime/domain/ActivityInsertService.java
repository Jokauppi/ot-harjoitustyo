package productivetime.domain;

import productivetime.dao.SQLActivityDao;
import java.sql.SQLException;

public class ActivityInsertService {
    private SQLActivityDao activityDB;

    public ActivityInsertService(SQLActivityDao activityDB) {
        this.activityDB = activityDB;
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

    public Activity retypeActivity(Activity activity, String newType) throws SQLException {
        return activityDB.retype(activity, newType);
    }

}
