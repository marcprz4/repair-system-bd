package com.bd.repairs.Controller;

import com.bd.repairs.Model.PasswordAuthentication;
import com.bd.repairs.Model.Personel;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateUserController implements Initializable {
    public TextField fname;
    public TextField lname;
    public TextField username;
    public PasswordField password;
    public CheckBox active;
    public JFXButton applyButton;
    public ChoiceBox<String> role;

    public void apply() {
        String passwordE;
        PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
        passwordE = passwordAuthentication.hash(password.getText().toCharArray());

        Personel personel = new Personel(0, fname.getText(), lname.getText(), role.getSelectionModel().getSelectedItem(), username.getText(), passwordE, active.isSelected());
        personel.insert();
        Stage stage = (Stage) applyButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        role.getItems().add("ADMIN");
        role.getItems().add("MANAGER");
        role.getItems().add("WORKER");
    }
}
