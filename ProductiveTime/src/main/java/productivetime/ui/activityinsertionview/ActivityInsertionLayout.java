package productivetime.ui.activityinsertionview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import productivetime.domain.ActivityInsertControl;
import productivetime.ui.UIElement;

public class ActivityInsertionLayout implements UIElement<VBox> {

    private VBox insertionLayout;
    private ActivityInsertControl activityInsertControl;

    public ActivityInsertionLayout(ActivityInsertControl activityInsertControl) {

        this.activityInsertControl = activityInsertControl;

        insertionLayout = new VBox();
        insertionLayout.setSpacing(40);
        insertionLayout.setAlignment(Pos.CENTER);
        insertionLayout.setPadding(new Insets(20, 20, 20, 20));

        TextField activityField = createActivityField();

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

    private TextField createActivityField() {
        TextField activityField = new TextField();

        activityField.setPromptText("Add activity");
        activityField.setPrefWidth(150);
        activityField.setMaxWidth(200);
        activityField.setScaleX(1.5);
        activityField.setScaleY(1.5);
        activityField.setBackground(new Background(new BackgroundFill(Color.rgb(240, 240, 240), new CornerRadii(30), null)));

        activityField.setOnAction(actionEvent -> insertActivity(activityField));
        activityField.setOnMouseClicked(mouseEvent -> activityField.setPromptText("Add activity"));

        return activityField;
    }

    private Button createActivityInsertButton(TextField relatedField) {
        Button activityInsertionButton = new Button("Add");

        activityInsertionButton.setBackground(new Background(new BackgroundFill(Color.rgb(66,133,255), new CornerRadii(30), null)));
        activityInsertionButton.setTextFill(Color.WHITE);
        activityInsertionButton.setScaleX(1.5);
        activityInsertionButton.setScaleY(1.5);

        activityInsertionButton.setOnAction((actionEvent -> insertActivity(relatedField)));

        return activityInsertionButton;
    }

    private void insertActivity(TextField insertionField) {
        if (!(insertionField.getText().equals("") || insertionField.getText().equals("Activity added"))) {
            activityInsertControl.addActivity(insertionField.getText());
            insertionField.setPromptText("Activity added");
            insertionField.setText("");
            insertionLayout.requestFocus();
        }
    }

    @Override
    public VBox getLayout() {
        return insertionLayout;
    }
}