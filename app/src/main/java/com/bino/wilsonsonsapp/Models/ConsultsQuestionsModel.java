package com.bino.wilsonsonsapp.Models;

import android.content.Context;

import com.bino.wilsonsonsapp.Controllers.Consults;

import java.util.List;

public class ConsultsQuestionsModel {

    public static ObjectQuestions selectQuestionPerId(Context context, int id) {
        ObjectQuestions objectQuestions = Consults.ConsultQuestions(context,"Select * from questions WHERE id = " + id + ";").get(0);
        return objectQuestions;
    }

    public static void somaQuestions1(Context context, boolean situation, int id) {
        ObjectQuestions objectQuestions = selectQuestionPerId(context, id);

        int error = objectQuestions.getErros();
        if(error == 0){ error = 1;}

        if(situation){
            Consults.ExecSql("UPDATE questions SET acertos = "+ objectQuestions.getAcertos()+1+ ", pontos = "+100/error +" WHERE id = "+id+";");
        }else{
            Consults.ExecSql("UPDATE questions SET erros = "+ objectQuestions.getErros()+1 +", pontos = "+100/(error+1) +" WHERE id = "+id+";");
        }
    }

    public static List<ObjectIntro> selectIntro(Context context, int id_intro){
       return Consults.ConsultIntro(context,"Select * from intro WHERE id_intro = "+id_intro+" order by ordem  ;");
    }

}
