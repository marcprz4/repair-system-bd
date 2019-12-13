package com.bd.repairs.View;

import javafx.scene.control.Alert;

public class AlertWindow {
    Alert alert;

    public AlertWindow(String title, String header, String content) {
        this.alert = new Alert(Alert.AlertType.ERROR);
        this.alert.setTitle(title);
        this.alert.setHeaderText(header);
        this.alert.setContentText(content);
        this.alert.showAndWait();
    }
}
