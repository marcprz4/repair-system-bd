package com.bd.repairs.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/* <Label fx:id="userInfo" layoutX="506.0" layoutY="14.0" prefHeight="30.0" prefWidth="107.0" text="Label" />
      <JFXListView fx:id="actList" layoutX="54.0" layoutY="90.0" prefHeight="251.0" prefWidth="302.0" />
      <Label layoutX="54.0" layoutY="73.0" prefHeight="17.0" prefWidth="201.0" text="activities" />
      <JFXButton fx:id="doneButton" layoutX="54.0" layoutY="350.0" prefHeight="30.0" prefWidth="107.0" style="-fx-background-color: #e6e6e6;" text="Done" />
      <JFXButton fx:id="cancelButton" layoutX="193.0" layoutY="350.0" prefHeight="30.0" prefWidth="107.0" style="-fx-background-color: #e6e6e6;" text="Cancel" />*/
public class WorkerController implements Initializable {
    public Label userInfo;
    public JFXListView<String> actList;
    public JFXButton doneButton;
    public JFXButton cancelButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
