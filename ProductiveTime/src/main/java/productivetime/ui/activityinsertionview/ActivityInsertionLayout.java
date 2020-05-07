package productivetime.ui.activityinsertionview;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import productivetime.domain.ActivityInsertService;
import productivetime.domain.ActivityListService;
import productivetime.ui.UIElement;

public class ActivityInsertionLayout implements UIElement<VBox> {

    private VBox insertionLayout;
    private ActivityInsertService activityInsertService;
    private ActivityListService activityListService;

    public ActivityInsertionLayout(ActivityInsertService activityInsertService, ActivityListService activityListService) {

        this.activityInsertService = activityInsertService;
        this.activityListService = activityListService;

        insertionLayout = new VBox();
        insertionLayout.setSpacing(40);
        insertionLayout.setAlignment(Pos.CENTER);
        insertionLayout.setPadding(new Insets(20, 20, 20, 20));

        ComboBox<String> activityField = createActivityField();

        insertionLayout.getChildren().add(createLogo());
        insertionLayout.getChildren().add(activityField);
        insertionLayout.getChildren().add(createActivityInsertButton(activityField));
    }

    private Label createLogo() {
        Label logo = new Label("ProductiveTime");

        logo.setPadding(new Insets(0, 0, 20, 0));
        logo.setFont(new Font(20));
        logo.setScaleX(1.5);
        logo.setScaleY(1.5);

        return logo;
    }

    private ComboBox<String> createActivityField() {
        ComboBox<String> activityField = new ComboBox<>(FXCollections.observableArrayList(activityListService.getFrequentTypes(10)));

        activityField.setPromptText("Add activity");
        activityField.setPrefWidth(400);
        activityField.setPrefHeight(40);
        activityField.setEditable(true);

        activityField.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                insertActivity(activityField);
            }
        });
        activityField.setOnMouseClicked(mouseEvent -> activityField.setPromptText("Add activity"));

        return activityField;
    }

    private Button createActivityInsertButton(ComboBox<String> relatedField) {
        Button activityInsertionButton = new Button("Add");

        activityInsertionButton.setBackground(new Background(new BackgroundFill(Color.rgb(66,133,255), new CornerRadii(30), null)));
        activityInsertionButton.setTextFill(Color.WHITE);
        activityInsertionButton.setScaleX(1.5);
        activityInsertionButton.setScaleY(1.5);

        activityInsertionButton.setOnAction((actionEvent -> insertActivity(relatedField)));

        return activityInsertionButton;
    }

    private void insertActivity(ComboBox<String> insertionField) {
        String type = "";

        if (insertionField.getValue() != null) {
            type = insertionField.getValue().toLowerCase().trim();
        }

        if (type.length() > 25) {
            insertionField.setPromptText("Type must be under 25 characters");
        } else if (type.length() > 0) {
            activityInsertService.addActivity(type);
            insertionField.setPromptText("Activity added");
        }

        insertionField.setValue("");
        insertionLayout.requestFocus();
    }

    @Override
    public VBox getLayout() {
        return insertionLayout;
    }
}