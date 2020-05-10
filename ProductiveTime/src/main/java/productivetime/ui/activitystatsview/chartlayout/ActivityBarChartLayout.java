package productivetime.ui.activitystatsview.chartlayout;

import javafx.scene.layout.BorderPane;
import productivetime.domain.ActivityListService;
import productivetime.domain.TimeService;
import productivetime.ui.UIElement;
import productivetime.ui.activitystatsview.selector.DaySelector;
import productivetime.ui.activitystatsview.chart.ActivityBarChart;

/**
 * Constructs a base for the ActivityBarChart to be viewed in.
 * @see productivetime.ui.activitystatsview.chart.ActivityBarChart
 */
public class ActivityBarChartLayout implements UIElement<BorderPane> {

    private BorderPane chartLayout;

    public ActivityBarChartLayout(ActivityListService activityListService) {

        chartLayout = new BorderPane();

        DaySelector daySelector = new DaySelector(chartLayout, activityListService);

        daySelector.setView(new ActivityBarChart(activityListService, TimeService.nowZoned()).getLayout());

        chartLayout.setBottom(daySelector.getLayout());

    }

    @Override
    public BorderPane getLayout() {
        return chartLayout;
    }
}
