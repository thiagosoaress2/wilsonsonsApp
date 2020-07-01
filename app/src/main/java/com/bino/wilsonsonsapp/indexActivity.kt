package com.bino.wilsonsonsapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.bino.wilsonsonsapp.Controllers.indexControllers
import com.bino.wilsonsonsapp.Models.ConsultsQuestionsModel
import com.bino.wilsonsonsapp.Models.ObjectQuestions
import com.bino.wilsonsonsapp.Models.indexModels
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView


class indexActivity : AppCompatActivity() {

    val LOGD : String = "teste"

    lateinit var toolbar: Toolbar
    lateinit var drawer: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var layInicial: ConstraintLayout
    lateinit var layIntroQuest: ConstraintLayout
    lateinit var lay_problema: ConstraintLayout
    lateinit var btnteste: Button
    lateinit var btnTesteProblema: Button
    lateinit var btnTestePerfil: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


    }

    override fun onStart() {
        super.onStart()

        loadComponents()

        val situacao = intent.getStringExtra("email")
        if (indexControllers.isNetworkAvailable(this) && situacao.equals("semLogin")){
            //   openPopUp("Opa! Você está conectado na internet", "Você agora possui internet e ainda não fez login. Vamos fazer o login para salvar poder salvar seus dados?", true, "Sim, fazer login", "Não", "login")
        } else if (indexControllers.isNetworkAvailable(this)){
            //verificar se tem novos mundos para baixar
            //chamar um método para baixar os conteudos e em seguida informar ao usuário que existem atualizações e novas fases

        }

        btnteste.setOnClickListener {
            indexModels.posicaoUser++
            indexModels.moveThePlayer(findViewById(R.id.playerAvatar))
        }

        btnTesteProblema.setOnClickListener {
            openIntroQuest()
        }

        btnTestePerfil.setOnClickListener {
            val intent = Intent(this, perfilActivity::class.java)
            //intent.putExtra("email", "semLogin")
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnAdminTeste).setOnClickListener {
            val intent = Intent(this, adminActivity::class.java)
            startActivity(intent)
        }

        indexModels.placeBackGroundAsMap(findViewById(R.id.backgroundPlaceHolder), this, 5, findViewById(R.id.layIndex), findViewById(R.id.playerAvatar))



        setupMenu()

        //anima nuvem
        //Carregue o objeto que vai receber a animação
        val nuvem: ImageView = findViewById(R.id.imgnuvem)
        //carregue a animação
        val movenuvem = AnimationUtils.loadAnimation(this, R.anim.movenuvem)
        //utilize assim
        nuvem.startAnimation(movenuvem)


    }

    fun loadComponents(){

        drawer = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        navigationView = findViewById(R.id.nav_view)
        layInicial = findViewById(R.id.layoutPrincipal)
        layIntroQuest = findViewById(R.id.LayQuestion_intro)
        lay_problema = findViewById(R.id.lay_problema)
        btnteste = findViewById(R.id.btnteste)
        btnTesteProblema = findViewById(R.id.btnTesteProblema)
        btnTestePerfil = findViewById(R.id.btnTeste2)
    }

    fun setupMenu(){

        val toggle =
            ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
            } else {
                drawer.openDrawer(GravityCompat.START);
            }

            when (it.itemId) {
                R.id.nav_user -> {

                    true
                }
                R.id.nav_perfil -> {

                    true
                }
                else -> false
            }
        }
    }

    fun openIntroQuest(){

        indexModels.openIntroQuest(findViewById<ConstraintLayout>(R.id.LayQuestion_intro), findViewById<RecyclerView>(R.id.question_intro_recyclerView), this)
        val btnAbrePergunta: Button = findViewById(R.id.questionIntro_btn)
        btnAbrePergunta.setOnClickListener {
            openProblema(0)
        }
    }

    fun openProblema(id: Int){

        layIntroQuest.visibility = View.GONE
       // layInicial.visibility = View.GONE
        lay_problema.visibility = View.VISIBLE

        var objectQuestions: ObjectQuestions = ObjectQuestions()
        objectQuestions = ConsultsQuestionsModel.selectQuestionPerId(id);

        val layRespostas: ConstraintLayout = findViewById(R.id.lay_respostaMultipla)

        Glide.with(this).load(objectQuestions.imagem).into(findViewById(R.id.problema_image))//imagem principal

        //1 - multipla  //2 - clicavel //3 - AB

        if (objectQuestions.type == 1){

            val btnAbreRespostas: Button = findViewById(R.id.problema_btnAbreRespostas)
            btnAbreRespostas.visibility = View.VISIBLE
            btnAbreRespostas.setOnClickListener {
                layRespostas.visibility = View.VISIBLE
                val btnFechaRespostas: Button = findViewById(R.id.resposta_btnFechar)
                btnFechaRespostas.setOnClickListener {
                    layRespostas.visibility = View.GONE
                }
            }

            val btnA: Button = findViewById(R.id.resposta_A)
            val btnB: Button = findViewById(R.id.resposta_B)
            val btnC: Button = findViewById(R.id.resposta_C)
            val btnD: Button = findViewById(R.id.resposta_D)
            val btnE: Button = findViewById(R.id.resposta_E)

            btnA.setOnClickListener {
                if (indexControllers.isCorrectAnswer("a", objectQuestions.alternativacorreta)){
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                    afterProblem(true, id)
                } else {
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                    afterProblem(false, id)
                }
            }
            btnB.setOnClickListener {
                if (indexControllers.isCorrectAnswer("b", objectQuestions.alternativacorreta)){
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                    afterProblem(true, id)
                } else {
                    afterProblem(false, id)
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                }
            }
            btnC.setOnClickListener {
                if (indexControllers.isCorrectAnswer("c", objectQuestions.alternativacorreta)){
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                    afterProblem(true, id)
                } else {
                    afterProblem(false, id)
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                }
            }
            btnD.setOnClickListener {
                if (indexControllers.isCorrectAnswer("d", objectQuestions.alternativacorreta)){
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                    afterProblem(true, id)
                } else {
                    afterProblem(false, id)
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                }
            }
            btnE.setOnClickListener {
                if (indexControllers.isCorrectAnswer("e", objectQuestions.alternativacorreta)){
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                    afterProblem(true, id)
                } else {
                    afterProblem(false, id)
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                }
            }

        } else if (objectQuestions.type == 2){

            val layProblema: ConstraintLayout = findViewById(R.id.lay_problema)

            if (objectQuestions.itemClicavel1.equals("sim")){

                val imageView = ImageView(this)
                // setting height and width of imageview
                imageView.layoutParams = LinearLayout.LayoutParams(200, 200)
                imageView.x = objectQuestions.item1X.toFloat() //setting margin from left
                imageView.y = objectQuestions.item1Y.toFloat() //setting margin from top
                //imageView.elevation=10f

                layProblema.addView(imageView) //adding image to the layout
                Glide.with(this).load(R.drawable.navio).into(imageView)
                //a imagem pode vir dentro de opção A

                imageView.setOnClickListener {
                    if (objectQuestions.alternativacorreta.equals("a")){
                        afterProblem(true, id)
                        Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                    } else {
                        afterProblem(false, id)
                        Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            if (objectQuestions.itemClicavel2.equals("sim")){

                val imageView = ImageView(this)
                // setting height and width of imageview
                imageView.layoutParams = LinearLayout.LayoutParams(150, 150)
                imageView.x = objectQuestions.item2X.toFloat() //setting margin from left
                imageView.y = objectQuestions.item2Y.toFloat() //setting margin from top
                //imageView2.elevation=10f

                layProblema.addView(imageView) //adding image to the layout
                Glide.with(this).load(R.drawable.navio).into(imageView)


                imageView.setOnClickListener {
                    if (objectQuestions.alternativacorreta.equals("b")){
                        afterProblem(true, id)
                        Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                    } else {
                        afterProblem(false, id)
                        Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                    }
                }

            }

        } else {
            //codigo AB
            val layAB: ConstraintLayout = findViewById(R.id.lay_tipoSimNao)
            layAB.visibility = View.VISIBLE

           indexModels.placeImage(findViewById(R.id.perguntaAB_img), this)

            val txtA: TextView = findViewById(R.id.perguntaAB_opcaoA)
            val txtB: TextView = findViewById(R.id.perguntaAB_opcaoB)

            txtA.setText(objectQuestions.multiplaa)
            txtB.setText(objectQuestions.multiplab)

            txtA.setOnClickListener {
                if (objectQuestions.alternativacorreta =="a"){
                    afterProblem(true, id)
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                } else {
                    afterProblem(false, id)
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                }
            }

            txtB.setOnClickListener {
                if (objectQuestions.alternativacorreta == "b"){
                    afterProblem(true, id)
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                } else {
                    afterProblem(false, id)
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                }
            }


        }

    }

    fun afterProblem(correct: Boolean, id: Int){

        val layResultado: ConstraintLayout = findViewById(R.id.lay_resultado)
        val layProblema: ConstraintLayout = findViewById(R.id.lay_problema)
        val txt: TextView = findViewById(R.id.resultado_txt)

        layProblema.visibility = View.GONE
        layResultado.visibility = View.VISIBLE

        if (correct){
            txt.setText("Acertou!")
            ConsultsQuestionsModel.somaQuestions1(true, id)
        } else {
            txt.setText("Errou")
            ConsultsQuestionsModel.somaQuestions1(false, id)
        }
    }

    //click listener da primeira recycleview
    interface ClickListener {
        fun onClick(view: View, position: Int)

        fun onLongClick(view: View?, position: Int)
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
}