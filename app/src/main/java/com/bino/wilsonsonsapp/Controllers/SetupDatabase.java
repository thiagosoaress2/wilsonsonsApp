package com.bino.wilsonsonsapp.Controllers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;

public class SetupDatabase{

    private SQLiteDatabase currentDatabase;
    private Context currentContext;

    private static final String SQL_CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS user (firebase_key text, name text, cargo text, date_nascimento text, ultimo_certificado text, date_certificado, photo text)";
    private static final String SQL_CREATE_SKILLS_TABLE = "CREATE TABLE IF NOT EXISTS skills (id integer, name text)";
    private static final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE IF NOT EXISTS questions (id integer, id_skills integer, type integer, id_intro integer, multiplaa text, multiplab text, multiplac text, multiplad text, multiplae text, alternativacorreta text," +
            " imagem text, acertos text, erros text, itemclicavel1 text, item1X text, item1Y text, itemclicavel2 text, item2X text, item2Y text, itemclicavel3 text, item3X text, item3Y text, itemclicavel4 text, item4X text, item4Y text, itemclicavel5 text, item5X text, item5Y text," +
            "movie text, moviesugest text, pontos text, totalpontos text, respondida boolean)";
    private static final String SQL_CREATE_INTRO_TABLE = "CREATE TABLE IF NOT EXISTS intro (id_intro, title text, texto text, img integer, ordem integer)";

    private static final String SQL_INSERT_INTO_SKILL1 = "INSERT INTO skills (id, name) VALUES (1, 'Segurança');";
    private static final String SQL_INSERT_INTO_SKILL2 = "INSERT INTO skills (id, name) VALUES (2, 'Técnica');";
    private static final String SQL_INSERT_INTO_SKILL3 = "INSERT INTO skills (id, name) VALUES (3, 'Relacionamento');";

    private static final String SQL_INSERT_INTO_QUESTIONS1 = "INSERT INTO questions (id, id_skills, type, id_intro, multiplaa, multiplab, multiplac, multiplad, multiplae, alternativacorreta, imagem," +
            "itemclicavel1, item1X, item1Y, itemclicavel2, item2X, item2Y, itemclicavel3, item3X, item3Y, itemclicavel4, item4X, item4Y, itemclicavel5, item5X, item5Y, movie, moviesugest, pontos, totalpontos, respondida)" +
            "VALUES ( 1, 1, 1, 0, 'Letra A', 'Letra B', 'Letra C', 'Letra D', 'Letra E', 'a',,,,,,,,,,,,,,,,,,,,,,,,,,, 100, false);";

    private static final String SQL_INSERT_INTO_1_1 = "INSERT INTO intro (id_intro, title, texto, img, ordem) VALUES (1, 'tituloooo', 1, 0);";
    private static final String SQL_INSERT_INTO_1_2 = "INSERT INTO intro (id_intro, title, texto, img, ordem) VALUES (1, 'tituloooo', 2, 1);";
    private static final String SQL_INSERT_INTO_1_3 = "INSERT INTO intro (id_intro, title, texto, img, ordem) VALUES (1, 'tituloooo', 1, 2);";



    private static final String SQL_INSERT_INTO_QUESTIONS2 = "INSERT INTO questions (id, id_skills, type, id_intro, multiplaa, multiplab, multiplac, multiplad, multiplae, alternativacorreta, imagem," +
            "itemclicavel1, item1X, item1Y, itemclicavel2, item2X, item2Y, itemclicavel3, item3X, item3Y, itemclicavel4, item4X, item4Y, itemclicavel5, item5X, item5Y, movie, moviesugest, pontos, totalpontos, respondida)" +
            "VALUES ( 2, 2, 2, 0, 'Letra A', 'Letra B', 'Letra C', 'Letra D', 'Letra E', 'b',,,,,,,,,,,,,,,,,,,,,,,,,,, 100, false);";

    private static final String SQL_INSERT_INTO_2_1 = "INSERT INTO intro (id_intro, title, texto, img, ordem) VALUES (1, 'tituloooo', 1, 0);";
    private static final String SQL_INSERT_INTO_2_2 = "INSERT INTO intro (id_intro, title, texto, img, ordem) VALUES (1, 'tituloooo', 2, 1);";
    private static final String SQL_INSERT_INTO_2_3 = "INSERT INTO intro (id_intro, title, texto, img, ordem) VALUES (1, 'tituloooo', 1, 2);";



    private static final String SQL_INSERT_INTO_QUESTIONS3 = "INSERT INTO questions (id, id_skills, type, id_intro, multiplaa, multiplab, multiplac, multiplad, multiplae, alternativacorreta, imagem," +
            "itemclicavel1, item1X, item1Y, itemclicavel2, item2X, item2Y, itemclicavel3, item3X, item3Y, itemclicavel4, item4X, item4Y, itemclicavel5, item5X, item5Y, movie, moviesugest, pontos, totalpontos, respondida)" +
            "VALUES ( 3, 3, 2, 0, 'Letra A', 'Letra B', 'Letra C', 'Letra D', 'Letra E', 'c',,,,,,,,,,,,,,,,,,,,,,,,,,, 100, false);";

    private static final String SQL_INSERT_INTO_3_1 = "INSERT INTO intro (id_intro, title, texto, img, ordem) VALUES (1, 'tituloooo', 1, 0);";
    private static final String SQL_INSERT_INTO_3_2 = "INSERT INTO intro (id_intro, title, texto, img, ordem) VALUES (1, 'tituloooo', 2, 1);";
    private static final String SQL_INSERT_INTO_3_3 = "INSERT INTO intro (id_intro, title, texto, img, ordem) VALUES (1, 'tituloooo', 1, 2);";



    private static final String SQL_INSERT_INTO_QUESTIONS4 = "INSERT INTO questions (id, id_skills, type, id_intro, multiplaa, multiplab, multiplac, multiplad, multiplae, alternativacorreta, imagem," +
            "itemclicavel1, item1X, item1Y, itemclicavel2, item2X, item2Y, itemclicavel3, item3X, item3Y, itemclicavel4, item4X, item4Y, itemclicavel5, item5X, item5Y, movie, moviesugest, pontos, totalpontos, respondida)" +
            "VALUES ( 4, 1, 1, 0, 'Letra A', 'Letra B', 'Letra C', 'Letra D', 'Letra E', 'd',,,,,,,,,,,,,,,,,,,,,,,,,,, 100, false);";

    private static final String SQL_INSERT_INTO_4_1 = "INSERT INTO intro (id_intro, title, texto, img, ordem) VALUES (1, 'tituloooo', 1, 0);";
    private static final String SQL_INSERT_INTO_4_2 = "INSERT INTO intro (id_intro, title, texto, img, ordem) VALUES (1, 'tituloooo', 2, 1);";
    private static final String SQL_INSERT_INTO_4_3 = "INSERT INTO intro (id_intro, title, texto, img, ordem) VALUES (1, 'tituloooo', 1, 2);";




    public SetupDatabase(AppCompatActivity activityRef) {
        this.currentContext = activityRef.getApplicationContext();
        this.currentDatabase = this.currentContext.openOrCreateDatabase(Constants.DATABASE_NAME, 0, null, null);
        //createTable
        this.currentDatabase.execSQL(SQL_CREATE_USER_TABLE);
        this.currentDatabase.execSQL(SQL_CREATE_SKILLS_TABLE);
        this.currentDatabase.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        this.currentDatabase.execSQL(SQL_CREATE_INTRO_TABLE);

        //insertIntro
        this.currentDatabase.execSQL(SQL_INSERT_INTO_SKILL1);
        this.currentDatabase.execSQL(SQL_INSERT_INTO_SKILL2);
        this.currentDatabase.execSQL(SQL_INSERT_INTO_SKILL3);

        //insertQuestions
/*        this.currentDatabase.execSQL(SQL_INSERT_INTO_QUESTIONS1);
        this.currentDatabase.execSQL(SQL_INSERT_INTO_QUESTIONS2);
        this.currentDatabase.execSQL(SQL_INSERT_INTO_QUESTIONS3);
        this.currentDatabase.execSQL(SQL_INSERT_INTO_QUESTIONS4);*/

    }
}
