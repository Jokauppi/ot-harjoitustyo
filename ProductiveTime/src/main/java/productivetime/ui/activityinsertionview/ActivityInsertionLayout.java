package productivetime.ui.activityinsertionview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import productivetime.ui.UIElement;

public class ActivityInsertionLayout implements UIElement<VBox> {

    private VBox insertionLayout;

    public ActivityInsertionLayout() {
        insertionLayout = new VBox();
        insertionLayout.setSpacing(20);
        insertionLayout.setAlignment(Pos.CENTER);
        insertionLayout.setPadding(new Insets(20,20,20,20));

        TextField activityField = new TextField("");
        activityField.setPrefWidth(150);
        activityField.setMaxWidth(200);

        Label logo = new Label("ProductiveTime");
        logo.setPadding(new Insets(0,0,20,0));
        logo.setFont(new Font(20));

        insertionLayout.getChildren().add(logo);
        insertionLayout.getChildren().add(activityField);
        insertionLayout.getChildren().add(new Button("Add"));
    }

    @Override
    public VBox getLayout() {
        return insertionLayout;
    }
}