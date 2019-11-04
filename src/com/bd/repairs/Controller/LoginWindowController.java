package com.bd.repairs.Controller;

import com.bd.repairs.Main;
import com.bd.repairs.Model.PasswordAuthentication;
import com.bd.repairs.Model.Personel;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Marcin Przybylski, Bartosz Prusak
 * @version 1.0
 */
public class LoginWindowController {
    @FXML
    Label errorLabel;
    @FXML
    JFXTextField username;
    @FXML
    JFXPasswordField password;
    @FXML
    JFXCheckBox rememberme;

    @FXML
    private void login(ActionEvent event) {
        try {
            Personel person = Personel.findPerson(username.getText().toString()).get();
            PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
            if (passwordAuthentication.authenticate(password.getText().toCharArray(), person.getPassword())) {
                Main.loggedPerson=person;
                Parent root;
                try{
                    root= FXMLLoader.load(getClass().getResource("../View/main_window.fxml"));
                    Stage stage=new Stage();
                    stage.setTitle("Repair System");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                errorLabel.setText("Wrong login data.");
            }
        } catch (NullPointerException ex) {
            errorLabel.setText(ex.getMessage());
        }
    }
}
