package productivetime.domain;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The class stores the data of an activity and provides methods to compare activities and get the string representations
 * of its start and duration.
 */
public class Activity implements Comparable<Activity> {

    private Integer id;
    private String type;
    private Long start;
    private Integer duration;

    /**
     * Creates a plain activity with only a type and no start.
     *
     * @param type The type of the activity
     */
    public Activity(String type) {
        this.type = type;
    }

    /**
     * Creates an activity with a type, start and duration.
     *
     * @param id The id of the activity
     * @param type The type of the activity
     * @param start The start time of the activity in seconds since Unix epoch
     * @param duration The duration of the activity in seconds
     */
    public Activity(Integer id, String type, long start, Integer duration) {
        this.id = id;
        this.type = type;
        this.start = start;
        this.duration = duration;
    }

    /**
     * Gets the id of the activity.
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the type of the activity.
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the start time of the activity in seconds since Unix epoch.
     * @return start time
     */
    public long getStart() {
        return start;
    }

    /**
     * Gives a formatted string representation of the activity's start time based on given date.
     *
     * @param now date as ZonedDateTime
     * @see ZonedDateTime
     * @return start time in format "hh:mm" if activity starts on given day,
     * in format "dd month hh:mm" if activity starts on given date's year,
     * otherwise in format "dd month yyyy hh:mm"
     */
    public String getStartFormatted(ZonedDateTime now) {
        ZonedDateTime activityDate = TimeService.zonedOfSeconds(this.start);
        if (now.getYear() == activityDate.getYear()) {
            if (now.getDayOfYear() == activityDate.getDayOfYear()) {
                String datePattern = "HH:mm";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
                return dateFormatter.format(activityDate);
            } else {
                String datePattern = "dd LLL HH:mm";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
                return dateFormatter.format(activityDate);
            }
        } else {
            String datePattern = "dd LLL uuuu HH:mm";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
            return dateFormatter.format(activityDate);
        }
    }

    /**
     * Gives a formatted string representation of the activity's start time based on given date.
     *
     * @return the time string given by getStartFormatted(ZonedDateTime) based on current date
     * @see #getStartFormatted(ZonedDateTime)
     */
    public String getStartFormatted() {
        return getStartFormatted(TimeService.nowZoned());
    }

    /**
     * Gives the duration of the activity in seconds
     *
     * @return duration of activity
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets a new duration for the activity.
     *
     * @param duration new duration in seconds
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Gives a formatted string representation of the activity's duration based on the magnitude of the duration
     *
     * @return "Ongoing" if activity is ongoing, duration in minutes if duration is less than an hour,
     * otherwise duration in hours and minutes
     */
    public String getDurationFormatted() {
        if (isOngoing()) {
            return "Ongoing";
        }
        if (duration < 3600) {
            return duration / 60 + " min";
        }
        return String.format("%d h %d min", duration / 3600, (duration % 3600) / 60);

    }

    /**
     * Gets the end time of the activity as seconds since Unix epoch
     *
     * @return the end time
     */
    public long getEnd() {
        return start + duration;
    }

    /**
     * Tells if the activity is ongoing or if it has been finished
     *
     * @return true if duration is null, otherwise false
     */
    public boolean isOngoing() {
        return duration == null;
    }

    /**
    * Tells if activities are equal. Activities are equal when they have the same type.
    *
    * @return true if activities have same type, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activity activity = (Activity) o;
        return type.equals(activity.type);
    }

    @Override
    public String toString() {
        return "Activity{" +
                "type='" + type + '\'' +
                ", start=" + start +
                ", duration=" + duration +
                '}';
    }

    /**
     * Compares which activity started has an earlier start time.
     *
     * @param activity activity to be compared to
     *
     * @return -1 if activity given as parameter starts earlier, otherwise 1
     */
    @Override
    public int compareTo(Activity activity) {
        if (this.getStart() > activity.getStart()) {
            return -1;
        } else {
            return 1;
        }
    }
}
