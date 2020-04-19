package productivetime.ui.activitylistview;

import javafx.scene.control.ScrollPane;
import productivetime.domain.Activity;
import productivetime.domain.ActivityInsertControl;
import productivetime.domain.ActivityListControl;
import productivetime.ui.ListLayout;
import productivetime.ui.UIElement;

import java.util.List;


public class ActivityListLayout extends ListLayout implements UIElement<ScrollPane> {

    public ActivityListLayout(ActivityListControl activityListControl, ActivityInsertControl activityInsertControl) {

        super();

        addBox(new ActivityBox("Type", "Start", "Duration"));

        List<Activity> activities = activityListControl.getActivitiesReversed();

        for (Activity activity : activities) {
            addBox(new ActivityBox(activity, activityInsertControl));
        }

    }

}
