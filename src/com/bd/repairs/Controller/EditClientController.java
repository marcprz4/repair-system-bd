package com.bd.repairs.Controller;

import com.bd.repairs.Model.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditClientController implements Initializable {
    public JFXTextField fname;
    public JFXTextField lname;
    public JFXTextField company;
    public JFXTextField telephone;
    public JFXButton applyButton;
    public Label warning;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
fname.setText(ManagerController.client.getFname());
lname.setText(ManagerController.client.getLname());
company.setText(ManagerController.client.getName());
telephone.setText(ManagerController.client.getTelephone());
    }

    public void apply(ActionEvent actionEvent) {
        if ((!fname.getText().isEmpty() && !lname.getText().isEmpty()) && (company.getText().isEmpty())) {
            Client client = new Client(ManagerController.client.getId_client(), fname.getText(), lname.getText(), fname.getText()+" "+lname.getText(), telephone.getText());
            client.update();
            Stage stage = (Stage) applyButton.getScene().getWindow();
            stage.close();
        } else if (((fname.getText().isEmpty() && lname.getText().isEmpty()) && (!company.getText().isEmpty()))) {
            Client client = new Client(ManagerController.client.getId_client(), fname.getText(), lname.getText(), company.getText(), telephone.getText());
            client.update();
            applyButton.getScene().getWindow().hide();
        } else {
            warning.setText("Insert full name or company name.");
        }
    }
}
