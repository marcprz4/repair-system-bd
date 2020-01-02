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

/**
 * @author Paweł Nieć, Daniel Sajdak
 * @version 1.0
 */
public class Object {
    private int id_object;
    private String name;
    private int id_client;
    private String id_type;

    public Object(int id_object, String name, int id_client, String id_type) {
        this.id_object = id_object;
        this.name = name;
        this.id_client = id_client;
        this.id_type = id_type;
    }

    public static Optional<ArrayList<Object>> findByOwner(int id) {
        String SQL = "SELECT id_object, name, id_client, id_type FROM public.\"Object\" WHERE id_client = ?;";
        Object object;
        ArrayList<Object> objects = new ArrayList<>();
        try {
            if (id == 0) {
                throw new NullPointerException();
            }
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                object = new Object(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4));
                objects.add(object);
            }
            return Optional.of(objects);
        } catch (SQLException e) {
            AlertWindow alert = new AlertWindow("Error", "Name not found.", "Check your input.");
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            AlertWindow alert = new AlertWindow("Error", "Wrong name field.", "Check your input.");
        }
        return Optional.empty();
    }

    public static Optional<ArrayList<Object>> findByName(String name) {
        String SQL = "SELECT id_object, name, id_client, id_type FROM public.\"Object\" WHERE name LIKE ?;";
        Object object;
        ArrayList<Object> objects = new ArrayList<>();
        try {
            if (name.isEmpty()) {
                throw new NullPointerException();
            }
            name = name.toUpperCase();
            name = name + '%';
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            statement.setString(1, name);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                object = new Object(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4));
                objects.add(object);
            }
            return Optional.of(objects);
        } catch (SQLException e) {
            AlertWindow alert = new AlertWindow("Error", "Name not found.", "Check your input.");
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            AlertWindow alert = new AlertWindow("Error", "Wrong name field.", "Check your input.");
        }
        return Optional.empty();
    }

    public static Optional<Object> findById(int id) {
        String SQL = "SELECT id_object, name, id_client, id_type FROM public.\"Object\" WHERE id_object = ?;";
        Object object;
        try {
            if (id == 0) {
                throw new NullPointerException();
            }
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                object = new Object(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4));
            } else {
                object = null;
            }
            return Optional.of(object);
        } catch (SQLException e) {
            AlertWindow alert = new AlertWindow("Error", "Name not found.", "Check your input.");
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            AlertWindow alert = new AlertWindow("Error", "Wrong name field.", "Check your input.");
        }
        return Optional.empty();
    }

    public static ArrayList<Object> findAll() {
        ArrayList<Object> list = new ArrayList<>();
        String SQL = "SELECT id_object, name, id_client, id_type FROM public.\"Object\";";
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Object object = new Object(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4));
                list.add(object);
            }
            return list;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Name not found.");
            alert.setContentText("Check your input.");
            alert.showAndWait();
        }
        return null;
    }

    public int getId_object() {
        return id_object;
    }

    public String getName() {
        return name;
    }

    public int getId_client() {
        return id_client;
    }

    public String getId_type() {
        return id_type;
    }

    public int insert() {
        String SQL = "INSERT INTO public.\"Object\"(name, id_client, id_type)VALUES (?, ?, ?);";
        int id = 0;
        try {

            PreparedStatement statement = Main.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, this.getName().toUpperCase());
            statement.setInt(2, this.getId_client());
            statement.setString(3, this.getId_type().toUpperCase());
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    } else
                        throw new SQLException();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

}