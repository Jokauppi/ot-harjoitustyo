package productivetime.domain;

import org.junit.*;
import productivetime.dao.SQLActivityDao;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class ActivityListServiceDurationsTest {

    private static productivetime.dao.SQLActivityDao SQLActivityDao;
    private static ActivityListService activityListService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        SQLActivityDao = new SQLActivityDao("test.db");
        SQLActivityDao.clear();
        activityListService = new ActivityListService(SQLActivityDao);

        ZonedDateTime datetime = TimeService.startOfZoned(TimeService.zonedOfSeconds(0));

        SQLActivityDao.create(new Activity("activity 1"), datetime.toEpochSecond());

        datetime = datetime.plusSeconds(100);

        SQLActivityDao.update(SQLActivityDao.readLast(), datetime.toEpochSecond());
        SQLActivityDao.create(new Activity("activity 2"), datetime.toEpochSecond());

        datetime = datetime.plusSeconds(100);

        SQLActivityDao.update(SQLActivityDao.readLast(), datetime.toEpochSecond());
        SQLActivityDao.create(new Activity("activity 1"), datetime.toEpochSecond());

        datetime = datetime.plusDays(1);

        SQLActivityDao.update(SQLActivityDao.readLast(), datetime.toEpochSecond());
        SQLActivityDao.create(new Activity("activity 2"), datetime.toEpochSecond());

        datetime = datetime.plusSeconds(100);

        SQLActivityDao.update(SQLActivityDao.readLast(), datetime.toEpochSecond());
        SQLActivityDao.create(new Activity("activity 1"), datetime.toEpochSecond());
    }

    @AfterClass
    public static void afterClass() throws Exception {
        SQLActivityDao.clear();
    }

    @Test
    public void durationEntirelyInOneDay() {
        Map<Long, Integer> durations = activityListService.getDurationsOfTypeOnDaysBetween("activity 2",
                TimeService.zonedOfSeconds(0),
                TimeService.zonedOfSeconds(0).plusDays(1));

        ArrayList<Long> keys = new ArrayList<>(durations.keySet());

        assertEquals(100, (int) durations.get(keys.get(0)));
    }

    @Test
    public void durationWithPartOnOtherDay() {
        Map<Long, Integer> durations = activityListService.getDurationsOfTypeOnDaysBetween("activity 1",
                TimeService.zonedOfSeconds(0),
                TimeService.zonedOfSeconds(0).plusDays(1));

        ArrayList<Long> keys = new ArrayList<>(durations.keySet());

        assertEquals(24 * 60 * 60 - 100, (int) durations.get(keys.get(0)));
    }

    @Test
    public void durationsOnTwoDays() {
        Map<Long, Integer> durations = activityListService.getDurationsOfTypeOnDaysBetween("activity 2",
                TimeService.zonedOfSeconds(0),
                TimeService.zonedOfSeconds(0).plusDays(2));

        ArrayList<Long> keys = new ArrayList<>(durations.keySet());

        assertEquals(100, (int) durations.get(keys.get(0)));
        assertEquals(100, (int) durations.get(keys.get(1)));
    }
}