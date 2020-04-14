package productivetime.domain;

import org.junit.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class ActivityTest {

    @Test
    public void equalsReturnsTrueForSameObject() {
        Activity a1 = new Activity("test");
        assertEquals(a1, a1);
    }

    @Test
    public void equalsReturnsTrueForObjectWihSameTypeVariable() {
        Activity a1 = new Activity("test");
        Activity a2 = new Activity("test");
        assertTrue(a1.equals(a2));
    }

    @Test
    public void equalsFalseForObjectWihDiffObjectType() {
        Activity a1 = new Activity("test");
        String s1 = "test";
        assertNotEquals(a1, s1);
    }

    @Test
    public void equalsFalseForNullObject() {
        Activity a1 = new Activity("test");
        String s1 = null;
        assertNotEquals(a1, s1);
    }

    @Test
    public void compareToWorks() {
        Activity a1 = new Activity(1, "first", 100, 2);
        Activity a2 = new Activity(2, "second", 102, 2);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(a1);
        activities.add(a2);
        Collections.sort(activities);
        assertEquals(activities.get(0), a2);
    }

    @Test
    public void getDateSameYearSameDay() {
        Instant now = Instant.now();
        Activity activity = new Activity(1, "test", now.getEpochSecond(), 120);
        ZonedDateTime nowDate = ZonedDateTime.ofInstant(now, ZoneId.of("Europe/Helsinki"));
        String expected = String.format("%02d:%02d", nowDate.getHour(), nowDate.getMinute());
        assertEquals(expected, activity.getStartDate());
    }
}