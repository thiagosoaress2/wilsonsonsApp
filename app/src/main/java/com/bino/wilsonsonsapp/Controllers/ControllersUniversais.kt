package com.bino.wilsonsonsapp.Controllers

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import java.text.SimpleDateFormat
import java.util.*

object ControllersUniversais {

    fun makeToast(activity: Activity, message: String){

        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun getDate () : String {

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(Date())

        return currentDate
    }

    fun getHour () : String {

        val sdf = SimpleDateFormat("hh:mm:ss")
        val currentDate = sdf.format(Date())

        return currentDate
    }

    fun GetfutureDate (daysToAdd: Int) : String {

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(Date())

        val c = Calendar.getInstance()
        c.time = sdf.parse(currentDate)
        c.add(Calendar.DATE, daysToAdd) // number of days to add

        var tomorrow: String = sdf.format(Date())
        tomorrow = sdf.format(c.time) // dt is now the new date

        return tomorrow

    }

    fun GetPastDate (daysToSub: Int) : String {

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(Date())

        val c = Calendar.getInstance()
        c.time = sdf.parse(currentDate)
        c.add(Calendar.DATE, -daysToSub) // number of days to sub

        var tomorrow: String = sdf.format(Date())
        tomorrow = sdf.format(c.time) // dt is now the new date

        return tomorrow

    }

    fun openCloseLay(close: ConstraintLayout, open: ConstraintLayout){
        close.visibility = View.GONE
        open.visibility = View.VISIBLE
    }

    fun rand (start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (start..end).random()
    }

}