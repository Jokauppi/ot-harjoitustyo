package productivetime.ui.settingsview;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import productivetime.domain.Settings;
import productivetime.ui.ListBox;

public class SettingDropdownBox extends ListBox {

    public SettingDropdownBox(String desc, String setting, ObservableList<String> values) {

        super();

        Label description = new Label(desc);

        ComboBox<String> chooseBox = new ComboBox<>(values);

        chooseBox.setValue(Settings.getSetting(setting));
        chooseBox.setEditable(false);
        chooseBox.setBackground(new Background(new BackgroundFill(Color.rgb(225, 225, 225), new CornerRadii(30), null)));

        chooseBox.setOnAction(actionEvent -> Settings.setSetting(setting, chooseBox.getValue()));

        setBox(description, chooseBox);

    }
}
