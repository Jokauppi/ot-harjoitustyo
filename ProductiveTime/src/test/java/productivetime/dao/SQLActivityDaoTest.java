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
    public void readLastReturnsNullWhenDBIsEmpty() {
        try {
            assertNull(sqlActivityDao.readLast());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void readReturnsNullWhenDBIsEmpty() {
        try {
            assertNull(sqlActivityDao.read(1));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void clearEmptiesDB() {
        try {
            sqlActivityDao.create(new Activity("test"), 10000);
            sqlActivityDao.clear();
            assertNull(sqlActivityDao.readLast());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void activityCreationAndReadLast() {
        try {
            sqlActivityDao.create(new Activity("test"), 10000);
            assertEquals(new Activity("test"), sqlActivityDao.readLast());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void updateWhenEmptyReturnsNull() {
        try {
            assertNull(sqlActivityDao.update(sqlActivityDao.readLast(), 10000));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void updateWhenActivityRunning() {
        try {
            sqlActivityDao.create(new Activity("activity 1"), 10000);
            sqlActivityDao.update(sqlActivityDao.readLast(), 12000);
            sqlActivityDao.create(new Activity("activity 2"), 12000);
            assertEquals(new Activity("activity 1"), sqlActivityDao.read(1));
            assertEquals(new Activity("activity 2"), sqlActivityDao.read(2));
            assertTrue(sqlActivityDao.read(2).isOngoing());
            assertFalse(sqlActivityDao.read(1).isOngoing());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void deleteRemovesFirstWhenKeyOne() {
        try {
            sqlActivityDao.create(new Activity("activity 1"), 10000);
            sqlActivityDao.update(sqlActivityDao.readLast(), 12000);
            sqlActivityDao.create(new Activity("activity 2"), 12000);
            sqlActivityDao.delete(1);
            assertEquals(new Activity("activity 2"), sqlActivityDao.readLast());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void deleteLastWhenDBEmpty() {
        try {
            sqlActivityDao.deleteLast();
            assertEquals(0, sqlActivityDao.list().size());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void deleteLastRemovesLast() {
        try {
            sqlActivityDao.create(new Activity("activity 1"), 10000);
            sqlActivityDao.update(sqlActivityDao.readLast(), 12000);
            sqlActivityDao.create(new Activity("activity 2"), 12000);
            sqlActivityDao.deleteLast();
            assertEquals(1, sqlActivityDao.list().size());
            assertEquals(new Activity("activity 1"), sqlActivityDao.readLast());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void listWhenDBEmpty() {
        try {
            assertEquals(0, sqlActivityDao.list().size());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void listWhenDBNotEmpty() {
        try {
            sqlActivityDao.create(new Activity("activity 1"), 100);
            sqlActivityDao.update(sqlActivityDao.readLast(), 120);
            sqlActivityDao.create(new Activity("activity 2"), 120);
            List<Activity> activities = sqlActivityDao.list();
            assertEquals(new Activity("activity 1"),activities.get(0));
            assertEquals(new Activity("activity 2"),activities.get(1));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void listTypes() {
        try {
            sqlActivityDao.create(new Activity("activity 1"), 10);
            sqlActivityDao.update(sqlActivityDao.readLast(), 20);
            sqlActivityDao.create(new Activity("activity 2"), 20);
            sqlActivityDao.update(sqlActivityDao.readLast(), 30);
            sqlActivityDao.create(new Activity("activity 2"), 30);
            sqlActivityDao.update(sqlActivityDao.readLast(), 40);
            List<String> types = sqlActivityDao.listTypes();
            assertEquals("activity 2", types.get(0));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}