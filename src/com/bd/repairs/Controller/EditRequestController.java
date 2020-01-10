package com.bd.repairs.Controller;

import com.bd.repairs.Model.Object;
import com.bd.repairs.Model.Request;
import com.bd.repairs.View.AlertWindow;
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
    private Request re;
    public void apply(ActionEvent actionEvent) {
        if(!desc.getText().isEmpty()&&startDate.getValue()!=null&&!status.getSelectionModel().isEmpty()){
            LocalDate temp = startDate.getValue();
            Date temp2 = Date.valueOf(temp);
            Date temp3=null;
            if(endDate.getValue()!= null){
                temp3 = Date.valueOf(endDate.getValue());
            }
            Request request = new Request(re.getId_request(), desc.getText(), res.getText(), status.getValue(), temp2, temp3, car.getId_object(), ManagerController.user.getId_personel());
            Request r2 = request;
            request.update();
            applyButton.getScene().getWindow().hide();
        } else{
            AlertWindow alertWindow=new AlertWindow("error","error","Empty fields!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        re=ManagerController.request;
        car=Object.findById(re.getId_object()).get();
        status.getItems().addAll("OPEN","IN PROGRESS", "FINISHED", "CANCELED");
        status.getSelectionModel().select(re.getStatus());
        ccar.setText(car.getName());
        startDate.setValue(re.getDate_start().toLocalDate());
        if(re.getDate_end()!=null){
            endDate.setValue(re.getDate_end().toLocalDate());
        }
        desc.setText(re.getDescription());
        res.setText(re.getResult());
    }
}
