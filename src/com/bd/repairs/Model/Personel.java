package com.bd.repairs.Model;

import com.bd.repairs.Main;

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
    int id_pers;
    String fname;
    String lname;
    String role;
    String username;
    String password;

    public Personel(int id_pers, String fname, String lname, String role, String username, String password) {
        this.id_pers = id_pers;
        this.fname = fname;
        this.lname = lname;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public int getId_pers() {
        return id_pers;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
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

    public static Optional<Personel> findId(String username) {
        String SQL = "SELECT id_pers, fname, lname, role, username, password FROM public.\"Personel\" WHERE username=?;";
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
                        rs.getString(6));
                return Optional.of(person);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insertPersonel() {
        String SQL = "INSERT INTO public.\"Personel\"(id_pers, fname, lname, role, username, password) VALUES (?, ?, ?, ?, ?, ?);";
        int id = 0;
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, this.getId_pers());
            statement.setString(2, this.getFname());
            statement.setString(3, this.getLname());
            statement.setString(4, this.getRole());
            statement.setString(5, this.getUsername());
            statement.setString(6, this.getPassword());
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
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
