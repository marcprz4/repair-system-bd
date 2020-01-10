package com.bd.repairs.Controller;

import com.bd.repairs.Model.PasswordAuthentication;
import com.bd.repairs.Model.Personel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public Label label;
    public JFXTextField login;
    public JFXPasswordField password;
    public JFXButton button;

    @FXML
    private void login(ActionEvent event) {
        try {
            Personel person = Personel.findByUsername(login.getText()).get();
            if (PasswordAuthentication.authenticate(password.getText().toCharArray(), person.getPassword())) {
                if (person.isActive()) {
                    WindowLoader windowLoader = new WindowLoader();
                    try {

                        switch (person.getRole()) {
                            case "ADMIN": {
                                windowLoader.load(new Stage(), "Application", "admin");
                                break;
                            }
                            case "MANAGER": {
                                ManagerController.user = person;
                                windowLoader.load(new Stage(), "Application", "manager");
                                break;
                            }
                            case "WORKER": {
                                WorkerController.user=person;
                                windowLoader.load(new Stage(), "Application", "worker");
                                break;
                            }
                        }
                    } catch (IOException e) {
                        label.setText("can't open window.");
                        e.printStackTrace();
                    }
                } else {
                    label.setText("user not active.");
                }
            } else {
                label.setText("wrong login data.");
            }
        } catch (NullPointerException ex) {
            label.setText("read data error.");
        }
    }

    public void enter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            login(new ActionEvent());
        }
    }
}
