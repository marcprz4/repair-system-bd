package com.bd.repairs.Model;

import com.bd.repairs.Main;
import com.bd.repairs.View.AlertWindow;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * @author Marcin Przybylski, Bartosz Prusak
 * @version 1.0
 */
public class Personel {
    int id_personel;
    String first_name;
    String last_name;
    String role;
    String username;
    String password;
    boolean active;

    public Personel(int id_personel, String first_name, String last_name, String role, String username, String password, boolean active) {
        this.id_personel = id_personel;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = role;
        this.username = username;
        this.password = password;
        this.active = active;
    }

    public int getId_personel() {
        return id_personel;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return active;
    }

    public int insert() {
        String SQL = "INSERT INTO public.\"Personel\"(id_personel, first_name, last_name, role, username, password, active) VALUES (?, ?, ?, ?, ?, ?, ?);";
        int id = 0;
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, this.getId_personel());
            statement.setString(2, this.getFirst_name());
            statement.setString(3, this.getLast_name());
            statement.setString(4, this.getRole());
            statement.setString(5, this.getUsername());
            statement.setString(6, this.getPassword());
            statement.setBoolean(7, this.isActive());
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                    else
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

    public static Optional<Personel> findByName(String name) {
        String SQL = "SELECT id_personel, first_name, last_name, role, username, password, active FROM public.\"Personel\" WHERE first_name=? AND last_name=?;";
        Personel person;
        try {
            if (name.isEmpty()) {
                throw new NullPointerException();
            }
            String[] names = name.split("\\s+");
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            if (names.length == 2) {
                statement.setString(1, names[0]);
                statement.setString(2, names[1]);
            } else if (names.length==3){
                statement.setString(1, names[0] + " " + names[1]);
                statement.setString(2, names[2]);
            }
            else{
                throw new IndexOutOfBoundsException();
            }

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                person = new Personel(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getBoolean(7));
                return Optional.of(person);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            AlertWindow alert=new AlertWindow("Error","Name not found.","Check your input.");
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            AlertWindow alert=new AlertWindow("Error","Wrong name field.","Check your input.");
        }
        return Optional.empty();
    }

    public static Optional<Personel> findByUsername(String username) {
        String SQL = "SELECT id_personel, first_name, last_name, role, username, password, active FROM public.\"Personel\" WHERE username=?;";
        Personel person;
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                person = new Personel(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getBoolean(7));
                return Optional.of(person);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Name not found.");
            alert.setContentText("Check your input.");
            alert.showAndWait();
        }
        return Optional.empty();
    }
}
