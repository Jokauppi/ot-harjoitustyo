package productivetime.ui.activitystatsview;

import javafx.scene.layout.BorderPane;
import productivetime.domain.ActivityListControl;
import productivetime.ui.UIElement;
import productivetime.ui.activitystatsview.chartlayout.ActivityBarChartLayout;
import productivetime.ui.activitystatsview.selector.ChartSelector;

public class ActivityStatsLayout implements UIElement<BorderPane> {

    private BorderPane statsViewLayout;

    public ActivityStatsLayout(ActivityListControl activityListControl) {

        statsViewLayout = new BorderPane();

        statsViewLayout.setBottom(new ChartSelector(statsViewLayout, activityListControl).getLayout());

        ActivityBarChartLayout initialActivityBarChart = new ActivityBarChartLayout(activityListControl);

        statsViewLayout.setCenter(initialActivityBarChart.getLayout());
    }

    @Override
    public BorderPane getLayout() {
        return statsViewLayout;
    }
}
