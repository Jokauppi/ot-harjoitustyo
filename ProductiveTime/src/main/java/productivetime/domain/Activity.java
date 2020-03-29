package productivetime.domain;

public class Activity {
    Integer id;
    String type;
    int start;
    int duration;

    public Activity(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return start+duration;
    }
}
