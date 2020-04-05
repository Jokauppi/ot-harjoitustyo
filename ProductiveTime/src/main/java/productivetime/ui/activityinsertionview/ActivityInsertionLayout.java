package productivetime.ui.activityinsertionview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import productivetime.domain.ActivityControl;
import productivetime.ui.UIElement;

import java.util.concurrent.TimeUnit;

public class ActivityInsertionLayout implements UIElement<VBox> {

    private VBox insertionLayout;
    private ActivityControl activityControl;

    public ActivityInsertionLayout(ActivityControl activityControl) {

        this.activityControl = activityControl;

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

        return activityField;
    }

    private Button createActivityInsertButton(TextField relatedField) {
        Button activityInsertionButton = new Button("Add");

        activityInsertionButton.setOnAction((actionEvent -> {
            if (!(relatedField.getText().equals("") || relatedField.getText().equals("Activity added"))) {
                activityControl.addActivity(relatedField.getText());
                relatedField.setText("Activity added");
            }
        }));

        return activityInsertionButton;
    }

    @Override
    public VBox getLayout() {
        return insertionLayout;
    }
}