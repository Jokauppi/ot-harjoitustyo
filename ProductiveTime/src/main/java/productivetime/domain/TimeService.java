package productivetime.domain;

import java.time.*;

public class TimeService {

    private static ZoneId timeZone;

    private static void updateTimeZone(){
        timeZone = ZoneId.of(Settings.getSetting("timezone"));
    }

    public static long nowSeconds() {
        return Instant.now().getEpochSecond();
    }

    public static ZonedDateTime nowZoned() {
        updateTimeZone();
        return ZonedDateTime.ofInstant(Instant.now(), timeZone);
    }

    public static ZonedDateTime zonedOfSeconds(long time) {
        updateTimeZone();
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(time), timeZone);
    }

    public static ZonedDateTime zonedOfLocalDate(LocalDate localDate) {
        updateTimeZone();
        return ZonedDateTime.of(localDate, LocalTime.MIDNIGHT, timeZone);
    }

    public static ZonedDateTime startOfZoned(ZonedDateTime dateTime) {
        updateTimeZone();
        return dateTime.toLocalDate().atStartOfDay(timeZone);
    }
}
