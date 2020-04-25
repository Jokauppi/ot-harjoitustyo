package productivetime.domain;

import javafx.collections.ObservableList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import productivetime.dao.SQLActivityDao;

import java.util.List;

import static org.junit.Assert.*;

public class ActivityListServiceTest {

    private static SQLActivityDao SQLActivityDao;
    private static ActivityListService activityListService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        SQLActivityDao = new SQLActivityDao("test.db");
        SQLActivityDao.clear();
        activityListService = new ActivityListService(SQLActivityDao);
        SQLActivityDao.create(new Activity("activity 1"), 100);
        SQLActivityDao.update(SQLActivityDao.readLast(), 200);
        SQLActivityDao.create(new Activity("activity 2"), 200);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        SQLActivityDao.clear();
    }

    @Test
    public void getActivityList() {
        List<Activity> activities = activityListService.getActivities();
        assertEquals(new Activity("activity 2"), activities.get(1));
    }

    @Test
    public void getActivityListReversed() {
        List<Activity> activities = activityListService.getActivitiesReversed();
        assertEquals(new Activity("activity 1"), activities.get(1));
    }

    @Test
    public void getActivityListAsObservableList() {
        ObservableList<Activity> activities = activityListService.getActivitiesObservable();
        assertEquals(new Activity("activity 2"), activities.get(0));
    }

}