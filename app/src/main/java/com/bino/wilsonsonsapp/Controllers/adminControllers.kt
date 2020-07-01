package com.bino.wilsonsonsapp.Controllers

import android.app.Activity
import android.widget.Toast
import com.bino.wilsonsonsapp.Models.indexModels
import java.text.SimpleDateFormat

object adminControllers {

    fun makeToast(activity: Activity, message: String){

        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun checkCertificateValidit(validade: String) : Boolean {

        val today = indexControllers.getDate()
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
}