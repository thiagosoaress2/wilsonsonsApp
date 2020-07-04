package com.bino.wilsonsonsapp.Controllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.bino.wilsonsonsapp.Models.ObjectCertificate;
import com.bino.wilsonsonsapp.Models.ObjectIntro;
import com.bino.wilsonsonsapp.Models.ObjectOccupation;
import com.bino.wilsonsonsapp.Models.ObjectQuestions;
import com.bino.wilsonsonsapp.Models.ObjectSkills;
import com.bino.wilsonsonsapp.Models.ObjectState;
import com.bino.wilsonsonsapp.Models.ObjectUser;

import java.util.ArrayList;
import java.util.List;

public class Consults {

    @SuppressLint("WrongConstant")
    public static ObjectUser ConsultUser(String sql) {
        ObjectUser objectUser = new ObjectUser();
        try {
            SQLiteDatabase conection = SQLiteDatabase.openDatabase(Constants.DatabasePATH + Constants.DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            if (conection.isOpen()) {

                Cursor cursor = conection.rawQuery(sql, null);
                int Colun_key = cursor.getColumnIndex("firebase_key");
                int Colun_email = cursor.getColumnIndex("email");
                int Colun_name = cursor.getColumnIndex("name");
                int Colun_number = cursor.getColumnIndex("number");
                int Colun_cargo = cursor.getColumnIndex("occupation_id");
                int Colun_state = cursor.getColumnIndex("state_id");
                int Colun_datenascimento = cursor.getColumnIndex("date_nascimento");
                int Colun_photo = cursor.getColumnIndex("photo");

                cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    do {
                        objectUser.setKey(cursor.getString(Colun_key));
                        objectUser.setEmail(cursor.getString(Colun_email));
                        objectUser.setName(cursor.getString(Colun_name));
                        objectUser.setNumber(cursor.getString(Colun_number));
                        objectUser.setCargo(cursor.getInt(Colun_cargo));
                        objectUser.setState(cursor.getInt(Colun_state));
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
    public static List<ObjectCertificate> ConsultCertificate(String sql) {
        List<ObjectCertificate> objectCertificate = new ArrayList<>();
        try {
            SQLiteDatabase conection = SQLiteDatabase.openDatabase(Constants.DatabasePATH + Constants.DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);

            if (conection.isOpen()) {

                Cursor cursor = conection.rawQuery(sql, null);
                int Colun_key = cursor.getColumnIndex("firebase_key_user");
                int Colun_id = cursor.getColumnIndex("id");
                int Colun_name = cursor.getColumnIndex("name");
                int Colun_validade = cursor.getColumnIndex("validade");

                cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    do {
                        ObjectCertificate objectCertificate1 = new ObjectCertificate();
                        objectCertificate1.setFirebase_key_user(cursor.getString(Colun_key));
                        objectCertificate1.setId(cursor.getInt(Colun_id));
                        objectCertificate1.setName(cursor.getString(Colun_name));
                        objectCertificate1.setValidade(cursor.getString(Colun_validade));
                        objectCertificate.add(objectCertificate1);
                        cursor.moveToNext();
                    } while (!cursor.isAfterLast());
                }
                cursor.close();
                conection.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return objectCertificate;
    }


    @SuppressLint("WrongConstant")
    public static List<ObjectOccupation> ConsultOccupation(String sql) {
        List<ObjectOccupation> objectOccupations = new ArrayList<>();
        try {
            SQLiteDatabase conection = SQLiteDatabase.openDatabase(Constants.DatabasePATH + Constants.DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            if (conection.isOpen()) {

                Cursor cursor = conection.rawQuery(sql, null);
                int Colun_id = cursor.getColumnIndex("id");
                int Colun_name = cursor.getColumnIndex("name");

                cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    do {
                        ObjectOccupation objectOccupation = new ObjectOccupation();
                        objectOccupation.setId(cursor.getInt(Colun_id));
                        objectOccupation.setName(cursor.getString(Colun_name));
                        objectOccupations.add(objectOccupation);
                        cursor.moveToNext();
                    } while (!cursor.isAfterLast());
                }
                cursor.close();
                conection.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return objectOccupations;
    }

    @SuppressLint("WrongConstant")
    public static ObjectSkills ConsultSkills(String sql) {
        ObjectSkills objectSkills = new ObjectSkills();
        try {
            SQLiteDatabase conection = SQLiteDatabase.openDatabase(Constants.DatabasePATH + Constants.DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
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
    public static List<ObjectQuestions> ConsultQuestions( String sql) {

        List<ObjectQuestions> objectQuestionsArrayList = new ArrayList<>();
        try {
            SQLiteDatabase conection = SQLiteDatabase.openDatabase(Constants.DatabasePATH + Constants.DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
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
                int Colun_item1X = cursor.getColumnIndex("item1X");
                int Colun_item1Y = cursor.getColumnIndex("item1Y");
                int Colun_item2X = cursor.getColumnIndex("item2X");
                int Colun_item2Y = cursor.getColumnIndex("item2Y");
                int Colun_item3X = cursor.getColumnIndex("item3X");
                int Colun_item3Y = cursor.getColumnIndex("item3Y");
                int Colun_item4X = cursor.getColumnIndex("item4X");
                int Colun_item4Y = cursor.getColumnIndex("item4Y");
                int Colun_item5X = cursor.getColumnIndex("item5X");
                int Colun_item5Y = cursor.getColumnIndex("item5Y");
                int Colun_largura = cursor.getColumnIndex("largura");
                int Colun_altura = cursor.getColumnIndex("altura");
                int Colun_movie = cursor.getColumnIndex("movie");
                int Colun_movie_sugest = cursor.getColumnIndex("moviesugest");
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
                        objectQuestions.setImagem(cursor.getInt(Colun_imagem));
                        objectQuestions.setAcertos(cursor.getInt(Colun_acertos));
                        objectQuestions.setErros(cursor.getInt(Colun_erros));
                        objectQuestions.setItem1X(cursor.getString(Colun_item1X));
                        objectQuestions.setItem1Y(cursor.getString(Colun_item1Y));
                        objectQuestions.setItem2X(cursor.getString(Colun_item2X));
                        objectQuestions.setItem2Y(cursor.getString(Colun_item2Y));
                        objectQuestions.setItem3X(cursor.getString(Colun_item3X));
                        objectQuestions.setItem3Y(cursor.getString(Colun_item3Y));
                        objectQuestions.setItem4X(cursor.getString(Colun_item4X));
                        objectQuestions.setItem4Y(cursor.getString(Colun_item4Y));
                        objectQuestions.setItem5X(cursor.getString(Colun_item5X));
                        objectQuestions.setItem5Y(cursor.getString(Colun_item5Y));
                        objectQuestions.setLargura(cursor.getString(Colun_largura));
                        objectQuestions.setAltura(cursor.getString(Colun_altura));
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


    @SuppressLint("WrongConstant")
    public static List<ObjectIntro> ConsultIntro( String sql) {
        List<ObjectIntro> objectIntroArrayList = new ArrayList<>();
        try {
            SQLiteDatabase conection = SQLiteDatabase.openDatabase(Constants.DatabasePATH + Constants.DATABASE_NAME, null, 1);
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
    public static List<ObjectState> ConsultState(String sql) {
        List<ObjectState> objectStates = new ArrayList<>();
        try {
            SQLiteDatabase conection = SQLiteDatabase.openDatabase(Constants.DatabasePATH + Constants.DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            if (conection.isOpen()) {

                Cursor cursor = conection.rawQuery(sql, null);
                int Colun_id = cursor.getColumnIndex("id");
                int Colun_name = cursor.getColumnIndex("name");
                cursor.moveToFirst();
                if (cursor.getCount() > 0) {

                    do {
                        ObjectState objectState = new ObjectState();
                        objectState.setId(cursor.getInt(Colun_id));
                        objectState.setName(cursor.getString(Colun_name));
                        objectStates.add(objectState);
                        cursor.moveToNext();
                    } while (!cursor.isAfterLast());
                }
                cursor.close();
                conection.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return objectStates;
    }

    @SuppressLint("WrongConstant")
    public static void ExecSql(String sql) {
        SQLiteDatabase conection;
        conection = SQLiteDatabase.openDatabase(Constants.DatabasePATH + Constants.DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        conection.execSQL(sql);
        conection.close();
    }
}
