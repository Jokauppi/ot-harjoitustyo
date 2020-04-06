package productivetime.ui.activitylistview;

import javafx.beans.property.ReadOnlyStringWrapper;
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

        //create table type column
        TableColumn<Activity, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setMinWidth(150);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        //create table start column
        TableColumn<Activity, String> startColumn = new TableColumn<>("Start time");
        startColumn.setMinWidth(249);
        startColumn.setCellValueFactory(p -> new ReadOnlyStringWrapper(p.getValue().getStartDate()));

        //create table duration column
        TableColumn<Activity, String> durationColumn = new TableColumn<>("Duration");
        durationColumn.setMinWidth(100);
        durationColumn.setCellValueFactory(p -> new  ReadOnlyStringWrapper(p.getValue().getDurationFormatted()));

        activityTable.setItems(activityControl.getActivitiesObservable());
        activityTable.getColumns().addAll(typeColumn, startColumn, durationColumn);
    }

    @Override
    public TableView getLayout() {
        return activityTable;
    }
}
