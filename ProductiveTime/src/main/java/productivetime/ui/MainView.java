package productivetime.ui;

import javafx.application.Application;
import javafx.css.Stylesheet;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import productivetime.dao.SQLActivityDao;
import productivetime.domain.ActivityInsertService;
import productivetime.domain.ActivityListService;
import productivetime.domain.Settings;

import java.sql.SQLException;

public class MainView extends Application {

    private ActivityInsertService activityInsertService;
    private ActivityListService activityListService;
    private boolean dBCreationSuccessful = false;

    @Override
    public void init() {

        try {
            SQLActivityDao activityDB = new SQLActivityDao(Settings.getSetting("appdata") + Settings.getSetting("dbname"));
            this.activityInsertService = new ActivityInsertService(activityDB);
            this.activityListService = new ActivityListService(activityDB);
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
            ViewSelector viewSelector = new ViewSelector(mainLayout, activityInsertService, activityListService);
            mainLayout.setBottom(viewSelector.getLayout());
        } else {
            mainLayout.setCenter(new Label("DATABASE\nCONNECTION\nUNSUCCESFUL"));
        }

        Scene mainView = new Scene(mainLayout);
        mainView.getStylesheets().add(getClass().getResource("/fonts.css").toExternalForm());

        primaryStage.setScene(mainView);

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}