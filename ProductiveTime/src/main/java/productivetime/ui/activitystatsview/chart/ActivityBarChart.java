package productivetime.ui.activitystatsview.chart;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

import productivetime.domain.Activity;
import productivetime.domain.ActivityListService;
import productivetime.ui.UIElement;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Constructs a chart to show all activities on a timeline on a given day.
 */
public class ActivityBarChart implements UIElement<StackedBarChart<Number, String>> {

    private StackedBarChart<Number, String> chart;

    public ActivityBarChart(ActivityListService activityListService, ZonedDateTime date) {

        List<Activity> activityToday = activityListService.getActivitiesOnDayOf(date);

        if (!activityToday.isEmpty()) {

            NumberAxis xAxis = new NumberAxis(0, 24, 1);
            xAxis.setLabel("Time (h)");

            CategoryAxis yAxis = new CategoryAxis();

            chart = new StackedBarChart<>(xAxis, yAxis);

            chart.setTitle("Activities on " + date.toLocalDate().format(DateTimeFormatter.ofPattern("dd LLL uuuu")));
            chart.setLegendVisible(true);

            for (Activity activity : activityToday) {
                XYChart.Series<Number, String> activityBlock = new XYChart.Series<>();
                activityBlock.setName(activity.getType());
                activityBlock.getData().add(new XYChart.Data<>(((double) activity.getDuration()) / 3600,  "today"));
                chart.getData().add(activityBlock);
            }

            chart.setMaxHeight(250);
        }
    }

    @Override
    public StackedBarChart<Number, String> getLayout() {
        return chart;
    }
}
