package productivetime.ui.activitylistview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import productivetime.domain.Activity;
import productivetime.domain.ActivityListControl;
import productivetime.ui.UIElement;

import java.util.List;


public class ActivityListLayout implements UIElement<ScrollPane> {

    private ScrollPane scrollPane;

    public ActivityListLayout(ActivityListControl activityListControl) {

        VBox list = new VBox();
        list.setSpacing(10);
        list.setPadding(new Insets(10));
        list.setAlignment(Pos.CENTER);

        list.getChildren().add(new ListBox("Type", "Start", "Duration").getLayout());

        List<Activity> activities = activityListControl.getActivitiesReversed();

        for (Activity activity : activities) {
            list.getChildren().add(new ListBox(activity).getLayout());
        }

        scrollPane = new ScrollPane();
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.setPannable(true);
        scrollPane.setContent(list);

    }

    @Override
    public ScrollPane getLayout() {
        return scrollPane;
    }

}
