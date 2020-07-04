package com.bino.wilsonsonsapp.Utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.bino.wilsonsonsapp.Controllers.ControllersUniversais
import com.bino.wilsonsonsapp.Models.IndexModels

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
        IndexModels.alertaDataEmbarque = sharedPref.getString("dataEmbarque", "nao").toString()
        IndexModels.alertaEmbarcacao = sharedPref.getString("embarcacao", "nao").toString()
    }

    fun removeAlert(){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove("dataEmbarque")
        editor.remove("embarcacao")
        editor.apply()
    }

    fun addCertificados(quantidade: Int, activity: Activity){
        if (quantidade>0){

            val editor = sharedPref.edit()
            var cont=0
            while (cont<quantidade){
                var field = "certificado"+(cont+1).toString()  //precisa adicioanr 1 pois os certificados começam do 1 e o nosso cont do 0
                editor.putString(field, IndexModels.arrayCertificados.get(cont))
                field = "valcert"+(cont+1).toString()
                editor.putString(field, IndexModels.arrayCertificadosValidade.get(cont))
                cont++
            }

            editor.putString("quantidade", quantidade.toString())
            editor.apply()

        } else {
            ControllersUniversais.makeToast(activity, "Você não pode incluir 0 certificados")
        }
    }

    fun deleteCertificado(posicao: Int, activity: Activity){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        var field = "certificado"+(posicao).toString()  //precisa adicioanr 1 pois os certificados começam do 1 e o nosso cont do 0
        editor.remove(field)
        field = "valcert"+(posicao).toString()
        editor.remove(field)

        //atualizando a quantidade
        val quantidade = sharedPref.getString("quantidade", "nao").toString()
        val quantidadeUpdated = quantidade.toInt()-1
        editor.putString("quantidade", quantidadeUpdated.toString())

        editor.apply()
        ControllersUniversais.makeToast(activity, "Certificado removido")
    }

    fun getQuantiCert(): Int{

        val quantidade = sharedPref.getString("quantidade", "nao").toString()
        return quantidade.toInt()

    }

    fun getllCertificates(){

    }

    fun loadCertificates() {

        val quantidade =sharedPref.getString("quantidade", "nao").toString()
        var cont=0
        while (cont<quantidade.toInt()){
            cont++
            var field = "certificado"+(cont).toString()  //precisa adicioanr 1 pois os certificados começam do 1 e o nosso cont do 0
            val cert = sharedPref.getString(field, "nao").toString()
            IndexModels.arrayCertificados.add(cert)
            field = "valcert"+(cont).toString()
            val valid = sharedPref.getString(field, "nao").toString()
            IndexModels.arrayCertificadosValidade.add(valid)

        }
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



    fun setCopyDatabase(){
        val editor = sharedPref.edit()
        editor.putBoolean("database", true)
        editor.apply()
    }


    fun getCopyDatabase(): Boolean {
        return sharedPref.getBoolean("database", false)
    }

}

