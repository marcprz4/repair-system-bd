package com.bd.repairs.Controller;

import com.bd.repairs.Model.ActDic;
import com.bd.repairs.Model.Activity;
import com.bd.repairs.Model.Personel;
import com.bd.repairs.Model.StringConverter;
import com.bd.repairs.View.AlertWindow;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;


public class EditActivityController implements Initializable {
    public JFXTextField seqNumber;
    public JFXTextArea desc;
    public JFXTextArea res;
    public DatePicker startDate;
    public DatePicker endDate;
    public ChoiceBox<String> status;
    public ChoiceBox<String> workerList;
    public JFXButton applyButton;
    public ChoiceBox<String> actDic;
    public Label currentReq;
    private WindowLoader windowLoader;
    private ArrayList<ActDic> actDics;
    private Activity a;
    public JFXButton addNewType;

    public void apply(ActionEvent actionEvent) {
        if(!seqNumber.getText().isEmpty()&&!desc.getText().isEmpty()&&startDate.getValue()!=null&&!status.getSelectionModel().isEmpty()&&!workerList.getSelectionModel().isEmpty()&&!actDic.getSelectionModel().isEmpty()){
            LocalDate temp = startDate.getValue();
            Date temp2 = Date.valueOf(temp);
            Date temp3=null;
            if(endDate.getValue()!= null){
                temp3 = Date.valueOf(endDate.getValue());
            }
            Activity activity = new Activity(a.getId_activity(), Integer.parseInt(seqNumber.getText()), desc.getText(), res.getText(), status.getSelectionModel().getSelectedItem(), temp2, temp3, ManagerController.request.getId_request(), StringConverter.convert(workerList.getSelectionModel().getSelectedItem()), ActDic.find(StringConverter.convertText(actDic.getSelectionModel().getSelectedItem())).get().getActdic_shortcut());
            activity.update();
            applyButton.getScene().getWindow().hide();
        } else{
            AlertWindow alertWindow=new AlertWindow("error","error","Empty fields!");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        windowLoader = new WindowLoader();
        actDic.getItems().clear();
        try {
            actDics = ActDic.findAll().get();
            for (ActDic a : actDics) {
                actDic.getItems().add(a.getActdic_shortcut() + " - " + a.getActdic_fullname());
            }
        } catch (NoSuchElementException e) {

        }
        currentReq.setText("current request: " + ManagerController.request.getId_request());
        status.getItems().addAll("OPEN","IN PROGRESS", "FINISHED", "CANCELED");
        ArrayList<Personel> array = Personel.findByRole("WORKER").get();
        for (Personel p : array) {
            workerList.getItems().add(p.getId_personel() + " " + p.getFirst_name() + " " + p.getLast_name());
        }
        a=ManagerController.activity;
        desc.setText(a.getDescription());
        status.getSelectionModel().select(a.getStatus());
        res.setText(a.getResult());
        startDate.setValue(a.getDate_start().toLocalDate());
        if(a.getDate_end()!=null){
            endDate.setValue(a.getDate_end().toLocalDate());
        }
        ActDic ac=ActDic.find(a.getActdic_shortcut()).get();
        actDic.getSelectionModel().select(ac.getActdic_shortcut() + " - " + ac.getActdic_fullname());
        Personel p=Personel.findById(a.getId_personel()).get();
        workerList.getSelectionModel().select(p.getId_personel() + " " + p.getFirst_name() + " " + p.getLast_name());
        currentReq.setText(Integer.toString(a.getId_request()));
        seqNumber.setText(Integer.toString(a.getSeq_number()));
    }

    public void addNewType(ActionEvent actionEvent) throws IOException {
        windowLoader.load(addNewType.getScene().getWindow(),new Stage(), "Application", "addActivityType");
    }

    public void refresh(ActionEvent actionEvent) {
        actDic.getItems().clear();
        try {
            actDics = ActDic.findAll().get();
            for (ActDic a : actDics) {
                actDic.getItems().add(a.getActdic_shortcut() + " " + a.getActdic_fullname());
            }
        } catch (NoSuchElementException e) {

        }
    }
}
