package com.bino.wilsonsonsapp.Models;

import com.bino.wilsonsonsapp.Controllers.Consults;

import java.util.List;

public class ConsultsModel {

    public ObjectQuestions SelectQuestionPerId(int id) {
        ObjectQuestions objectQuestions = Consults.ConsultQuestions("Select * from questions where id = " + id + ";").get(0);
        return objectQuestions;
    }

    public ObjectUser SelectUser() {
        ObjectUser objectUser = Consults.ConsultUser("Select * from user;");
        return objectUser;
    }

    public ObjectStatusUser SelectPoints() {

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
