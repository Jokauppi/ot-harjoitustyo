package productivetime.domain;

import productivetime.dao.SQLActivityDao;
import java.sql.SQLException;

/**
 * Offers methods to store new activities and rename them.
 */
public class ActivityInsertService {

    private SQLActivityDao activityDB;

    /**
     * Sets the database object to be used.
     *
     * @param activityDB database object to be used in storing activities
     */
    public ActivityInsertService(SQLActivityDao activityDB) {
        this.activityDB = activityDB;
    }

    /**
     * Stores a new activity with a start time equal to the current time.
     *
     * @param type type of activity to be added
     */
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

    /**
     * Changes the type of a stored activity.
     * @param activity activity to be changed
     * @param newType new type of the activity
     * @return the updated activity if changing type is successful, otherwise the original activity
     */
    public Activity retypeActivity(Activity activity, String newType) {
        try {
            return activityDB.retype(activity, newType);
        } catch (SQLException sqlException) {
            return activity;
        }
    }

}
