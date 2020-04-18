package productivetime.ui.activitylistview;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    public ActivityBox(Activity activity, ActivityInsertControl activityInsertControl) {

        this.activityInsertControl = activityInsertControl;
        this.activity = activity;

        Label start = new Label(activity.getStartFormatted());
        Label duration = new Label(activity.getDurationFormatted());
        Label type = getTypeLabel();

        setBox(type, start, duration);

    }

    public ActivityBox(String f1, String f2, String f3) {

        setBox(new Label(f1), new Label(f2), new Label(f3));

    }

    private Label getTypeLabel() {

        System.out.println("setting label");

        Label typeLabel = new Label(activity.getType());

        typeLabel.setOnMouseClicked(mouseEvent -> {
            Label start = new Label(activity.getStartFormatted());
            Label duration = new Label(activity.getDurationFormatted());
            setBox(getTypeField(), start, duration);
        });

        return typeLabel;
    }

    private TextField getTypeField() {

        System.out.println("setting field");

        TextField renameField = new TextField(activity.getType());

        renameField.setOnAction(actionEvent -> {
            try {
                activity = activityInsertControl.retypeActivity(activity, renameField.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Label start = new Label(activity.getStartFormatted());
            Label duration = new Label(activity.getDurationFormatted());
            setBox(getTypeLabel(), start, duration);
        });

        return renameField;
    }

    private void setBox(Node l1, Node l2, Node l3) {

        box = new GridPane();
        box.setPadding(new Insets(10));
        box.setMaxWidth(700);
        box.setBackground(new Background(new BackgroundFill(Color.rgb(220, 220, 220), new CornerRadii(30), null)));

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
