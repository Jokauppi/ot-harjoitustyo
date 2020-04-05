package productivetime.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Productivetime");

        BorderPane mainLayout = new BorderPane();
        mainLayout.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), null, null)));
        mainLayout.setPrefSize(500, 400);
        mainLayout.setMaxSize(800, 800);

        ViewSelector viewSelector = new ViewSelector(mainLayout);
        mainLayout.setBottom(viewSelector.getLayout());

        Scene mainView = new Scene(mainLayout);

        primaryStage.setScene(mainView);

        primaryStage.show();

    }
}