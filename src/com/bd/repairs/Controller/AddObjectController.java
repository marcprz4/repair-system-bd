package com.bd.repairs.Controller;

import com.bd.repairs.Model.Client;
import com.bd.repairs.Model.Object;
import com.bd.repairs.Model.ObjectType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddObjectController implements Initializable {
    public JFXTextField brand;
    public JFXTextField model;
    public JFXButton applyButton;
    public ChoiceBox<String> typeList;
    public JFXButton newTypeButton;
    public Label owner;
    private WindowLoader windowLoader;
    private int clId;
private Client client;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        windowLoader = new WindowLoader();
        client = ManagerController.client;
            owner.setText("Owner:\r" + client.getName());
        refresh();
    }

    public void apply(ActionEvent actionEvent) {
        if (!brand.getText().isEmpty() && !model.getText().isEmpty() && !typeList.getSelectionModel().isEmpty()) {
            Object object = new Object(0, brand.getText() + " " + model.getText(), client.getId_client(), typeList.getSelectionModel().getSelectedItem());
            object.insert();
        } else {
            //error
            return;
        }
        Stage stage = (Stage) applyButton.getScene().getWindow();
        stage.close();
    }

    public void addType(ActionEvent actionEvent) throws IOException {
        windowLoader.load(new Stage(), "Application", "addObjectType");
    }

    private void refresh() {
        typeList.getItems().clear();
        for (ObjectType objectType : ObjectType.findAll()) {
            typeList.getItems().add(objectType.getShortcut());
        }
    }

    public void ref(ActionEvent actionEvent) {
        refresh();
    }
}
