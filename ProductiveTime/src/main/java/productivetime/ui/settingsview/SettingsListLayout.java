package productivetime.ui.settingsview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import productivetime.domain.Activity;
import productivetime.domain.ActivityInsertService;
import productivetime.domain.ActivityListService;
import productivetime.domain.CSVExport;
import productivetime.ui.ListLayout;

import java.io.File;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Constructs a list view to display different application settings.
 */
public class SettingsListLayout extends ListLayout {

    private ActivityInsertService activityInsertService;
    private ActivityListService activityListService;

    public SettingsListLayout(ActivityInsertService activityInsertService, ActivityListService activityListService) {

        this.activityInsertService = activityInsertService;
        this.activityListService = activityListService;

        ObservableList<String> timezones = FXCollections.observableArrayList(ZoneId.getAvailableZoneIds());
        Collections.sort(timezones);

        addBox(new SettingDropdownBox("Choose timezone", "timezone", timezones));

        addBox(getExportButton());

        addBox(getTrackingPauseButton());

        addBox(getActivityClearBox());

        this.getLayout().getStylesheets().add(getClass().getResource("/stylesheets/settingsView.css").toExternalForm());
    }

    private SettingButtonBox getExportButton() {

        return new SettingButtonBox("Export activities as CSV") {
            @Override
            void setButtonProperties() {
                button.setText("Export");
                button.setId("grey_button");

                button.setOnAction(actionEvent -> exportActivities(button));
            }
        };

    }

    private void exportActivities(Button button) {
        List<Activity> activities = activityListService.getActivities();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("activities.csv");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            if (CSVExport.writeActivitiesToCSV(activities, file)) {
                button.setText("Saved");
            } else {
                button.setText("Error");
            }
        }
    }

    private SettingButtonBox getTrackingPauseButton() {

        return new SettingButtonBox("Pause activity tracking") {
            @Override
            void setButtonProperties() {
                button.setId("grey_button");
                if (activityListService.isTrackingOn()) {
                    button.setText("Pause");

                    button.setOnAction(actionEvent -> {
                        if (activityInsertService.pauseTracking()) {
                            button.setText("Paused (Add a new activity to resume)");
                        } else {
                            button.setText("Error");
                        }
                    });

                } else {
                    button.setText("Paused (Add a new activity to resume)");
                }
            }
        };
    }

    private SettingButtonBox getActivityClearBox() {

        return new SettingButtonBox("Clear all activities") {
            @Override
            void setButtonProperties() {
                button.setText("Clear All");
                button.setId("red_button");

                button.setOnAction(actionEvent -> {

                    if (clearConfirmAlert()) {
                        if (activityInsertService.clearActivities()) {
                            button.setText("Activities cleared");
                        } else {
                            button.setText("Clear Unsuccessful");
                        }
                    }

                });
            }
        };
    }

    private boolean clearConfirmAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clearing all activities");
        alert.setHeaderText("Are you sure you want to delete all activities?");
        alert.setContentText("Action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
