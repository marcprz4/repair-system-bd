package com.bd.repairs.Controller;

import com.bd.repairs.Model.Client;
import com.bd.repairs.Model.Object;
import com.bd.repairs.Model.StringConverter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {
    public static int clientId;
    /* <JFXButton fx:id="button1" layoutX="54.0" layoutY="107.0" onAction="#searchObject" prefHeight="33.0" prefWidth="116.0" style="-fx-background-color: #e6e6e6;" text="search" />
        <ChoiceBox fx:id="searchList" layoutX="177.0" layoutY="77.0" prefHeight="22.0" prefWidth="214.0" />
        <JFXButton fx:id="addCar" layoutX="226.0" layoutY="107.0" onAction="#addObject" prefHeight="33.0" prefWidth="116.0" style="-fx-background-color: #e6e6e6;" text="add car to owner" />
        <Label fx:id="userInfo" layoutX="637.0" layoutY="14.0" prefHeight="22.0" prefWidth="99.0" text="Label" />
        <JFXListView fx:id="listView" layoutX="84.0" layoutY="204.0" prefHeight="191.0" prefWidth="346.0" />
        <Label layoutX="87.0" layoutY="190.0" prefHeight="17.0" prefWidth="153.0" text="activities" />
        <JFXButton fx:id="addActivity" layoutX="87.0" layoutY="422.0" onAction="#addActivity" prefHeight="33.0" prefWidth="116.0" style="-fx-background-color: #e6e6e6;" text="add new activity" />
        <JFXButton fx:id="activateAct" layoutX="226.0" layoutY="422.0" onAction="#activate2" prefHeight="33.0" prefWidth="153.0" style="-fx-background-color: #e6e6e6;" text="make active/deactivate" />
        <JFXButton fx:id="editWorker" layoutX="79.0" layoutY="510.0" onAction="#editActivityWorker" prefHeight="33.0" prefWidth="133.0" style="-fx-background-color: #e6e6e6;" text="edit activity worker" />
        <ChoiceBox fx:id="reqList" layoutX="84.0" layoutY="161.0" prefHeight="22.0" prefWidth="214.0" />
        <Label layoutX="87.0" layoutY="144.0" prefHeight="17.0" prefWidth="153.0" text="requests" />
        <Label layoutX="87.0" layoutY="51.0" prefHeight="17.0" prefWidth="122.0" text="search by car/owner" />
        <Label fx:id="objectInfo" layoutX="416.0" layoutY="51.0" prefHeight="77.0" prefWidth="271.0" text="active car/owner" />
        <JFXButton fx:id="addReq" layoutX="310.0" layoutY="161.0" onAction="#addRequest" prefHeight="22.0" prefWidth="64.0" style="-fx-background-color: #e6e6e6;" text="add" />
        <JFXButton fx:id="activateReq" layoutX="384.0" layoutY="161.0" onAction="#activate" prefHeight="25.0" prefWidth="85.0" style="-fx-background-color: #e6e6e6;" text="(de)activate" />
        <Label layoutX="87.0" layoutY="484.0" prefHeight="17.0" prefWidth="153.0" text="workers" />
        <ChoiceBox fx:id="typeList" layoutX="212.0" layoutY="47.0" prefHeight="0.0" prefWidth="105.0" />
      <TextField fx:id="searchField1" layoutX="54.0" layoutY="77.0" prefHeight="25.0" prefWidth="116.0" promptText="enter car/owner" />*/
    public JFXButton button1;
    public ChoiceBox<String> searchList;
    public ChoiceBox<String> typeList;
    public JFXButton addCar;
    public Label userInfo;
    public JFXListView<String> listView;
    public JFXButton addActivity;
    public JFXButton activateAct;
    public JFXButton editWorker;
    public ChoiceBox<String> reqList;
    public JFXButton addReq;
    public JFXButton activateReq;
    public TextField searchField1;
    public CheckBox business;
    private WindowLoader windowLoader;
    public JFXButton addClient;

    private void initList(int var) {
        searchList.getItems().clear();
        String sp = "   ";
        switch (var) {
            case 0: {
                for (Client c : Client.findAll()) {
                    if (!c.getFname().isEmpty() && !c.getLname().isEmpty()) {
                        searchList.getItems().add(c.getId_client() + sp + c.getFname() + sp + c.getLname());
                    } else
                        searchList.getItems().add(c.getId_client() + sp + c.getName());
                }
                break;
            }
            case 1: {
                for (Object c : Object.findAll()) {
                    Client owner = Client.findById(c.getId_client()).get();
                    if (!owner.getFname().isEmpty() && !owner.getLname().isEmpty()) {
                        searchList.getItems().add(c.getId_client() + sp + c.getName() + sp + owner.getFname() + sp + owner.getLname());
                    }
                    searchList.getItems().add(c.getId_client() + sp + c.getName() + sp + owner.getName());
                }
                break;
            }
        }

    }

    public void searchObject(ActionEvent actionEvent) {
        String sp = "   ";
        if (searchField1.getText().isEmpty()) {
            initList(typeList.getSelectionModel().getSelectedIndex());
        } else {
            switch (typeList.getSelectionModel().getSelectedIndex()) {
                case 0: {
                    searchList.getItems().clear();
                    if (business.isSelected()) {
                        for (Client c : Client.findByName(searchField1.getText()).get()) {
                            searchList.getItems().add(c.getId_client() + sp + c.getName());
                        }
                    } else {
                        for (Client c : Client.findByNames(searchField1.getText()).get()) {
                            searchList.getItems().add(c.getId_client() + sp + c.getFname() + sp + c.getLname());
                        }
                    }
                }

                break;

                case 1: {
                    searchList.getItems().clear();
                    for (Object c : Object.FindByName(searchField1.getText()).get()) {
                        Client owner = Client.findById(c.getId_client()).get();
                        if (!owner.getFname().isEmpty() && !owner.getLname().isEmpty()) {
                            searchList.getItems().add(c.getId_client() + sp + c.getName() + sp + owner.getFname() + sp + owner.getLname());
                        }
                        searchList.getItems().add(c.getId_client() + sp + c.getName() + sp + owner.getName());
                    }
                    break;
                }
            }
        }
    }

    public void addCar(ActionEvent actionEvent) throws IOException {
        if (typeList.getSelectionModel().getSelectedItem().equals("by Owner") && !searchList.getSelectionModel().getSelectedItem().isEmpty()) {
            clientId = StringConverter.convert(searchList.getSelectionModel().getSelectedItem());
            windowLoader.load(new Stage(), "Application", "addObject");
        }
    }

    public void addActivity(ActionEvent actionEvent) {
    }

    public void activate2(ActionEvent actionEvent) {
    }

    public void editActivityWorker(ActionEvent actionEvent) {
    }

    public void addRequest(ActionEvent actionEvent) {
    }

    public void activate(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        windowLoader = new WindowLoader();
        typeList.getItems().add("by Owner");
        typeList.getItems().add("by Car");
        typeList.getSelectionModel().select(0);
        initList(typeList.getSelectionModel().getSelectedIndex());
    }

    public void addClient(ActionEvent actionEvent) throws IOException {
        //windowLoader.load(new Stage(), "Application", "editUser");
        windowLoader.load(new Stage(), "Application", "addClient");
    }
}
