package com.bd.repairs.Controller;

import com.bd.repairs.Model.ObjectType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class AddObjectTypeController {
    public JFXTextField shortcut;
    public JFXTextField fullname;
    public JFXButton applyButton;

    public void apply(ActionEvent actionEvent) {
        if (!shortcut.getText().isEmpty() && !fullname.getText().isEmpty()) {
            ObjectType objectType = new ObjectType(shortcut.getText(), fullname.getText());
            objectType.insert();
            Stage stage = (Stage) applyButton.getScene().getWindow();
            stage.close();
        } else {
            //error
            return;
        }
    }
}
