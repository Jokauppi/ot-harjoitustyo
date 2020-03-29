package productivetime.domain;

import productivetime.dao.ActivityDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivityControl {
    private ActivityDao activityDB;

    public ActivityControl() {
        try {
            activityDB = new ActivityDao();
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

    public void listActivities(){
        try {
            List<Activity> allActivities = activityDB.list();
            for (Activity activity : allActivities){
                System.out.println(activity);
            }
        } catch (SQLException e){
            System.out.println(e);
        }
    }
}
