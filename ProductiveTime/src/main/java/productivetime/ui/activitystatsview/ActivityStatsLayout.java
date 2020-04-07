package productivetime.ui.activitystatsview;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import productivetime.domain.Activity;
import productivetime.domain.ActivityControl;
import productivetime.ui.UIElement;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityStatsLayout implements UIElement<BorderPane> {

    private BorderPane statsViewLayout;
    private ActivityControl activityControl;

    public ActivityStatsLayout(ActivityControl activityControl) {
        this.activityControl = activityControl;

        statsViewLayout = new BorderPane();

        statsViewLayout.setCenter(getChartToday());
    }

    private BarChart<String, Number> getChartToday() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> todayChart = new BarChart<String, Number>(xAxis, yAxis);
        todayChart.setTitle("Activities today");
        xAxis.setLabel("Activity types");
        yAxis.setLabel("Duration today");

        XYChart.Series<String, Number> durations = new XYChart.Series<>();
        durations.setName("Duration");

        ZonedDateTime todayStart = ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0);

        List<Activity> activityToday = activityControl.getActivitiesDay(todayStart);

        HashMap<String, Integer> activityMap = new HashMap<>();
        for (Activity activity : activityToday) {
            activityMap.put(activity.getType(), activityMap.getOrDefault(activity.getType(), 0) + activity.getDuration()/60);
        }

        for (String type : activityMap.keySet()){
            durations.getData().add(new XYChart.Data<>(type, activityMap.get(type)));
        }

        todayChart.getData().add(durations);

        return todayChart;
    }

    @Override
    public BorderPane getLayout() {
        return statsViewLayout;
    }
}
