package productivetime.ui.activitystatsview.chartlayout;

import javafx.scene.layout.BorderPane;
import productivetime.domain.ActivityListControl;
import productivetime.domain.TimeService;
import productivetime.ui.UIElement;
import productivetime.ui.activitystatsview.selector.ChartType;
import productivetime.ui.activitystatsview.selector.DaySelector;
import productivetime.ui.activitystatsview.chart.ActivityBarChart;

public class ActivityBarChartLayout implements UIElement<BorderPane> {

    private BorderPane chartLayout;

    public ActivityBarChartLayout(ActivityListControl activityListControl) {

        chartLayout = new BorderPane();

        DaySelector daySelector = new DaySelector(chartLayout, activityListControl, ChartType.BAR);

        daySelector.setView(new ActivityBarChart(activityListControl, TimeService.nowZoned()).getLayout());

        chartLayout.setBottom(daySelector.getLayout());

    }

    @Override
    public BorderPane getLayout() {
        return chartLayout;
    }
}
