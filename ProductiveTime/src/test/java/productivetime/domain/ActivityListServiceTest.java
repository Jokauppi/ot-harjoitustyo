package productivetime.domain;

import javafx.collections.ObservableList;
import org.junit.*;
import productivetime.dao.SQLActivityDao;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class ActivityListServiceTest {

    private static SQLActivityDao SQLActivityDao;
    private static ActivityListService activityListService;

    @Before
    public void setUp() throws Exception {
        SQLActivityDao = new SQLActivityDao("test.db");
        SQLActivityDao.clear();
        activityListService = new ActivityListService(SQLActivityDao);
        SQLActivityDao.create(new Activity("activity 1"), 100);
        SQLActivityDao.update(SQLActivityDao.readLast(), 200);
        SQLActivityDao.create(new Activity("activity 2"), 200);
        SQLActivityDao.update(SQLActivityDao.readLast(), 300);
        SQLActivityDao.create(new Activity("activity 2"), 300);
    }

    @After
    public void tearDown() throws Exception {
        SQLActivityDao.clear();
    }

    @Test
    public void getActivityList() {
        List<Activity> activities = activityListService.getActivities();
        assertEquals(new Activity("activity 2"), activities.get(1));
    }

    @Test
    public void getActivityListReversed() {
        List<Activity> activities = activityListService.getActivitiesReversed();
        assertEquals(new Activity("activity 1"), activities.get(2));
    }

    @Test
    public void getActivityListAsObservableList() {
        ObservableList<Activity> activities = activityListService.getActivitiesObservable();
        assertEquals(new Activity("activity 2"), activities.get(0));
    }

    @Test
    public void getAllTypes() throws SQLException {
        List<String> allTypes = activityListService.getAllTypes();
        assertEquals("activity 1", allTypes.get(1));
    }

    @Test
    public void getFrequentTypes() throws SQLException {
        List<String> frequentTypes = activityListService.getFrequentTypes(1);
        assertEquals("activity 2", frequentTypes.get(0));
        assertEquals(1, frequentTypes.size());
    }
}