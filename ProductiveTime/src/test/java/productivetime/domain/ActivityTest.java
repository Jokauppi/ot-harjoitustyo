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
        assertEquals(a1, a2);
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
        Activity a3 = new Activity(2, "second", 104, 2);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(a3);
        activities.add(a1);
        activities.add(a2);
        Collections.sort(activities);
        assertEquals(a3, activities.get(0));
        assertEquals(a2, activities.get(1));
    }

    // Method with no parameters should always return the formatted string relative to the current date
    @Test
    public void getDateNoParameters() {
        ZonedDateTime day = ZonedDateTime.now(Settings.getTimeZone());
        Activity activity = new Activity(1, "test", day.toEpochSecond(), 120);
        String expected = String.format("%02d:%02d", day.getHour(), day.getMinute());
        assertEquals(expected, activity.getStartFormatted());
    }

    // Tests the returned string when the activity has been started on the same day as the method is called
    @Test
    public void getDateSameYearSameDay() {
        ZonedDateTime day = ZonedDateTime.of(LocalDateTime.of(2000, 2, 2, 2, 2), Settings.getTimeZone());
        Activity activity = new Activity(1, "test", day.toEpochSecond(), 120);
        assertEquals("02:02", activity.getStartFormatted(day));
    }

    // Tests the returned string when the activity has been started on the same year as the method is called but on a different date
    @Test
    public void getDateSameYearDifferentDay() {
        ZonedDateTime day = ZonedDateTime.of(LocalDateTime.of(2000, 2, 2, 2, 2), Settings.getTimeZone());
        Activity activity = new Activity(1, "test", day.toEpochSecond(), 120);
        assertEquals("02 Feb 02:02", activity.getStartFormatted(day.plusDays(1)));
    }

    // Tests the returned string when the activity has been started on a different year to when the method is called
    @Test
    public void getDateDifferentYear() {
        ZonedDateTime day = ZonedDateTime.of(LocalDateTime.of(2000, 2, 2, 2, 2), ZoneId.of("Europe/Helsinki"));
        Activity activity = new Activity(1, "test", day.toEpochSecond(), 120);
        assertEquals("02 Feb 2000 02:02", activity.getStartFormatted(day.plusDays(400)));
    }

    @Test
    public void GetDurationForOngoing() {
        Activity activity = new Activity(1, "test", 100, null);
        assertEquals("Ongoing", activity.getDurationFormatted());
    }

    @Test
    public void GetDurationForLessThanMinute() {
        Activity activity = new Activity(1, "test", 100, 30);
        assertEquals("0 min", activity.getDurationFormatted());
    }

    @Test
    public void GetDurationForLessThanHour() {
        Activity activity = new Activity(1, "test", 100, 60*30);
        assertEquals("30 min", activity.getDurationFormatted());
    }

    @Test
    public void GetDurationForMoreThanHour() {
        Activity activity = new Activity(1, "test", 100, 60*90);
        assertEquals("1 h 30 min", activity.getDurationFormatted());
    }

    @Test
    public void getEnd() {
        Activity activity = new Activity(1, "test", 100, 100);
        assertEquals(200, activity.getEnd());
    }

}