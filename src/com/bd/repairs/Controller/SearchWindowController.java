package com.bd.repairs.Controller;

import com.bd.repairs.Model.Client;
import com.bd.repairs.Model.Personel;
import com.bd.repairs.Model.Object;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SearchWindowController implements Initializable {
    @FXML
    private Label helloLabel;
    @FXML
    private ListView<String> list;
    @FXML
    private ChoiceBox<String> searchBy;
    @FXML
    private Label label;
    @FXML
    private TextField resField1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list.getItems().clear();
//        System.out.println("o: "+MainWindowController.type);
        switch(MainWindowController.type){
            case "Personel": {
                searchBy.getItems().addAll("by name","by username");
                label.setText("id" + "    " + "First name" + "    " + "Last name" + "    " + "role" + "    " + "username");

                    ArrayList<Personel> people = Personel.findAll();
                    for (Personel p : people) {
                        list.getItems().add(p.getId_personel() + "    " + p.getFirst_name() + "    " + p.getLast_name() + "    " + p.getRole() + "    " + p.getUsername());
                    }
                    break;
            }
                case "Client": {
                    searchBy.getItems().addAll("by names","by company name");
                    label.setText("id" + "    " + "First name" + "    " + "Last name(name)" + "    " + "Telephone");

                    ArrayList<Client> clients = Client.findAll();
                    for (Client c : clients) {
                        if (!c.getFname().isEmpty())
                            list.getItems().add(c.getId_client() + "    " + c.getFname() + "    " + c.getLname() + "    " + c.getTelephone());
                        else
                            list.getItems().add(c.getId_client() + "    " + c.getName() + "    " + c.getTelephone());
                    }
                    break;
                }
            case "Car": {
                searchBy.getItems().addAll("by name");
//                private int id_object;
//                private String name;
//                private int id_client;
//                private int id_type;
                label.setText("id" + "    " + "name" + "    " + "id_client" + "    " + "id_type");
                break;
            }
            default:{
                //cost
            }
        }
    }

    public SearchWindowController() {
//        helloLabel.setText("Searching on "+MainWindowController.entitiesComboBox.getSelectionModel().getSelectedItem()+" table");
    }

    public void search(ActionEvent actionEvent) {
        list.getItems().clear();
        switch(MainWindowController.type){
            case "Personel": {
                if(!resField1.getText().isEmpty()){
                    if(!resField1.getText().isEmpty()&&searchBy.getSelectionModel().getSelectedItem().equals("by name")){
                        Personel p=Personel.findByName(resField1.getText()).get();
                            list.getItems().add(p.getId_personel() + "    " + p.getFirst_name() + "    " + p.getLast_name() + "    " + p.getRole() + "    " + p.getUsername());
                    }
                    else if(!resField1.getText().isEmpty()&&searchBy.getSelectionModel().getSelectedItem().equals("by username")){
                        Personel p=Personel.findByUsername(resField1.getText()).get();
                        list.getItems().add(p.getId_personel() + "    " + p.getFirst_name() + "    " + p.getLast_name() + "    " + p.getRole() + "    " + p.getUsername());
                    }
                }
            }
            case "Client": {
                if(!resField1.getText().isEmpty()){
                    if(!resField1.getText().isEmpty()&&searchBy.getSelectionModel().getSelectedItem().equals("by company name")){
                        Client c=Client.findByName(resField1.getText()).get();
                            list.getItems().add(c.getId_client() + "    " + c.getName() + "    " + c.getTelephone());
                    }
                    else if(!resField1.getText().isEmpty()&&searchBy.getSelectionModel().getSelectedItem().equals("by names")){
                        Client c=Client.findByNames(resField1.getText()).get();
                            list.getItems().add(c.getId_client() + "    " + c.getFname() + "    " + c.getLname() + "    " + c.getTelephone());
                    }
                }
                break;
            }
            case "Car": {
                if(!resField1.getText().isEmpty()){
                    if(!resField1.getText().isEmpty()&&searchBy.getSelectionModel().getSelectedItem().equals("by name")){
                        Object o=Object.FindByName(resField1.getText()).get();
                        list.getItems().add(o.getId_object()+ "    "+o.getName()+ "    "+o.getId_client()+o.getId_type());
                    }
                }
                break;
            }
            default:{
                //cost
            }
        }
    }

    public void clear(ActionEvent actionEvent) {
        list.getItems().clear();
    }
}
