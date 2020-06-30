package com.bino.wilsonsonsapp.Models

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bino.wilsonsonsapp.Controllers.indexControllers
import com.bino.wilsonsonsapp.R
import com.bino.wilsonsonsapp.Utils.CircleTransform
import com.bino.wilsonsonsapp.Utils.introQuestAdapter
import com.bino.wilsonsonsapp.indexActivity
import com.bumptech.glide.Glide


object indexModels {

    val arrayPosicoesX: MutableList<Int> = ArrayList()
    val arrayPosicoesY: MutableList<Int> = ArrayList()

    var posicaoUser=0

    var userBd = "nao"
    var userImg= "nao"

    fun placeBackGroundAsMap(backgroundPlaceHolder: ImageView, activity: Activity, fases: Int, layout: ConstraintLayout, playerAvatar: ImageView){

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

            layout?.addView(imageView) //adding image to the layout

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

    fun openIntroQuest(layIntroQuest: ConstraintLayout, recyclerView: RecyclerView, activity: Activity){

        //layInicial.visibility = View.GONE
        layIntroQuest.visibility = View.VISIBLE

        val arrayTitulo: MutableList<String> = ArrayList()
        arrayTitulo.add("Olaaaa")
        arrayTitulo.add("Este é o titulo 2")
        val arrayTexto: MutableList<String> = ArrayList()
        arrayTexto.add("Este é o texto 1")
        arrayTexto.add("Este é o texto 2, um texto maior para podermos testar")
        val arrayImage: MutableList<String> = ArrayList()
        arrayImage.add("https://firebasestorage.googleapis.com/v0/b/wilsonsonshack.appspot.com/o/problemas%2Fquadrinho.jpg?alt=media&token=c27bd083-fe07-4a37-8557-14125a99ebf4")
        arrayImage.add("https://firebasestorage.googleapis.com/v0/b/wilsonsonshack.appspot.com/o/problemas%2Fquadrinho2.jpg?alt=media&token=843e406c-cca5-4fa8-9ad5-5a9f1adce458")

        val adapter: introQuestAdapter = introQuestAdapter(activity, arrayTitulo, arrayTexto, arrayImage)

        //chame a recyclerview
        //val recyclerView: RecyclerView = findViewById(R.id.question_intro_recyclerView)

        //define o tipo de layout (linerr, grid)
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(activity)

        //coloca o adapter na recycleview
        recyclerView.adapter = adapter

        recyclerView.layoutManager = linearLayoutManager

        // Notify the adapter for data change.
        adapter.notifyDataSetChanged()

        recyclerView.addOnItemTouchListener(
            indexActivity.RecyclerTouchListener(
                activity,
                recyclerView!!,
                object : indexActivity.ClickListener {

                    override fun onClick(view: View, position: Int) {
                        Log.d("teste", arrayTitulo.get(position))
                        //Toast.makeText(this@MainActivity, !! aNome.get(position).toString(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onLongClick(view: View?, position: Int) {

                    }
                })
        )


    }

    fun placeImage(imageView: ImageView, activity: Activity){

        if (userImg.equals("nao")){
            try {
                Glide.with(activity)
                    .load(R.drawable.avatar)
                    .thumbnail(0.2f)
                    .skipMemoryCache(true)
                    .transform(CircleTransform(activity))
                    .into(imageView)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            try {
                Glide.with(activity)
                    .load(R.drawable.avatar)
                    .thumbnail(0.2f)
                    .skipMemoryCache(true)
                    .transform(CircleTransform(activity))
                    .into(imageView)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}