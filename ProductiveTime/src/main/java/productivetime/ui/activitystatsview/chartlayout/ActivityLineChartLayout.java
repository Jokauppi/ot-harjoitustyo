package productivetime.ui.activitystatsview.chartlayout;

import javafx.scene.layout.BorderPane;
import productivetime.domain.ActivityListService;
import productivetime.ui.UIElement;
import productivetime.ui.activitystatsview.selector.ActivitySelector;

/**
 * Constructs a base for the ActivityLineChart to be viewed in.
 * @see productivetime.ui.activitystatsview.chart.ActivityLineChart
 */
public class ActivityLineChartLayout implements UIElement<BorderPane> {

    private BorderPane chartLayout;

    public ActivityLineChartLayout(ActivityListService activityListService) {

        chartLayout = new BorderPane();

        ActivitySelector activitySelector = new ActivitySelector(chartLayout, activityListService);

        chartLayout.setBottom(activitySelector.getLayout());

        activitySelector.updateLineChart();
    }

    @Override
    public BorderPane getLayout() {
        return chartLayout;
    }
}
