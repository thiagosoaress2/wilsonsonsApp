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
                int Colun_date_certificado = cursor.getColumnIndex("date_certificado");
                int Colun_ultimo_certificado = cursor.getColumnIndex("ultimo_certificado");
                int Colun_photo = cursor.getColumnIndex("photo");

                cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    do {
                        objectUser.setKey(cursor.getString(Colun_key));
                        objectUser.setName(cursor.getString(Colun_name));
                        objectUser.setCargo(cursor.getString(Colun_cargo));
                        objectUser.setDatenascimento(cursor.getString(Colun_datenascimento));
                        objectUser.setDate_certificado(cursor.getString(Colun_date_certificado));
                        objectUser.setUltimo_curso(cursor.getString(Colun_ultimo_certificado));
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
                int Colun_itemclicavel3 = cursor.getColumnIndex("itemclicavel3");
                int Colun_item3X = cursor.getColumnIndex("item3X");
                int Colun_item3Y = cursor.getColumnIndex("item3Y");
                int Colun_itemclicavel4 = cursor.getColumnIndex("itemclicavel4");
                int Colun_item4X = cursor.getColumnIndex("item4X");
                int Colun_item4Y = cursor.getColumnIndex("item4Y");
                int Colun_itemclicavel5 = cursor.getColumnIndex("itemclicavel5");
                int Colun_item5X = cursor.getColumnIndex("item5X");
                int Colun_item5Y = cursor.getColumnIndex("item5Y");
                int Colun_movie = cursor.getColumnIndex("movie");
                int Colun_movie_sugest = cursor.getColumnIndex("movie_sugest");
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
                        objectQuestions.setAcertos(cursor.getInt(Colun_acertos));
                        objectQuestions.setErros(cursor.getInt(Colun_erros));
                        objectQuestions.setItemClicavel1(cursor.getString(Colun_itemclicavel1));
                        objectQuestions.setItem1X(cursor.getString(Colun_item1X));
                        objectQuestions.setItem1Y(cursor.getString(Colun_item1Y));
                        objectQuestions.setItemClicavel2(cursor.getString(Colun_itemclicavel2));
                        objectQuestions.setItem2X(cursor.getString(Colun_item2X));
                        objectQuestions.setItem2Y(cursor.getString(Colun_item2Y));
                        objectQuestions.setItemClicavel3(cursor.getString(Colun_itemclicavel3));
                        objectQuestions.setItem3X(cursor.getString(Colun_item3X));
                        objectQuestions.setItem3Y(cursor.getString(Colun_item3Y));
                        objectQuestions.setItemClicavel4(cursor.getString(Colun_itemclicavel4));
                        objectQuestions.setItem4X(cursor.getString(Colun_item4X));
                        objectQuestions.setItem4Y(cursor.getString(Colun_item4Y));
                        objectQuestions.setItemClicavel5(cursor.getString(Colun_itemclicavel5));
                        objectQuestions.setItem5X(cursor.getString(Colun_item5X));
                        objectQuestions.setItem5Y(cursor.getString(Colun_item5Y));
                        objectQuestions.setMovie(cursor.getString(Colun_movie));
                        objectQuestions.setMoviesSugest(cursor.getString(Colun_movie_sugest));
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


    public static List<ObjectIntro> ConsultIntro(String sql) {
        List<ObjectIntro> objectIntroArrayList = new ArrayList<>();
        SQLiteDatabase conection;
        try {
            conection = SQLiteDatabase.openDatabase(Constants.DatabasePATH + Constants.DATABASE_NAME, null, 1);
            if (conection.isOpen()) {

                Cursor cursor = conection.rawQuery(sql, null);
                int Colun_id_intro = cursor.getColumnIndex("id_intro");
                int Colun_title = cursor.getColumnIndex("title");
                int Colun_texto = cursor.getColumnIndex("texto");
                int Colun_img = cursor.getColumnIndex("img");
                int Colun_ordem = cursor.getColumnIndex("ordem");
                cursor.moveToFirst();
                if (cursor.getCount() > 0) {

                    do {
                        ObjectIntro objectIntro = new ObjectIntro();
                        objectIntro.setId(cursor.getInt(Colun_id_intro));
                        objectIntro.setTitle(cursor.getString(Colun_title));
                        objectIntro.setText(cursor.getString(Colun_texto));
                        objectIntro.setImg(cursor.getInt(Colun_img));
                        objectIntro.setOrdem(cursor.getInt(Colun_ordem));
                        objectIntroArrayList.add(objectIntro);
                        cursor.moveToNext();
                    } while (!cursor.isAfterLast());
                }
                cursor.close();
                conection.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return objectIntroArrayList;
    }

    @SuppressLint("WrongConstant")
    public static void ExecSql(String sql) {
        SQLiteDatabase conection;
        conection = SQLiteDatabase.openDatabase(Constants.DatabasePATH + Constants.DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        conection.execSQL(sql);
        conection.close();
    }
}
