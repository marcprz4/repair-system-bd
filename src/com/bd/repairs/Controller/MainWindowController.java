package com.bd.repairs.Controller;


import com.bd.repairs.Model.Personel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    Label helloLabel;
    @FXML
    JFXComboBox<String> entitiesComboBox;
    @FXML
    JFXTextField searchField;
    @FXML
    JFXButton searchButton;
    @FXML
    JFXButton editButton;
    @FXML
    JFXButton addButton;
    @FXML
    JFXButton removeButton;
    @FXML
    Label resultLabel;

    @FXML
    private void search(ActionEvent event) {
        switch(entitiesComboBox.getValue()){
            case "Personel":
            {
                Personel.findPersonByName(searchField.getText()).ifPresent(personel -> resultLabel.setText("name: "+personel.getFirst_name()+"\nlast name: "+personel.getLast_name()+"\nrole: "+personel.getRole()+"\nusername: "+personel.getUsername()+"\nid: "+personel.getId_personel()));

            }
        }
    }

    @FXML
    private void edit(ActionEvent event) {

    }

    @FXML
    private void add(ActionEvent event) {

    }

    @FXML
    private void remove(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        helloLabel.setText("hello "+ Main.loggedPerson.getFirst_name()+" "+Main.loggedPerson.getLast_name()+" you have "+Main.loggedPerson.getRole()+" rights.");
        entitiesComboBox.setItems(FXCollections.observableArrayList("Request","Car","Client","Activity","Personel","Activity-dictionary","Car-type"));
    }
}
