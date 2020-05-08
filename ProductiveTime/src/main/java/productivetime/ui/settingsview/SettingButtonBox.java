package productivetime.ui.settingsview;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import productivetime.ui.ListBox;

public abstract class SettingButtonBox extends ListBox {

    protected Button button;

    public SettingButtonBox(String description) {

        Label desc = new Label(description);

        button = new Button();

        setButtonProperties();

        setBox(desc, button);

    }

    abstract void setButtonProperties();
}
