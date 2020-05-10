package productivetime.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import productivetime.dao.SQLActivityDao;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Offers methods to get different lists of activities from the given database object.
 */
public class ActivityListService {

    private SQLActivityDao activityDB;

    /**
     * Sets the database object to be used by other methods.
     * @param activityDB the database object.
     */
    public ActivityListService(SQLActivityDao activityDB) {
        this.activityDB = activityDB;
    }

    /**
     * Gets a list of all activities ordered from oldest to newest.
     * @return a list of activities
     */
    public List<Activity> getActivities() {
        List<Activity> activitiesList = new ArrayList<>();
        try {
            activitiesList = activityDB.list();
        } catch (SQLException ignored) {
        }
        return activitiesList;
    }

    /**
     * Gets a list of all activities ordered from newest to oldest.
     * @return a list of activities
     */
    public List<Activity> getActivitiesReversed() {
        List<Activity> activitiesList = getActivities();
        Collections.sort(activitiesList);
        return activitiesList;
    }

    /**
     * Gets an ObservableList of all activities ordered from newest to oldest
     * @return an ObservableList of activities
     * @see ObservableList
     */
    public ObservableList<Activity> getActivitiesObservable() {
        ObservableList<Activity> activitiesOList = FXCollections.observableArrayList();
        activitiesOList.addAll(getActivitiesReversed());
        return activitiesOList;
    }

    /**
     * Gets a list activities that occurred on the specified date. Activities that took place partly on an another day
     * are truncated to include only the portion that took place on the specified date. Possible ongoing activities are
     * included and their duration is calculated to the current time of the end of the day, whichever is earlier.
     * @param date of activities to get as ZonedDateTime
     * @see ZonedDateTime
     * @return a list of activities
     */
    public List<Activity> getActivitiesOnDayOf(ZonedDateTime date) {
        List<Activity> activitiesList = new ArrayList<>();

        long beginning = TimeService.startOfZoned(date).toEpochSecond();
        long end = TimeService.startOfZoned(date).plusDays(1).toEpochSecond();

        // List of activities partly or entirely in the range is fetched
        try {
            activitiesList = activityDB.list(beginning, end);
        } catch (SQLException ignored) {
        }

        if (!activitiesList.isEmpty()) {
            //if the first activity doesn't start at the start of the given day an empty "No Data" activity is added at the start to fill the chart properly;
            if (activitiesList.get(0).getStart() > beginning) {
                ArrayList<Activity> replacingList = new ArrayList<>();
                replacingList.add(new Activity(0, "No Data", beginning, (int) (activitiesList.get(0).getStart() - beginning)));
                replacingList.addAll(activitiesList);
                activitiesList = replacingList;
            }
            // The first and last activities of the list are resized to include only the parts that are inside the range
            return truncateFirstAndLastActivity(activitiesList, beginning, end);
        }

        return activitiesList;
    }

    private List<Activity> truncateFirstAndLastActivity(List<Activity> activitiesList, long beginning, long end) {

        Activity last = activitiesList.get(activitiesList.size() - 1);

        // If the last activity on the list is still ongoing, its duration set so that the activity ends at the current time
        if (last.isOngoing()) {
            last.setDuration((int) (TimeService.nowSeconds() - last.getStart()));
        }

        // If the last activity ends before the beginning of the range, an empty list is returned
        // This should happen when the method is called with a beginning that is later than the current moment
        if (last.getEnd() < beginning) {
            return new ArrayList<>();
        }
        // If the last activity goes partly above the range, the duration is shortened so that the activity ends at the end of the range
        if (last.getEnd() > end) {
            last.setDuration((int) (end - last.getStart()));
        }

        activitiesList.set(activitiesList.size() - 1, last);

        Activity first = activitiesList.get(0);

        // If the first activity goes partly below the range, the start time is moved and the duration is shortened so that the activity starts at the beginning of the range and ends at the same time as before
        if (first.getStart() < beginning) {
            activitiesList.set(0, new Activity(first.getId(), first.getType(), beginning, first.getDuration() - (int) (beginning - first.getStart())));
        }

        return activitiesList;
    }

    /**
     * Returns a list of the most frequently used types of activity ordered from most to least frequent.
     * @return a list of types.
     */
    public List<String> getAllTypes() {

        List<String> types;

        try {
            types = activityDB.listTypes();
        } catch (SQLException sqlException) {
            return new ArrayList<>();
        }

        return types;
    }

    /**
     * Returns the requested amount of most frequently used types of activities ordered from most to least frequent.
     * @param amount number of types to get.
     * @return a list of types.
     */
    public List<String> getFrequentTypes(int amount) {

        List<String> types = getAllTypes();

        List<String> ntypes = new ArrayList<>();

        for (int i = 0; i < Math.min(amount, types.size()); i++) {
            ntypes.add(types.get(i));
        }

        return ntypes;
    }

    /**
     * Returns the duration of activities of the specified type on all days in the specified range.
     * @param type type of activity to be searched for
     * @param beginning beginning time of range to be searched
     * @param end end time of range to be searched
     * @return a map with keys depicting dates and values depicting duration of activities of the given type on the day of the key.
     * The key is the start of a date in seconds from Unix epoch.
     */
    public Map<Long, Integer> getDurationsOfTypeOnDaysBetween(String type, ZonedDateTime beginning, ZonedDateTime end) {

        ZonedDateTime date = TimeService.startOfZoned(beginning);
        end = TimeService.startOfZoned(end);

        HashMap<Long, Integer> durations = new HashMap<>();

        while (date.compareTo(end) < 0) {

            List<Activity> activities = getActivitiesOnDayOf(date);

            if (!activities.isEmpty()) {
                durations.put(date.toEpochSecond(), 0);

                for (Activity activity : activities) {
                    if (activity.getType().equals(type)) {
                        durations.put(date.toEpochSecond(), durations.get(date.toEpochSecond()) + activity.getDuration());
                    }
                }
            }

            date = date.plusDays(1);
        }

        return durations;
    }

    /**
     * Returns whether the most recent activity is being tracked.
     * @return true if the last activity is being tracked. Otherwise false.
     */
    public boolean isTrackingOn() {
        try {
            Activity last = activityDB.readLast();
            return last.isOngoing();
        } catch (SQLException e) {
            return false;
        }
    }
}
