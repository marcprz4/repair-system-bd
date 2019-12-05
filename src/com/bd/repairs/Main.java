package com.bd.repairs;

import com.bd.repairs.Controller.PostgreSQLController;
import com.bd.repairs.Model.PasswordAuthentication;
import com.bd.repairs.Model.Personel;
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
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/login_window.fxml"));
        primaryStage.setTitle("Repair System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        connection = PostgreSQLController.connect();
//Creator.createClient("Janusz","Nosacz","brak","101010101");//1
//        Creator.createClient("Gabriela","Gabrielowa","brak","123123123");//2
//        Creator.createClient("Edward","Tester","brak","090909090");//3
//        Creator.createClient("brak","brak","Polskie zaklady programowania","101010123");//4
//        Creator.createObject("VW Golf 7",1,"VW");
//        Creator.createObject("Peugeot 508",2,"PSA");
//        Creator.createObject("VW Passat B8",1,"VW");
//        Creator.createObject("Audi A4 B9",3,"VW");
//        Creator.createObject("Opel Corsa 2016",4,"GM");
    }
}
