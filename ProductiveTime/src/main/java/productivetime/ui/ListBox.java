package productivetime.ui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ListBox implements UIElement<GridPane> {

    protected GridPane box;

    public ListBox() {
        box = new GridPane();
        box.setPadding(new Insets(10));
        box.setMaxWidth(700);
        box.setBackground(new Background(new BackgroundFill(Color.rgb(225, 225, 225), new CornerRadii(30), null)));
    }

    public void setBox(Node l1) {

        box.getChildren().clear();
        box.getColumnConstraints().clear();

        GridPane.setHalignment(l1, HPos.CENTER);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(100);
        box.getColumnConstraints().add(cc);

        box.add(l1, 0, 0);

    }

    public void setBox(Node l1, Node l2) {

        box.getChildren().clear();
        box.getColumnConstraints().clear();

        GridPane.setHalignment(l1, HPos.LEFT);
        GridPane.setHalignment(l2, HPos.RIGHT);

        for (int i = 0; i < 2; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(50);
            box.getColumnConstraints().add(cc);
        }

        box.add(l1, 0, 0);
        box.add(l2, 1, 0);

    }

    public void setBox(Node l1, Node l2, Node l3) {

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
