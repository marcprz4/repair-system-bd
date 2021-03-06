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
 * @author Marcin Przybylski, Bartosz Prusak
 * @version 1.0
 */
public class Personel {
    private int id_personel;
    private String first_name;
    private String last_name;
    private String role;
    private String username;
    private String password;
    private boolean active;

    public Personel(int id_personel, String first_name, String last_name, String role, String username, String password, boolean active) {
        this.id_personel = id_personel;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = role;
        this.username = username;
        this.password = password;
        this.active = active;
    }

    public Personel(Personel other) {
        this.id_personel = other.getId_personel();
        this.first_name = other.getFirst_name();
        this.last_name = other.getLast_name();
        this.role = other.getRole();
        this.username = other.getUsername();
        this.password = other.getPassword();
        this.active = other.isActive();
    }

    public static Optional<ArrayList<Personel>> findByRole(String role) {
        String SQL = "SELECT id_personel, first_name, last_name, role, username, password, active FROM public.\"Personel\" WHERE role=?;";
        role = role.toUpperCase();
        ArrayList<Personel> people = new ArrayList<>();
        Personel person;
        try {
            if (role.isEmpty()) {
                throw new NullPointerException();
            }
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            statement.setString(1, role);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                person = new Personel(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getBoolean(7));
                people.add(person);
            }
            return Optional.of(people);
        } catch (SQLException e) {
            AlertWindow alert = new AlertWindow("Error", "Name not found.", "Check your input.");
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            AlertWindow alert = new AlertWindow("Error", "Wrong name field.", "Check your input.");
        }
        return Optional.empty();
    }

    public static Optional<ArrayList<Personel>> findByName(String name1) {
        String SQL = "SELECT id_personel, first_name, last_name, role, username, password, active FROM public.\"Personel\" WHERE first_name LIKE ? OR last_name LIKE ? ORDER BY id_personel;";
        name1 = name1.toUpperCase();
        String name = "%"+name1+'%';
        ArrayList<Personel> people = new ArrayList<>();
        Personel person;
        try {
            if (name.isEmpty()) {
                throw new NullPointerException();
            }
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            statement.setString(1, name);
            statement.setString(2, name);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                person = new Personel(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getBoolean(7));
                people.add(person);
            }
            return Optional.of(people);
//            if (rs.next()) {
//
////                return Optional.of(person);
//            } else {
//                throw new SQLException();
//            }
        } catch (SQLException e) {
            AlertWindow alert = new AlertWindow("Error", "Name not found.", "Check your input.");
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            AlertWindow alert = new AlertWindow("Error", "Wrong name field.", "Check your input.");
        }
        return Optional.empty();
    }

    public static Optional<Personel> findByUsername(String username) {
        String SQL = "SELECT id_personel, first_name, last_name, role, username, password, active FROM public.\"Personel\" WHERE username = ?;";
        username = username.toUpperCase();
//        username+='%';
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Username not found.");
            alert.setContentText("Check your input.");
            alert.showAndWait();
        }
        return Optional.empty();
    }

    public static Optional<Personel> findById(int id) {
        String SQL = "SELECT id_personel, first_name, last_name, role, username, password, active FROM public.\"Personel\" WHERE id_personel = ?;";
        Personel person;
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            statement.setInt(1, id);
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Id not found.");
            alert.setContentText("Check your input.");
            alert.showAndWait();
        }
        return Optional.empty();
    }

    public static ArrayList<Personel> findAll() {
        ArrayList<Personel> list = new ArrayList<>();
        String SQL = "SELECT id_personel, first_name, last_name, role, username, password, active FROM public.\"Personel\" ORDER BY id_personel;";
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Personel person = new Personel(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getBoolean(7));
                list.add(person);
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

    public void changeActive() {
        active = !active;
    }

    public int updateWoutPass() {
        String SQL = "UPDATE public.\"Personel\" SET first_name=?, last_name=?, role=?, active=?, username=? WHERE id_personel=?;";
        int affectedRows = 0;
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, this.getFirst_name().toUpperCase());
            statement.setString(2, this.getLast_name().toUpperCase());
            statement.setString(3, this.getRole());
            statement.setBoolean(4, this.isActive());
            statement.setString(5, this.getUsername());
            statement.setInt(6, this.getId_personel());
            affectedRows = statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedRows;
    }

    public int update() {
        String SQL = "UPDATE public.\"Personel\" SET first_name=?, last_name=?, role=?, active=?, username=?, password=? WHERE id_personel=?;";
        int affectedRows = 0;
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, this.getFirst_name().toUpperCase());
            statement.setString(2, this.getLast_name().toUpperCase());
            statement.setString(3, this.getRole());
            statement.setBoolean(4, this.isActive());
            statement.setString(5, this.getUsername());
            statement.setString(6, this.getPassword());
            statement.setInt(7, this.getId_personel());
            affectedRows = statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedRows;
    }

    public int insert() {
        String SQL = "INSERT INTO public.\"Personel\"(first_name, last_name, role, username, password, active) VALUES (?, ?, ?, ?, ?, ?);";
        int affectedRows = 0;
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, this.getFirst_name().toUpperCase());
            statement.setString(2, this.getLast_name().toUpperCase());
            statement.setString(3, this.getRole().toUpperCase());
            statement.setString(4, this.getUsername().toUpperCase());
            statement.setString(5, this.getPassword());
            statement.setBoolean(6, this.isActive());
            affectedRows = statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedRows;
    }
}
