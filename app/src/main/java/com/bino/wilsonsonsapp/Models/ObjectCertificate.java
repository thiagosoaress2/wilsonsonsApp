package com.bino.wilsonsonsapp.Models;

public class ObjectCertificate {

    private String firebase_key_user;
    private int id;
    private String name;
    private String validade;

    public String getFirebase_key_user() {
        return firebase_key_user;
    }

    public void setFirebase_key_user(String firebase_key_user) {
        this.firebase_key_user = firebase_key_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }
}
