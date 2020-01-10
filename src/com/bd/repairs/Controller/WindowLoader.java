package com.bd.repairs.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class WindowLoader {
    public WindowLoader() {
    }

    public void load(Window owner, Stage stage, String name, String filename) throws IOException {
        String path = "../View/" + filename + ".fxml";
        Parent parent = FXMLLoader.load(getClass().getResource(path));
        /*stage.initOwner(btn1.getScene().getWindow());
        stage.setScene(new Scene((Parent) loader.load()));*/
        stage.initOwner(owner);
        stage.setTitle(name);
        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        stage.showAndWait();
    }
}
