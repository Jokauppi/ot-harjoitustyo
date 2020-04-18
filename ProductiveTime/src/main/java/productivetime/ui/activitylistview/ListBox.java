package productivetime.ui.activitylistview;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import productivetime.domain.Activity;
import productivetime.ui.UIElement;

public class ListBox implements UIElement<GridPane> {

    private Activity activity;
    private GridPane box;

    public ListBox(Color color) {
        box = new GridPane();
        box.setPadding(new Insets(10));
        box.setMaxWidth(700);
        box.setBackground(new Background(new BackgroundFill(color, new CornerRadii(30), null)));
    }

    public ListBox(Activity activity) {

        this(Color.rgb(225, 225, 225));

        this.activity = activity;

        Label type = new Label(activity.getType());
        Label start = new Label(activity.getStartFormatted());
        Label duration = new Label(activity.getDurationFormatted());

        setBox(type, start, duration);

    }

    public ListBox(String f1, String f2, String f3) {

        this(Color.rgb(200, 200, 200));

        setBox(new Label(f1), new Label(f2), new Label(f3));

    }

    private void setBox(Node l1, Node l2, Node l3) {

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
