package productivetime.ui.activitylistview;

import javafx.scene.control.TableView;
import productivetime.domain.Activity;
import productivetime.ui.UIElement;

public class ActivityListLayout implements UIElement<TableView<Activity>> {

    private TableView<Activity> activityList;

    public ActivityListLayout() {
        activityList = new TableView<>();
    }

    @Override
    public TableView getLayout() {
        return activityList;
    }
}
