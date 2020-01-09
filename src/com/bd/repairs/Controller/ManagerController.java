package com.bd.repairs.Controller;

import com.bd.repairs.Model.Object;
import com.bd.repairs.Model.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
    public ChoiceBox<String> typeList;
    public JFXButton addCar;
    public Label userInfo;
    public JFXListView<String> listView;
    public JFXButton addActivity;
    public JFXButton activateAct;
    public JFXButton editWorker;
    public ChoiceBox<String> reqList;
    public JFXButton addReq;
    public JFXButton activateReq;
    public TextField searchField1;
    public CheckBox business;
    public JFXButton addClient;
    public Label objectInfo;
    public JFXButton showcars;
    public JFXButton showA;
    private WindowLoader windowLoader;
    private boolean showClicked = false;

    private void initList(int var) {
        searchList.getItems().clear();
        String sp = "   ";
        switch (var) {
            case 0: {
                for (Client c : Client.findAll()) {
                    if (!c.getFname().isEmpty() && !c.getLname().isEmpty()) {
                        searchList.getItems().add(c.getId_client() + sp + c.getFname() + sp + c.getLname());
                    } else
                        searchList.getItems().add(c.getId_client() + sp + c.getName());
                }
                break;
            }
            case 1: {
                for (Object c : Object.findAll()) {
                    Client owner = Client.findById(c.getId_client()).get();
                    if (!owner.getFname().isEmpty() && !owner.getLname().isEmpty()) {
                        searchList.getItems().add(c.getId_object() + sp + c.getName() + sp + owner.getFname() + sp + owner.getLname());
                    } else {
                        searchList.getItems().add(c.getId_object() + sp + c.getName() + sp + owner.getName());
                    }
                }
                break;
            }
        }
    }

    public void refreshObject(ActionEvent actionEvent) {
        String sp = "   ";
//        listView.add
        if (searchField1.getText().isEmpty()) {
            initList(typeList.getSelectionModel().getSelectedIndex());
        } else {
            switch (typeList.getSelectionModel().getSelectedIndex()) {
                case 0: {
                    searchList.getItems().clear();
                    if (business.isSelected()) {
                        for (Client c : Client.findByName(searchField1.getText()).get()) {
                            searchList.getItems().add(c.getId_client() + sp + c.getName());
                        }
                    } else {
                        for (Client c : Client.findByNames(searchField1.getText()).get()) {
                            searchList.getItems().add(c.getId_client() + sp + c.getFname() + sp + c.getLname());
                        }
                    }
                    break;
                }
                case 1: {
                    searchList.getItems().clear();
                    for (Object c : Object.findByName(searchField1.getText()).get()) {
                        Client owner = Client.findById(c.getId_client()).get();
                        if (!owner.getFname().isEmpty() && !owner.getLname().isEmpty()) {
                            searchList.getItems().add(c.getId_object() + sp + c.getName() + sp + owner.getFname() + sp + owner.getLname());
                        } else {
                            searchList.getItems().add(c.getId_object() + sp + c.getName() + sp + owner.getName());
                        }
                    }
                    break;
                }
            }
        }
    }

    public void addCar(ActionEvent actionEvent) throws IOException {
        if (typeList.getSelectionModel().getSelectedItem().equals("by Owner") && !searchList.getSelectionModel().getSelectedItem().isEmpty()) {
            clientId = StringConverter.convert(searchList.getSelectionModel().getSelectedItem());
            windowLoader.load(new Stage(), "Application", "addObject");
        } else {
            return;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        windowLoader = new WindowLoader();
        typeList.getItems().add("by Owner");
        typeList.getItems().add("by Car");
        typeList.getSelectionModel().select(0);
        initList(typeList.getSelectionModel().getSelectedIndex());
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

    public void refreshOk(ActionEvent actionEvent) {
        String sp = "   ";
        if (typeList.getSelectionModel().getSelectedIndex() == 1) {
            Object car = Object.findById(StringConverter.convert(searchList.getSelectionModel().getSelectedItem())).get();
            Client owner = Client.findById(car.getId_client()).get();
            if (!owner.getFname().isEmpty() && !owner.getLname().isEmpty()) {
                objectInfo.setText("owner:\r" + owner.getFname() + sp + owner.getLname());
            } else {
                objectInfo.setText("owner:\r" + owner.getName());
            }
            ArrayList<Request> reqs = Request.findByIdObject(car.getId_object()).get();
            for (Request req : reqs) {
                String requestString = req.getId_request() + " " + car.getName() + " " + owner.getLname() + " " + req.getStatus();
                reqList.getItems().add(requestString);
            }
        } else if (typeList.getSelectionModel().getSelectedIndex() == 0 && !showClicked) {
            Client owner = Client.findById(StringConverter.convert(searchList.getSelectionModel().getSelectedItem())).get();
            if (!owner.getFname().isEmpty() && !owner.getLname().isEmpty()) {
                objectInfo.setText("owner:\r" + owner.getFname() + sp + owner.getLname());
            } else {
                objectInfo.setText("owner:\r" + owner.getName());
            }
        } else {
            Object car = Object.findById(StringConverter.convert(searchList.getSelectionModel().getSelectedItem())).get();
            Client owner = Client.findById(car.getId_client()).get();
            if (!owner.getFname().isEmpty() && !owner.getLname().isEmpty()) {
                objectInfo.setText("owner:\r" + owner.getFname() + sp + owner.getLname());
            } else {
                objectInfo.setText("owner:\r" + owner.getName());
            }
            ArrayList<Request> reqs = Request.findByIdObject(car.getId_object()).get();
            reqList.getItems().clear();
            for (Request req : reqs) {
                String requestString = req.getId_request() + " " + car.getName() + " " + owner.getLname() + " " + req.getStatus();
                reqList.getItems().add(requestString);
            }
        }
    }

    public void showCars(ActionEvent actionEvent) {
        int id = StringConverter.convert(searchList.getSelectionModel().getSelectedItem());
        searchList.getItems().clear();
        String sp = "   ";
        for (Object c : Object.findByOwner(id).get()) {
            Client owner = Client.findById(c.getId_client()).get();
            if (!owner.getFname().isEmpty() && !owner.getLname().isEmpty()) {
                searchList.getItems().add(c.getId_object() + sp + c.getName() + sp + owner.getFname() + sp + owner.getLname());
            } else {
                searchList.getItems().add(c.getId_object() + sp + c.getName() + sp + owner.getName());
            }
        }
        showClicked = true;
    }

    public void showActivities(ActionEvent actionEvent) {
        listView.getItems().clear();
        try {
            int id = Request.findById(StringConverter.convert(reqList.getSelectionModel().getSelectedItem())).get().getId_request();
            ArrayList<Activity> activities = Activity.findByRequest(id).get();
            for (Activity a : activities) {
                Personel p = Personel.findById(a.getId_personel()).get();
                listView.getItems().add(a.getId_activity() + " " + a.getDescription() + " "+a.getResult()+" " + p.getFirst_name() + " " + p.getLast_name() + " " + a.getStatus());
            }
        } catch (NoSuchElementException e) {
            return;
        }
    }
}
