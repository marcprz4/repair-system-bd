package com.bd.repairs.Controller;

import com.bd.repairs.Model.Personel;
import com.bd.repairs.Model.StringConverter;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
public static int id;
    public Label userInfo;
    public JFXListView usersList;
    public TextField findField;
    private WindowLoader windowLoader;
    private ArrayList<String> users;
    public AdminController() {
        windowLoader=new WindowLoader();
    }

    public void refreshList(){
        users.clear();
        for(Personel person:Personel.findAll()){
            String sp="    ";
            users.add(person.getId_personel()+ sp + person.getFirst_name()+ sp + person.getLast_name() +
                    sp + person.getUsername() + sp + person.getRole() + sp + person.isActive());
        }
        usersList.getItems().setAll(FXCollections.observableList(users));
    }

    public void refreshSingleElement(Personel person){
        users.clear();
        String sp="    ";
        users.add(person.getId_personel()+ sp + person.getFirst_name()+ sp + person.getLast_name() +
                sp + person.getUsername() + sp + person.getRole() + sp + person.isActive());
        usersList.getItems().setAll(FXCollections.observableList(users));
    }

    public void add(ActionEvent actionEvent) throws IOException {
            windowLoader.load(new Stage(), "Application", "createUser");
    }

    public void deactivate(ActionEvent actionEvent) {
        if(!usersList.getSelectionModel().isEmpty()) {
            String line=usersList.getSelectionModel().getSelectedItem().toString();
            Personel personel = Personel.findById(StringConverter.convert(line)).get();
            personel.changeActive();
            personel.update();
            refreshList();
        }
    }

    public void edit(ActionEvent actionEvent) throws IOException {
        String line=usersList.getSelectionModel().getSelectedItem().toString();
        id=StringConverter.convert(line);
        if(!usersList.getSelectionModel().isEmpty()) {
            windowLoader.load(new Stage(), "Application", "editUser");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userInfo.setText("");
        users=new ArrayList<>();
        for(Personel person:Personel.findAll()){
            String sp="    ";
            users.add(person.getId_personel()+ sp + person.getFirst_name()+ sp + person.getLast_name() +
                    sp + person.getUsername() + sp + person.getRole() + sp + person.isActive());
        }
        usersList.getItems().setAll(FXCollections.observableList(users));
    }

    public void refresh(ActionEvent actionEvent) {
        findField.setText("");
        refreshList();
    }

    public void find(ActionEvent actionEvent) {
        if(!findField.getText().isEmpty()){
            Personel person=Personel.findByName(findField.getText()).get();
            refreshSingleElement(person);
        }
    }
}
