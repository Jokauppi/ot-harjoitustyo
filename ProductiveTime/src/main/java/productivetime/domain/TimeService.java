package productivetime.domain;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeService {

    private static final ZoneId TIMEZONE = Settings.getTimeZone();

    public static long nowSeconds() {
        return Instant.now().getEpochSecond();
    }

    public static ZonedDateTime nowZoned() {
        return ZonedDateTime.ofInstant(Instant.now(), TIMEZONE);
    }

    public static ZonedDateTime zonedOfSeconds(long time) {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(time), TIMEZONE);
    }

    public static ZonedDateTime todayStartAsZoned() {
        return ZonedDateTime.now(TIMEZONE).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }
    
}
