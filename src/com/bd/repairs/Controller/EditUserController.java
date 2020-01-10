package com.bd.repairs.Controller;

import com.bd.repairs.Model.PasswordAuthentication;
import com.bd.repairs.Model.Personel;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditUserController implements Initializable {
    public TextField fname;
    public TextField lname;
    public TextField username;
    public PasswordField password;
    public CheckBox active;
    public JFXButton applyButton;
    public ChoiceBox<String> role;
    private PasswordAuthentication pass;
    Personel person;

    public void apply() {
        pass=new PasswordAuthentication();
        Personel person = new Personel(Personel.findById(AdminController.id).get().getId_personel(), fname.getText(), lname.getText(), role.getSelectionModel().getSelectedItem(), username.getText(), pass.hash(password.getText()), active.isSelected());
        person.update();
        applyButton.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        person = Personel.findById(AdminController.id).get();
        this.fname.setText(person.getFirst_name());
        this.lname.setText(person.getLast_name());
        this.username.setText(person.getUsername());
        this.password.setText(person.getPassword());
        this.active.setSelected(person.isActive());
        role.getItems().add("ADMIN");
        role.getItems().add("MANAGER");
        role.getItems().add("WORKER");
        role.getSelectionModel().select(person.getRole());
    }
}
