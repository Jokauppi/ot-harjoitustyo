package productivetime.domain;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Activity implements Comparable<Activity> {
    private Integer id;
    private String type;
    private Long start;
    private Integer duration;

    public Activity(String type) {
        this.type = type;
    }

    public Activity(Integer id, String type, long start, int duration) {
        this.id = id;
        this.type = type;
        this.start = start;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public long getStart() {
        return start;
    }

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

    public String getStartFormatted() {
        return getStartFormatted(TimeService.nowZoned());
    }

    public int getDuration() {
        return duration;
    }

    public String getDurationFormatted() {
        if (duration == 0) {
            return "Ongoing";
        }
        if (duration < 60) {
            return 0 + " min";
        }
        if (duration < 3600) {
            return duration / 60 + " min";
        }
        return String.format("%d h %d min", duration / 3600, (duration % 3600) / 60);
    }

    public long getEnd() {
        return start + duration;
    }

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


    @Override
    public int compareTo(Activity activity) {
        if (this.getStart() > activity.getStart()) {
            return -1;
        } else {
            return 1;
        }
    }
}
