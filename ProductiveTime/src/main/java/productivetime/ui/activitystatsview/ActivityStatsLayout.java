package productivetime.ui.activitystatsview;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import productivetime.domain.Activity;
import productivetime.domain.ActivityListControl;
import productivetime.domain.TimeService;
import productivetime.ui.UIElement;
import java.util.HashMap;
import java.util.List;

public class ActivityStatsLayout implements UIElement<BorderPane> {

    private BorderPane statsViewLayout;
    private ActivityListControl activityListControl;

    public ActivityStatsLayout(ActivityListControl activityListControl) {
        this.activityListControl = activityListControl;

        statsViewLayout = new BorderPane();

        statsViewLayout.setCenter(getChartToday());
    }

    private BarChart<String, Number> getChartToday() {

        List<Activity> activityToday = activityListControl.getActivitiesDay(TimeService.todayStartAsZoned());

        CategoryAxis xAxis = new CategoryAxis();

        int upperbound = ActivityListControl.getLongestDurationInMinutes(activityToday) + 30;

        NumberAxis yAxis = new NumberAxis(0, upperbound, 30);

        xAxis.setLabel("Activity types");
        yAxis.setLabel("Duration (min)");

        BarChart<String, Number> todayChart = new BarChart<>(xAxis, yAxis);

        todayChart.setTitle("Activities today");
        todayChart.setLegendVisible(false);

        XYChart.Series<String, Number> durations = new XYChart.Series<>();
        durations.setName("Duration");

        HashMap<String, Integer> activityMap = new HashMap<>();
        for (Activity activity : activityToday) {
            activityMap.put(activity.getType(), activityMap.getOrDefault(activity.getType(), 0) + activity.getDuration() / 60);
        }

        for (String type : activityMap.keySet()) {
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
