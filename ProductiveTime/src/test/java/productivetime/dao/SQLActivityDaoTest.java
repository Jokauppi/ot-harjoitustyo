package productivetime.dao;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import productivetime.domain.Activity;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class SQLActivityDaoTest {

    private static SQLActivityDao sqlActivityDao;

    @BeforeClass
    public static void beforeClass() throws Exception {
        sqlActivityDao = new SQLActivityDao("test.db");
        sqlActivityDao.clear();
    }

    @After
    public void tearDown() throws Exception {
        sqlActivityDao.clear();
    }

    @Test
    public void readLastReturnsNullWhenDBIsEmpty() throws SQLException {
        assertNull(sqlActivityDao.readLast());
    }

    @Test
    public void readReturnsNullWhenDBIsEmpty() throws SQLException {
        assertNull(sqlActivityDao.read(1));
    }

    @Test
    public void clearEmptiesDB() throws SQLException {
        sqlActivityDao.create(new Activity("test"), 10000);
        sqlActivityDao.clear();
        assertNull(sqlActivityDao.readLast());
    }

    @Test
    public void activityCreationAndReadLast() throws SQLException {
        sqlActivityDao.create(new Activity("test"), 10000);
        assertEquals(new Activity("test"), sqlActivityDao.readLast());
    }

    @Test
    public void updateWhenEmptyReturnsNull() throws SQLException {
        assertNull(sqlActivityDao.update(sqlActivityDao.readLast(), 10000));
    }

    @Test
    public void updateWhenActivityRunning() throws SQLException, InterruptedException {
        sqlActivityDao.create(new Activity("activity 1"), 10000);
        sqlActivityDao.update(sqlActivityDao.readLast(), 12000);
        sqlActivityDao.create(new Activity("activity 2"), 12000);
        assertEquals(new Activity("activity 1"), sqlActivityDao.read(1));
        assertEquals(new Activity("activity 2"), sqlActivityDao.read(2));
        assertTrue(sqlActivityDao.read(2).isOngoing());
        assertFalse(sqlActivityDao.read(1).isOngoing());
    }

    @Test
    public void deleteRemovesFirstWhenKeyOne() throws SQLException {
        sqlActivityDao.create(new Activity("activity 1"), 10000);
        sqlActivityDao.update(sqlActivityDao.readLast(), 12000);
        sqlActivityDao.create(new Activity("activity 2"), 12000);
        sqlActivityDao.delete(1);
        assertEquals(new Activity("activity 2"), sqlActivityDao.readLast());
    }

    @Test
    public void deleteLastWhenDBEmpty() throws SQLException {
        sqlActivityDao.deleteLast();
        assertEquals(0, sqlActivityDao.list().size());
    }

    @Test
    public void deleteLastRemovesLast() throws SQLException {
        sqlActivityDao.create(new Activity("activity 1"), 10000);
        sqlActivityDao.update(sqlActivityDao.readLast(), 12000);
        sqlActivityDao.create(new Activity("activity 2"), 12000);
        sqlActivityDao.deleteLast();
        assertEquals(1, sqlActivityDao.list().size());
        assertEquals(new Activity("activity 1"), sqlActivityDao.readLast());
    }

    @Test
    public void listWhenDBEmpty() throws SQLException {
        assertEquals(0, sqlActivityDao.list().size());
    }

    @Test
    public void listWhenDBNotEmpty() throws SQLException {
        sqlActivityDao.create(new Activity("activity 1"), 100);
        sqlActivityDao.update(sqlActivityDao.readLast(), 120);
        sqlActivityDao.create(new Activity("activity 2"), 120);
        List<Activity> activities = sqlActivityDao.list();
        assertEquals(new Activity("activity 1"),activities.get(0));
        assertEquals(new Activity("activity 2"),activities.get(1));
    }

}