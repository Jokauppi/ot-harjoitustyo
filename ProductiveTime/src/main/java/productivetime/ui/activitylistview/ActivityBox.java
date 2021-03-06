package productivetime.ui.activitylistview;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import productivetime.domain.Activity;
import productivetime.domain.ActivityInsertService;
import productivetime.ui.ListBox;
import productivetime.ui.UIElement;

/**
 * Constructs a box to display a single activity.
 */
public class ActivityBox extends ListBox implements UIElement<GridPane> {

    private Activity activity;
    private ActivityInsertService activityInsertService;

    public ActivityBox(Activity activity, ActivityInsertService activityInsertService) {

        super();

        this.activityInsertService = activityInsertService;
        this.activity = activity;

        setTypeLabel();

    }

    public ActivityBox(String f1, String f2, String f3) {

        box.setBackground(new Background(new BackgroundFill(Color.rgb(210, 210, 210), new CornerRadii(30), null)));

        setBox(new Label(f1), new Label(f2), new Label(f3));

    }

    private void setTypeLabel() {

        Label typeLabel = new Label(activity.getType());

        typeLabel.setOnMouseClicked(mouseEvent -> setTypeField());

        box.setBackground(new Background(new BackgroundFill(Color.rgb(225, 225, 225), new CornerRadii(30), null)));

        Label start = new Label(activity.getStartFormatted());
        Label duration = new Label(activity.getDurationFormatted());

        setBox(typeLabel, start, duration);
    }

    private void setTypeField() {

        TextField renameField = new TextField(activity.getType());
        renameField.setBackground(new Background(new BackgroundFill(Color.rgb(225, 225, 225), new CornerRadii(30), null)));

        renameField.setOnAction(actionEvent -> {
            if (renameField.getText().trim().length() > 0 && renameField.getText().length() < 26) {
                activity = activityInsertService.retypeActivity(activity, renameField.getText().trim().toLowerCase());
            }
            setTypeLabel();
        });

        renameField.focusedProperty().addListener((oValue, oldValue, newValue) -> {
            if (!newValue) {
                setTypeLabel();
            }
        });

        Label start = new Label(activity.getStartFormatted());
        Label duration = new Label(activity.getDurationFormatted());

        setBox(renameField, start, duration);

        renameField.requestFocus();

        box.setBackground(new Background(new BackgroundFill(Color.rgb(210, 210, 210), new CornerRadii(30), null)));

    }

}
