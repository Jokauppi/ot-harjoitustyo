package productivetime.ui.activityinsertionview;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
        insertionLayout.setBackground(new Background(new BackgroundFill(Color.rgb(243, 243, 243), null, null)));

        ComboBox<String> activityField = createActivityField();

        insertionLayout.getChildren().add(createLogo());
        insertionLayout.getChildren().add(activityField);
        insertionLayout.getChildren().add(createActivityInsertButton(activityField));
    }

    private ImageView createLogo() {

        Image logo = new Image(getClass().getResourceAsStream("/PT_logo.png"));

        ImageView image = new ImageView(logo);
        image.setPreserveRatio(true);
        image.setFitWidth(400);
        image.setSmooth(true);

        return image;
    }

    private ComboBox<String> createActivityField() {
        ComboBox<String> activityField = new ComboBox<>(FXCollections.observableArrayList(activityListService.getFrequentTypes(10)));

        activityField.setPromptText("Add activity");
        activityField.setPrefWidth(600);
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
        Button activityInsertionButton = new Button("   Add   ");
        activityInsertionButton.setId("blue_button");

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