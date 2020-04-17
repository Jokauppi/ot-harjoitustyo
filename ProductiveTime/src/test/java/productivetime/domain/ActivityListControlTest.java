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
    private static ActivityListControl activityListControl;

    @BeforeClass
    public static void beforeClass() throws Exception {
        activityDao = new ActivityDao("test.db");
        activityDao.clear();
        activityListControl = new ActivityListControl(activityDao);
        activityDao.create(new Activity("activity 1"), 100);
        activityDao.update(activityDao.readLast(), 200);
        activityDao.create(new Activity("activity 2"), 200);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        activityDao.clear();
    }

    @Test
    public void getActivityList() {
        List<Activity> activities = activityListControl.getActivities();
        assertEquals(new Activity("activity 2"), activities.get(1));
    }

    @Test
    public void getActivityListReversed() {
        List<Activity> activities = activityListControl.getActivitiesReversed();
        assertEquals(new Activity("activity 1"), activities.get(1));
    }

    @Test
    public void getActivityListAsObservableList() {
        ObservableList<Activity> activities = activityListControl.getActivitiesObservable();
        assertEquals(new Activity("activity 2"), activities.get(0));
    }

}