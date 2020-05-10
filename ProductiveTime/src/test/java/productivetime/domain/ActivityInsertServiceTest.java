package productivetime.domain;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import productivetime.dao.SQLActivityDao;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class ActivityInsertServiceTest {

    private static SQLActivityDao sqlActivityDao;
    private static ActivityInsertService activityInsertService;

    @BeforeClass
    public static void beforeClass() {
        try {
            sqlActivityDao = new SQLActivityDao("test.db");
            sqlActivityDao.clear();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        activityInsertService = new ActivityInsertService(sqlActivityDao);
    }

    @After
    public void tearDown() {
        try {
            sqlActivityDao.clear();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void addActivityWhenEmpty() {
        activityInsertService.addActivity("test");
        try {
            assertEquals(new Activity("test"), sqlActivityDao.readLast());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void addActivityWhenNotEmpty() {
        activityInsertService.addActivity("activity 1");
        activityInsertService.addActivity("activity 2");
        try {
            assertEquals(new Activity("activity 2"), sqlActivityDao.read(2));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void addActivityUpdatesPrevious() {
        activityInsertService.addActivity("activity 1");
        activityInsertService.addActivity("activity 2");
        try {
            assertFalse(sqlActivityDao.read(1).isOngoing());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void addActivityNewIsOngoing() {
        activityInsertService.addActivity("activity 1");
        activityInsertService.addActivity("activity 2");
        try {
            assertTrue(sqlActivityDao.read(2).isOngoing());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void retypeWorks() {
        activityInsertService.addActivity("activity 1");
        activityInsertService.addActivity("activity 2");
        try {
            activityInsertService.retypeActivity(sqlActivityDao.read(1), "activity 3");
            assertEquals(new Activity("activity 3"), sqlActivityDao.read(1));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void pauseWorks() {
        activityInsertService.addActivity("activity 1");
        activityInsertService.addActivity("activity 2");
        activityInsertService.pauseTracking();
        try {
            assertFalse(sqlActivityDao.read(2).isOngoing());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void clearWorks() {
        activityInsertService.addActivity("activity 1");
        activityInsertService.addActivity("activity 2");
        activityInsertService.clearActivities();
        try {
            assertEquals(0, sqlActivityDao.list().size());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}