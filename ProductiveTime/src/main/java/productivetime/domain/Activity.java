package productivetime.domain;

import java.util.Objects;

public class Activity implements Comparable<Activity> {
    private Integer id;
    private String type;
    private Integer start;
    private Integer duration;

    public Activity(String type) {
        this.type = type;
    }

    public Activity(Integer id, String type, int start, int duration) {
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

    public int getStart() {
        return start;
    }

    public int getDuration() {
        return duration;
    }

    public int getEnd() {
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
    public int hashCode() {
        return Objects.hash(type);
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
