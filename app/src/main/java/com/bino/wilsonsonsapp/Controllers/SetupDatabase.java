package com.bino.wilsonsonsapp.Controllers;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import com.bino.wilsonsonsapp.Models.ObjectIntro;
import com.bino.wilsonsonsapp.Models.ObjectQuestions;
import com.bino.wilsonsonsapp.Models.ObjectSkills;
import com.bino.wilsonsonsapp.Models.ObjectUser;

import java.util.ArrayList;
import java.util.List;

public class SetupDatabase{

    private static final String DATABASE_NAME = "DatabaseWilsonson.db";
    public static final String DatabasePATH = "/data/data/com.bino.wilsonsonapp/databases/";
    private static final Integer DATABASE_VERSION = 1;
    private SQLiteDatabase currentDatabase;
    private Context currentContext;

    private static final String SQL_CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS user (firebase_key text, name text, cargo text, date_nascimento text, photo text)";
    private static final String SQL_CREATE_SKILLS_TABLE = "CREATE TABLE IF NOT EXISTS skills (id integer, name text)";
    private static final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE IF NOT EXISTS questions (id integer, id_skills integer, type integer, id_intro integer, multiplaa text, multiplab text, multiplac text, multiplad text, multiplae text, alternativacorreta text," +
            " imagem text, acertos text, erros text, itemclicavel1 text, item1X text, item1Y text, itemclicavel2 text, item2X text, item2Y text, pontos text, totalpontos text, respondida boolean)";
    private static final String SQL_CREATE_INTRO_TABLE = "CREATE TABLE IF NOT EXISTS points (id_user integer, id_skills integer, id_questions integer, points integer, allpoints integer)";


    public SetupDatabase(AppCompatActivity activityRef) {
        this.currentContext = activityRef.getApplicationContext();
        this.currentDatabase = this.currentContext.openOrCreateDatabase(DATABASE_NAME, 0, null, null);
        this.currentDatabase.execSQL(SQL_CREATE_USER_TABLE);
        this.currentDatabase.execSQL(SQL_CREATE_SKILLS_TABLE);
        this.currentDatabase.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        this.currentDatabase.execSQL(SQL_CREATE_INTRO_TABLE);

    }



}
