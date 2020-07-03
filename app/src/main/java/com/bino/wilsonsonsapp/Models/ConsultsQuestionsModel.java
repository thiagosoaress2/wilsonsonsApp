package com.bino.wilsonsonsapp.Models;

import android.content.Context;

import com.bino.wilsonsonsapp.Controllers.Consults;

import java.util.List;

public class ConsultsQuestionsModel {

    public static ObjectQuestions selectQuestionPerId( int id) {
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

    public static List<ObjectIntro> selectIntro(int id_intro){
       return Consults.ConsultIntro("Select * from intro order by ordem where id = "+id_intro+" ;");
    }

}
