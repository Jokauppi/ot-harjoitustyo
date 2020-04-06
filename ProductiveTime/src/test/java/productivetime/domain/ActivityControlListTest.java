package productivetime.domain;

import javafx.collections.ObservableList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import productivetime.dao.ActivityDao;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ActivityControlListTest {

    private static ActivityDao activityDao;
    private static ActivityControl activityControl;

    @BeforeClass
    public static void beforeClass() throws Exception {
        activityDao = new ActivityDao();
        activityDao.clear();
        activityControl = new ActivityControl(activityDao);
        activityControl.addActivity("activity 1");
        TimeUnit.SECONDS.sleep(2);
        activityControl.addActivity("activity 2");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        activityDao.clear();
    }

    @Test
    public void getActivityList() {
        List<Activity> activities = activityControl.getActivities();
        assertEquals(new Activity("activity 1"), activities.get(0));
    }

    @Test
    public void getActivityListReversed() {
        List<Activity> activities = activityControl.getActivitiesReversed();
        assertEquals(new Activity("activity 2"), activities.get(0));
    }

    @Test
    public void getActivityListAsObservableList() {
        ObservableList<Activity> activities = activityControl.getActivitiesObservable();
        assertEquals(new Activity("activity 2"), activities.get(0));
    }

}