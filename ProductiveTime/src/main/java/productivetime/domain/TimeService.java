package productivetime.domain;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeService {

    private static ZoneId timeZone = Settings.getTimeZone();

    public static long nowSeconds() {
        return Instant.now().getEpochSecond();
    }

    public static ZonedDateTime nowZoned() {
        return ZonedDateTime.ofInstant(Instant.now(), timeZone);
    }

    public static ZonedDateTime zonedOfSeconds(long time) {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(time), timeZone);
    }

    public static ZonedDateTime startOfZoned(ZonedDateTime dateTime) {
        return dateTime.toLocalDate().atStartOfDay(timeZone);
    }
}
