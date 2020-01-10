package com.bd.repairs.Controller;

import com.bd.repairs.Model.Object;
import com.bd.repairs.Model.*;
import com.bd.repairs.View.AlertWindow;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WorkerController implements Initializable {
    public static Personel user;
    public Label userInfo;
    public JFXListView<String> actList;
    public JFXButton doneButton;
    public JFXButton cancelButton;
    public JFXTextArea result;
    private static int selected;
    AlertWindow alertWindow;
    private void refresh() {
        ArrayList<Activity> arrayList = Activity.finByWorkerId(user.getId_personel()).get();
        actList.getItems().clear();
        for (Activity a : arrayList) {
            Request r = Request.findById(a.getId_request()).get();
            Object car = Object.findById(r.getId_object()).get();
            actList.getItems().add(a.getId_activity() + " " + car.getName() + " " + a.getDescription() + " " + a.getResult() + " " + a.getDate_start() + " " + a.getSeq_number() + " " + a.getStatus());
        }
        actList.getSelectionModel().select(selected);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userInfo.setText("Hello " + user.getFirst_name() + " " + user.getLast_name());
        refresh();
    }

    public void checkDone(ActionEvent actionEvent) {
        if(!actList.getSelectionModel().isEmpty()){
            Activity a = Activity.finById(StringConverter.convert(actList.getSelectionModel().getSelectedItem())).get();
            selected=actList.getSelectionModel().getSelectedIndex();
            a.setStatus("FINISHED");
            if(!result.getText().isEmpty())
                a.setResult(result.getText());
            a.update();
            refresh();
        }
        else{
            alertWindow=new AlertWindow("error","error","No item selected!");
        }
    }

    public void cancel(ActionEvent actionEvent) {
        if(!actList.getSelectionModel().isEmpty()) {
            Activity a = Activity.finById(StringConverter.convert(actList.getSelectionModel().getSelectedItem())).get();
            selected = actList.getSelectionModel().getSelectedIndex();
            a.setStatus("CANCELED");
            if(!result.getText().isEmpty())
                a.setResult(result.getText());
            a.update();
            refresh();
        }
        else{
            alertWindow=new AlertWindow("error","error","No item selected!");
        }
    }

    public void start(ActionEvent actionEvent) {
        if(!actList.getSelectionModel().isEmpty()) {
            Activity a = Activity.finById(StringConverter.convert(actList.getSelectionModel().getSelectedItem())).get();
            selected = actList.getSelectionModel().getSelectedIndex();
            a.setStatus("IN PROGRESS");
            if(!result.getText().isEmpty())
            a.setResult(result.getText());
            a.update();
            refresh();
        }
        else{
            alertWindow=new AlertWindow("error","error","No item selected!");
        }
    }
}
