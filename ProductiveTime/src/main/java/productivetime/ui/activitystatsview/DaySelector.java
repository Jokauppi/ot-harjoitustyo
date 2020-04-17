package productivetime.ui.activitystatsview;

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
import productivetime.domain.ActivityListControl;
import productivetime.domain.TimeService;
import productivetime.ui.UISelectorElement;

public class DaySelector implements UISelectorElement<HBox> {

    private ActivityListControl activityListControl;
    private BorderPane chartLayout;
    private HBox daySelector;
    private ChartType type;

    public DaySelector(BorderPane chartLayout, ActivityListControl activityListControl, ChartType type) {

        this.activityListControl = activityListControl;
        this.chartLayout = chartLayout;
        this.type = type;

        daySelector = new HBox();

        daySelector.setSpacing(50);
        daySelector.setAlignment(Pos.CENTER);
        daySelector.setPadding(new Insets(20, 20, 20, 20));
        daySelector.setBackground(new Background(new BackgroundFill(Color.rgb(250, 250, 250), null, null)));

        if (type == ChartType.BAR) {
            singleDaySelector();
        } else if (type == ChartType.LINE) {
            dayRangeSelector();
        }
    }

    private void singleDaySelector() {

        DatePicker daySelect = new DatePicker(TimeService.nowZoned().toLocalDate());
        daySelect.setShowWeekNumbers(false);

        daySelect.setOnAction(actionEvent ->
                setView(new ActivityBarChart(activityListControl, TimeService.zonedOfLocalDate(daySelect.getValue())).getLayout()));

        daySelector.getChildren().add(daySelect);

        Button todayButton = new Button("Today");

        todayButton.setOnAction(actionEvent ->
                setView(new ActivityBarChart(activityListControl, TimeService.nowZoned()).getLayout()));

        daySelector.getChildren().add(todayButton);
    }

    private void dayRangeSelector() {

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
