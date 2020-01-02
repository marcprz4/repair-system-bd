package com.bd.repairs.Model;

import com.bd.repairs.Main;
import com.bd.repairs.View.AlertWindow;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;


public class Activity {
    private int id_activity;
    private int seq_number;
    private String description;
    private String result;
    private String status;
    private Date date_start;
    private Date date_end;
    private int id_request;
    private int id_personel;
    private String actdic_shortcut;


    public Activity(int id_activity, int seq_number, String description, String result, String status, Date date_start, Date date_end, int id_request, int id_personel, String actdic_shortcut) {
        this.id_activity = id_activity;
        this.seq_number = seq_number;
        this.description = description;
        this.result = result;
        this.status = status;
        this.date_start = date_start;
        this.date_end = date_end;
        this.id_request = id_request;
        this.id_personel = id_personel;
        this.actdic_shortcut = actdic_shortcut;
    }

    public static Optional<ArrayList<Activity>> findByRequest(int req) {
        String SQL = "SELECT id_activity, seq_number, description, result, status, date_start, date_end, id_request, id_personel, actdic_shortcut  FROM public.\"Activity\"  WHERE id_request=? ;";
        ArrayList<Activity> activities=new ArrayList<>();
        Activity activ;
        try {
            if (req==0) {
                throw new NullPointerException();
            }
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            statement.setInt(1,req);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                activ = new Activity(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getDate(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getString(10));

                return Optional.of(activities);
            }
        } catch (SQLException e) {
            AlertWindow alert = new AlertWindow("Error", "Name not found.", "Check your input.");
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            AlertWindow alert = new AlertWindow("Error", "Wrong name field.", "Check your input.");
        }
        return Optional.empty();
    }

    public static Optional<Activity> findByName(String actdic_shortcut) {
        String SQL = "SELECT id_activity, seq_number, description, result, status, date_start, date_end, id_request, id_personel, actdic_shortcut  FROM public.\"Activity\"  WHERE actdic_shortcut=? ;";
        Activity activ;
        try {
            if (actdic_shortcut.isEmpty()) {
                throw new NullPointerException();
            }
            String[] names = actdic_shortcut.split("\\s+");
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            if (names.length == 2) {
                statement.setString(1, names[0]);
            } else if (names.length == 3) {
                statement.setString(1, names[0] + " " + names[1]);
            } else {
                throw new IndexOutOfBoundsException();
            }

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                activ = new Activity(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getDate(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getString(10));
                return Optional.of(activ);
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

    public static ArrayList<Activity> findAll() {
        ArrayList<Activity> list = new ArrayList<>();
        String SQL = "SELECT id_activity, seq_number, description, result, status, date_start, date_end, id_request, id_personel, actdic_shortcut  FROM public.\"Activity\";";
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                Activity activ = new Activity(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getDate(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getString(10));

                list.add(activ);
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

    public int getId_activity() {
        return id_activity;
    }

    public int getSeq_number() {
        return seq_number;
    }

    public String getDescription() {
        return description;
    }

    public String getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public Date getDate_start() {
        return date_start;
    }

    public Date getDate_end() {
        return date_end;
    }

    public int getId_request() {
        return id_request;
    }

    public int getId_personel() {
        return id_personel;
    }

    public String getActdic_shortcut() {
        return actdic_shortcut;
    }

    public int insert() {
        String SQL = "INSERT INTO public.\"Activity\"(id_activity, seq_number, description, result, status,date_start,date_end, id_request,id_personel,actdic_shortcut) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        int id = 0;
        try {
            PreparedStatement statement = Main.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, this.getId_activity());
            statement.setInt(2, this.getSeq_number());
            statement.setString(3, this.getDescription());
            statement.setString(4, this.getResult());
            statement.setString(5, this.getStatus());
            statement.setDate(6, this.getDate_start());
            statement.setDate(7, this.getDate_end());
            statement.setInt(8, this.getId_request());
            statement.setInt(9, this.getId_personel());
            statement.setString(10, this.getActdic_shortcut());

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