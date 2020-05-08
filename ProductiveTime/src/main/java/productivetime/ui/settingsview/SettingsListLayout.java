package productivetime.ui.settingsview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import productivetime.domain.ActivityInsertService;
import productivetime.ui.ListLayout;
import java.time.ZoneId;
import java.util.Collections;

public class SettingsListLayout extends ListLayout {

    public SettingsListLayout(ActivityInsertService activityInsertService) {

        ObservableList<String> timezones = FXCollections.observableArrayList(ZoneId.getAvailableZoneIds());
        Collections.sort(timezones);

        addBox(new SettingDropdownBox("Choose timezone", "timezone", timezones));

        SettingButtonBox clearDBBox = new SettingButtonBox("Clear all activities") {
            @Override
            void setButtonProperties() {
                button.setText("Clear All");
                button.setId("red_button");

                button.setOnAction(actionEvent -> {
                    if (button.getText().equals("Sure?")) {
                        if (activityInsertService.clearActivities()) {
                            button.setText("Activities cleared");
                        } else {
                            button.setText("Clear Unsuccessful");
                        }

                    } else {
                        button.setText("Sure?");
                    }
                });
            }
        };

        addBox(clearDBBox);

        this.getLayout().getStylesheets().add(getClass().getResource("/stylesheets/settingsView.css").toExternalForm());
    }
}
