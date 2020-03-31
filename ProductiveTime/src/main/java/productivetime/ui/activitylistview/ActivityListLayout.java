package productivetime.ui.activitylistview;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import productivetime.domain.Activity;
import productivetime.domain.ActivityControl;
import productivetime.ui.UIElement;

public class ActivityListLayout implements UIElement<TableView<Activity>> {

    private TableView<Activity> activityTable;

    public ActivityListLayout(ActivityControl activityControl) {

        activityTable = new TableView<>();
        activityTable.setMaxWidth(500);

        //create type column
        TableColumn<Activity, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setMinWidth(150);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        //create type column
        TableColumn<Activity, String> startColumn = new TableColumn<>("Start time (s since 1/1/1970)");
        startColumn.setMinWidth(249);
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));

        //create type column
        TableColumn<Activity, String> durationColumn = new TableColumn<>("Duration (s)");
        durationColumn.setMinWidth(100);
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        activityTable.setItems(activityControl.getActivities());
        activityTable.getColumns().addAll(typeColumn, startColumn, durationColumn);
    }

    @Override
    public TableView getLayout() {
        return activityTable;
    }
}
