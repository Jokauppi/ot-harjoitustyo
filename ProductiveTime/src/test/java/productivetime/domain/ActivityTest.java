package productivetime.domain;

import org.junit.Test;

import java.time.LocalDateTime;
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
        ZonedDateTime day = ZonedDateTime.of(LocalDateTime.of(2000, 2, 2, 2, 2), ZoneId.of("Europe/Helsinki"));
        Activity activity = new Activity(1, "test", day.toEpochSecond(), 120);
        assertEquals("02:02", activity.getStartFormatted(day));
    }

    @Test
    public void getDateSameYearDifferentDay() {
        ZonedDateTime day = ZonedDateTime.of(LocalDateTime.of(2000, 2, 2, 2, 2), ZoneId.of("Europe/Helsinki"));
        Activity activity = new Activity(1, "test", day.toEpochSecond(), 120);
        assertEquals("02 Feb 02:02", activity.getStartFormatted(day.plusDays(1)));
    }

    @Test
    public void getDateDifferentYear() {
        ZonedDateTime day = ZonedDateTime.of(LocalDateTime.of(2000, 2, 2, 2, 2), ZoneId.of("Europe/Helsinki"));
        Activity activity = new Activity(1, "test", day.toEpochSecond(), 120);
        assertEquals("02 Feb 2000 02:02", activity.getStartFormatted(day.plusDays(400)));
    }
}