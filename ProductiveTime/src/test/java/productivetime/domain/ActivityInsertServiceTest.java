package productivetime.domain;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import productivetime.dao.SQLActivityDao;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class ActivityInsertServiceTest {

    private static SQLActivityDao SQLActivityDao;
    private static ActivityInsertService activityInsertService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        SQLActivityDao = new SQLActivityDao("test.db");
        SQLActivityDao.clear();
        activityInsertService = new ActivityInsertService(SQLActivityDao);
    }

    @After
    public void tearDown() throws Exception {
        SQLActivityDao.clear();
    }

    @Test
    public void addActivityWhenEmpty() throws SQLException {
        activityInsertService.addActivity("test");
        assertEquals(new Activity("test"), SQLActivityDao.readLast());
    }

    @Test
    public void addActivityWhenNotEmpty() throws SQLException, InterruptedException {
        activityInsertService.addActivity("activity 1");
        activityInsertService.addActivity("activity 2");
        assertEquals(new Activity("activity 2"), SQLActivityDao.read(2));
    }

    @Test
    public void addActivityUpdatesPrevious() throws SQLException, InterruptedException {
        activityInsertService.addActivity("activity 1");
        activityInsertService.addActivity("activity 2");
        assertFalse(SQLActivityDao.read(1).isOngoing());
    }

    @Test
    public void addActivityNewIsOngoing() throws SQLException, InterruptedException {
        activityInsertService.addActivity("activity 1");
        activityInsertService.addActivity("activity 2");
        assertTrue(SQLActivityDao.read(2).isOngoing());
    }
}