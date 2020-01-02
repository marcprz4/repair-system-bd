package com.bd.repairs.Controller;

import com.bd.repairs.Model.ActDic;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddActivityTypeController implements Initializable {
    public JFXTextField shortcut;
    public JFXTextField full;
    public JFXButton apply;
    public void applyChanges(ActionEvent actionEvent) {
        ActDic actDic=new ActDic(shortcut.getText(),full.getText());
        actDic.insert();
        Stage stage = (Stage) apply.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
