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
    public static void beforeClass() throws Exception {
        sqlActivityDao = new SQLActivityDao("test.db");
        sqlActivityDao.clear();
        activityInsertService = new ActivityInsertService(sqlActivityDao);
    }

    @After
    public void tearDown() throws Exception {
        sqlActivityDao.clear();
    }

    @Test
    public void addActivityWhenEmpty() throws SQLException {
        activityInsertService.addActivity("test");
        assertEquals(new Activity("test"), sqlActivityDao.readLast());
    }

    @Test
    public void addActivityWhenNotEmpty() throws SQLException {
        activityInsertService.addActivity("activity 1");
        activityInsertService.addActivity("activity 2");
        assertEquals(new Activity("activity 2"), sqlActivityDao.read(2));
    }

    @Test
    public void addActivityUpdatesPrevious() throws SQLException {
        activityInsertService.addActivity("activity 1");
        activityInsertService.addActivity("activity 2");
        assertFalse(sqlActivityDao.read(1).isOngoing());
    }

    @Test
    public void addActivityNewIsOngoing() throws SQLException {
        activityInsertService.addActivity("activity 1");
        activityInsertService.addActivity("activity 2");
        assertTrue(sqlActivityDao.read(2).isOngoing());
    }

    @Test
    public void retypeWorks() throws SQLException {
        activityInsertService.addActivity("activity 1");
        activityInsertService.addActivity("activity 2");
        activityInsertService.retypeActivity(sqlActivityDao.read(1), "activity 3");
        assertEquals(new Activity("activity 3"), sqlActivityDao.read(1));
    }

    @Test
    public void pauseWorks() throws SQLException {
        activityInsertService.addActivity("activity 1");
        activityInsertService.addActivity("activity 2");
        activityInsertService.pauseTracking();
        assertFalse(sqlActivityDao.read(2).isOngoing());
    }

    @Test
    public void clearWorks() throws SQLException {
        activityInsertService.addActivity("activity 1");
        activityInsertService.addActivity("activity 2");
        activityInsertService.clearActivities();
        assertEquals(0, sqlActivityDao.list().size());
    }
}