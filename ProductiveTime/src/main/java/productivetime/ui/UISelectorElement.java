package productivetime.ui;

import javafx.scene.Node;

/**
 * Basic interface for all subcomponents of the ProductiveTime application that change a child node in some other node.
 * @param <E> type of the parent element of the UI component. Usually a Pane.
 * @see javafx.scene.layout.Pane
 */
public interface UISelectorElement<E> {

    E getLayout();
    void setView(Node node);
}
