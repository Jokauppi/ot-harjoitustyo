package productivetime.ui.settingsview;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import productivetime.domain.Settings;
import productivetime.ui.ListBox;

/**
 * Constructs a dropdown box to choose the value of a given application setting.
 */
public class SettingDropdownBox extends ListBox {

    public SettingDropdownBox(String desc, String setting, ObservableList<String> values) {

        Label description = new Label(desc);

        ComboBox<String> chooseBox = new ComboBox<>(values);

        String currentSetting = Settings.getSetting(setting);

        chooseBox.setEditable(false);
        chooseBox.setStyle("-fx-background-color: #eeeeee");

        if (currentSetting != null) {

            chooseBox.setValue(currentSetting);

            chooseBox.setOnAction(actionEvent -> Settings.setSetting(setting, chooseBox.getValue()));

        } else {

            chooseBox.setValue("Error");

        }

        setBox(description, chooseBox);

    }
}
