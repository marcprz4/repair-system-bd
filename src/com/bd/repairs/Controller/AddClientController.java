package com.bd.repairs.Controller;

import com.bd.repairs.Model.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AddClientController {
    public JFXTextField fname;
    public JFXTextField lname;
    public JFXTextField company;
    public JFXTextField telephone;
    public JFXButton applyButton;
    public Label warning;

    public void apply(ActionEvent actionEvent) {
        if ((!fname.getText().isEmpty() && !lname.getText().isEmpty()) && (company.getText().isEmpty())) {
            Client client = new Client(0, fname.getText(), lname.getText(), company.getText(), telephone.getText());
            client.insert();
            Stage stage = (Stage) applyButton.getScene().getWindow();
            stage.close();
        } else if (((fname.getText().isEmpty() && lname.getText().isEmpty()) && (!company.getText().isEmpty()))) {
            Client client = new Client(0, fname.getText(), lname.getText(), company.getText(), telephone.getText());
            client.insert();
            Stage stage = (Stage) applyButton.getScene().getWindow();
            stage.close();
        } else {
            warning.setText("Insert full name or company name.");
        }

    }
}
