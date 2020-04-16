package productivetime.domain;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;

public class TimeServiceTest {

    @Test
    public void zonedOfSeconds() {
        ZonedDateTime time = ZonedDateTime.of(LocalDateTime.of(1970, 1, 1, 2, 0, 30), Settings.getTimeZone());
        assertEquals(time, TimeService.zonedOfSeconds(30));
    }

    @Test
    public void todayStartAsZoned() {
        ZonedDateTime now = ZonedDateTime.now(Settings.getTimeZone()).withHour(0).withMinute(0).withSecond(0).withNano(0);
        assertEquals(now, TimeService.todayStartAsZoned());
    }
}