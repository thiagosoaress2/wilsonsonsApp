package com.bino.wilsonsonsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout

class adminActivity : AppCompatActivity() {

    val paginaIndex: ConstraintLayout = findViewById(R.id.paginaIndex)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val btn: Button = findViewById(R.id.btnCadDesafio)
        btn.setOnClickListener {
            abreCadDesafio()
        }

    }

    fun abreCadDesafio(){
        paginaIndex.visibility = View.GONE
        val paginaCadDes: ConstraintLayout = findViewById(R.id.paginaCadDesafio)
        paginaCadDes.visibility = View.VISIBLE


    }
}