package com.bino.wilsonsonsapp.Controllers;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.bino.wilsonsonsapp.Models.ObjectIntro;
import com.bino.wilsonsonsapp.Models.ObjectQuestions;
import com.bino.wilsonsonsapp.Models.ObjectSkills;
import com.bino.wilsonsonsapp.Models.ObjectUser;

import java.util.ArrayList;
import java.util.List;

public class Consults {


    @SuppressLint("WrongConstant")
    public static ObjectUser ConsultUser(String sql) {
        ObjectUser objectUser = new ObjectUser();
        SQLiteDatabase conection;
        try {
            conection = SQLiteDatabase.openDatabase(Constants.DatabasePATH + Constants.DATABASE_NAME, null, 1);
            if (conection.isOpen()) {

                Cursor cursor = conection.rawQuery(sql, null);
                int Colun_key = cursor.getColumnIndex("firebase_key");
                int Colun_name = cursor.getColumnIndex("name");
                int Colun_cargo = cursor.getColumnIndex("cargo");
                int Colun_datenascimento = cursor.getColumnIndex("date_nascimento");
                int Colun_photo = cursor.getColumnIndex("photo");

                cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    do {
                        objectUser.setKey(cursor.getString(Colun_key));
                        objectUser.setName(cursor.getString(Colun_name));
                        objectUser.setCargo(cursor.getString(Colun_cargo));
                        objectUser.setDatenascimento(cursor.getString(Colun_datenascimento));
                        objectUser.setPhoto(cursor.getString(Colun_photo));
                        cursor.moveToNext();
                    } while (!cursor.isAfterLast());
                }
                cursor.close();
                conection.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return objectUser;
    }


    @SuppressLint("WrongConstant")
    public static ObjectSkills ConsultSkills(String sql) {
        ObjectSkills objectSkills = new ObjectSkills();
        SQLiteDatabase conection;
        try {
            conection = SQLiteDatabase.openDatabase(Constants.DatabasePATH + Constants.DATABASE_NAME, null, 1);
            if (conection.isOpen()) {

                Cursor cursor = conection.rawQuery(sql, null);
                int Colun_id = cursor.getColumnIndex("id");
                int Colun_name = cursor.getColumnIndex("name");

                cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    do {
                        objectSkills.setId(cursor.getInt(Colun_id));
                        objectSkills.setName(cursor.getString(Colun_name));
                        cursor.moveToNext();
                    } while (!cursor.isAfterLast());
                }
                cursor.close();
                conection.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return objectSkills;
    }



    @SuppressLint("WrongConstant")
    public static List<ObjectQuestions> ConsultQuestions(String sql) {
        SQLiteDatabase conection;
        List<ObjectQuestions> objectQuestionsArrayList = new ArrayList<>();
        try {
            conection = SQLiteDatabase.openDatabase(Constants.DatabasePATH + Constants.DATABASE_NAME, null, 1);
            if (conection.isOpen()) {

                Cursor cursor = conection.rawQuery(sql, null);
                int Colun_id = cursor.getColumnIndex("id");
                int Colun_id_skills = cursor.getColumnIndex("id_skills");
                int Colun_type = cursor.getColumnIndex("type");
                int Colun_id_intro = cursor.getColumnIndex("id_intro");
                int Colun_multiplaa = cursor.getColumnIndex("multiplaa");
                int Colun_multiplab = cursor.getColumnIndex("multiplab");
                int Colun_multiplac = cursor.getColumnIndex("multiplac");
                int Colun_multiplad = cursor.getColumnIndex("multiplad");
                int Colun_multiplae = cursor.getColumnIndex("multiplae");
                int Colun_alternativacorreta = cursor.getColumnIndex("alternativacorreta");
                int Colun_imagem = cursor.getColumnIndex("imagem");
                int Colun_acertos = cursor.getColumnIndex("acertos");
                int Colun_erros = cursor.getColumnIndex("erros");
                int Colun_itemclicavel1 = cursor.getColumnIndex("itemclicavel1");
                int Colun_item1X = cursor.getColumnIndex("item1X");
                int Colun_item1Y = cursor.getColumnIndex("item1Y");
                int Colun_itemclicavel2 = cursor.getColumnIndex("itemclicavel2");
                int Colun_item2X = cursor.getColumnIndex("item2X");
                int Colun_item2Y = cursor.getColumnIndex("item2Y");
                int Colun_pontos = cursor.getColumnIndex("pontos");
                int Colun_totalpontos = cursor.getColumnIndex("totalpontos");
                cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    do {
                        ObjectQuestions objectQuestions = new ObjectQuestions();
                        objectQuestions.setId(cursor.getInt(Colun_id));
                        objectQuestions.setId_skills(cursor.getInt(Colun_id_skills));
                        objectQuestions.setType(cursor.getInt(Colun_type));
                        objectQuestions.setId_intro(cursor.getInt(Colun_id_intro));
                        objectQuestions.setMultiplaa(cursor.getString(Colun_multiplaa));
                        objectQuestions.setMultiplab(cursor.getString(Colun_multiplab));
                        objectQuestions.setMultiplac(cursor.getString(Colun_multiplac));
                        objectQuestions.setMultiplad(cursor.getString(Colun_multiplad));
                        objectQuestions.setMultiplae(cursor.getString(Colun_multiplae));
                        objectQuestions.setAlternativacorreta(cursor.getString(Colun_alternativacorreta));
                        objectQuestions.setImagem(cursor.getString(Colun_imagem));
                        objectQuestions.setAcertos(cursor.getString(Colun_acertos));
                        objectQuestions.setErros(cursor.getString(Colun_erros));
                        objectQuestions.setItemclicavel1(cursor.getString(Colun_itemclicavel1));
                        objectQuestions.setItem1X(cursor.getString(Colun_item1X));
                        objectQuestions.setItem1Y(cursor.getString(Colun_item1Y));
                        objectQuestions.setItemclicavel2(cursor.getString(Colun_itemclicavel2));
                        objectQuestions.setItem2X(cursor.getString(Colun_item2X));
                        objectQuestions.setItem2Y(cursor.getString(Colun_item2Y));
                        objectQuestions.setPontos(cursor.getInt(Colun_pontos));
                        objectQuestions.setTotalpontos(cursor.getInt(Colun_totalpontos));
                        objectQuestionsArrayList.add(objectQuestions);
                        cursor.moveToNext();
                    } while (!cursor.isAfterLast());
                }
                cursor.close();
                conection.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return objectQuestionsArrayList;
    }


/*    public static List<ObjectIntro> ConsultIntro(String sql) {
        List<ObjectIntro> objectQuestionsArrayList = new ArrayList<>();
        SQLiteDatabase conection;
        try {
            conection = SQLiteDatabase.openDatabase(Constants.DatabasePATH + Constants.DATABASE_NAME, null, 1);
            if (conection.isOpen()) {

                Cursor cursor = conection.rawQuery(sql, null);
                int Colun_aaa = cursor.getColumnIndex("aaaa");
                cursor.moveToFirst();
                if (cursor.getCount() > 0) {

                    do {
                        ObjectQuestions objectQuestions = new ObjectQuestions();
                        objectQuestions.setId(cursor.getInt(Colun_aaa));
                        objectQuestionsArrayList.add(objectQuestions);
                        cursor.moveToNext();
                    } while (!cursor.isAfterLast());
                }
                cursor.close();
                conection.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return objectQuestionsArrayList;
    }*/

    @SuppressLint("WrongConstant")
    public static void ExecSql(String sql) {
        SQLiteDatabase conection;
        conection = SQLiteDatabase.openDatabase(Constants.DatabasePATH + Constants.DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        conection.execSQL(sql);
        conection.close();
    }
}
