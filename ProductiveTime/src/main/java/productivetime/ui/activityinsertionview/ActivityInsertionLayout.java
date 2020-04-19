package productivetime.ui.activityinsertionview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import productivetime.domain.ActivityInsertControl;
import productivetime.ui.UIElement;

public class ActivityInsertionLayout implements UIElement<VBox> {

    private VBox insertionLayout;
    private ActivityInsertControl activityInsertControl;

    public ActivityInsertionLayout(ActivityInsertControl activityInsertControl) {

        this.activityInsertControl = activityInsertControl;

        insertionLayout = new VBox();
        insertionLayout.setSpacing(20);
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

        return logo;
    }

    private TextField createActivityField() {
        TextField activityField = new TextField("");

        activityField.setPrefWidth(150);
        activityField.setMaxWidth(200);

        activityField.setOnMouseClicked(mouseEvent -> {
            if (activityField.getText().equals("Activity added")) {
                activityField.setText("");
            }
        });

        activityField.setOnAction(actionEvent -> insertActivity(activityField));

        return activityField;
    }

    private Button createActivityInsertButton(TextField relatedField) {
        Button activityInsertionButton = new Button("Add");

        activityInsertionButton.setOnAction((actionEvent -> insertActivity(relatedField)));

        return activityInsertionButton;
    }

    private void insertActivity(TextField insertionField) {
        if (!(insertionField.getText().equals("") || insertionField.getText().equals("Activity added"))) {
            activityInsertControl.addActivity(insertionField.getText());
            insertionField.setText("Activity added");
        }
    }

    @Override
    public VBox getLayout() {
        return insertionLayout;
    }
}