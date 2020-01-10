package com.bd.repairs.Controller;

import com.bd.repairs.Model.Client;
import com.bd.repairs.Model.Object;
import com.bd.repairs.Model.ObjectType;
import com.bd.repairs.View.AlertWindow;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditObjectController implements Initializable {
    public JFXTextField brand;
    public JFXTextField model;
    public JFXButton applyButton;
    public ChoiceBox<String> typeList;
    public JFXButton newTypeButton;
    public Label owner;
    private WindowLoader windowLoader;
private Client client;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        windowLoader = new WindowLoader();
        client = Client.findById(ManagerController.object.getId_client()).get();
        owner.setText("Owner:\r" + client.getName());
        String carName=ManagerController.object.getName();
        typeList.getSelectionModel().select(ManagerController.object.getId_type());
        //String[] phones = lineOfPhonesWithMultipleWhiteSpace.split("\\s+");
        String[] names = carName.split("\\s+");
        int i=0;
        String modelName="";
        for(String name:names){
            if(i==0){
                brand.setText(name);
            }else{
                modelName+=name;
            }
            i++;
        }
        model.setText(modelName);
        refresh();
    }

    public void apply(ActionEvent actionEvent) {
        if (!brand.getText().isEmpty() && !model.getText().isEmpty() && !typeList.getSelectionModel().isEmpty()) {
            Object object = new Object(ManagerController.object.getId_object(), brand.getText() + " " + model.getText(), client.getId_client(), typeList.getSelectionModel().getSelectedItem());
            object.update();
        } else {
            AlertWindow alertWindow=new AlertWindow("error","error","Empty fields!");
            return;
        }
        applyButton.getScene().getWindow().hide();
    }

    public void addType(ActionEvent actionEvent) throws IOException {
        windowLoader.load(applyButton.getScene().getWindow(),new Stage(), "Application", "addObjectType");
    }

    private void refresh() {
        typeList.getItems().clear();
        for (ObjectType objectType : ObjectType.findAll()) {
            typeList.getItems().add(objectType.getShortcut());
        }
    }

    public void ref(ActionEvent actionEvent) {
        refresh();
    }
}
