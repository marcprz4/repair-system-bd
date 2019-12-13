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
 * @author Bartosz Prusak
 * @version 1.0
 */

public class Client {

    private int id_client;
    private String fname;
    private String lname;
    private String name;
    private String telephone;

    public Client(int id_client, String fname, String lname, String name, String telephone) {
        this.id_client = id_client;
        this.fname = fname;
        this.lname = lname;
        this.name = name;
        this.telephone = telephone;
    }

    public static Optional<Client> findById(int id) {
        String SQL = "SELECT id_client, fname, lname, name, telephone FROM public.\"Client\" WHERE id_client = ?;";
        Client client;
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                client = new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                return Optional.of(client);
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

    public static Optional<Client> findByNames(String name) {
        String SQL = "SELECT id_client, fname, lname, name, telephone FROM public.\"Client\" WHERE fname LIKE ? OR lname LIKE ?;";
        name += '%';
        Client client;
        try {
            if (name.isEmpty()) {
                throw new NullPointerException();
            }
//            String[] names = name.split("\\s+");
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            statement.setString(1, name);
            statement.setString(2, name);
//            if (names.length == 2) {
//                statement.setString(1, names[0]);
//                statement.setString(2, names[1]);
//            } else if (names.length==3){
//                statement.setString(1, names[0] + " " + names[1]);
//                statement.setString(2, names[2]);
//            }
//            else{
//                throw new IndexOutOfBoundsException();
//            }

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                client = new Client(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5));
                return Optional.of(client);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            AlertWindow alert = new AlertWindow("Error", "Name not found.", "Check your input.");
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            AlertWindow alert = new AlertWindow("Error", "Wrong name field.", "Check your input.");
        }
        return Optional.empty();
    }

    public static Optional<Client> findByName(String name) {
        String SQL = "SELECT id_client, fname, lname, name, telephone FROM public.\"Client\" WHERE name LIKE ?;";
        name += '%';
        Client client;
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                client = new Client(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5));
                return Optional.of(client);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Name not found.");
            alert.setContentText("Check your input.");
            alert.showAndWait();
        }
        return Optional.empty();
    }

    public static ArrayList<Client> findAll() {
        ArrayList<Client> list = new ArrayList<>();
        String SQL = "SELECT id_client, fname, lname, name, telephone FROM public.\"Client\";";
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                Client client = new Client(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5));

                list.add(client);
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

    public int getId_client() {
        return id_client;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public int insert() {
        String SQL = "INSERT INTO public.\"Client\"(fname, lname, name, telephone) VALUES (?, ?, ?, ?);";
        int id = 0;
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, this.getFname());
            statement.setString(2, this.getLname());
            statement.setString(3, this.getName());
            statement.setString(4, this.getTelephone());

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
