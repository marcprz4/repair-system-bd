package com.bd.repairs.Controller;

import com.bd.repairs.Model.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AddObjectController implements Initializable {
    public JFXTextField brand;
    public JFXTextField model;
    public JFXButton applyButton;
    public ChoiceBox<String> typeList;
    public JFXButton newTypeButton;
    public Label owner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Client client = Client.findById(ManagerController.clientId).get();
        if (!client.getFname().isEmpty() && !client.getLname().isEmpty())
            owner.setText("Owner:\r" + client.getFname() + " " + client.getLname());
        else
            owner.setText("Owner:\r" + client.getName());
    }

    public void apply(ActionEvent actionEvent) {
    }
}
