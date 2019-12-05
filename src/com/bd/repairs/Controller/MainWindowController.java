package com.bd.repairs.Controller;

import com.bd.repairs.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    private Label helloLabel;
    @FXML
    private JFXComboBox<String> entitiesComboBox;
    public static String type;
//    @FXML
//    private JFXTextField searchField;
//    @FXML
//    private JFXButton searchButton;
//    @FXML
//    private JFXButton editButton;
//    @FXML
//    private JFXButton addButton;
//    @FXML
//    private JFXButton removeButton;
//    @FXML
//    private Label resLabel1;
//    @FXML
//    private Label resLabel2;
//    @FXML
//    private Label resLabel3;
//    @FXML
//    private Label resLabel4;
//    @FXML
//    private Label resLabel5;
//    @FXML
//    private TextField resField1;
//    @FXML
//    private TextField resField2;
//    @FXML
//    private TextField resField3;
//    @FXML
//    private TextField resField4;
//    @FXML
//    private TextField resField5;
    @FXML
    private VBox resVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        System.out.println(LoginWindowController.loggedPerson.getFirst_name());
        helloLabel.setText("TEST");
        ArrayList<String> list=new ArrayList<>();
        list.add("Request");
        list.add("Car");
        list.add("Client");
        list.add("Activity");
        list.add("Personel");
        list.add("Activity-dictionary");
        list.add("Car-type");
        ObservableList<String> obList= FXCollections.observableList(list);
        entitiesComboBox.getItems().addAll(obList);
        resVBox.setVisible(false);
    }

    @FXML
    private void search(ActionEvent event) throws IOException {
        type=entitiesComboBox.getValue();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("../View/search_window.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Repair System");
        stage.setScene(new Scene(root));
        stage.show();
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
//        resField1.clear();
//        resField2.clear();
//        resField3.clear();
//        resField4.clear();
//        resField5.clear();
//        resLabel1.setText("");
//        resLabel2.setText("");
//        resLabel3.setText("");
//        resLabel4.setText("");
//        resLabel5.setText("");
//        searchField.clear();
    }
}
