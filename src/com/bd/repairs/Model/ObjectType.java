package com.bd.repairs.Model;

import com.bd.repairs.Main;
import com.bd.repairs.View.AlertWindow;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

public class ObjectType {
    String shortcut;
    String fullname;

    public ObjectType(String shortcut, String fullname) {
        this.shortcut = shortcut;
        this.fullname = fullname;
    }

    public String getShortcut() {
        return shortcut;
    }

    public String getFullname() {
        return fullname;
    }

    public static ArrayList<ObjectType> findAll() {
        ArrayList<ObjectType> list = new ArrayList<>();
        String SQL = "SELECT type_shortcut, type_fullname FROM public.\"Object_type\";";
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ObjectType object = new ObjectType(rs.getString(1),
                        rs.getString(2));
                list.add(object);
            }
            return list;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Object type not found.");
            alert.setContentText("Check your input.");
            alert.showAndWait();
        }
        return null;
    }

    public int insert() {
        String SQL = "INSERT INTO public.\"Object_type\"(type_shortcut, type_fullname) VALUES (?, ?);";
        int id = 0;
        try {

            PreparedStatement statement = Main.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, this.getShortcut().toUpperCase());
            statement.setString(2, this.getFullname().toUpperCase());
            int affectedRows = statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }
}
