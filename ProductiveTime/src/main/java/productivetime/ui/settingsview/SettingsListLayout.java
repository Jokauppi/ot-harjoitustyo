package productivetime.ui.settingsview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import productivetime.ui.ListBox;
import productivetime.ui.ListLayout;

import java.time.ZoneId;
import java.util.Collections;

public class SettingsListLayout extends ListLayout {

    public SettingsListLayout() {

        super();

        ObservableList<String> timezones = FXCollections.observableArrayList(ZoneId.getAvailableZoneIds());
        Collections.sort(timezones);

        addBox(new SettingDropdownBox("Choose timezone", "timezone", timezones));
        ListBox notWorkingLabel = new ListBox();
        notWorkingLabel.setBox(new Label("Settings not working as of yet"));
        addBox(notWorkingLabel);
    }
}
