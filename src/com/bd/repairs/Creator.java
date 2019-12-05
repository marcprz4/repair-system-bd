package com.bd.repairs;

import com.bd.repairs.Model.Client;
import com.bd.repairs.Model.Object;
import com.bd.repairs.Model.PasswordAuthentication;
import com.bd.repairs.Model.Personel;

public class Creator {
    public static void createPersonel(String pass,String fname, String lname, String role, String username, boolean active){
        PasswordAuthentication passwordAuthentication=new PasswordAuthentication();
        String password=passwordAuthentication.hash(pass.toCharArray());
        Personel personel=new Personel(0, fname, lname, role, username,password, active);
        personel.insert();
    }
    /*INSERT INTO public."Client"(
	id_client, fname, lname, name, telephone)
	VALUES (?, ?, ?, ?, ?);*/
    public static void createClient(String fname, String lname, String name, String tel){
        Client client=new Client(0,fname,lname,name,tel);
        client.insert();
    }
//id_object, name, id_client, id_type)
    public static void createObject(String name, int id_client, String type){
        Object object=new Object(0,name,id_client,type);
        object.insert();
    }
}
