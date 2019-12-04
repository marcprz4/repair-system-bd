package com.bd.repairs.Model;

import com.bd.repairs.Main;
import com.bd.repairs.View.AlertWindow;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class Object {
    private int id_object;
    private String name;
    private int id_client;
    private int id_type;

    public Object(int id_object, String name,int id_client, int id_type){
        this.id_object = id_object;
        this.name = name;
        this.id_client = id_client;
        this.id_type = id_type;
    }

    public int getId_object() { return id_object; }

    public String getName() { return name; }

    public int getId_client() { return id_client; }

    public int getId_type() { return id_type; }

    public int insert(){
        String SQL = "INSERT INTO public.\"Object\"(id_object, name, id_client, id_type)VALUES (?, ?, ?, ?);";
        int id = 0;
        try {

            PreparedStatement statement = Main.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,this.getId_object());
            statement.setString(2,this.getName());
            statement.setInt(3,this.getId_client());
            statement.setInt(4,this.getId_type());
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

    public static Optional<Object> FindByName(String name){
        String SQL = "SELECT id_object, name, id_client, id_type FROM public.\"Object\"; WHERE name LIKE ?";
        Object object;
        try {
            if (name.isEmpty()){
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
                statement.setString(1, names[0]);
            }

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                object = new Object(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4));
                return Optional.of(object);
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


}