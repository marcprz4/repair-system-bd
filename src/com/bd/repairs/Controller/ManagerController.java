package com.bd.repairs.Controller;

import com.bd.repairs.Model.*;
import com.bd.repairs.Model.Object;
import com.bd.repairs.View.AlertWindow;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {
    public JFXButton addCarButton;
    public JFXButton addAcButton;
    public JFXButton addReqButton;
    public JFXButton clrButton;
    public JFXButton addClientButton;
    public Label loggedUserInfo;
    public JFXListView<String> listActivity;
    public JFXListView<String> listClient;
    public JFXListView<String> listObject;
    public JFXListView<String> listRequest;
    public TextField searchField1;
    public TextField searchField2;

    public static Object object;
    public static Client client;
    public static Activity ac;
    public static Personel user;
    public static Request req;
    private WindowLoader windowLoader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshClients();
        refreshObjects();
        refreshRequests();
//        refreshActivities();
        windowLoader = new WindowLoader();
        if (user != null)
            loggedUserInfo.setText(user.getFirst_name() + " " + user.getLast_name());
    }

    //-------------wyswietlanie list
    private void displayClients(ArrayList<Client> clients){
        listClient.getItems().clear();
        ArrayList<String> stringList=new ArrayList<>();
        for(Client c:clients){
            stringList.add(c.getId_client()+" "+c.getName()+" "+c.getTelephone());
        }
        listClient.getItems().addAll(stringList);
    }

    private void refreshClients(){
        ArrayList<Client> clients=Client.findAll();
        displayClients(clients);
    }
    private void displayObjects(ArrayList<Object> objs){
        listObject.getItems().clear();
        ArrayList<String> stringList=new ArrayList<>();
        Client owner=null;
        for(Object c:objs){
            owner=Client.findById(c.getId_client()).get();
            stringList.add(c.getId_object()+" "+c.getName()+" "+owner.getName());
        }
        listObject.getItems().addAll(stringList);
    }

    private void refreshObjects(){
        ArrayList<Object> objects=Object.findAll();
        displayObjects(objects);
    }

    private void displayRequests(ArrayList<Request> requests){
        listRequest.getItems().clear();
        ArrayList<String> stringList=new ArrayList<>();
        for(Request c:requests){
            stringList.add(c.getId_request()+" s: "+c.getDate_start()+" e: "+c.getDate_end()+" "+c.getStatus()+" "+c.getDescription()+" "+c.getResult());
        }
        listRequest.getItems().addAll(stringList);
    }

    private void refreshRequests(){
        ArrayList<Request> requests=Request.findByIdPersonel(user.getId_personel()).get();//odpowiada findAll - jest OK.
        displayRequests(requests);
    }

    private void displayActivities(ArrayList<Activity> activities) {
        listActivity.getItems().clear();
        ArrayList<String> stringList=new ArrayList<>();
        for(Activity c:activities){
            Personel worker=Personel.findById(c.getId_personel()).get();
            stringList.add(c.getId_activity()+" s: "+c.getDate_start()+" e: "+c.getDate_end()+" "+c.getStatus()+" "+worker.getLast_name()+" "+c.getDescription()+" "+c.getResult());
        }
        listActivity.getItems().addAll(stringList);
    }

    private void refreshActivities() {
        ArrayList<Activity> activities=Activity.findAll();
        displayActivities(activities);
    }

    //-----------------obsluga przyciskow
    public void addClient(ActionEvent actionEvent) throws IOException {
        if(!listClient.getSelectionModel().isEmpty()){
            client=Client.findById(StringConverter.convert(listClient.getSelectionModel().getSelectedItem())).get();
            windowLoader.load(new Stage(), "Application", "editClient");
        } else{
            windowLoader.load(new Stage(), "Application", "addClient");
        }
        KeyEvent keyEvent=null;
        loadClients(keyEvent);
    }

    public void addObject(ActionEvent actionEvent) throws IOException {
        if (listObject.getSelectionModel().isEmpty()) {
            if(listClient.getSelectionModel().isEmpty()){
                AlertWindow alertWindow=new AlertWindow("Error","error","No client selected to add car.");
            } else{
                client=Client.findById(StringConverter.convert(listClient.getSelectionModel().getSelectedItem())).get();
                windowLoader.load(new Stage(), "Application", "addObject");
            }
        } else {
            Object car1=Object.findById(StringConverter.convert(listObject.getSelectionModel().getSelectedItem())).get();
            object =car1;
            windowLoader.load(new Stage(), "Application", "editObject");
        }
        KeyEvent keyEvent=null;
        loadObjects(keyEvent);
    }

    public void addRequest(ActionEvent actionEvent) throws IOException {
//        AddRequestController.car = Object.findById(StringConverter.convert(searchList.getSelectionModel().getSelectedItem())).get();
//
//        windowLoader.load(new Stage(), "Application", "addRequest");
    }

    public void addActivity(ActionEvent actionEvent) throws IOException {
//        req = Request.findById(StringConverter.convert(reqList.getSelectionModel().getSelectedItem())).get();
//        windowLoader.load(new Stage(), "Application", "addActivity");
    }

//---------obsluga myszki/przyciskow klawiatury
    public void loadClients(KeyEvent keyEvent) {
        if(searchField1.getText().isEmpty()){
            refreshClients();
        } else{
            ArrayList<Client> clients2=Client.findByName(searchField1.getText()).get();
            displayClients(clients2);
            }
        }

    public void loadObjects2(MouseEvent mouseEvent) {
        KeyEvent keyEvent=null;
        loadObjects(keyEvent);
    }

    public void loadObjects(KeyEvent keyEvent) {
        ArrayList<String> stringList2=new ArrayList<>();
        if(!listClient.getSelectionModel().isEmpty()){
            ArrayList<Object> objects2=Object.findByOwner(StringConverter.convert(listClient.getSelectionModel().getSelectedItem())).get();
            displayObjects(objects2);
        } else{
            if(searchField2.getText().isEmpty()){
                refreshObjects();
            }
            else{
                ArrayList<Object> objects2=Object.findByName(searchField2.getText()).get();
                displayObjects(objects2);
            }
        }
    }

    public void loadRequests(MouseEvent mouseEvent) {
        if(listObject.getSelectionModel().isEmpty()){
            refreshRequests();
        } else{
                ArrayList<Request> requests2=Request.findByIdObject(StringConverter.convert(listObject.getSelectionModel().getSelectedItem())).get();
                displayRequests(requests2);
        }
    }

    public void loadActivities(MouseEvent mouseEvent) {
        if(!listRequest.getSelectionModel().isEmpty()){
            ArrayList<Activity> activities2=Activity.findByRequest(StringConverter.convert(listRequest.getSelectionModel().getSelectedItem())).get();
                displayActivities(activities2);
        }
    }

    public void clear(ActionEvent actionEvent) {
        refreshClients();
        refreshObjects();
        refreshRequests();
    }
}
