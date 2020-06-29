package com.bino.wilsonsonsapp.Models

import android.app.Activity
import android.content.Context
import android.os.Build
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat.getSystemService
import com.bino.wilsonsonsapp.Controllers.indexControllers
import com.bino.wilsonsonsapp.R
import com.bumptech.glide.Glide


object indexModels {

    val arrayPosicoesX: MutableList<Int> = ArrayList()
    val arrayPosicoesY: MutableList<Int> = ArrayList()

    var posicaoUser=0

    fun placeBackGroundAsMap(backgroundPlaceHolder: ImageView, activity: Activity, fases: Int, layoutPrincipal: ConstraintLayout, playerAvatar: ImageView){

        placeMapOnScreen(activity, R.drawable.map, backgroundPlaceHolder)

        val screenHeight = indexControllers.calculateTheScreenSizeH(activity, backgroundPlaceHolder)
        val screenWidth = indexControllers.calculateTheScreenSizeW(activity, backgroundPlaceHolder)

        val interval = screenWidth/fases
        val startPoint = interval/4

        val intervalH = screenHeight/6
        val startPointH = screenHeight/2

        var cont=0
        while (cont<fases){
            val imageView = ImageView(activity)
            // setting height and width of imageview
            imageView.layoutParams = LinearLayout.LayoutParams(80, 80)

            if (cont==0){
                imageView.x = startPoint.toFloat() //setting margin from left
                imageView.y = startPointH.toFloat() //setting margin from top
                arrayPosicoesX.add(startPoint)
                arrayPosicoesY.add(startPointH)
            } else {

                if ((cont % 2) == 0) {
                    // par
                    imageView.x = startPoint.toFloat()+interval*cont
                    imageView.y = startPointH.toFloat()+intervalH

                    arrayPosicoesX.add(startPoint+interval*cont)
                    arrayPosicoesY.add(startPointH+intervalH)
                }

                else {
                    // impar
                    imageView.x = startPoint.toFloat()+interval*cont
                    imageView.y = startPointH.toFloat()-intervalH
                    arrayPosicoesX.add(startPoint+interval*cont)
                    arrayPosicoesY.add(startPointH-intervalH)
                }

            }

            layoutPrincipal?.addView(imageView) //adding image to the layout

            Glide.with(activity).load(R.drawable.navio).into(imageView)
            cont++
        }


        placeThePlayerInitial(playerAvatar)

    }

    private fun placeMapOnScreen(activity: Activity, img: Int, imageView: ImageView){

        Glide.with(activity).load(img).into(imageView)
    }

    private fun placeThePlayerInitial(playerAvatar: ImageView){

        playerAvatar.x = arrayPosicoesX.get(posicaoUser).toFloat()
        playerAvatar.y = arrayPosicoesY.get(posicaoUser).toFloat()

    }

    fun moveThePlayer(playerAvatar: ImageView){

        if (posicaoUser<arrayPosicoesX.size) {
            playerAvatar.animate().translationX(arrayPosicoesX.get(posicaoUser).toFloat()).translationY(
                arrayPosicoesY.get(posicaoUser).toFloat())
        } else {
            Log.d("teste", "Jogador está no final, chamar proximo procedimento")
        }

    }

}