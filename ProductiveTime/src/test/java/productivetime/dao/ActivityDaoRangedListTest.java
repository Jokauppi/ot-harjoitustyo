package productivetime.dao;

import org.junit.*;
import productivetime.domain.Activity;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ActivityDaoRangedListTest {

    private static ActivityDao activityDao;

    @BeforeClass
    public static void beforeClass() throws Exception {

        activityDao = new ActivityDao("test.db");
    }

    @Before
    public void setUp() throws Exception {

        activityDao.clear();
        activityDao.create(new Activity("activity 1"), 100);
        activityDao.update(activityDao.readLast(), 120);
        activityDao.create(new Activity("activity 2"), 120);
        activityDao.update(activityDao.readLast(), 140);
        activityDao.create(new Activity("activity 3"), 140);
    }

    @AfterClass
    public static void afterClass() throws Exception {

        activityDao.clear();
    }

    // Tests whether ranged listing includes an activity that has been completed and is fully in the range
    @Test
    public void listActivityIncludesFullyInRange() throws SQLException {

        List<Activity> activities = activityDao.list(90, 150);
        assertEquals(new Activity("activity 2"),activities.get(1));
    }

    // Tests whether ranged listing includes an activity that has been completed and is partly below the range
    @Test
    public void listActivityIncludesCompletedPartlyInRangeAtStart() throws SQLException {

        List<Activity> activities = activityDao.list(110, 150);
        assertEquals(new Activity("activity 1"),activities.get(0));
    }

    // Tests whether ranged listing includes an activity at the end that has been completed and is partly above the range.
    @Test
    public void listActivityIncludesCompletedPartlyInRangeAtEnd() throws SQLException {

        List<Activity> activities = activityDao.list(90, 130);
        assertEquals(new Activity("activity 2"),activities.get(1));
    }

    // Tests whether ranged listing includes an activity that is still ongoing and has its start below the range end
    // Has to be checked later if should still be included, ActivityDao doesn't know the current time
    @Test
    public void listActivityIncludesOngoingWithStartBelowRangeEnd() throws SQLException {

        List<Activity> activities = activityDao.list(150, 160);
        assertEquals(new Activity("activity 3"),activities.get(0));
    }

    // Test that ranged listing doesn't include an activity that ends exactly at the beginning of the range
    @Test
    public void listActivityDoesntIncludeIfEndExactlyAtRangeBeginning() throws SQLException {
        List<Activity> activities = activityDao.list(120, 130);
        assertEquals(new Activity("activity 2"),activities.get(0));
    }

    // Tests that ranged listing doesn't include an activity that starts exactly at the end of the range
    @Test
    public void listActivityDoesntIncludeIfStartExactlyAtRangeEnd() throws SQLException {
        List<Activity> activities = activityDao.list(100, 120);
        assertEquals(1, activities.size());
        assertEquals(new Activity("activity 1"), activities.get(0));
    }

    // Tests that ranged listing includes an activity that starts before the range beginning and end after the range end (the range is entirely inside the activity)
    @Test
    public void listActivityIncludesStartBelowRangeAndEndAboveRange() throws SQLException {
        List<Activity> activities = activityDao.list(122, 138);
        assertEquals(1, activities.size());
        assertEquals(new Activity("activity 2"), activities.get(0));
    }

}