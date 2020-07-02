package com.bino.wilsonsonsapp.Controllers

import android.app.Activity
import android.widget.Toast
import com.bino.wilsonsonsapp.Models.indexModels
import java.text.SimpleDateFormat

object adminControllers {

    fun checkCertificateValidit(validade: String) : Boolean {

        val today = ControllersUniversais.getDate()
        //val limiteDate = GetfutureDate(30)

        val format = SimpleDateFormat("dd/MM/yyyy")
        val date1 = format.parse(validade)
        val date2 = format.parse(today)

        if (date1.compareTo(date2) >= 0) {  //se for hoje ou no futuro
            return false //menor nao precisa avisar
        } else {
            return true  //maior  precisa
        }
    }

    fun checkCertificateAboutToExpire(validade: String): Boolean {

        val today = ControllersUniversais.getDate()

        val format = SimpleDateFormat("dd/MM/yyyy")
        val date1 = format.parse(validade)
        val date2 = format.parse(today)

        if (date1.compareTo(date2) >= 30) {  //se for hoje ou no futuro
            return false //nao precisa avisar
        } else {
            return true  //precisa
        }
    }
}