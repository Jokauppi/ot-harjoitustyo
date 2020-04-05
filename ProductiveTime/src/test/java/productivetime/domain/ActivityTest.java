package productivetime.domain;

import org.junit.Test;

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
        activities.add(a2);
        activities.add(a1);
        Collections.sort(activities);
        assertTrue(activities.get(1).equals(a1));
    }

}