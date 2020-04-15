package productivetime.ui.activitystatsview;

import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;
import productivetime.domain.Activity;
import productivetime.domain.ActivityListControl;
import productivetime.domain.TimeService;
import productivetime.ui.UIElement;
import java.util.List;

public class ActivityStatsLayout implements UIElement<BorderPane> {

    private BorderPane statsViewLayout;
    private ActivityListControl activityListControl;

    public ActivityStatsLayout(ActivityListControl activityListControl) {
        this.activityListControl = activityListControl;

        statsViewLayout = new BorderPane();

        statsViewLayout.setCenter(getChartToday());
    }

    private StackedBarChart<Number, String> getChartToday() {

        List<Activity> activityToday = activityListControl.getActivitiesDay(TimeService.todayStartAsZoned());

        NumberAxis xAxis = new NumberAxis(0, 24, 1);
        xAxis.setLabel("Time (h)");

        CategoryAxis yAxis = new CategoryAxis();

        StackedBarChart<Number, String> todayChart = new StackedBarChart<>(xAxis, yAxis);

        todayChart.setTitle("Activities today");
        todayChart.setLegendVisible(true);

        for (Activity activity : activityToday) {
            XYChart.Series<Number, String> activityBlock = new XYChart.Series<>();
            activityBlock.setName(activity.getType());
            activityBlock.getData().add(new XYChart.Data<>(((double) activity.getDuration())/3600,  "today"));
            todayChart.getData().add(activityBlock);
        }

        todayChart.setMaxHeight(250);

        return todayChart;
    }

    @Override
    public BorderPane getLayout() {
        return statsViewLayout;
    }
}
