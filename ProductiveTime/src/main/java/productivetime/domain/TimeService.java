package productivetime.domain;

import java.time.*;

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

    public static ZonedDateTime zonedOfLocalDate(LocalDate localDate) {
        return ZonedDateTime.of(localDate, LocalTime.MIDNIGHT, timeZone);
    }

    public static ZonedDateTime startOfZoned(ZonedDateTime dateTime) {
        return dateTime.toLocalDate().atStartOfDay(timeZone);
    }
}
