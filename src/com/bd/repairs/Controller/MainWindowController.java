package com.bd.repairs.Controller;


import com.bd.repairs.Main;
import com.bd.repairs.Model.Personel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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
    Label resLabel1;
    @FXML
    Label resLabel2;
    @FXML
    Label resLabel3;
    @FXML
    Label resLabel4;
    @FXML
    Label resLabel5;
    @FXML
    TextField resField1;
    @FXML
    TextField resField2;
    @FXML
    TextField resField3;
    @FXML
    TextField resField4;
    @FXML
    TextField resField5;
    @FXML
    VBox resVBox;

    @FXML
    private void search(ActionEvent event) {
        switch (entitiesComboBox.getValue()) {
            case "Personel": {
                try {
                    Personel.findByName(searchField.getText()).ifPresent(personel ->{
                        resVBox.setVisible(true);
                        resLabel1.setText("Name: ");
                        resField1.setText(personel.getFirst_name()+" "+personel.getLast_name());
                        resLabel2.setText("Role: ");
                        resField2.setText(personel.getRole());
                        resLabel3.setText("Username: ");
                        resField3.setText(personel.getUsername());
                        resLabel4.setText("Id: ");
                        resField4.setText(Integer.toString(personel.getId_personel()));
                        resField4.setEditable(false);
                        resLabel5.setVisible(false);
                        resField5.setVisible(false);
                    });
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
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

    @FXML
    private void clear(ActionEvent event){
        resField1.clear();
        resField2.clear();
        resField3.clear();
        resField4.clear();
        resField5.clear();
        resLabel1.setText("");
        resLabel2.setText("");
        resLabel3.setText("");
        resLabel4.setText("");
        resLabel5.setText("");
        searchField.clear();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        helloLabel.setText("hello "+ Main.loggedPerson.getFirst_name()+" "+ Main.loggedPerson.getLast_name()+" you have "+Main.loggedPerson.getRole()+" rights.");
//        helloLabel.setText("HELLO USERNAME YOU ARE ADMIN");
        entitiesComboBox.setItems(FXCollections.observableArrayList("Request", "Car", "Client", "Activity", "Personel", "Activity-dictionary", "Car-type"));
        resVBox.setVisible(false);
    }
}
