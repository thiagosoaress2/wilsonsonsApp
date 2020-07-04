package com.bino.wilsonsonsapp.Models;

import com.bino.wilsonsonsapp.Controllers.Consults;

import java.util.List;

public class ConsultsOccupationModel {

    public static ObjectOccupation selectOccupationPerId(int id) {
        return Consults.ConsultOccupation("SELECT * FROM occupation WHERE id = "+id+";").get(0);
    }

    public static void insertOccupation( String name) {
        Consults.ExecSql("INSERT INTO occupation (name) VALUES ('"+name+"');");
    }

    public static void updateOccupation(int id, String name) {
        Consults.ExecSql("UPDATE occupation SET name ='"+name+"' WHERE id = "+id+";");
    }

    public static void deleteOccupation(int id) {
        Consults.ExecSql("DELETE FROM occupation WHERE id = "+id+";");
    }
}
