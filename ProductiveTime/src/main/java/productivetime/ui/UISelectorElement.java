package productivetime.ui;

import javafx.scene.Node;

public interface UISelectorElement<E> {

    E getLayout();
    void setView(Node node);
}
