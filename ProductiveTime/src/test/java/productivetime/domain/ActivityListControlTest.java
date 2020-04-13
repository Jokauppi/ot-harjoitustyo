package productivetime.domain;

import javafx.collections.ObservableList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import productivetime.dao.ActivityDao;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ActivityListControlTest {

    private static ActivityDao activityDao;
    private static ActivityInsertControl activityInsertControl;
    private static ActivityListControl activityListControl;

    @BeforeClass
    public static void beforeClass() throws Exception {
        activityDao = new ActivityDao();
        activityDao.clear();
        activityInsertControl = new ActivityInsertControl(activityDao);
        activityListControl = new ActivityListControl(activityDao);
        activityInsertControl.addActivity("activity 1");
        TimeUnit.SECONDS.sleep(2);
        activityInsertControl.addActivity("activity 2");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        activityDao.clear();
    }

    @Test
    public void getActivityList() {
        List<Activity> activities = activityListControl.getActivities();
        assertEquals(new Activity("activity 1"), activities.get(0));
    }

    @Test
    public void getActivityListReversed() {
        List<Activity> activities = activityListControl.getActivitiesReversed();
        assertEquals(new Activity("activity 2"), activities.get(0));
    }

    @Test
    public void getActivityListAsObservableList() {
        ObservableList<Activity> activities = activityListControl.getActivitiesObservable();
        assertEquals(new Activity("activity 2"), activities.get(0));
    }

}