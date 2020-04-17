package productivetime.ui.activitystatsview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import productivetime.domain.ActivityListControl;
import productivetime.ui.UISelectorElement;

public class ChartSelector implements UISelectorElement<HBox> {

    private ActivityListControl activityListControl;
    private BorderPane statsViewLayout;
    private HBox chartSelector;

    public ChartSelector(BorderPane statsViewLayout, ActivityListControl activityListControl) {
        this.statsViewLayout = statsViewLayout;
        this.activityListControl = activityListControl;

        chartSelector = new HBox();

        chartSelector.setSpacing(50);
        chartSelector.setAlignment(Pos.CENTER);
        chartSelector.setPadding(new Insets(20, 20, 20, 20));
        chartSelector.setBackground(new Background(new BackgroundFill(Color.rgb(240, 240, 240), null, null)));

        chartSelector.getChildren().add(barChartButton());
        chartSelector.getChildren().add(lineChartButton());
    }

    private Button barChartButton() {
        Button button = new Button("Day view");

        button.setOnAction(actionEvent ->
                setView(new ActivityBarChartLayout(activityListControl).getLayout()));

        return button;
    }

    private Button lineChartButton() {
        Button button = new Button("Activity view");

        button.setOnAction(actionEvent ->
                setView(new Label("Activity Chart not implemented yet")));

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
