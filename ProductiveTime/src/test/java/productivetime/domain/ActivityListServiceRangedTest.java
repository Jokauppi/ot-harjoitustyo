package productivetime.domain;

import org.junit.*;
import productivetime.dao.SQLActivityDao;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ActivityListServiceRangedTest {

    private static SQLActivityDao SQLActivityDao;
    private static ActivityListService activityListService;
    private static final ZonedDateTime time = ZonedDateTime.of(2000, 2, 2, 21, 30, 0, 0, TimeService.getTimeZone());

    @BeforeClass
    public static void beforeClass() {

        try {
            SQLActivityDao = new SQLActivityDao("test.db");
            activityListService = new ActivityListService(SQLActivityDao);

            SQLActivityDao.clear();

            ZonedDateTime itime = time;
            SQLActivityDao.create(new Activity("test0"), TimeService.startOfZoned(itime).toEpochSecond()-3600);
            for (int i = 1; i < 4; i++) {
                SQLActivityDao.update(SQLActivityDao.readLast(), itime.toEpochSecond());
                SQLActivityDao.create(new Activity("test" + i), itime.toEpochSecond());
                itime = itime.plusHours(1);
            }
            itime = itime.plusDays(2);
            SQLActivityDao.update(SQLActivityDao.readLast(), itime.toEpochSecond());
            SQLActivityDao.create(new Activity("test4"), itime.toEpochSecond());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    @AfterClass
    public static void afterClass() {

        try {
            SQLActivityDao.clear();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void getActivitiesOfDayWithFourActivities() {
        List<Activity> activities = activityListService.getActivitiesOnDayOf(time);
        assertEquals(4, activities.size());
    }

    @Test
    public void getActivitiesOfDayWithOneActivityStartingInMiddleOfDay() {
        List<Activity> activities = activityListService.getActivitiesOnDayOf(time.plusDays(-1));
        assertEquals(2, activities.size());
        assertEquals("No Data", activities.get(0).getType());
    }

    @Test
    public void getActivitiesOfDayWithOneDayLongActivity() {
        List<Activity> activities = activityListService.getActivitiesOnDayOf(time.plusDays(1));
        assertEquals(1, activities.size());
    }

    @Test
    public void getActivitiesOfDayWithNoActivitiesNoOngoing() {
        List<Activity> activities = activityListService.getActivitiesOnDayOf(time.plusDays(-2));
        assertEquals(0, activities.size());
    }

    @Test
    public void getActivitiesOfPastDayWithNoCompletedActivitiesOneOngoing() {
        List<Activity> activities = activityListService.getActivitiesOnDayOf(time.plusDays(4));
        assertEquals(1, activities.size());
    }

    @Test
    public void getActivitiesOfCurrentDayWithActivityOngoing() {
        List<Activity> activities = activityListService.getActivitiesOnDayOf(ZonedDateTime.now());
        assertEquals(1, activities.size());
    }

    @Test
    public void getActivitiesOfFutureDayWithActivityOngoing() {
        List<Activity> activities = activityListService.getActivitiesOnDayOf(ZonedDateTime.now().plusDays(2));
        assertEquals(0, activities.size());
    }

    @Test
    public void getActivitiesLastDayTruncated() {
        List<Activity> activities = activityListService.getActivitiesOnDayOf(time);
        assertEquals(30*60, activities.get(activities.size()-1).getDuration());
    }

    @Test
    public void getActivitiesWithDayLongActivityTruncated() {
        List<Activity> activities = activityListService.getActivitiesOnDayOf(time.plusDays(1));
        assertEquals(24*60*60, activities.get(0).getDuration());
    }

    @Test
    public void getActivitiesWithOngoingActivityDurationCalculated() {
        List<Activity> activities = activityListService.getActivitiesOnDayOf(time.plusDays(4));
        assertEquals(24*60*60, activities.get(0).getDuration());
    }

}