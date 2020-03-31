package productivetime.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import productivetime.domain.Activity;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ActivityDaoTest {

    private static ActivityDao activityDao;

    @BeforeClass
    public static void beforeClass() throws Exception {
        activityDao = new ActivityDao();
        activityDao.clear();
    }

    @After
    public void tearDown() throws Exception {
        activityDao.clear();
    }

    @Test
    public void readLastReturnsNullWhenDBIsEmpty() throws SQLException {
        assertNull(activityDao.readLast());
    }

    @Test
    public void readReturnsNullWhenDBIsEmpty() throws SQLException {
        assertNull(activityDao.read(1));
    }

    @Test
    public void clearEmptiesDB() throws SQLException {
        activityDao.create(new Activity("test"));
        activityDao.clear();
        assertNull(activityDao.readLast());
    }

    @Test
    public void activityCreationAndReadLast() throws SQLException {
        activityDao.create(new Activity("test"));
        assertEquals(new Activity("test"), activityDao.readLast());
    }

    @Test
    public void updateWhenEmptyReturnsNull() throws SQLException {
        assertNull(activityDao.update(activityDao.readLast()));
    }

    @Test
    public void updateWhenActivityRunning() throws SQLException, InterruptedException {
        activityDao.create(new Activity("activity 1"));
        TimeUnit.SECONDS.sleep(2);
        activityDao.update(activityDao.readLast());
        activityDao.create(new Activity("activity 2"));
        assertEquals(new Activity("activity 1"), activityDao.read(1));
        assertEquals(new Activity("activity 2"), activityDao.read(2));
        assertEquals(0, activityDao.read(2).getDuration());
        assertTrue(activityDao.read(1).getDuration() != 0);
    }

    @Test
    public void deleteRemovesFirstWhenKeyOne() throws SQLException {
        activityDao.create(new Activity("activity 1"));
        activityDao.update(activityDao.readLast());
        activityDao.create(new Activity("activity 2"));
        activityDao.delete(1);
        assertEquals(new Activity("activity 2"), activityDao.readLast());
    }

    @Test
    public void deleteLastWhenDBEmpty() throws SQLException {
        assertFalse(activityDao.deleteLast());
    }

    @Test
    public void deleteLastRemovesLast() throws SQLException {
        activityDao.create(new Activity("activity 1"));
        activityDao.update(activityDao.readLast());
        activityDao.create(new Activity("activity 2"));
        assertTrue(activityDao.deleteLast());
        assertEquals(new Activity("activity 1"), activityDao.readLast());
    }

    @Test
    public void listWhenDBEmpty() throws SQLException {
        assertEquals(0, activityDao.list().size());
    }

    @Test
    public void listWhenDBNotEmpty() throws SQLException {
        activityDao.create(new Activity("activity 1"));
        activityDao.update(activityDao.readLast());
        activityDao.create(new Activity("activity 2"));
        List<Activity> activities = activityDao.list();
        assertEquals(new Activity("activity 1"),activities.get(0));
        assertEquals(new Activity("activity 2"),activities.get(1));

    }
}