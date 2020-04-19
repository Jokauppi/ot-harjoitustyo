package productivetime.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class ListLayout implements UIElement<ScrollPane> {

    private ScrollPane scrollPane;
    protected VBox list;

    public ListLayout() {
        list = new VBox();
        list.setSpacing(10);
        list.setPadding(new Insets(10));
        list.setAlignment(Pos.CENTER);
        scrollPane = new ScrollPane();
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.setPannable(true);
        scrollPane.setContent(list);
    }

    public void addBox(ListBox listBox) {
        list.getChildren().add(listBox.getLayout());
    }



    @Override
    public ScrollPane getLayout() {
        return scrollPane;
    }
}
