package com.bd.repairs;

import com.bd.repairs.Controller.PostgreSQLController;
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/login.fxml"));
        primaryStage.setTitle("Repair System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        connection = PostgreSQLController.connect();
        //Adding first user - admin.
//        PasswordAuthentication passwordAuthentication=new PasswordAuthentication();
//        String password=passwordAuthentication.hash("admin".toCharArray());
//        Personel personel=new Personel(1,"marcin","marcin2","ADMIN","admin1",password);
//        personel.insertPersonel();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
