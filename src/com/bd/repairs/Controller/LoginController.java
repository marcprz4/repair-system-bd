package com.bd.repairs.Controller;

import com.bd.repairs.Model.PasswordAuthentication;
import com.bd.repairs.Model.Personel;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Marcin Przybylski, Bartosz Prusak
 * @version 1.0
 */
public class LoginController implements Initializable {
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
            Personel person = Personel.findId(username.getText().toString()).get();
            PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
            if (passwordAuthentication.authenticate(password.getText().toCharArray(), person.getPassword())) {
                errorLabel.setText("SUCCESS");
            } else {
                errorLabel.setText("WRONG PASSWORD");
            }
        } catch (NullPointerException ex) {
            errorLabel.setText(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
