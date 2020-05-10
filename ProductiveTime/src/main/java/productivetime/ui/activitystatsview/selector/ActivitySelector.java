package productivetime.ui.activitystatsview.selector;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import productivetime.domain.ActivityListService;
import productivetime.domain.TimeService;
import productivetime.ui.UISelectorElement;
import productivetime.ui.activitystatsview.chart.ActivityLineChart;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Constructs a selector component to select a time range and a type of activity to be shown in an ActivityLineChartLayout.
 * @see ActivityLineChart
 */
public class ActivitySelector implements UISelectorElement<HBox> {

    private ActivityListService activityListService;
    private BorderPane chartLayout;
    private HBox selectorPanel;
    private ComboBox<String> activitySelector;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;

    public ActivitySelector(BorderPane chartLayout, ActivityListService activityListService) {

        this.activityListService = activityListService;
        this.chartLayout = chartLayout;

        selectorPanel = new HBox();

        selectorPanel.setSpacing(50);
        selectorPanel.setAlignment(Pos.CENTER);
        selectorPanel.setPadding(new Insets(20, 20, 20, 20));
        selectorPanel.setBackground(new Background(new BackgroundFill(Color.rgb(250, 250, 250), null, null)));

        activitySelector = activitySelector();
        activitySelector.setOnAction(actionEvent -> updateLineChart());

        startDatePicker = daySelector(TimeService.nowZoned().plusDays(-7));
        startDatePicker.setOnAction(actionEvent -> updateLineChart());

        endDatePicker = daySelector(TimeService.nowZoned());
        endDatePicker.setOnAction(actionEvent -> updateLineChart());

        selectorPanel.getChildren().addAll(List.of(activitySelector, startDatePicker, endDatePicker));
    }
    
    private ComboBox<String> activitySelector() {
        List<String> allActivities = activityListService.getAllTypes();
        
        ComboBox<String> activityBox = new ComboBox<>(FXCollections.observableArrayList(allActivities));

        if (!allActivities.isEmpty()) {
            activityBox.setValue(allActivities.get(0));
        }
        activityBox.setEditable(false);
        activityBox.setBackground(new Background(new BackgroundFill(Color.rgb(225, 225, 225), new CornerRadii(30), null)));

        return activityBox;
    }

    private DatePicker daySelector(ZonedDateTime defaultDate) {

        DatePicker daySelect = new DatePicker(TimeService.nowZoned().toLocalDate());
        daySelect.setShowWeekNumbers(false);
        daySelect.setValue(defaultDate.toLocalDate());

        return daySelect;
    }

    public void updateLineChart() {
        if (startDatePicker.getValue().compareTo(endDatePicker.getValue()) <= 0) {
            ZonedDateTime start = TimeService.zonedOfLocalDate(startDatePicker.getValue());
            ZonedDateTime end = TimeService.zonedOfLocalDate(endDatePicker.getValue().plusDays(1));
            setView(new ActivityLineChart(activityListService,
                    activitySelector.getValue(), start, end).getLayout());
        }
    }

    @Override
    public HBox getLayout() {
        return selectorPanel;
    }

    @Override
    public void setView(Node node) {
        chartLayout.setCenter(Objects.requireNonNullElseGet(node, () -> new Label("No Data")));
    }
}
