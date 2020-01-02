package com.bd.repairs.Model;

//public class ActDic {

import com.bd.repairs.Main;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author Paweł Nieć
 * @version 1.0
 */
public class ActDic {
    private String actdic_shortcut;
    private String actdic_fullname;

    public ActDic(String actdic_shortcut, String actdic_fullname) {
        this.actdic_shortcut = actdic_shortcut;
        this.actdic_fullname = actdic_fullname;
    }

    public static Optional<ActDic> find(String name) {
        String SQL = "SELECT actdic_shortcut, actdic_fullname FROM public.\"Act_dict\" WHERE actdic_shortcut=?;";
        ActDic actDic;
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                actDic = new ActDic(rs.getString(1), rs.getString(2));
                return Optional.of(actDic);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Id of client not found.");
            alert.setContentText("Check your input.");
            alert.showAndWait();
        }
        return Optional.empty();
    }

    public static Optional<ArrayList<ActDic>> findAll() {
        ArrayList<ActDic> list = new ArrayList<>();
        String SQL = "SELECT actdic_shortcut, actdic_fullname FROM public.\"Act_dict\";";
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ActDic act = new ActDic(rs.getString(1),
                        rs.getString(2));
                list.add(act);
            }
            return Optional.of(list);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Activity_dic not found.");
            alert.setContentText("Check your input.");
            alert.showAndWait();
            System.out.print(e.getMessage());
        }
        return Optional.empty();
    }

    public String getActdic_shortcut() {
        return actdic_shortcut;
    }

    public String getActdic_fullname() {
        return actdic_fullname;
    }

    public int insert() {
        String SQL = "INSERT INTO public.\"Act_dict\"(actdic_shortcut, actdic_fullname) VALUES (?, ?);";
        int affectedRows = 0;
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, this.getActdic_shortcut().toUpperCase());
            statement.setString(2, this.getActdic_fullname().toUpperCase());
            affectedRows = statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedRows;
    }
}