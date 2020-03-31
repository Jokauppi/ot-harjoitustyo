package productivetime.domain;

import productivetime.dao.ActivityDao;

import java.sql.SQLException;
import java.util.List;

public class ActivityControl {
    private ActivityDao activityDB;

    public ActivityControl(ActivityDao activityDB) {
        try {
            this.activityDB = activityDB;
            this.activityDB.initializeDB();
        } catch (SQLException e) {
            System.out.println("database creation unsuccessful");
        }
    }

    public void addActivity(String type){
        try {
            Activity last = activityDB.readLast();
            if (last != null){
                activityDB.update(last);
            }
            Activity next = new Activity(type);
            activityDB.create(next);
        } catch (SQLException e){
            System.out.println(e);
        }
    }

    public List<Activity> listActivities(){
        try {
            List<Activity> allActivities = activityDB.list();
            return allActivities;
        } catch (SQLException e){
            return null;
        }
    }
}
