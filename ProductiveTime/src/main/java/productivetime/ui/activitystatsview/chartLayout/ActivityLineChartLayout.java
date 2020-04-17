package productivetime.ui.activitystatsview.chartLayout;

import javafx.scene.chart.LineChart;
import javafx.scene.layout.BorderPane;
import productivetime.ui.UIElement;

import java.time.ZonedDateTime;

public class ActivityLineChartLayout implements UIElement<BorderPane> {

    private LineChart<Number, Number> activityChart;

    public ActivityLineChartLayout(LineChart<Number, Number> activityChart, ZonedDateTime startDate, ZonedDateTime endDate) {
        this.activityChart = activityChart;
    }

    @Override
    public BorderPane getLayout() {
        return null;
    }
}
