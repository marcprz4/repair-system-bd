package com.bd.repairs.Controller;

import com.bd.repairs.Model.Client;
import com.bd.repairs.Model.Object;
import com.bd.repairs.Model.StringConverter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {
    public static int clientId;
    /*
      <Label layoutX="57.0" layoutY="188.0" prefHeight="17.0" prefWidth="153.0" text="activities" />
      <JFXButton fx:id="addActivity" layoutX="57.0" layoutY="420.0" onAction="#addActivity" prefHeight="33.0" prefWidth="116.0" style="-fx-background-color: #e6e6e6;" text="add new activity" />
      <JFXButton fx:id="activateAct" layoutX="196.0" layoutY="420.0" onAction="#activate2" prefHeight="33.0" prefWidth="153.0" style="-fx-background-color: #e6e6e6;" text="make active/deactivate" />
      <JFXButton fx:id="editWorker" layoutX="49.0" layoutY="508.0" onAction="#editActivityWorker" prefHeight="33.0" prefWidth="133.0" style="-fx-background-color: #e6e6e6;" text="edit activity worker" />
      <ChoiceBox fx:id="reqList" layoutX="54.0" layoutY="159.0" prefHeight="22.0" prefWidth="214.0" />
      <Label layoutX="57.0" layoutY="142.0" prefHeight="17.0" prefWidth="153.0" text="requests" />
      <Label layoutX="57.0" layoutY="57.0" prefHeight="17.0" prefWidth="153.0" text="search by car/owner" />
      <Label fx:id="objectInfo" layoutX="386.0" layoutY="49.0" prefHeight="77.0" prefWidth="271.0" text="active car/owner" />
      <JFXButton fx:id="addReq" layoutX="280.0" layoutY="159.0" onAction="#addRequest" prefHeight="22.0" prefWidth="64.0" style="-fx-background-color: #e6e6e6;" text="add" />
      <JFXButton fx:id="activateReq" layoutX="354.0" layoutY="159.0" onAction="#activate" prefHeight="25.0" prefWidth="85.0" style="-fx-background-color: #e6e6e6;" text="(de)activate" />
      <Label layoutX="57.0" layoutY="482.0" prefHeight="17.0" prefWidth="153.0" text="workers" />*/
    public JFXButton button1;
    public ChoiceBox<String> searchList;
    public ChoiceBox<String> typeList;
    public JFXButton button2;
    public Label userInfo;
    public JFXListView<String> listView;
    public JFXButton addActivity;
    public JFXButton activateAct;
    public JFXButton editWorker;
    public ChoiceBox<String> reqList;
    public JFXButton addReq;
    public JFXButton activateReq;
    private WindowLoader windowLoader;

    private void initList(int var) {
        searchList.getItems().clear();
        String sp = "   ";
        switch (var) {
            case 0: {
                for (Client c : Client.findAll()) {
                    if (!c.getFname().isEmpty() && !c.getLname().isEmpty()) {
                        searchList.getItems().add(c.getId_client() + sp + c.getFname() + sp + c.getLname());
                    } else
                        searchList.getItems().add(c.getName());
                }
                break;
            }
            case 1: {
                System.out.println(Object.findAll().size());
                for (Object c : Object.findAll()) {
                    Client owner = Client.findById(c.getId_client()).get();
                    if (!owner.getFname().isEmpty() && !owner.getLname().isEmpty()) {
                        searchList.getItems().add(c.getId_client() + sp + c.getName() + sp + owner.getFname() + sp + owner.getLname());
                    }
                    searchList.getItems().add(c.getName() + sp + owner.getName());
                }
                break;
            }
        }

    }

    public void searchObject(ActionEvent actionEvent) {
        initList(typeList.getSelectionModel().getSelectedIndex());
    }

    public void addObject(ActionEvent actionEvent) throws IOException {
        if (typeList.getSelectionModel().getSelectedItem().equals("by Owner") && !searchList.getSelectionModel().getSelectedItem().isEmpty())
            clientId = StringConverter.convert(searchList.getSelectionModel().getSelectedItem());
        windowLoader.load(new Stage(), "Application", "addObject");
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
}
