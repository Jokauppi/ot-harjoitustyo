package productivetime.domain;

public class Activity {
    Integer id;
    String type;
    int start;
    Integer duration;

    public Activity(String type) {
        this.type = type;
    }

    public Activity(Integer id, String type, int start) {
        this.id = id;
        this.type = type;
        this.start = start;
        this.duration = null;
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

    public Integer getDuration() {
        return duration;
    }

    public int getEnd() {
        return start+duration;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", start=" + start +
                ", duration=" + duration +
                '}';
    }
}
