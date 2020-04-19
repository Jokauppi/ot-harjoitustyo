package productivetime.ui.activitylistview;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import productivetime.domain.Activity;
import productivetime.domain.ActivityInsertControl;
import productivetime.ui.UIElement;

import java.sql.SQLException;

public class ActivityBox implements UIElement<GridPane> {

    private Activity activity;
    private GridPane box;
    private ActivityInsertControl activityInsertControl;

    public ActivityBox() {
        box = new GridPane();
        box.setPadding(new Insets(10));
        box.setMaxWidth(700);
    }

    public ActivityBox(Activity activity, ActivityInsertControl activityInsertControl) {

        this();

        this.activityInsertControl = activityInsertControl;
        this.activity = activity;

        setTypeLabel();

    }

    public ActivityBox(String f1, String f2, String f3) {

        this();

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

    private void setBox(Node l1, Node l2, Node l3) {

        box.getChildren().clear();
        box.getColumnConstraints().clear();

        GridPane.setHalignment(l1, HPos.LEFT);
        GridPane.setHalignment(l2, HPos.CENTER);
        GridPane.setHalignment(l3, HPos.RIGHT);

        for (int i = 0; i < 3; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(33);
            box.getColumnConstraints().add(cc);
        }

        box.add(l1, 0, 0);
        box.add(l2, 1, 0);
        box.add(l3, 2, 0);

    }

    @Override
    public GridPane getLayout() {
        return box;
    }
}
