package com.bino.wilsonsonsapp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.*
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.bino.wilsonsonsapp.Controllers.indexControllers
import com.bino.wilsonsonsapp.Models.ConsultsModel
import com.bino.wilsonsonsapp.Models.ObjectQuestions
import com.bino.wilsonsonsapp.Models.indexModels
import com.bino.wilsonsonsapp.Utils.CircleTransform
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

        indexModels.placeBackGroundAsMap(findViewById(R.id.backgroundPlaceHolder), this, 5, findViewById(R.id.layIndex), findViewById(R.id.playerAvatar))

        if (indexModels.checkCertificate()){
            //avisa o user
            openPopUp("Atenção", "Seu certificado vai vencer dentro de 30 dias.", false, "n", "n", "n")
        }

        setupMenu()

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

    fun openPopUp (titulo: String, texto:String, exibeBtnOpcoes:Boolean, btnSim: String, btnNao: String, call: String) {
        //exibeBtnOpcoes - se for não, vai exibir apenas o botão com OK, sem opção. Senão, exibe dois botões e pega os textos deles de btnSim e btnNao

        //EXIBIR POPUP
        // Initialize a new layout inflater instance
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Inflate a custom view using layout inflater
        val view = inflater.inflate(R.layout.popup_model,null)

        // Initialize a new instance of popup window
        val popupWindow = PopupWindow(
            view, // Custom view to show in popup window
            LinearLayout.LayoutParams.MATCH_PARENT, // Width of popup window
            LinearLayout.LayoutParams.WRAP_CONTENT // Window height
        )

        // Set an elevation for the popup window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }


        // If API level 23 or higher then execute the code
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // Create a new slide animation for popup window enter transition
            val slideIn = Slide()
            slideIn.slideEdge = Gravity.TOP
            popupWindow.enterTransition = slideIn

            // Slide animation for popup window exit transition
            val slideOut = Slide()
            slideOut.slideEdge = Gravity.RIGHT
            popupWindow.exitTransition = slideOut

        }


        // Get the widgets reference from custom view
        val buttonPopupN = view.findViewById<Button>(R.id.btnReclamar)
        val buttonPopupS = view.findViewById<Button>(R.id.BtnRecebimento)
        val buttonPopupOk = view.findViewById<Button>(R.id.popupBtnOk)
        val txtTitulo = view.findViewById<TextView>(R.id.popupTitulo)
        val txtTexto = view.findViewById<TextView>(R.id.popupTexto)

        if (exibeBtnOpcoes){
            //vai exibir os botões com textos e esconder o btn ok
            buttonPopupOk.visibility = View.GONE
            //exibe e ajusta os textos dos botões
            buttonPopupN.text = btnNao
            buttonPopupS.text = btnSim

            // Set a click listener for popup's button widget
            buttonPopupN.setOnClickListener{
                // Dismiss the popup window
                popupWindow.dismiss()
            }

        } else {

            //vai esconder os botões com textos e exibir o btn ok
            buttonPopupOk.visibility = View.VISIBLE
            //exibe e ajusta os textos dos botões
            buttonPopupN.visibility = View.GONE
            buttonPopupS.visibility = View.GONE


            buttonPopupOk.setOnClickListener{
                // Dismiss the popup window
                popupWindow.dismiss()
            }

        }

        txtTitulo.text = titulo
        txtTexto.text = texto


        // Set a dismiss listener for popup window
        popupWindow.setOnDismissListener {
            //Fecha a janela ao clicar fora também
        }

        //lay_root é o layout parent que vou colocar a popup
        val lay_root: ConstraintLayout = findViewById(R.id.layoutPrincipal)

        // Finally, show the popup window on app
        TransitionManager.beginDelayedTransition(lay_root)
        popupWindow.showAtLocation(
            lay_root, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )

        if (call.equals("login")){
            finish()
        }


    }

    fun openIntroQuest(){

        indexModels.openIntroQuest(findViewById<ConstraintLayout>(R.id.LayQuestion_intro), findViewById<RecyclerView>(R.id.question_intro_recyclerView), this)
        val btnAbrePergunta: Button = findViewById(R.id.questionIntro_btn)
        btnAbrePergunta.setOnClickListener {
            openProblema()
        }
    }

    fun openProblema(){

        layIntroQuest.visibility = View.GONE
       // layInicial.visibility = View.GONE
        lay_problema.visibility = View.VISIBLE

        //var objectQuestions: ObjectQuestions = ObjectQuestions()

        val id: Int = 0
        val skill = "fazer"
        val tipo = "AB"   //clicavel  multipla  AB
        val bdDaIntro = "idDaIntro"
        val opcaoA = "opcaoA"
        val opcaoB = "opcaoB"
        val opcaoC = "opcaoC"
        val opcaoD = "opcaoD"
        val opcaoE = "opcaoE"
        val correta = "opcaoA"
        val imagem = "https://firebasestorage.googleapis.com/v0/b/wilsonsonshack.appspot.com/o/problemas%2Fquadrinho2.jpg?alt=media&token=843e406c-cca5-4fa8-9ad5-5a9f1adce458"
        val acertos = 10
        val erros = 0
        val itemClicavel1 = "sim"
        val item1X = "100"
        val item1Y = "200"
        val itemclicavel2 = "nao"
        val item2X = "500"
        val item2Y = "500"
        val pontos = "1000"
        val totalPontos = "20000"

        val layRespostas: ConstraintLayout = findViewById(R.id.lay_respostaMultipla)

        Glide.with(this).load(imagem).into(findViewById(R.id.problema_image))//imagem principal

        //1 - multipla  //2 - clicavel //3 - AB

        if (tipo.equals("multipla")){

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
                if (indexControllers.isCorrectAnswer("a", correta)){
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                    afterProblem(true, skill, acertos, erros)
                } else {
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                    afterProblem(false, skill, acertos, erros)
                }
            }
            btnB.setOnClickListener {
                if (indexControllers.isCorrectAnswer("b", correta)){
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                    afterProblem(true, skill, acertos, erros)
                } else {
                    afterProblem(false, skill, acertos, erros)
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                }
            }
            btnC.setOnClickListener {
                if (indexControllers.isCorrectAnswer("c", correta)){
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                    afterProblem(true, skill, acertos, erros)
                } else {
                    afterProblem(false, skill, acertos, erros)
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                }
            }
            btnD.setOnClickListener {
                if (indexControllers.isCorrectAnswer("d", correta)){
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                    afterProblem(true, skill, acertos, erros)
                } else {
                    afterProblem(false, skill, acertos, erros)
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                }
            }
            btnE.setOnClickListener {
                if (indexControllers.isCorrectAnswer("e", correta)){
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                    afterProblem(true, skill, acertos, erros)
                } else {
                    afterProblem(false, skill, acertos, erros)
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                }
            }

        } else if (tipo.equals("clicavel")){

            val layProblema: ConstraintLayout = findViewById(R.id.lay_problema)

            if (itemClicavel1.equals("sim")){

                val imageView = ImageView(this)
                // setting height and width of imageview
                imageView.layoutParams = LinearLayout.LayoutParams(200, 200)
                imageView.x = item1X.toFloat() //setting margin from left
                imageView.y = item1Y.toFloat() //setting margin from top
                //imageView.elevation=10f

                layProblema.addView(imageView) //adding image to the layout
                Glide.with(this).load(R.drawable.navio).into(imageView)
                //a imagem pode vir dentro de opção A

                imageView.setOnClickListener {
                    if (correta.equals("a")){
                        afterProblem(true, skill, acertos, erros)
                        Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                    } else {
                        afterProblem(false, skill, acertos, erros)
                        Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                    }
                }

            }

            if (itemclicavel2.equals("sim")){

                val imageView = ImageView(this)
                // setting height and width of imageview
                imageView.layoutParams = LinearLayout.LayoutParams(150, 150)
                imageView.x = item2X.toFloat() //setting margin from left
                imageView.y = item2Y.toFloat() //setting margin from top
                //imageView2.elevation=10f

                layProblema.addView(imageView) //adding image to the layout
                Glide.with(this).load(R.drawable.navio).into(imageView)


                imageView.setOnClickListener {
                    if (correta.equals("b")){
                        afterProblem(true, skill, acertos, erros)
                        Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                    } else {
                        afterProblem(false, skill, acertos, erros)
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

            txtA.setText(opcaoA)
            txtB.setText(opcaoB)

            txtA.setOnClickListener {
                if (correta=="a"){
                    afterProblem(true, skill, acertos, erros)
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                } else {
                    afterProblem(false, skill, acertos, erros)
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                }
            }

            txtB.setOnClickListener {
                if (correta=="b"){
                    afterProblem(true, skill, acertos, erros)
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                } else {
                    afterProblem(false, skill, acertos, erros)
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                }
            }


        }

    }

    fun afterProblem(correct: Boolean, skill: String, acertos: Int, erros: Int){

        val layResultado: ConstraintLayout = findViewById(R.id.lay_resultado)
        val layProblema: ConstraintLayout = findViewById(R.id.lay_problema)
        val txt: TextView = findViewById(R.id.resultado_txt)

        layProblema.visibility = View.GONE
        layResultado.visibility = View.VISIBLE

        if (correct){
            txt.setText("Acertou!")
        } else {
            txt.setText("Errou")
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