package productivetime.domain;

import org.junit.*;
import productivetime.dao.ActivityDao;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ActivityListControlRangedTest {

    private static ActivityDao activityDao;
    private static ActivityListControl activityListControl;
    private static final ZonedDateTime time = ZonedDateTime.of(2000, 2, 2, 21, 30, 0, 0, Settings.getTimeZone());

    @BeforeClass
    public static void beforeClass() throws Exception {

        activityDao = new ActivityDao("test.db");
        activityListControl = new ActivityListControl(activityDao);

        activityDao.clear();

        ZonedDateTime itime = time;
        for (int i = 1; i < 4; i++) {
            activityDao.update(activityDao.readLast(), itime.toEpochSecond());
            activityDao.create(new Activity("test" + i), itime.toEpochSecond());
            itime = itime.plusHours(1);
        }
        itime = itime.plusDays(2);
        activityDao.update(activityDao.readLast(), itime.toEpochSecond());
        activityDao.create(new Activity("test4"), itime.toEpochSecond());

    }

    @AfterClass
    public static void afterClass() throws Exception {

        activityDao.clear();
    }

    @Test
    public void getActivitiesOfDayWithThreeActivities() {
        List<Activity> activities = activityListControl.getActivitiesOnDayOf(time);
        assertEquals(3, activities.size());
    }

    @Test
    public void getActivitiesOfDayWithOneDayLongActivity() {
        List<Activity> activities = activityListControl.getActivitiesOnDayOf(time.plusDays(1));
        assertEquals(1, activities.size());
    }

    @Test
    public void getActivitiesOfDayWithNoActivitiesNoOngoing() {
        List<Activity> activities = activityListControl.getActivitiesOnDayOf(time.plusDays(-1));
        assertEquals(0, activities.size());
    }

    @Test
    public void getActivitiesOfPastDayWithNoCompletedActivitiesOneOngoing() {
        List<Activity> activities = activityListControl.getActivitiesOnDayOf(time.plusDays(4));
        assertEquals(1, activities.size());
    }

    @Test
    public void getActivitiesOfCurrentDayWithActivityOngoing() {
        List<Activity> activities = activityListControl.getActivitiesOnDayOf(ZonedDateTime.now());
        assertEquals(1, activities.size());
    }

    @Test
    public void getActivitiesOfFutureDayWithActivityOngoing() {
        List<Activity> activities = activityListControl.getActivitiesOnDayOf(ZonedDateTime.now().plusDays(2));
        assertEquals(0, activities.size());
    }

    @Test
    public void getActivitiesLastDayTruncated() {
        List<Activity> activities = activityListControl.getActivitiesOnDayOf(time);
        assertEquals(30*60, activities.get(activities.size()-1).getDuration());
    }

    @Test
    public void getActivitiesWithDayLongActivityTruncated() {
        List<Activity> activities = activityListControl.getActivitiesOnDayOf(time.plusDays(1));
        assertEquals(24*60*60, activities.get(0).getDuration());
    }

    @Test
    public void getActivitiesWithOngoingActivityDurationCalculated() {
        List<Activity> activities = activityListControl.getActivitiesOnDayOf(time.plusDays(4));
        assertEquals(24*60*60, activities.get(0).getDuration());
    }

}