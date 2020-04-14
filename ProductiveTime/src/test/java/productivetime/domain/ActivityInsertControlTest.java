package productivetime.domain;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import productivetime.dao.ActivityDao;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ActivityInsertControlTest {

    private static ActivityDao activityDao;
    private static ActivityInsertControl activityInsertControl;

    @BeforeClass
    public static void beforeClass() throws Exception {
        activityDao = new ActivityDao("test.db");
        activityDao.clear();
        activityInsertControl = new ActivityInsertControl(activityDao);
    }

    @After
    public void tearDown() throws Exception {
        activityDao.clear();
    }

    @Test
    public void addActivityWhenEmpty() throws SQLException {
        activityInsertControl.addActivity("test");
        assertEquals(new Activity("test"), activityDao.readLast());
    }

    @Test
    public void addActivityWhenNotEmpty() throws SQLException, InterruptedException {
        activityInsertControl.addActivity("activity 1");
        TimeUnit.SECONDS.sleep(2);
        activityInsertControl.addActivity("activity 2");
        assertEquals(new Activity("activity 1"), activityDao.read(1));
        assertEquals(new Activity("activity 2"), activityDao.read(2));
        assertEquals(0, activityDao.read(2).getDuration());
        assertTrue(activityDao.read(1).getDuration() != 0);
    }
}