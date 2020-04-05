package productivetime.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import productivetime.dao.ActivityDao;
import productivetime.domain.ActivityControl;
import productivetime.ui.activityinsertionview.ActivityInsertionLayout;
import productivetime.ui.activitylistview.ActivityListLayout;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewSelector implements UIElement<HBox> {

    private HBox viewSelector;
    private BorderPane mainLayout;
    private ActivityControl activityControl;
    private boolean dBCreationSuccesful;

    public ViewSelector(BorderPane mainLayout) {

        this.mainLayout = mainLayout;

        try {
            ActivityDao activityDB = new ActivityDao();
            this.activityControl = new ActivityControl(activityDB);
            dBCreationSuccesful = true;
        } catch (SQLException e) {
            dBCreationSuccesful = false;
            mainLayout.setCenter(new Label("DATABASE\nCONNECTION\nUNSUCCESFUL"));
        }

        viewSelector = new HBox();

        viewSelector.setSpacing(50);
        viewSelector.setAlignment(Pos.CENTER);
        viewSelector.setPadding(new Insets(20, 20, 20, 20));
        viewSelector.setBackground(new Background(new BackgroundFill(Color.rgb(245, 245, 245), null, null)));

        viewSelector.getChildren().addAll(createButtons());

        if (dBCreationSuccesful) {
            ActivityInsertionLayout insertionLayout = new ActivityInsertionLayout(activityControl);
            mainLayout.setCenter(insertionLayout.getLayout());
        }
    }

    private List<Button> createButtons() {
        Button activityButton = createActivityButton();
        Button homeButton = createHomeButton();
        Button statsButton = createStatisticsButton();
        return new ArrayList<>(Arrays.asList(activityButton, homeButton, statsButton));
    }

    private Button createActivityButton() {
        Button activityButton = new Button("Activities");

        if (dBCreationSuccesful) {
            activityButton.setOnAction((actionEvent -> {
                ActivityListLayout activityListLayout = new ActivityListLayout(activityControl);
                mainLayout.setCenter(activityListLayout.getLayout());
            }));
        }

        return activityButton;
    }

    private Button createHomeButton() {
        Button homeButton = new Button("Home");

        if (dBCreationSuccesful) {
            homeButton.setOnAction((actionEvent -> {
                ActivityInsertionLayout insertionLayout = new ActivityInsertionLayout(activityControl);
                mainLayout.setCenter(insertionLayout.getLayout());
            }));
        }

        return homeButton;
    }

    private Button createStatisticsButton() {
        Button statsButton = new Button("Statistics");

        if (dBCreationSuccesful) {
            statsButton.setOnAction((actionEvent -> {
                mainLayout.setCenter(new Label("Statistics view\nnot yet implemented"));
            }));
        }

        return statsButton;
    }

    @Override
    public HBox getLayout() {
        return viewSelector;
    }
}
