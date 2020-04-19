package productivetime.ui.activitylistview;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import productivetime.domain.Activity;
import productivetime.domain.ActivityInsertControl;
import productivetime.ui.ListBox;
import productivetime.ui.UIElement;

import java.sql.SQLException;

public class ActivityBox extends ListBox implements UIElement<GridPane> {

    private Activity activity;
    private ActivityInsertControl activityInsertControl;

    public ActivityBox(Activity activity, ActivityInsertControl activityInsertControl) {

        super();

        this.activityInsertControl = activityInsertControl;
        this.activity = activity;

        setTypeLabel();

    }

    public ActivityBox(String f1, String f2, String f3) {

        super();

        box.setBackground(new Background(new BackgroundFill(Color.rgb(210, 210, 210), new CornerRadii(30), null)));

        setBox(new Label(f1), new Label(f2), new Label(f3));

    }

    private void setTypeLabel() {

        Label typeLabel = new Label(activity.getType());

        typeLabel.setOnMouseClicked(mouseEvent -> {
            setTypeField();
        });

        box.setBackground(new Background(new BackgroundFill(Color.rgb(225, 225, 225), new CornerRadii(30), null)));

        Label start = new Label(activity.getStartFormatted());
        Label duration = new Label(activity.getDurationFormatted());

        setBox(typeLabel, start, duration);
    }

    private void setTypeField() {

        TextField renameField = new TextField(activity.getType());
        renameField.setBackground(new Background(new BackgroundFill(Color.rgb(225, 225, 225), new CornerRadii(30), null)));

        renameField.setOnAction(actionEvent -> {
            try {
                activity = activityInsertControl.retypeActivity(activity, renameField.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            setTypeLabel();
        });

        Label start = new Label(activity.getStartFormatted());
        start.setOnMouseClicked(mouseEvent -> setTypeLabel());
        Label duration = new Label(activity.getDurationFormatted());
        start.setOnMouseClicked(mouseEvent -> setTypeLabel());

        setBox(renameField, start, duration);

        renameField.requestFocus();

        box.setBackground(new Background(new BackgroundFill(Color.rgb(210, 210, 210), new CornerRadii(30), null)));

    }

}
