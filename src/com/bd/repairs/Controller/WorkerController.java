package com.bd.repairs.Controller;

import com.bd.repairs.Model.*;
import com.bd.repairs.Model.Object;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WorkerController implements Initializable {
    public Label userInfo;
    public JFXListView<String> actList;
    public JFXButton doneButton;
    public JFXButton cancelButton;
    public static Personel user;
    public JFXTextArea result;
private void refresh(){
    ArrayList<Activity> arrayList= Activity.finByWorkerId(user.getId_personel()).get();
    actList.getItems().clear();
    for(Activity a:arrayList){
        Request r= Request.findById(a.getId_request()).get();
        Object car=Object.findById(r.getId_object()).get();
        actList.getItems().add(a.getId_activity()+ " "+car.getName()+ " "+a.getSeq_number()+ " "+a.getDescription()+" "+a.getStatus());
    }
}
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userInfo.setText("Hello "+user.getFirst_name()+" "+user.getLast_name());
        refresh();
    }

    public void checkDone(ActionEvent actionEvent) {
        Activity a=Activity.finById(StringConverter.convert(actList.getSelectionModel().getSelectedItem())).get();
        a.setStatus("FINISHED");
        a.setResult(result.getText());
        a.update();
        refresh();
    }

    public void cancel(ActionEvent actionEvent) {
        Activity a=Activity.finById(StringConverter.convert(actList.getSelectionModel().getSelectedItem())).get();
        a.setStatus("CANCELED");
        a.setResult(result.getText());
        a.update();
        refresh();
    }
}
