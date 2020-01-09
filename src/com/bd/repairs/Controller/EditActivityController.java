package com.bd.repairs.Controller;

import com.bd.repairs.Model.ActDic;
import com.bd.repairs.Model.Activity;
import com.bd.repairs.Model.Personel;
import com.bd.repairs.Model.StringConverter;
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

    public void apply(ActionEvent actionEvent) {

        LocalDate temp = startDate.getValue();
        Date temp2 = Date.valueOf(temp);
        Date temp3 = Date.valueOf(endDate.getValue());
        Activity activity = new Activity(0, Integer.parseInt(seqNumber.getText()), desc.getText(), res.getText(), status.getSelectionModel().getSelectedItem(), temp2, temp3, ManagerController.request.getId_request(), StringConverter.convert(workerList.getSelectionModel().getSelectedItem()), ActDic.find(StringConverter.convertText(actDic.getSelectionModel().getSelectedItem())).get().getActdic_shortcut());
        activity.update();
        Stage stage = (Stage) applyButton.getScene().getWindow();
        stage.close();
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
        status.getItems().addAll("IN PROGRESS", "FINISHED", "CANCELED");
        ArrayList<Personel> array = Personel.findByRole("WORKER").get();
        for (Personel p : array) {
            workerList.getItems().add(p.getId_personel() + " " + p.getFirst_name() + " " + p.getLast_name());
        }
        Activity a=Activity.finById(ManagerController.act.getId_activity()).get();
        desc.setText(a.getDescription());
        res.setText(a.getResult());
        startDate.setValue(a.getDate_start().toLocalDate());
        endDate.setValue(a.getDate_end().toLocalDate());
        currentReq.setText(Integer.toString(a.getId_request()));
        seqNumber.setText(Integer.toString(a.getSeq_number()));

        /*public JFXTextField seqNumber;
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
    private ArrayList<ActDic> actDics;*/
//
//        LocalDate temp = startDate.getValue();
//        Date temp2 = Date.valueOf(temp);
//        Date temp3 = Date.valueOf(endDate.getValue());
//        Activity activity = new Activity(0, Integer.parseInt(seqNumber.getText()), desc.getText(), res.getText(), status.getSelectionModel().getSelectedItem(), temp2, temp3, ManagerController.request.getId_request(), StringConverter.convert(workerList.getSelectionModel().getSelectedItem()), ActDic.find(StringConverter.convertText(actDic.getSelectionModel().getSelectedItem())).get().getActdic_shortcut());
//        activity.insert();
    }

    public void addNewType(ActionEvent actionEvent) throws IOException {
        windowLoader.load(new Stage(), "Application", "addActivityType");
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
