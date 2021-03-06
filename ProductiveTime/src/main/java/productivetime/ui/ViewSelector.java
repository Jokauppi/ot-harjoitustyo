package productivetime.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import productivetime.domain.ActivityInsertService;
import productivetime.domain.ActivityListService;
import productivetime.ui.activityinsertionview.ActivityInsertionLayout;
import productivetime.ui.activitylistview.ActivityListLayout;
import productivetime.ui.activitystatsview.ActivityStatsLayout;
import productivetime.ui.settingsview.SettingsListLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Constructs a selector element to change between different views in the application.
 */
public class ViewSelector implements UISelectorElement<HBox> {

    private HBox viewSelector;
    private BorderPane mainLayout;
    private ActivityInsertService activityInsertService;
    private ActivityListService activityListService;

    public ViewSelector(BorderPane mainLayout, ActivityInsertService activityInsertService, ActivityListService activityListService) {

        this.mainLayout = mainLayout;
        this.activityInsertService = activityInsertService;
        this.activityListService = activityListService;

        viewSelector = new HBox();

        viewSelector.setSpacing(50);
        viewSelector.setAlignment(Pos.CENTER);
        viewSelector.setPadding(new Insets(20, 20, 20, 20));
        viewSelector.setStyle("-fx-background-color: #fbfbfb");
        viewSelector.setStyle("-fx-border-color: #cccccc");

        viewSelector.getChildren().addAll(createButtons());

        setView(new ActivityInsertionLayout(activityInsertService, activityListService).getLayout());
    }

    private List<Button> createButtons() {
        Button activityButton = createButton("Activities");
        activityButton.setOnAction(actionEvent -> setView(new ActivityListLayout(activityListService, activityInsertService).getLayout()));
        Button homeButton = createButton("Home");
        homeButton.setOnAction(actionEvent -> setView(new ActivityInsertionLayout(activityInsertService, activityListService).getLayout()));
        Button statsButton = createButton("Statistics");
        statsButton.setOnAction(actionEvent -> setView(new ActivityStatsLayout(activityListService).getLayout()));
        Button settingsButton = createButton("Settings");
        settingsButton.setOnAction(actionEvent -> setView(new SettingsListLayout(activityInsertService, activityListService).getLayout()));
        return new ArrayList<>(Arrays.asList(activityButton, homeButton, statsButton, settingsButton));
    }

    private Button createButton(String label) {
        Button button = new Button(label);
        button.setId("grey_button");
        return button;
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
