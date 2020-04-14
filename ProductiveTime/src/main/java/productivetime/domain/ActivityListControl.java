package productivetime.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import productivetime.dao.ActivityDao;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityListControl {

    private ActivityDao activityDB;

    public ActivityListControl(ActivityDao activityDB) {
        this.activityDB = activityDB;
    }

    public List<Activity> getActivities() {
        List<Activity> activitiesList = new ArrayList<>();
        try {
            activitiesList = activityDB.list();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return activitiesList;
    }

    public List<Activity> getActivitiesReversed() {
        List<Activity> activitiesList = getActivities();
        Collections.sort(activitiesList);
        return activitiesList;
    }

    public ObservableList<Activity> getActivitiesObservable() {
        ObservableList<Activity> activitiesOList = FXCollections.observableArrayList();
        activitiesOList.addAll(getActivitiesReversed());
        return activitiesOList;
    }

    public List<Activity> getActivitiesDay(ZonedDateTime date) {
        List<Activity> activitiesList = new ArrayList<>();

        ZonedDateTime dateEnd = date.plusDays(1);

        long beginning = date.toEpochSecond();
        long end = dateEnd.toEpochSecond();

        try {
            activitiesList = activityDB.list(beginning, end);
        } catch (SQLException e) {
            System.out.println(e);
        }

        return truncateFirstAndLastActivity(activitiesList, beginning, end);
    }

    private List<Activity> truncateFirstAndLastActivity(List<Activity> activitiesList, long beginning, long end) {

        if (!activitiesList.isEmpty()) {

            Activity last = activitiesList.get(activitiesList.size() - 1);

            int duration = last.getDuration();

            if (duration == 0) {
                duration = (int) (TimeService.nowSeconds() - last.getStart());
            }
            if (last.getStart() + duration > end) {
                duration = (int) (end - last.getStart());
            }
            activitiesList.set(activitiesList.size() - 1, new Activity(last.getId(), last.getType(), last.getStart(), duration));

            Activity first = activitiesList.get(0);

            if (first.getStart() < beginning) {
                activitiesList.set(0, new Activity(first.getId(), first.getType(), beginning, first.getDuration() - (int) (beginning - first.getStart())));
            }
        }

        return activitiesList;
    }

}
