package productivetime.ui.settingsview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import productivetime.ui.ListLayout;

import java.time.ZoneId;
import java.util.Collections;

public class SettingsListLayout extends ListLayout {

    public SettingsListLayout() {

        super();

        ObservableList<String> timezones = FXCollections.observableArrayList(ZoneId.getAvailableZoneIds());
        Collections.sort(timezones);

        addBox(new SettingDropdownBox("Choose timezone", "timezone", timezones));

    }
}
