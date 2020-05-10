package productivetime.ui.activitystatsview;

import javafx.scene.layout.BorderPane;
import productivetime.domain.ActivityListService;
import productivetime.ui.UIElement;
import productivetime.ui.activitystatsview.chartlayout.ActivityBarChartLayout;
import productivetime.ui.activitystatsview.chartlayout.ActivityLineChartLayout;
import productivetime.ui.activitystatsview.selector.ChartSelector;

/**
 * Constructs the base layout for different selector and chart layouts.
 */
public class ActivityStatsLayout implements UIElement<BorderPane> {

    private BorderPane statsViewLayout;

    public ActivityStatsLayout(ActivityListService activityListService) {

        statsViewLayout = new BorderPane();

        statsViewLayout.setBottom(new ChartSelector(statsViewLayout, activityListService).getLayout());

        ActivityLineChartLayout initialActivityLineChartLayout = new ActivityLineChartLayout(activityListService);

        statsViewLayout.setCenter(initialActivityLineChartLayout.getLayout());
    }

    @Override
    public BorderPane getLayout() {
        return statsViewLayout;
    }
}
