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

    public List<Activity> getActivitiesOnDayOf(ZonedDateTime date) {
        List<Activity> activitiesList = new ArrayList<>();

        long beginning = TimeService.startOfZoned(date).toEpochSecond();
        long end = TimeService.startOfZoned(date).plusDays(1).toEpochSecond();

        // List of activities partly or entirely in the range is fetched
        try {
            activitiesList = activityDB.list(beginning, end);
        } catch (SQLException e) {
            System.out.println(e);
        }

        //if the first activity doesn't start at the start of the given day an empty "No Data" activity is added at the start to fill the chart properly;
        if (!activitiesList.isEmpty() && activitiesList.get(0).getStart() > beginning) {
            ArrayList<Activity> replacingList = new ArrayList<>();
            replacingList.add(new Activity(0, "No Data", beginning, (int) (activitiesList.get(0).getStart() - beginning)));
            replacingList.addAll(activitiesList);
            activitiesList = replacingList;
        }

        // The first and last activities of the list are resized to include only the parts that are inside the range
        if (!activitiesList.isEmpty()) {
            return truncateFirstAndLastActivity(activitiesList, beginning, end);
        }
        return activitiesList;
    }

    private List<Activity> truncateFirstAndLastActivity(List<Activity> activitiesList, long beginning, long end) {

        Activity last = activitiesList.get(activitiesList.size() - 1);

        int duration = last.getDuration();

        // If the last activity on the list is still ongoing, its duration set so that the activity ends at the current time
        if (duration == 0) {
            duration = (int) (TimeService.nowSeconds() - last.getStart());
        }
        // If the last activity ends before the beginning of the range, an empty list is returned
        // This should happen when the method is called with a beginning that is later than the current moment
        if (last.getStart() + duration < beginning) {
            return new ArrayList<>();
        }
        // If the last activity goes partly above the range, the duration is shortened so that the activity ends at the end of the range
        if (last.getStart() + duration > end) {
            duration = (int) (end - last.getStart());
        }

        activitiesList.set(activitiesList.size() - 1, new Activity(last.getId(), last.getType(), last.getStart(), duration));

        Activity first = activitiesList.get(0);

        // If the first activity goes partly below the range, the start time is moved and the duration is shortened so that the activity starts at the beginning of the range and ends at the same time as before
        if (first.getStart() < beginning) {
            activitiesList.set(0, new Activity(first.getId(), first.getType(), beginning, first.getDuration() - (int) (beginning - first.getStart())));
        }

        return activitiesList;
    }

}
