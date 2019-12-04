package com.bd.repairs.Controller;

import com.bd.repairs.Main;
import com.bd.repairs.Model.PasswordAuthentication;
import com.bd.repairs.Model.Personel;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Marcin Przybylski, Bartosz Prusak
 * @version 1.0
 */
public class LoginWindowController {
    public static Personel loggedPerson;
    @FXML
    private Label errorLabel;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    @FXML
    private void login(ActionEvent event) {
        try {
            Personel person = Personel.findByUsername(username.getText()).get();
            if (PasswordAuthentication.authenticate(password.getText().toCharArray(), person.getPassword())) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("../View/main_window.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Repair System");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    errorLabel.setText(e.getMessage());
                    e.printStackTrace();
                }
                loggedPerson = person;
            } else {
                errorLabel.setText("Wrong login or password.");
            }
        } catch (NullPointerException ex) {
            errorLabel.setText("Database error.");
        }
    }
}
