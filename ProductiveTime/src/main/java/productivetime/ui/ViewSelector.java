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
import productivetime.ui.activityinsertionview.ActivityInsertionLayout;
import productivetime.ui.activitylistview.ActivityListLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewSelector implements UIElement<HBox> {

        private HBox viewSelector;
        private BorderPane mainLayout;

    public ViewSelector(BorderPane mainLayout) {

        viewSelector = new HBox();
        this.mainLayout = mainLayout;

        viewSelector.setSpacing(50);
        viewSelector.setAlignment(Pos.CENTER);
        viewSelector.setPadding(new Insets(20,20,20,20));
        viewSelector.setBackground(new Background(new BackgroundFill(Color.rgb(245,245,245), null, null)));

        viewSelector.getChildren().addAll(createButtons());
    }

    private List<Button> createButtons(){
        Button activityButton = new Button("Activities");

        activityButton.setOnAction((actionEvent -> {
            ActivityListLayout activityListLayout = new ActivityListLayout();
            mainLayout.setCenter(activityListLayout.getLayout());
        }));

        Button homeButton = new Button("Home");

        homeButton.setOnAction((actionEvent -> {
            ActivityInsertionLayout activityInsertionLayout = new ActivityInsertionLayout();
            mainLayout.setCenter(activityInsertionLayout.getLayout());
        }));

        Button statsButton = new Button("Activities");

        statsButton.setOnAction((actionEvent -> {
            mainLayout.setCenter(new Label("Statistics view\nnot yet implemented"));
        }));

        return new ArrayList<>(Arrays.asList(activityButton, homeButton, statsButton));
    }

    @Override
    public HBox getLayout() {
        return viewSelector;
    }
}
