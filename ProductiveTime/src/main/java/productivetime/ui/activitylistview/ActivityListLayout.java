package productivetime.ui.activitylistview;

import javafx.scene.control.ScrollPane;
import productivetime.domain.Activity;
import productivetime.domain.ActivityInsertService;
import productivetime.domain.ActivityListService;
import productivetime.ui.ListLayout;
import productivetime.ui.UIElement;

import java.util.List;

/**
 * Constructs the activity list layout to view all tracked activities.
 */
public class ActivityListLayout extends ListLayout implements UIElement<ScrollPane> {

    public ActivityListLayout(ActivityListService activityListService, ActivityInsertService activityInsertService) {

        super();

        addBox(new ActivityBox("Type (Click to rename)", "Start", "Duration"));

        List<Activity> activities = activityListService.getActivitiesReversed();

        for (Activity activity : activities) {
            addBox(new ActivityBox(activity, activityInsertService));
        }

    }

}
