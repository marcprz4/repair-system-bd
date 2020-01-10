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
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditRequestController implements Initializable {
    public static Object car;
    public JFXTextArea desc;
    public JFXTextArea res;
    public DatePicker startDate;
    public DatePicker endDate;
    public ChoiceBox<String> status;
    public Label ccar;
    public JFXButton applyButton;

    public void apply(ActionEvent actionEvent) {
        LocalDate temp = startDate.getValue();
        Date temp2 = Date.valueOf(temp);
        Date temp3 = Date.valueOf(endDate.getValue());
        Request request = new Request(0, desc.getText(), res.getText(), status.getValue(), temp2, temp3, car.getId_object(), ManagerController.user.getId_personel());
        Request r2 = request;
        request.update();
        Stage stage = (Stage) applyButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Request r=ManagerController.request;
        car=Object.findById(r.getId_object()).get();
        status.getItems().addAll("OPEN","IN PROGRESS", "FINISHED", "CANCELED");
        ccar.setText(car.getName());
        startDate.setValue(r.getDate_start().toLocalDate());
        endDate.setValue(r.getDate_end().toLocalDate());
        desc.setText(r.getDescription());
        res.setText(r.getResult());
    }
}
