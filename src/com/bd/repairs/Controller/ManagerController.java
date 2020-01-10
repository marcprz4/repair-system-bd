package com.bd.repairs.Controller;

import com.bd.repairs.Model.Object;
import com.bd.repairs.Model.*;
import com.bd.repairs.View.AlertWindow;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {
    public static int clientId;
    public static Personel user;
    public static Request request;
    public static Activity act;
    public JFXButton button1;
    public ChoiceBox<String> searchList;
//    public ChoiceBox<String> typeList;
    public JFXButton addCar;
    public Label userInfo;
    public JFXListView<String> listView;
    public JFXButton addActivity;
    public JFXButton activateAct;
    public JFXButton editWorker;
    public ChoiceBox<String> reqList;
    public JFXButton addReq;
    public JFXButton addReq1;
    public JFXButton activateReq;
    public TextField searchField1;
    public CheckBox business;
    public JFXButton addClient;
    public Label objectInfo;
    public JFXButton showcars;
    public JFXButton showA;
    public JFXButton resetButton;
    private WindowLoader windowLoader;
    private boolean showClicked = false;
    public static int reqNum;
    public JFXListView<String> searchListResult;
    public JFXListView<String> searchListResult2;
    public TextField searchField2;
    public static Object car;


    public void addCar(ActionEvent actionEvent) throws IOException {
        if (searchListResult2.getSelectionModel().getSelectedItem().isEmpty()) {
            if(searchListResult.getSelectionModel().isEmpty()){
                AlertWindow alertWindow=new AlertWindow("Error","error","No client selected to add car.");
            } else{
                clientId = StringConverter.convert(searchListResult.getSelectionModel().getSelectedItem());
                windowLoader.load(new Stage(), "Application", "addObject");
            }
        } else {
            Object car1=Object.findById(StringConverter.convert(searchListResult2.getSelectionModel().getSelectedItem())).get();
            car=car1;
            clientId=car.getId_client();
            windowLoader.load(new Stage(), "Application", "editObject");
        }

    }

    public void addActivity(ActionEvent actionEvent) throws IOException {
        request = Request.findById(StringConverter.convert(reqList.getSelectionModel().getSelectedItem())).get();
        windowLoader.load(new Stage(), "Application", "addActivity");
    }

    public void activate2(ActionEvent actionEvent) {
        int id = StringConverter.convert(listView.getSelectionModel().getSelectedItem());
        Activity temp = Activity.finById(id).get();
        if (temp.getStatus().equals("IN PROGRESS")) {
            temp.setStatus("FINISHED");
        } else if (temp.getStatus().equals("CANCELED") || temp.getStatus().equals("FINISHED")) {
            temp.setStatus("IN PROGRESS");
        }
        temp.update();
        listView.getItems().clear();
        ArrayList<Activity> activities = Activity.findByRequest(id).get();
        for (Activity a : activities) {
            Personel p = Personel.findById(a.getId_personel()).get();
            listView.getItems().add(a.getId_activity() + " " + a.getDescription() + " " + p.getFirst_name() + " " + p.getLast_name() + " " + a.getStatus());
        }
    }

    public void addRequest(ActionEvent actionEvent) throws IOException {
        AddRequestController.car = Object.findById(StringConverter.convert(searchList.getSelectionModel().getSelectedItem())).get();

        windowLoader.load(new Stage(), "Application", "addRequest");
    }

    public void editRequest(ActionEvent actionEvent) throws IOException {
        request = Request.findById(StringConverter.convert(reqList.getSelectionModel().getSelectedItem())).get();
//        reqNum=StringConverter.convert(reqList.getSelectionModel().getSelectedItem());
        AddRequestController.car = Object.findById(StringConverter.convert(searchList.getSelectionModel().getSelectedItem())).get();
        windowLoader.load(new Stage(), "Application", "editRequest");
    }

    public void activate(ActionEvent actionEvent) {
        int id = StringConverter.convert(reqList.getSelectionModel().getSelectedItem());
        ArrayList<Request> temp = Request.findByIdObject(id).get();
        if (temp.get(0).getStatus().equals("IN PROGRESS")) {
            temp.get(0).setStatus("FINISHED");
        } else if (temp.get(0).getStatus().equals("CANCELED") || temp.get(0).getStatus().equals("FINISHED")) {
            temp.get(0).setStatus("IN PROGRESS");
        }
        temp.get(0).update();
        reqList.getItems().clear();
        Object car = Object.findById(StringConverter.convert(searchList.getSelectionModel().getSelectedItem())).get();
        Client owner = Client.findById(car.getId_client()).get();
        ArrayList<Request> reqs = Request.findByIdObject(car.getId_object()).get();
        for (Request req : reqs) {
            String requestString = req.getId_request() + " " + car.getName() + " " + owner.getLname() + " " + req.getStatus();
            reqList.getItems().add(requestString);
        }
    }
    private void displayClientsList(ArrayList<Client> clients){
        searchListResult.getItems().clear();
        ArrayList<String> stringList=new ArrayList<>();
        for(Client c:clients){
            stringList.add(c.getId_client()+" "+c.getName()+" "+c.getTelephone());
        }
        searchListResult.getItems().addAll(stringList);
    }

public void refreshList(){
    ArrayList<Client> clients=Client.findAll();
    displayClientsList(clients);
}

    private void displayObjectsList(ArrayList<Object> objs){
        searchListResult2.getItems().clear();
        ArrayList<String> stringList=new ArrayList<>();
        Client owner=null;
        for(Object c:objs){
            owner=Client.findById(c.getId_client()).get();
            stringList.add(c.getId_object()+" "+c.getName()+" "+owner.getName());
        }
        searchListResult2.getItems().addAll(stringList);
    }

    public void refreshCarList(){
        ArrayList<Object> objects=Object.findAll();
        displayObjectsList(objects);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshList();
        refreshCarList();
        windowLoader = new WindowLoader();
//        typeList.getItems().add("by Owner");
//        typeList.getItems().add("by Car");
//        typeList.getSelectionModel().select(0);
//        initList(typeList.getSelectionModel().getSelectedIndex());
        if (user != null)
            userInfo.setText(user.getFirst_name() + " " + user.getLast_name());
        for (int i = 0; i < Request.findAll().size(); i++) {
            Request req = Request.findAll().get(i);
            Object obj = Object.findById(req.getId_object()).get();
            Client cli = Client.findById(obj.getId_client()).get();
            //option with company name
            String requestString = req.getId_request() + " " + obj.getName() + " " + cli.getLname() + " " + req.getStatus();
            reqList.getItems().add(requestString);
        }
    }

    public void addClient(ActionEvent actionEvent) throws IOException {
        //windowLoader.load(new Stage(), "Application", "editUser");
        windowLoader.load(new Stage(), "Application", "addClient");
    }

    public void showActivities(ActionEvent actionEvent) {
        listView.getItems().clear();
        try {
            int id = Request.findById(StringConverter.convert(reqList.getSelectionModel().getSelectedItem())).get().getId_request();
            ArrayList<Activity> activities = Activity.findByRequest(id).get();
            for (Activity a : activities) {
                Personel p = Personel.findById(a.getId_personel()).get();
                listView.getItems().add(a.getId_activity() + " " + a.getDescription() + " Notatka: "+a.getResult()+" " + p.getFirst_name() + " " + p.getLast_name() + " " + a.getStatus());
            }
        } catch (NoSuchElementException e) {
            return;
        }
    }

    public void editActivity(ActionEvent actionEvent) throws IOException {
        act=Activity.finById(StringConverter.convert(listView.getSelectionModel().getSelectedItem())).get();
        request = Request.findById(StringConverter.convert(reqList.getSelectionModel().getSelectedItem())).get();
        windowLoader.load(new Stage(), "Application", "editActivity");
    }

    public void refresh(KeyEvent keyEvent) {
        if(searchField1.getText().isEmpty()){
            refreshList();
        } else{
            ArrayList<Client> clients2=Client.findByName(searchField1.getText()).get();
            displayClientsList(clients2);
            }
        }

    public void refreshCars(KeyEvent keyEvent) {
        ArrayList<String> stringList2=new ArrayList<>();
        if(!searchListResult.getSelectionModel().isEmpty()){
             clientId=StringConverter.convert(searchListResult.getSelectionModel().getSelectedItem());
            ArrayList<Object> objects2=Object.findByOwner(clientId).get();
            displayObjectsList(objects2);

        } else{
            if(searchField2.getText().isEmpty()){
                refreshCarList();
            }
            searchListResult2.getItems().clear();
            ArrayList<Object> objects2=Object.findByName(searchField2.getText()).get();
            displayObjectsList(objects2);
        }
    }

    public void loadCars(MouseEvent mouseEvent) {
        KeyEvent keyEvent=null;
        refreshCars(keyEvent);
    }

    public void clear(ActionEvent actionEvent) {
        KeyEvent keyEvent=null;
        searchListResult.getSelectionModel().clearSelection();
        refresh(keyEvent);
        refreshCars(keyEvent);
    }
}
