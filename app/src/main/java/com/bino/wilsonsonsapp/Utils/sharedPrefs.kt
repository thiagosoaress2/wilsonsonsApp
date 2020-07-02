package com.bino.wilsonsonsapp.Utils

import android.content.Context
import android.content.SharedPreferences
import com.bino.wilsonsonsapp.Controllers.indexControllers
import com.bino.wilsonsonsapp.Models.indexModels

class mySharedPrefs (val context: Context) {

    private val PREFS_NAME = "wilsonsApp"  //nome do sharedprefs
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    //salvar um valor
    fun setValue(field: String, value: String){

        val editor = sharedPref.edit()
        editor.putString(field, value)
        editor.apply()
    }

    //pegar valor
    fun getValue(field: String): String {

        return sharedPref.getString(field, "nao").toString()
    }

    fun setAlertInfo(dataEmbarque: String, embarcacao: String){
        val editor = sharedPref.edit()
        editor.putString("dataEmbarque", dataEmbarque)
        editor.putString("embarcacao", embarcacao)
        editor.apply()
    }

    fun getAlertInfo() {
        indexModels.alertaEmbarcacao = sharedPref.getString("dataEmbarque", "nao").toString()
        indexModels.alertaEmbarcacao = sharedPref.getString("embarcacao", "nao").toString()
    }

    fun removeAlert(){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove("dataEmbarque")
        editor.remove("embarcacao")
        editor.apply()
    }

    //apagar tudo
    fun clearSharedPreference() {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.clear()
        editor.apply()
    }

    //remove specific value
    fun removeValue(KEY_NAME: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.apply()
    }

}

