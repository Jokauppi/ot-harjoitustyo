package productivetime.ui.activitystatsview.selector;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import productivetime.domain.ActivityListService;
import productivetime.domain.TimeService;
import productivetime.ui.UISelectorElement;
import productivetime.ui.activitystatsview.chart.ActivityBarChart;

/**
 * Constructs a selector component to select a day to be shown in an ActivityBarChartLayout.
 * @see ActivityBarChart
 */
public class DaySelector implements UISelectorElement<HBox> {

    private ActivityListService activityListService;
    private BorderPane chartLayout;
    private HBox daySelector;

    public DaySelector(BorderPane chartLayout, ActivityListService activityListService) {

        this.activityListService = activityListService;
        this.chartLayout = chartLayout;

        daySelector = new HBox();

        daySelector.setSpacing(50);
        daySelector.setAlignment(Pos.CENTER);
        daySelector.setPadding(new Insets(20, 20, 20, 20));
        daySelector.setBackground(new Background(new BackgroundFill(Color.rgb(250, 250, 250), null, null)));

        daySelector();
    }

    private void daySelector() {

        DatePicker daySelect = new DatePicker(TimeService.nowZoned().toLocalDate());
        daySelect.setShowWeekNumbers(false);

        daySelect.setOnAction(actionEvent ->
                setView(new ActivityBarChart(activityListService, TimeService.zonedOfLocalDate(daySelect.getValue())).getLayout()));

        daySelector.getChildren().add(daySelect);

        Button todayButton = new Button("Today");
        todayButton.setId("grey_button");

        todayButton.setOnAction(actionEvent ->
                setView(new ActivityBarChart(activityListService, TimeService.nowZoned()).getLayout()));

        daySelector.getChildren().add(todayButton);
    }

    @Override
    public HBox getLayout() {
        return daySelector;
    }

    @Override
    public void setView(Node node) {
        if (node == null) {
            chartLayout.setCenter(new Label("No Data"));
        } else {
            chartLayout.setCenter(node);
        }
    }
}
