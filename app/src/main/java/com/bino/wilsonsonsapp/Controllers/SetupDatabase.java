package com.bino.wilsonsonsapp.Controllers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;

import com.bino.wilsonsonsapp.Models.ConsultsStateModel;

public class SetupDatabase{

    private SQLiteDatabase currentDatabase;
    private Context currentContext;

    private static final String SQL_CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS user (firebase_key text, email text, name text, number text, occupation_id integer, state_id integer, date_nascimento text, photo text)";
    private static final String SQL_CREATE_SKILLS_TABLE = "CREATE TABLE IF NOT EXISTS skills (id integer, name text)";
    private static final String SQL_CREATE_OCCUPATION_TABLE = "CREATE TABLE IF NOT EXISTS occupation (id integer, name text)";
    private static final String SQL_CREATE_STATE_TABLE = "CREATE TABLE IF NOT EXISTS state (id integer, name text)";
    private static final String SQL_CREATE_CERTIFICADO_TABLE = "CREATE TABLE IF NOT EXISTS certificate (firebase_key_user text, id integer, name text, validade text)";
    private static final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE IF NOT EXISTS questions (id integer, id_skills integer, type integer, id_intro integer, multiplaa text, multiplab text, multiplac text, multiplad text, multiplae text, alternativacorreta text," +
            " imagem text, acertos text, erros text, itemclicavel1 text, item1X text, item1Y text, itemclicavel2 text, item2X text, item2Y text, itemclicavel3 text, item3X text, item3Y text, itemclicavel4 text, item4X text, item4Y text, itemclicavel5 text, item5X text, item5Y text," +
            "movie text, moviesugest text, pontos text, totalpontos text, respondida boolean)";
    private static final String SQL_CREATE_INTRO_TABLE = "CREATE TABLE IF NOT EXISTS intro (id_intro, title text, texto text, img integer, ordem integer)";

    private static final String SQL_INSERT_INTO_SKILL1 = "INSERT INTO skills (id, name) VALUES (1, 'Segurança');";
    private static final String SQL_INSERT_INTO_SKILL2 = "INSERT INTO skills (id, name) VALUES (2, 'Técnica');";
    private static final String SQL_INSERT_INTO_SKILL3 = "INSERT INTO skills (id, name) VALUES (3, 'Relacionamento');";

    private static final String SQL_INSERT_OCCUPATION1 = "INSERT INTO occupation (id, name) VALUES (1, 'Mecânico');";
    private static final String SQL_INSERT_OCCUPATION2 = "INSERT INTO occupation (id, name) VALUES (2, 'Mecânico');";
    private static final String SQL_INSERT_OCCUPATION3 = "INSERT INTO occupation (id, name) VALUES (3, 'Mecânico');";

    private static final String SQL_INSERT_STATE1  = "INSERT INTO state (id, name) VALUES (1, 'AC');";
    private static final String SQL_INSERT_STATE2  = "INSERT INTO state (id, name) VALUES (2, 'AL');";
    private static final String SQL_INSERT_STATE3  = "INSERT INTO state (id, name) VALUES (3, 'AP');";
    private static final String SQL_INSERT_STATE4  = "INSERT INTO state (id, name) VALUES (4, 'AM');";
    private static final String SQL_INSERT_STATE5  = "INSERT INTO state (id, name) VALUES (5, 'BA');";
    private static final String SQL_INSERT_STATE6  = "INSERT INTO state (id, name) VALUES (6, 'CE');";
    private static final String SQL_INSERT_STATE7  = "INSERT INTO state (id, name) VALUES (7, 'DF');";
    private static final String SQL_INSERT_STATE8  = "INSERT INTO state (id, name) VALUES (8, 'ES');";
    private static final String SQL_INSERT_STATE9  = "INSERT INTO state (id, name) VALUES (9, 'GO');";
    private static final String SQL_INSERT_STATE10 = "INSERT INTO state (id, name) VALUES (10, 'MA');";
    private static final String SQL_INSERT_STATE11 = "INSERT INTO state (id, name) VALUES (11, 'MT');";
    private static final String SQL_INSERT_STATE12 = "INSERT INTO state (id, name) VALUES (12, 'MS');";
    private static final String SQL_INSERT_STATE13 = "INSERT INTO state (id, name) VALUES (13, 'MG');";
    private static final String SQL_INSERT_STATE14 = "INSERT INTO state (id, name) VALUES (14, 'PA');";
    private static final String SQL_INSERT_STATE15 = "INSERT INTO state (id, name) VALUES (15, 'PB');";
    private static final String SQL_INSERT_STATE16 = "INSERT INTO state (id, name) VALUES (16, 'PR');";
    private static final String SQL_INSERT_STATE17 = "INSERT INTO state (id, name) VALUES (17, 'PE');";
    private static final String SQL_INSERT_STATE18 = "INSERT INTO state (id, name) VALUES (18, 'PI');";
    private static final String SQL_INSERT_STATE19 = "INSERT INTO state (id, name) VALUES (19, 'RJ');";
    private static final String SQL_INSERT_STATE20 = "INSERT INTO state (id, name) VALUES (20, 'RN');";
    private static final String SQL_INSERT_STATE21 = "INSERT INTO state (id, name) VALUES (21, 'RS');";
    private static final String SQL_INSERT_STATE22 = "INSERT INTO state (id, name) VALUES (22, 'RO');";
    private static final String SQL_INSERT_STATE23 = "INSERT INTO state (id, name) VALUES (23, 'RR');";
    private static final String SQL_INSERT_STATE24 = "INSERT INTO state (id, name) VALUES (24, 'SC');";
    private static final String SQL_INSERT_STATE25 = "INSERT INTO state (id, name) VALUES (25, 'SP');";
    private static final String SQL_INSERT_STATE26 = "INSERT INTO state (id, name) VALUES (26, 'SE');";
    private static final String SQL_INSERT_STATE27 = "INSERT INTO state (id, name) VALUES (27, 'TO');";

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

      //  if() {
            //createTable
            this.currentDatabase.execSQL(SQL_CREATE_USER_TABLE);
            this.currentDatabase.execSQL(SQL_CREATE_SKILLS_TABLE);
            this.currentDatabase.execSQL(SQL_CREATE_STATE_TABLE);
            this.currentDatabase.execSQL(SQL_CREATE_OCCUPATION_TABLE);
            this.currentDatabase.execSQL(SQL_CREATE_CERTIFICADO_TABLE);
            this.currentDatabase.execSQL(SQL_CREATE_QUESTIONS_TABLE);
            this.currentDatabase.execSQL(SQL_CREATE_INTRO_TABLE);

            //insertOccupation
            this.currentDatabase.execSQL(SQL_INSERT_OCCUPATION1);
            this.currentDatabase.execSQL(SQL_INSERT_OCCUPATION2);
            this.currentDatabase.execSQL(SQL_INSERT_OCCUPATION3);

            //insertSkill
            this.currentDatabase.execSQL(SQL_INSERT_INTO_SKILL1);
            this.currentDatabase.execSQL(SQL_INSERT_INTO_SKILL2);
            this.currentDatabase.execSQL(SQL_INSERT_INTO_SKILL3);

            //State
            this.currentDatabase.execSQL(SQL_INSERT_STATE1);
            this.currentDatabase.execSQL(SQL_INSERT_STATE2);
            this.currentDatabase.execSQL(SQL_INSERT_STATE3);
            this.currentDatabase.execSQL(SQL_INSERT_STATE4);
            this.currentDatabase.execSQL(SQL_INSERT_STATE5);
            this.currentDatabase.execSQL(SQL_INSERT_STATE6);
            this.currentDatabase.execSQL(SQL_INSERT_STATE7);
            this.currentDatabase.execSQL(SQL_INSERT_STATE8);
            this.currentDatabase.execSQL(SQL_INSERT_STATE9);
            this.currentDatabase.execSQL(SQL_INSERT_STATE10);
            this.currentDatabase.execSQL(SQL_INSERT_STATE11);
            this.currentDatabase.execSQL(SQL_INSERT_STATE12);
            this.currentDatabase.execSQL(SQL_INSERT_STATE13);
            this.currentDatabase.execSQL(SQL_INSERT_STATE14);
            this.currentDatabase.execSQL(SQL_INSERT_STATE15);
            this.currentDatabase.execSQL(SQL_INSERT_STATE16);
            this.currentDatabase.execSQL(SQL_INSERT_STATE17);
            this.currentDatabase.execSQL(SQL_INSERT_STATE18);
            this.currentDatabase.execSQL(SQL_INSERT_STATE19);
            this.currentDatabase.execSQL(SQL_INSERT_STATE20);
            this.currentDatabase.execSQL(SQL_INSERT_STATE21);
            this.currentDatabase.execSQL(SQL_INSERT_STATE22);
            this.currentDatabase.execSQL(SQL_INSERT_STATE23);
            this.currentDatabase.execSQL(SQL_INSERT_STATE24);
            this.currentDatabase.execSQL(SQL_INSERT_STATE25);
            this.currentDatabase.execSQL(SQL_INSERT_STATE26);
            this.currentDatabase.execSQL(SQL_INSERT_STATE27);

            this.currentDatabase.close();
    //    }
        /*

        //insertQuestions - intro
      this.currentDatabase.execSQL(SQL_INSERT_INTO_QUESTIONS1);
      this.currentDatabase.execSQL(SQL_INSERT_INTO_SKILL1);
      this.currentDatabase.execSQL(SQL_INSERT_INTO_SKILL2);
      this.currentDatabase.execSQL(SQL_INSERT_INTO_SKILL3);


      this.currentDatabase.execSQL(SQL_INSERT_INTO_QUESTIONS2);
      this.currentDatabase.execSQL(SQL_INSERT_INTO_SKILL1);
      this.currentDatabase.execSQL(SQL_INSERT_INTO_SKILL2);
      this.currentDatabase.execSQL(SQL_INSERT_INTO_SKILL3);

      this.currentDatabase.execSQL(SQL_INSERT_INTO_QUESTIONS3);
      this.currentDatabase.execSQL(SQL_INSERT_INTO_SKILL1);
      this.currentDatabase.execSQL(SQL_INSERT_INTO_SKILL2);
      this.currentDatabase.execSQL(SQL_INSERT_INTO_SKILL3);


      this.currentDatabase.execSQL(SQL_INSERT_INTO_QUESTIONS4);
      this.currentDatabase.execSQL(SQL_INSERT_INTO_SKILL1);
      this.currentDatabase.execSQL(SQL_INSERT_INTO_SKILL2);
      this.currentDatabase.execSQL(SQL_INSERT_INTO_SKILL3);
        */
    }
}