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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.Date;
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
    public TextField searchField;
    AlertWindow alertWindow;

    private void displayActivities2(String text) {
        ArrayList<Activity> activities=Activity.findByWorkerId(user.getId_personel()).get();
        actList.getItems().clear();
        ArrayList<String> stringList=new ArrayList<>();
        for(Activity a:activities){
            Request r=Request.findById(a.getId_request()).get();
            Object c=Object.findById(r.getId_object()).get();
            if(c.getName().contains(text.toUpperCase())){
                stringList.add(a.getId_activity()+ " s: " + a.getDate_start() + " e: " +a.getDate_end()+ " " + c.getName()
                        + " " + a.getDescription() + " " + a.getResult()+" " + a.getSeq_number() + " " + a.getStatus());
            }
        }
        actList.getItems().addAll(stringList);
    }

    private void displayActivities(ArrayList<Activity> activities){
        actList.getItems().clear();
        ArrayList<String> stringList=new ArrayList<>();
        for(Activity a:activities){
            Request r=Request.findById(a.getId_request()).get();
            Object c=Object.findById(r.getId_object()).get();
            stringList.add(a.getId_activity()+ " s: " + a.getDate_start() + " e: " +a.getDate_end()+ " " + c.getName()
                    + " " + a.getDescription() + " " + a.getResult()+" " + a.getSeq_number() + " " + a.getStatus());
        }
        actList.getItems().addAll(stringList);
    }

    private void refreshActivitiess(){
        if(searchField.getText().isEmpty()){
            ArrayList<Activity> activities=Activity.findByWorkerId(user.getId_personel()).get();
            displayActivities(activities);
        }else{
            displayActivities2(searchField.getText());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userInfo.setText("Hello " + user.getFirst_name() + " " + user.getLast_name());
        refreshActivitiess();
    }

    public void checkDone(ActionEvent actionEvent) {
        if(!actList.getSelectionModel().isEmpty()){
            Activity a = Activity.findById(StringConverter.convert(actList.getSelectionModel().getSelectedItem())).get();
            selected=actList.getSelectionModel().getSelectedIndex();
            a.setStatus("FINISHED");
            if(!result.getText().isEmpty())
                a.setResult(result.getText());
            java.util.Date now=new java.util.Date();
            a.setDate_end(new Date(now.getTime()));
            a.update();
            refreshActivitiess();
        }
        else{
            alertWindow=new AlertWindow("error","error","No item selected!");
        }
    }

    public void cancel(ActionEvent actionEvent) {
        if(!actList.getSelectionModel().isEmpty()) {
            Activity a = Activity.findById(StringConverter.convert(actList.getSelectionModel().getSelectedItem())).get();
            selected = actList.getSelectionModel().getSelectedIndex();
            a.setStatus("CANCELED");
            if(!result.getText().isEmpty())
                a.setResult(result.getText());
            java.util.Date now=new java.util.Date();
            a.setDate_end(new Date(now.getTime()));
            a.update();
            refreshActivitiess();
        }
        else{
            alertWindow=new AlertWindow("error","error","No item selected!");
        }
    }

    public void start(ActionEvent actionEvent) {
        if(!actList.getSelectionModel().isEmpty()) {
            Activity a = Activity.findById(StringConverter.convert(actList.getSelectionModel().getSelectedItem())).get();
            selected = actList.getSelectionModel().getSelectedIndex();
            a.setStatus("IN PROGRESS");
            if(!result.getText().isEmpty())
            a.setResult(result.getText());
            a.update();
            refreshActivitiess();
        }
        else{
            alertWindow=new AlertWindow("error","error","No item selected!");
        }
    }

    public void find(KeyEvent keyEvent) {
        if(searchField.getText().isEmpty()){
            refreshActivitiess();
        }else{
            displayActivities2(searchField.getText());
        }
    }
}
