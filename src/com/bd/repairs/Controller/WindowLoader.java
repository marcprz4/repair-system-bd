package com.bd.repairs.Controller;

import com.bd.repairs.Model.Personel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowLoader {
    public WindowLoader() {    }

    public void load(Stage stage, String name, String filename) throws IOException {
        String path="../View/"+filename+".fxml";
        Parent parent = FXMLLoader.load(getClass().getResource(path));
        stage.setTitle(name);
        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        stage.show();
    }
}
