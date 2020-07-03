package com.bino.wilsonsonsapp.Models

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object AdminModels {

    lateinit var estado:String

    var certificados: MutableList<String> = ArrayList()
    var validCert: MutableList<String> = ArrayList()
    var funcao: MutableList<String> = ArrayList()
    var nome: MutableList<String> = ArrayList()
    var bd: MutableList<String> = ArrayList()

    var img: MutableList<String> = ArrayList()  //usado apenas na janela de detalhes
    var whats: MutableList<String> = ArrayList() //usados apenas na janela de detalhes

    var skillSeg: MutableList<String> = ArrayList()
    var skillTec: MutableList<String> = ArrayList()
    var skillRel: MutableList<String> = ArrayList()

    fun openCloseLay(close: ConstraintLayout, open: ConstraintLayout){
        close.visibility = View.GONE
        open.visibility = View.VISIBLE
    }


}