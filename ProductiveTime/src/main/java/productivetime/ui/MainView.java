package productivetime.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import productivetime.dao.ActivityDao;
import productivetime.domain.ActivityInsertControl;
import productivetime.domain.ActivityListControl;

import java.sql.SQLException;

public class MainView extends Application {

    private ActivityInsertControl activityInsertControl;
    private ActivityListControl activityListControl;
    private boolean dBCreationSuccessful = false;

    @Override
    public void init() {

        try {
            ActivityDao activityDB = new ActivityDao("activity.db");
            this.activityInsertControl = new ActivityInsertControl(activityDB);
            this.activityListControl = new ActivityListControl(activityDB);
            dBCreationSuccessful = true;
        } catch (SQLException ignored) {
        }

    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Productivetime");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), null, null)));

        if (dBCreationSuccessful) {
            ViewSelector viewSelector = new ViewSelector(mainLayout, activityInsertControl, activityListControl);
            mainLayout.setBottom(viewSelector.getLayout());
        } else {
            mainLayout.setCenter(new Label("DATABASE\nCONNECTION\nUNSUCCESFUL"));
        }

        Scene mainView = new Scene(mainLayout);

        primaryStage.setScene(mainView);

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}