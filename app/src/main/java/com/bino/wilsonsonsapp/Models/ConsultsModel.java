package com.bino.wilsonsonsapp.Models;

import com.bino.wilsonsonsapp.Controllers.Consults;

import java.util.List;

public class ConsultsModel {

    public static ObjectQuestions selectQuestionPerId(int id) {
        ObjectQuestions objectQuestions = Consults.ConsultQuestions("Select * from questions where id = " + id + ";").get(0);
        return objectQuestions;
    }

    public static void somaQuestions1(boolean situation, int id) {
        ObjectQuestions objectQuestions = selectQuestionPerId(id);

        int error = objectQuestions.getErros();
        if(error == 0){ error = 1;}

        if(situation){
            Consults.ExecSql("UPDATE INTO questions SET acertos = "+ objectQuestions.getAcertos()+1+ ", points = "+100/error +" WHERE id = "+id+";");
        }else{
            Consults.ExecSql("UPDATE INTO questions SET erros = "+ objectQuestions.getErros()+1 +", points = "+100/(error+1) +" WHERE id = "+id+";");
        }
    }

    public static ObjectUser selectUser() {
        ObjectUser objectUser = Consults.ConsultUser("Select * from user;");
        return objectUser;
    }

    public static List<ObjectIntro> selectIntro(int id_intro){
       return Consults.ConsultIntro("Select * from intro order by ordem where id = "+id_intro+";");
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
