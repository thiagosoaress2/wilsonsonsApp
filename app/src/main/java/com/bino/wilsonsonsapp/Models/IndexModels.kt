package com.bino.wilsonsonsapp.Models

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bino.wilsonsonsapp.Controllers.ControllersUniversais
import com.bino.wilsonsonsapp.Controllers.IndexControllers
import com.bino.wilsonsonsapp.R
import com.bino.wilsonsonsapp.Utils.CircleTransform
import com.bino.wilsonsonsapp.Utils.IntroQuestAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList


object IndexModels {

    val arrayPosicoesX: MutableList<Int> = ArrayList()
    val arrayPosicoesY: MutableList<Int> = ArrayList()

    var isverified: Boolean = false

    var posicaoUser=0

    var userBd = "testeThiago"
    var userImg= "nao"

    var limitCertificate = "11/10/2023"

    var alertaEmbarcacao: String = "nao"
    var alertaDataEmbarque: String = "nao"

    val arrayCursos: MutableList<String> = ArrayList()
    val arrayCertificados: MutableList<String> = ArrayList()
    val arrayCertificadosValidade: MutableList<String> = ArrayList()

    var pontos: Int = 0

    //var uId: String = "nao"

    fun placeBackGroundAsMap(backgroundPlaceHolder: ImageView, activity: Activity, fases: Int, layout: ConstraintLayout, playerAvatar: ImageView){

        placeMapOnScreen(activity, R.drawable.gamebackground1, backgroundPlaceHolder)

        val screenHeight = IndexControllers.calculateTheScreenSizeH(activity, backgroundPlaceHolder)
        var screenWidth = IndexControllers.calculateTheScreenSizeW(activity, backgroundPlaceHolder)-100

        val intervalX = screenWidth/(fases/2)  //pega a quantidade de fases e divide por 2. Digamos, se for 6 fases, vai dividir por 3. Assim teremos uma variação pequena
        var startPointX = 100 //para começar no cantinho

        val intervalY = (screenHeight)/fases
        var startPointY = screenHeight-300

        val variation = 40

        var cont=0
        while (cont<fases){
            val imageView = ImageView(activity)
            // setting height and width of imageview
            imageView.layoutParams = LinearLayout.LayoutParams(80, 80)

            if (cont==0){
                imageView.x = startPointX.toFloat() //setting margin from left
                imageView.y = startPointY.toFloat() //setting margin from top
                arrayPosicoesX.add(startPointX)
                arrayPosicoesY.add(startPointY)
            } else if (cont+1==fases){
                imageView.x = screenWidth.toFloat()//-100 //setting margin from left
                imageView.y = 0f //setting margin from top
                arrayPosicoesX.add(screenWidth)
                arrayPosicoesY.add(0)
            }
            else {

                if ((cont % 2) == 0) {
                    // par

                    startPointY = startPointY-intervalY
                    imageView.x = startPointX.toFloat()+10
                    imageView.y = startPointY.toFloat()

                    arrayPosicoesX.add(startPointX+10)
                    arrayPosicoesY.add(startPointY)
                } else {
                    // impar este é o segundo ponto

                    startPointY = startPointY-intervalY
                    imageView.x = startPointX.toFloat()+300
                    imageView.y = startPointY.toFloat()
                    arrayPosicoesX.add(startPointX+300)
                    arrayPosicoesY.add(startPointY)
                }

            }

            layout?.addView(imageView) //adding image to the layout

            Glide.with(activity).load(R.drawable.pontovermelho).into(imageView)
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
        posicaoUser++
        if (posicaoUser<arrayPosicoesX.size) {
            playerAvatar.animate().translationX(arrayPosicoesX.get(posicaoUser).toFloat()).translationY(
                arrayPosicoesY.get(posicaoUser).toFloat())
        } else {
           finishTheLevel()
        }

    }

    fun setTheResultInMap(activity: Activity, layout: ConstraintLayout, acertou: Boolean){

        val imageView = ImageView(activity)
        // setting height and width of imageview
        imageView.layoutParams = LinearLayout.LayoutParams(60, 60)

        imageView.x = arrayPosicoesX.get(posicaoUser - 1).toFloat() //setting margin from left
        imageView.y = arrayPosicoesY.get(posicaoUser - 1).toFloat() //setting margin from top
        layout?.addView(imageView) //adding image to the layout

        val textView = TextView(activity)
        textView.layoutParams = LinearLayout.LayoutParams(80, 40)
        textView.x = arrayPosicoesX.get(posicaoUser -1).toFloat()
        textView.y = arrayPosicoesY.get(posicaoUser -1).toFloat()-25
        layout?.addView(textView)

        if (acertou){
            textView.setText(pontos+100)
            Glide.with(activity).load(R.drawable.acertonomapa).into(imageView)
        } else {

            textView.setText(pontos-50)
            Glide.with(activity).load(R.drawable.erronomapa).into(imageView)
        }

    }

    fun openIntroQuest(layIntroQuest: ConstraintLayout, recyclerView: RecyclerView, activity: Activity, objectQuestions: ObjectQuestions){

        //layInicial.visibility = View.GONE
        layIntroQuest.visibility = View.VISIBLE

        var objectIntro: List<ObjectIntro> = ConsultsQuestionsModel.selectIntro(activity, objectQuestions.id_intro)

     /*   val arrayTitulo: MutableList<String> = ArrayList()
        arrayTitulo.add("Olaaaa")
        arrayTitulo.add("Este é o titulo 2")
        val arrayTexto: MutableList<String> = ArrayList()
        arrayTexto.add("Este é o texto 1")
        arrayTexto.add("Este é o texto 2, um texto maior para podermos testar")
        val arrayImage: MutableList<String> = ArrayList()
        arrayImage.add("https://firebasestorage.googleapis.com/v0/b/wilsonsonshack.appspot.com/o/problemas%2Fquadrinho.jpg?alt=media&token=c27bd083-fe07-4a37-8557-14125a99ebf4")
        arrayImage.add("https://firebasestorage.googleapis.com/v0/b/wilsonsonshack.appspot.com/o/problemas%2Fquadrinho2.jpg?alt=media&token=843e406c-cca5-4fa8-9ad5-5a9f1adce458")
*/
        val adapter: IntroQuestAdapter = IntroQuestAdapter(activity, objectIntro)

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
            IndexModels.RecyclerTouchListener(
                activity,
                recyclerView!!,
                object : IndexModels.ClickListener {

                    override fun onClick(view: View, position: Int) {
                        Log.d("teste", objectIntro.get(position).title)
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

    private fun finishTheLevel(){
        
        //calcular aqui como o user foi
        //exibir um resumo
        //avisar se tem mais ou se acabou

    }

    fun checkCertificate() : Boolean {

        val today = ControllersUniversais.getDate()
        //val limiteDate = GetfutureDate(30)

        val format = SimpleDateFormat("dd/MM/yyyy")
        val date1 = format.parse(limitCertificate)
        val date2 = format.parse(today)

        if (date1.compareTo(date2) >= 0) {  //se for hoje ou no futuro
            return false //menor nao precisa avisar
        } else {
            return true  //maior  precisa
        }
    }

    interface ClickListener {
        fun onClick(view: View, position: Int)

        fun onLongClick(view: View?, position: Int)
    }

    fun loadCourses() : Boolean{

        arrayCursos.clear()

        arrayCursos.add("Rebocador Torda")
        arrayCursos.add("Rebocador Skua")
        arrayCursos.add("Rebocador Biguá")
        arrayCursos.add("Rebocador Petrel")
        arrayCursos.add("Rebocador Saveiro")
        arrayCursos.add("Rebocador Saveiro Atobá")
        arrayCursos.add("Rebocador Talha-mar")
        arrayCursos.add("Rebocador Saveiro Pelicano")
        arrayCursos.add("Rebocador Saveiro Albatroz")
        arrayCursos.add("Rebocador Cormoran")
        arrayCursos.add("Rebocador Batuíra")
        arrayCursos.add("Rebocador Sterna")
        arrayCursos.add("Rebocador Prion")
        arrayCursos.add("Rebocador Tagaz")
        arrayCursos.add("Rebocador Zarapito")
        arrayCursos.add("Rebocador Alcatraz")
        arrayCursos.add("Rebocador Larus")
        arrayCursos.add("Rebocador Pinguim")

        return true

    }

    internal class RecyclerTouchListener(context: Context, recyclerView: RecyclerView, private val clickListener: ClickListener?) : RecyclerView.OnItemTouchListener {

        private val gestureDetector: GestureDetector

        init {
            gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    val child = recyclerView.findChildViewUnder(e.x, e.y)
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child))
                    }
                }
            })
        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {

            val child = rv.findChildViewUnder(e.x, e.y)
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child))
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

        }
    }

    fun checkCadInfo() : Boolean{

        val objectUser: ObjectUser = ObjectUser()

        if (objectUser.state ==0 || objectUser.number == null || objectUser.name ==null || objectUser.datenascimento ==null || objectUser.datenascimento ==null || objectUser.cargo==0 || objectUser.photo == null){
            //openPopUpCadInfo("Completar", "Fazer depois", findViewById(R.id.layoutPrincipal))
            return false
        } else{
            return true
        }
    }



}