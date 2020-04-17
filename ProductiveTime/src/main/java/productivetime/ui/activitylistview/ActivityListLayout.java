package productivetime.ui.activitylistview;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import productivetime.domain.Activity;
import productivetime.domain.ActivityListControl;
import productivetime.ui.UIElement;

import java.util.Arrays;

public class ActivityListLayout implements UIElement<TableView<Activity>> {

    private TableView<Activity> activityTable;

    public ActivityListLayout(ActivityListControl activityListControl) {

        activityTable = new TableView<>();

        //create table type column
        TableColumn<Activity, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        //create table start column
        TableColumn<Activity, String> startColumn = new TableColumn<>("Start time");
        startColumn.setCellValueFactory(p -> new ReadOnlyStringWrapper(p.getValue().getStartFormatted()));

        //create table duration column
        TableColumn<Activity, String> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(p -> new  ReadOnlyStringWrapper(p.getValue().getDurationFormatted()));

        activityTable.setItems(activityListControl.getActivitiesObservable());
        activityTable.getColumns().addAll(Arrays.asList(typeColumn, startColumn, durationColumn));
    }

    @Override
    public TableView getLayout() {
        return activityTable;
    }

}
