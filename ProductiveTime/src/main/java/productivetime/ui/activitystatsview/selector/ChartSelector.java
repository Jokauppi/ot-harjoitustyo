package productivetime.ui.activitystatsview.selector;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import productivetime.domain.ActivityListService;
import productivetime.ui.UISelectorElement;
import productivetime.ui.activitystatsview.chartlayout.ActivityBarChartLayout;
import productivetime.ui.activitystatsview.chartlayout.ActivityLineChartLayout;

/**
 * Constructs a selector component to change between different chart layouts.
 */
public class ChartSelector implements UISelectorElement<HBox> {

    private ActivityListService activityListService;
    private BorderPane statsViewLayout;
    private HBox chartSelector;

    public ChartSelector(BorderPane statsViewLayout, ActivityListService activityListService) {
        this.statsViewLayout = statsViewLayout;
        this.activityListService = activityListService;

        chartSelector = new HBox();

        chartSelector.setSpacing(50);
        chartSelector.setAlignment(Pos.CENTER);
        chartSelector.setPadding(new Insets(20, 20, 20, 20));
        chartSelector.setBackground(new Background(new BackgroundFill(Color.rgb(245, 245, 245), null, null)));
        chartSelector.setStyle("-fx-border-color: #cccccc");

        chartSelector.getChildren().add(lineChartButton());
        chartSelector.getChildren().add(barChartButton());
    }

    private Button barChartButton() {
        Button button = new Button("Day view");
        button.setId("grey_button");

        button.setOnAction(actionEvent ->
                setView(new ActivityBarChartLayout(activityListService).getLayout()));

        return button;
    }

    private Button lineChartButton() {
        Button button = new Button("Activity view");
        button.setId("grey_button");

        button.setOnAction(actionEvent ->
                setView(new ActivityLineChartLayout(activityListService).getLayout()));

        return button;
    }

    @Override
    public HBox getLayout() {
        return chartSelector;
    }

    @Override
    public void setView(Node node) {
        statsViewLayout.setCenter(node);
    }
}
