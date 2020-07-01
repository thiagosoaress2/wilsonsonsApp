package com.bino.wilsonsonsapp.Models;

import com.bino.wilsonsonsapp.Controllers.Consults;
import com.bino.wilsonsonsapp.Controllers.SetupDatabase;

import java.util.ArrayList;
import java.util.List;

public class ConsultsCertificateModel {

    public static List<ObjectCertificate> selectCertificatePerKey(String firebase_key_user) {
        return Consults.ConsultCertificate("SELECT * FROM certificate WHERE firebase_key_user = "+firebase_key_user+";");
    }

    public static List<ObjectCertificate> selectCertificatePerId(int id) {
        return Consults.ConsultCertificate("SELECT * FROM certificate WHERE id = "+id+";");
    }

    public static void insertCertificate(String firebase_key_user, String name, String validade) {
        Consults.ExecSql("INSERT INTO certificate (firebase_key_user, name, validade) VALUES ('"+firebase_key_user+"', '"+name+"', '"+validade+"');");
    }

    public static void updateCertificate(int id, String name, String validade) {
        Consults.ExecSql("UPDATE certificate SET name ='"+name+"', validade = '"+validade+"' WHERE id = "+id+";");
    }

    public static void deleteCertificate(int id) {
        Consults.ExecSql("DELETE FROM certificate WHERE id = "+id+";");
    }
}
