package com.bd.repairs.Model;

import com.bd.repairs.Main;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author Paweł Nieć
 * @version 1.0
 */
public class Request {
    private int id_request;
    private String description;
    private String result;
    private String status;
    private Date date_start;
    private Date date_end;
    private int id_object;
    private int id_personel;

    public Request(int id_request, String description, String result, String status, Date date_start, Date date_end, int id_object, int id_personel) {
        this.id_request = id_request;
        this.description = description;
        this.result = result;
        this.status = status;
        this.date_start = date_start;
        this.date_end = date_end;
        this.id_object = id_object;
        this.id_personel = id_personel;
    }

    public static Optional<ArrayList<Request>> findByIdObject(int id) {
        String SQL = "SELECT id_request, description, result, status, date_start, date_end, id_object, id_personel FROM public.\"Request\" WHERE id_object = ? ORDER BY date_start, status;";
        Request req;
        ArrayList<Request> reqs = new ArrayList<>();
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                req = new Request(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getDate(6),
                        rs.getInt(7),
                        rs.getInt(8));
                reqs.add(req);
            }
            return Optional.of(reqs);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Id not found.");
            alert.setContentText("Check your input.");
            alert.showAndWait();
        }
        return Optional.empty();
    }

    public static Optional<ArrayList<Request>> findByIdPersonel(int id) {
        String SQL = "SELECT id_request, description, result, status, date_start, date_end, id_object, id_personel FROM public.\"Request\" WHERE id_personel = ? ORDER BY date_start, result;";
        Request req;
        ArrayList<Request> requests=new ArrayList<>();
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                req = new Request(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getDate(6),
                        rs.getInt(7),
                        rs.getInt(8));
                requests.add(req);
            }
            return Optional.of(requests);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Id not found.");
            alert.setContentText("Check your input.");
            alert.showAndWait();
        }
        return Optional.empty();
    }

    public static Optional<Request> findById(int id) {
        String SQL = "SELECT id_request, description, result, status, date_start, date_end, id_object, id_personel FROM public.\"Request\" WHERE id_request = ?;";
        Request req;
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                req = new Request(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getDate(6),
                        rs.getInt(7),
                        rs.getInt(8));
                return Optional.of(req);
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

    public static ArrayList<Request> findAll() {
        ArrayList<Request> list = new ArrayList<>();
        String SQL = "SELECT id_request, description, result, status, date_start, date_end, id_object, id_personel FROM public.\"Request\";";
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Request req = new Request(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getDate(6),
                        rs.getInt(7),
                        rs.getInt(8));
                list.add(req);
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

    public int getId_request() {
        return id_request;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public Date getDate_end() {
        return date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }

    public int getId_object() {
        return id_object;
    }

    public int update() {
        String SQL = "UPDATE public.\"Request\" SET description=?, result=?, status=?, date_start=?, date_end=?, id_object=?,id_personel=? WHERE id_request=?;";
        int id = 0;
        int affectedRows = 0;
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, this.getDescription().toUpperCase());
            statement.setString(2, this.getResult().toUpperCase());
            statement.setString(3, this.getStatus().toUpperCase());
            statement.setDate(4, this.getDate_start());
            statement.setDate(5, this.getDate_end());
            statement.setInt(6, this.getId_object());
            statement.setInt(7, this.getId_personel());
            statement.setInt(8, this.getId_request());

            affectedRows = statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedRows;
    }

    public int insert() {
        String SQL = "INSERT INTO public.\"Request\"(description, result, status, date_start, date_end, id_object, id_personel) VALUES (?, ?, ?, ?, ?, ?, ?);";
        int affectedRows = 0;
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, this.getDescription().toUpperCase());
            statement.setString(2, this.getResult().toUpperCase());
            statement.setString(3, this.getStatus().toUpperCase());
            statement.setDate(4, this.getDate_start());
            statement.setDate(5, this.getDate_end());
            statement.setInt(6, this.getId_object());
            statement.setInt(7, this.getId_personel());
            affectedRows = statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedRows;
    }
}