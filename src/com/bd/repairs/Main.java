package com.bd.repairs;

import com.bd.repairs.Controller.PostgreSQLController;
import com.bd.repairs.Controller.WindowLoader;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.Connection;

/**
 * @author Marcin Przybylski, Bartosz Prusak
 * @version 1.0
 */
public class Main extends Application {
    public static Connection connection;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        connection = PostgreSQLController.connect();
        WindowLoader windowLoader = new WindowLoader();
        windowLoader.load(primaryStage, "Application", "manager");
    }
}
