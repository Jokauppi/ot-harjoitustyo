package productivetime.ui;

/**
 * Basic interface for all subcomponents of the ProductiveTime application.
 * @param <E> type of the parent element of the UI component. Usually a Pane.
 * @see javafx.scene.layout.Pane
 */
public interface UIElement<E> {
    E getLayout();
}
