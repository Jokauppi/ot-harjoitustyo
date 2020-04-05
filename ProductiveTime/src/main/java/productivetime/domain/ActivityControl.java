package productivetime.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import productivetime.dao.ActivityDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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

    public ObservableList<Activity> getActivities() {
        ObservableList<Activity> activitiesOList = FXCollections.observableArrayList();
        List<Activity> activitiesList = new ArrayList<>();
        try {
            activitiesList = activityDB.list();
        } catch (SQLException e) {
            System.out.println(e);
        }
        Collections.sort(activitiesList);
        activitiesOList.addAll(activitiesList);
        return activitiesOList;
    }
}
