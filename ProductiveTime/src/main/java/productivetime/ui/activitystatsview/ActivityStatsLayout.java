package productivetime.ui.activitystatsview;

import javafx.scene.layout.BorderPane;
import productivetime.domain.ActivityListService;
import productivetime.ui.UIElement;
import productivetime.ui.activitystatsview.chartlayout.ActivityBarChartLayout;
import productivetime.ui.activitystatsview.selector.ChartSelector;

public class ActivityStatsLayout implements UIElement<BorderPane> {

    private BorderPane statsViewLayout;

    public ActivityStatsLayout(ActivityListService activityListService) {

        statsViewLayout = new BorderPane();

        statsViewLayout.setBottom(new ChartSelector(statsViewLayout, activityListService).getLayout());

        ActivityBarChartLayout initialActivityBarChart = new ActivityBarChartLayout(activityListService);

        statsViewLayout.setCenter(initialActivityBarChart.getLayout());
    }

    @Override
    public BorderPane getLayout() {
        return statsViewLayout;
    }
}
