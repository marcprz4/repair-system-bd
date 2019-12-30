package com.bd.repairs.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
public class WorkerController implements Initializable {
    public Label userInfo;
    public JFXListView<String> actList;
    public JFXButton doneButton;
    public JFXButton cancelButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
