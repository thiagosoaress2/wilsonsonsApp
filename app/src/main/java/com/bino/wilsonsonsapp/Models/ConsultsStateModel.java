package com.bino.wilsonsonsapp.Models;

import com.bino.wilsonsonsapp.Controllers.Consults;

import java.util.List;

public class ConsultsStateModel {

    public static List<ObjectState> selectAllState() {
        return Consults.ConsultState("SELECT * FROM state;");
    }

    public static ObjectState selectStatePerId(int id) {
        return Consults.ConsultState("SELECT * FROM state WHERE id = "+id+";").get(0);
    }
}
