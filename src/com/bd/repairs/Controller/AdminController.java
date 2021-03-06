package com.bd.repairs.Controller;

import com.bd.repairs.Model.Personel;
import com.bd.repairs.Model.StringConverter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public static int id;
    public Label userInfo;
    public JFXListView usersList;
    public TextField findField;
    private WindowLoader windowLoader;
    private ArrayList<String> users;
    public static Personel user;
public JFXButton editButton;
public JFXButton refreshButton;
    public AdminController() {
        windowLoader = new WindowLoader();
    }

    public void refreshList() {
        usersList.getItems().clear();
        users=new ArrayList<>();
        for (Personel person : Personel.findAll()) {
            String sp = "    ";
            users.add(person.getId_personel() + sp + person.getFirst_name() + sp + person.getLast_name() +
                    sp + person.getUsername() + sp + person.getRole() + sp + person.isActive());
        }
        usersList.getItems().setAll(FXCollections.observableList(users));
    }

    public void refreshArrayOfElements(ArrayList<Personel> people) {
        usersList.getItems().clear();
        users=new ArrayList<>();
        String sp = "    ";
        for (Personel person : people) {
            users.add(person.getId_personel() + sp + person.getFirst_name() + sp + person.getLast_name() +
                    sp + person.getUsername() + sp + person.getRole() + sp + person.isActive());
        }
        usersList.getItems().setAll(FXCollections.observableList(users));
    }

    public void add(ActionEvent actionEvent) throws IOException {
        windowLoader.load(editButton.getScene().getWindow(),new Stage(), "Application", "createUser");
        refreshList();
    }

    public void edit(ActionEvent actionEvent) throws IOException {
        if (!usersList.getSelectionModel().isEmpty()) {
            String line = usersList.getSelectionModel().getSelectedItem().toString();
            id = StringConverter.convert(line);
            windowLoader.load(editButton.getScene().getWindow(),new Stage(), "Application", "editUser");
            refreshList();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(user!=null){
            userInfo.setText(user.getFirst_name()+ " "+user.getLast_name());
        }
        users=new ArrayList<>();
        refreshList();
//        users = new ArrayList<>();
//        for (Personel person : Personel.findAll()) {
//            String sp = "    ";
//            users.add(person.getId_personel() + sp + person.getFirst_name() + sp + person.getLast_name() +
//                    sp + person.getUsername() + sp + person.getRole() + "   active:  " + person.isActive());
//        }
//        usersList.getItems().setAll(FXCollections.observableList(users));
    }

    public void refresh(ActionEvent actionEvent) {
        findField.clear();
        refreshList();
    }

    public void findName(javafx.scene.input.KeyEvent keyEvent) {
        ArrayList<Personel> people=null;
        if (findField.getText().isEmpty()) {
            refreshList();
        }
        else{
            people = Personel.findByName(findField.getText()).get();
            refreshArrayOfElements(people);

        }
    }
}
