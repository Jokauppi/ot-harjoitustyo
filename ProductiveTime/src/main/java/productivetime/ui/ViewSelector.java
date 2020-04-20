package productivetime.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import productivetime.domain.ActivityInsertControl;
import productivetime.domain.ActivityListControl;
import productivetime.ui.activityinsertionview.ActivityInsertionLayout;
import productivetime.ui.activitylistview.ActivityListLayout;
import productivetime.ui.activitystatsview.ActivityStatsLayout;
import productivetime.ui.settingsview.SettingsListLayout;

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
        viewSelector.setBackground(new Background(new BackgroundFill(Color.rgb(245, 245, 245), null, null)));

        viewSelector.getChildren().addAll(createButtons());

        setView(new ActivityInsertionLayout(activityInsertControl).getLayout());
    }

    private List<Button> createButtons() {
        Button activityButton = createButton("Activities", new ActivityListLayout(activityListControl, activityInsertControl).getLayout());
        Button homeButton = createButton("Home", new ActivityInsertionLayout(activityInsertControl).getLayout());
        Button statsButton = createButton("Statistics", new ActivityStatsLayout(activityListControl).getLayout());
        Button settingsButton = createButton("Settings", new SettingsListLayout().getLayout());
        return new ArrayList<>(Arrays.asList(activityButton, homeButton, statsButton, settingsButton));
    }

    private Button createButton(String label, Node view) {
        Button button = new Button(label);

        button.setBackground(new Background(new BackgroundFill(Color.rgb(220,220,220), new CornerRadii(30), null)));
        button.setTextFill(Color.BLACK);
        button.setScaleX(1.3);
        button.setScaleY(1.3);

        button.setOnAction(actionEvent -> setView(view));

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
