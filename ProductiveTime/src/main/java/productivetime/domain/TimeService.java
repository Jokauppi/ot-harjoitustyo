package productivetime.domain;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Class offers various static methods to for example convert different date and time types to other types needed by other
 * application classes based on application configuration data.
 */
public class TimeService {

    private static ZoneId timeZone;

    private static void updateTimeZone() {
        String timeZoneName = Settings.getSetting("timezone");
        if (timeZoneName != null) {
            timeZone = ZoneId.of(timeZoneName);
        } else {
            if (timeZone == null) {
                timeZone = ZoneId.ofOffset("UTC", ZoneOffset.UTC);
            }
        }
    }

    /**
     * Gets the timezone specified in the applications configuration data.
     * @return ZoneId of the current timezone
     * @see ZoneId
     */
    public static ZoneId getTimeZone() {
        updateTimeZone();
        return timeZone;
    }

    /**
     * Returns the current time as seconds since unix epoch.
     * @return time in seconds since Unix epoch
     */
    public static long nowSeconds() {
        return Instant.now().getEpochSecond();
    }

    /**
     * Gives the current time as ZonedDateTime in the current timezone.
     * @return time as ZonedDateTime
     * @see ZonedDateTime
     */
    public static ZonedDateTime nowZoned() {
        updateTimeZone();
        return ZonedDateTime.ofInstant(Instant.now(), timeZone);
    }

    /**
     * Converts the given time to ZonedDateTime based on the current timezone.
     * @param time datetime to convert as seconds since Unix epoch.
     * @return time as ZonedDateTime
     * @see ZonedDateTime
     */
    public static ZonedDateTime zonedOfSeconds(long time) {
        updateTimeZone();
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(time), timeZone);
    }

    /**
     * Converts the given LocalDate to ZonedDateTime based on the current timezone.
     * @param localDate date to convert as LocalDate
     * @return time as ZonedDateTime
     */
    public static ZonedDateTime zonedOfLocalDate(LocalDate localDate) {
        updateTimeZone();
        return ZonedDateTime.of(localDate, LocalTime.MIDNIGHT, timeZone);
    }

    /**
     * Returns the start of day of the given date.
     * @param dateTime a ZonedDateTime to get the start of
     * @return start of day as ZonedDateTime
     * @see ZonedDateTime
     */
    public static ZonedDateTime startOfZoned(ZonedDateTime dateTime) {
        updateTimeZone();
        return dateTime.toLocalDate().atStartOfDay(timeZone);
    }

    /**
     * Gives the formatted string representation of the given datetime in the given format.
     * @param dateTime datetime to format as ZonedDateTime
     * @param format the wanted format as a string accepted by DateTimeFormatter
     * @return datetime as string
     * @see DateTimeFormatter
     * @see ZonedDateTime
     */
    public static String formatZoned(ZonedDateTime dateTime, String format) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        return dateTime.format(formatter);
    }
}
