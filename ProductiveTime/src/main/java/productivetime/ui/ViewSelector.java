package productivetime.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import productivetime.dao.ActivityDao;
import productivetime.domain.ActivityInsertControl;
import productivetime.domain.ActivityListControl;
import productivetime.ui.activityinsertionview.ActivityInsertionLayout;
import productivetime.ui.activitylistview.ActivityListLayout;
import productivetime.ui.activitystatsview.ActivityStatsLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewSelector implements UISelectorElement<HBox> {

    private HBox viewSelector;
    private BorderPane mainLayout;
    private ActivityInsertControl activityInsertControl;
    private ActivityListControl activityListControl;

    public ViewSelector(BorderPane mainLayout, ActivityInsertControl activityInsertControl, ActivityListControl activityListControl) {

        this.mainLayout = mainLayout;
        this.activityInsertControl = activityInsertControl;
        this.activityListControl = activityListControl;

        viewSelector = new HBox();

        viewSelector.setSpacing(50);
        viewSelector.setAlignment(Pos.CENTER);
        viewSelector.setPadding(new Insets(20, 20, 20, 20));
        viewSelector.setBackground(new Background(new BackgroundFill(Color.rgb(225, 225, 225), null, null)));

        viewSelector.getChildren().addAll(createButtons());

        ActivityInsertionLayout insertionLayout = new ActivityInsertionLayout(activityInsertControl);
        mainLayout.setCenter(insertionLayout.getLayout());
    }

    private List<Button> createButtons() {
        Button activityButton = createActivityButton();
        Button homeButton = createHomeButton();
        Button statsButton = createStatisticsButton();
        return new ArrayList<>(Arrays.asList(activityButton, homeButton, statsButton));
    }

    private Button createActivityButton() {
        Button activityButton = new Button("Activities");

        activityButton.setOnAction(actionEvent ->
                setView(new ActivityListLayout(activityListControl, activityInsertControl).getLayout()));

        return activityButton;
    }

    private Button createHomeButton() {
        Button homeButton = new Button("Home");

        homeButton.setOnAction(actionEvent ->
                setView(new ActivityInsertionLayout(activityInsertControl).getLayout()));

        return homeButton;
    }


    private Button createStatisticsButton() {
        Button statsButton = new Button("Statistics");

        statsButton.setOnAction(actionEvent ->
                setView(new ActivityStatsLayout(activityListControl).getLayout()));

        return statsButton;
    }

    @Override
    public HBox getLayout() {
        return viewSelector;
    }

    @Override
    public void setView(Node node) {
        mainLayout.setCenter(node);
    }


}
