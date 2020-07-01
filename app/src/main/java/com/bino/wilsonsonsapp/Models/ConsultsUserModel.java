package com.bino.wilsonsonsapp.Models;

import com.bino.wilsonsonsapp.Controllers.Consults;

import java.util.List;

public class ConsultsUserModel {

    public static ObjectUser selectUser() {
        ObjectUser objectUser = Consults.ConsultUser("SELECT * FROM user;");
        return objectUser;
    }

    public static void insertUser(String firebase_key, String name, String number, String cargo, String date_nascimento, String photo) {
        Consults.ExecSql("INSERT INTO user (firebase_key, name, number, cargo, date_nascimento, photo) VALUES" +
                " ('"+firebase_key+"', '"+name+"', '"+number+"', '"+cargo+"', '"+date_nascimento+"', '"+photo+"');");
    }

    public static void updateUser(int id, String name, String number, String cargo, String date_nascimento, String photo) {
        Consults.ExecSql("UPDATE user SET name ='"+name+"', number = '"+number+"', cargo = '"+cargo+"', date_nascimento = '"+date_nascimento+"', photo = '"+photo+"';");
    }

    public static void deleteUser() {
        Consults.ExecSql("DELETE FROM user;");
    }

    public static ObjectStatusUser selectPoints() {

        ObjectStatusUser objectStatusUser = null;
        List<ObjectQuestions> objectQuestionsList = Consults.ConsultQuestions("Select * from questions where respondida = " + true + ";");
        for(int i = 0; i < objectQuestionsList.size(); i++){

           switch (objectQuestionsList.get(i).getId_skills()){
               case 1:{
                   objectStatusUser.setSkill1_points(objectStatusUser.getSkill1_points() + objectQuestionsList.get(i).getPontos());
                   objectStatusUser.setSkill1_total_points(objectStatusUser.getSkill1_total_points() + objectQuestionsList.get(i).getTotalpontos());
               }case 2:{
                   objectStatusUser.setSkill2_points(objectStatusUser.getSkill2_points() + objectQuestionsList.get(i).getPontos());
                   objectStatusUser.setSkill2_total_points(objectStatusUser.getSkill2_total_points() + objectQuestionsList.get(i).getTotalpontos());
               }case 3:{
                   objectStatusUser.setSkill3_points(objectStatusUser.getSkill3_points() + objectQuestionsList.get(i).getPontos());
                   objectStatusUser.setSkill3_total_points(objectStatusUser.getSkill3_total_points() + objectQuestionsList.get(i).getTotalpontos());
               }
           }
        }
        objectStatusUser.setPoints(objectStatusUser.getSkill1_points() + objectStatusUser.getSkill2_points() + objectStatusUser.getSkill3_points());
        objectStatusUser.setTotal_points(objectStatusUser.getSkill1_total_points() + objectStatusUser.getSkill2_total_points() + objectStatusUser.getSkill3_total_points());

        return objectStatusUser;
    }
}
