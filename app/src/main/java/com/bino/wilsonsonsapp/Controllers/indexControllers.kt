package com.bino.wilsonsonsapp.Controllers

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.bino.wilsonsonsapp.R

object indexControllers {


    fun isNetworkAvailable(activity: Activity): Boolean {
        val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    fun calculateTheScreenSizeH(activity: Activity, imageView: ImageView) : Int {

        //pegando o tamanho da tela do celular
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        //val width: Int = size.x
        val height: Int = size.y
        return height

    }

    fun calculateTheImageViewSizeH(activity: Activity, imageView: ImageView): Int {
        val height : Int = imageView.y.toInt()
        return height
    }

    fun calculateTheImageViewSizeW(activity: Activity, imageView: ImageView): Int {
        val height : Int = imageView.x.toInt()
        return height
    }

    fun calculateTheScreenSizeW(activity: Activity, imageView: ImageView) : Int {

        //pegando o tamanho da tela do celular
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width: Int = size.x
        //val height: Int = size.y
        return width

    }


}