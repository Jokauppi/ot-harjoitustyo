package productivetime.dao;

import org.junit.*;
import productivetime.domain.Activity;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SQLActivityDaoRangedListTest {

    private static SQLActivityDao SQLActivityDao;

    @BeforeClass
    public static void beforeClass() throws Exception {

        SQLActivityDao = new SQLActivityDao("test.db");
    }

    @Before
    public void setUp() throws Exception {

        SQLActivityDao.clear();
        SQLActivityDao.create(new Activity("activity 1"), 100);
        SQLActivityDao.update(SQLActivityDao.readLast(), 120);
        SQLActivityDao.create(new Activity("activity 2"), 120);
        SQLActivityDao.update(SQLActivityDao.readLast(), 140);
        SQLActivityDao.create(new Activity("activity 3"), 140);
    }

    @AfterClass
    public static void afterClass() throws Exception {

        SQLActivityDao.clear();
    }

    // Tests whether ranged listing includes an activity that has been completed and is fully in the range
    @Test
    public void listActivityIncludesFullyInRange() {

        List<Activity> activities = null;
        try {
            activities = SQLActivityDao.list(90, 150);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        assertEquals(new Activity("activity 2"),activities.get(1));
    }

    // Tests whether ranged listing includes an activity that has been completed and is partly below the range
    @Test
    public void listActivityIncludesCompletedPartlyInRangeAtStart() {

        List<Activity> activities = null;
        try {
            activities = SQLActivityDao.list(110, 150);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        assertEquals(new Activity("activity 1"),activities.get(0));
    }

    // Tests whether ranged listing includes an activity at the end that has been completed and is partly above the range.
    @Test
    public void listActivityIncludesCompletedPartlyInRangeAtEnd() {

        List<Activity> activities = null;
        try {
            activities = SQLActivityDao.list(90, 130);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        assertEquals(new Activity("activity 2"),activities.get(1));
    }

    // Tests whether ranged listing includes an activity that is still ongoing and has its start below the range end
    // Has to be checked later if should still be included, ActivityDao doesn't know the current time
    @Test
    public void listActivityIncludesOngoingWithStartBelowRangeEnd() {

        List<Activity> activities = null;
        try {
            activities = SQLActivityDao.list(150, 160);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        assertEquals(new Activity("activity 3"),activities.get(0));
    }

    // Test that ranged listing doesn't include an activity that ends exactly at the beginning of the range
    @Test
    public void listActivityDoesntIncludeIfEndExactlyAtRangeBeginning() {
        List<Activity> activities = null;
        try {
            activities = SQLActivityDao.list(120, 130);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        assertEquals(new Activity("activity 2"),activities.get(0));
    }

    // Tests that ranged listing doesn't include an activity that starts exactly at the end of the range
    @Test
    public void listActivityDoesntIncludeIfStartExactlyAtRangeEnd() {
        List<Activity> activities = null;
        try {
            activities = SQLActivityDao.list(100, 120);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        assertEquals(1, activities.size());
        assertEquals(new Activity("activity 1"), activities.get(0));
    }

    // Tests that ranged listing includes an activity that starts before the range beginning and end after the range end (the range is entirely inside the activity)
    @Test
    public void listActivityIncludesStartBelowRangeAndEndAboveRange() {
        List<Activity> activities = null;
        try {
            activities = SQLActivityDao.list(122, 138);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        assertEquals(1, activities.size());
        assertEquals(new Activity("activity 2"), activities.get(0));
    }

}