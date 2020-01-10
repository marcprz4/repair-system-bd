package com.bd.repairs;

import com.bd.repairs.Controller.PostgreSQLController;
import com.bd.repairs.Controller.WindowLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    public void start(Stage stage) throws Exception {
        connection = PostgreSQLController.connect();
        Parent root = FXMLLoader.load(getClass().getResource("View/login.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
//        WindowLoader windowLoader = new WindowLoader();
//        windowLoader.load(,primaryStage, "Application", "login");
    }
}
