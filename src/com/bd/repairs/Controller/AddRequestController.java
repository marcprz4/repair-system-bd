package com.bd.repairs.Controller;

import com.bd.repairs.Model.Object;
import com.bd.repairs.Model.Request;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.sql.Date;

public class AddRequestController implements Initializable {
    public JFXTextArea desc;
    public JFXTextArea res;
    public DatePicker startDate;
    public DatePicker endDate;
    public ChoiceBox<String> status;
    public static Object car;
    public Label ccar;
    public JFXButton applyButton;
    public void apply(ActionEvent actionEvent) {
        LocalDate temp=startDate.getValue();
        Date temp2=Date.valueOf(temp);
        Date temp3=Date.valueOf(endDate.getValue());

        Request request=new Request(0,desc.getText(),res.getText(),status.getValue(),temp2,temp3,car.getId_object(),ManagerController.user.getId_personel());
        request.insert();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        status.getItems().addAll("IN PROGRESS","FINISHED","CANCELED");
        ccar.setText(car.getName());
    }
}
