package com.bino.wilsonsonsapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bino.wilsonsonsapp.Controllers.AdminControllers
import com.bino.wilsonsonsapp.Controllers.ControllersUniversais
import com.bino.wilsonsonsapp.Controllers.IndexControllers
import com.bino.wilsonsonsapp.Models.ConsultsQuestionsModel
import com.bino.wilsonsonsapp.Models.IndexModels
import com.bino.wilsonsonsapp.Models.ObjectQuestions
import com.bino.wilsonsonsapp.Models.ObjectUser
import com.bino.wilsonsonsapp.Utils.ListCursosAdapter
import com.bino.wilsonsonsapp.Utils.mySharedPrefs
import com.bino.wilsonsonsapp.Utils.startSound
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class IndexActivityNew : AppCompatActivity() {

    val LOGD : String = "teste"

    lateinit var toolbar: Toolbar
    lateinit var drawer: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var layInicial: ConstraintLayout
    lateinit var layIntroQuest: ConstraintLayout
    lateinit var lay_problema: ConstraintLayout
    lateinit var layListas: ConstraintLayout
    lateinit var btnteste: Button
    lateinit var btnTesteProblema: Button
    lateinit var userAvatar: ImageView

    lateinit var auth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference

    lateinit var mySharedPrefs: mySharedPrefs
    lateinit var objectUser: ObjectUser

    lateinit var objectQuestionsList: List<ObjectQuestions>
    lateinit var objectQuestions: ObjectQuestions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_index_new)
        setContentView(R.layout.activity_menu)

        objectQuestions = ObjectQuestions()

        auth = FirebaseAuth.getInstance()

        if (!IndexModels.checkCadInfo()){
            openPopUpCadInfo("Completar", "Fazer depois")
        }

        // como pegar o int das imagens
        var bmp = R.drawable.intro1img1
        Log.d("teste", "o valor de intro1img1 "+bmp)
        bmp = R.drawable.intro1img2
        Log.d("teste", "o valor de intro1img2 "+bmp)
        bmp = R.drawable.intro1img3
        Log.d("teste", "o valor de intro1img3 "+bmp)

        //codigos
        //intro1img1 2131165353
        //intro1img2 2131165354
        //intro1img3 2131165355


    }

    override fun onStart() {
        super.onStart()

        loadComponents()

        val situacao = intent.getStringExtra("email")
        if (!situacao.equals("semLogin")){
            //IndexModels.uId = auth.currentUser!!.uid.toString()
            objectUser.key = auth.currentUser!!.uid.toString()
        }

        if (!IndexModels.isverified){ //para carregar uma unica vez

            IndexModels.isverified=true

            if (IndexControllers.isNetworkAvailable(this) && situacao.equals("semLogin")){
                //   openPopUp("Opa! Você está conectado na internet", "Você agora possui internet e ainda não fez login. Vamos fazer o login para salvar poder salvar seus dados?", true, "Sim, fazer login", "Não", "login")
            } else if (IndexControllers.isNetworkAvailable(this)){
                //verificar se tem novos mundos para baixar
                //chamar um método para baixar os conteudos e em seguida informar ao usuário que existem atualizações e novas fases

                //primeiro pega alerta no bd se tiver
                queryConvocacoes()
                updateCertificatesOnLine()

            } else {
                //verifica se tem algo no sharedPrefs de alerta
                verificaAlertaTreinamento()
                updateCertificatesOffLine()
            }

        }

        btnteste.setOnClickListener {
            IndexModels.moveThePlayer(userAvatar)
        }


        IndexModels.placeBackGroundAsMap(findViewById(R.id.backgroundPlaceHolder), this, 5, findViewById(R.id.layIndex), findViewById(R.id.playerAvatar))
        placePlayButtonInitialy(findViewById(R.id.layIndex))

        val anim = AnimationUtils.loadAnimation(this, R.anim.anim_open_down_showfase)
        IndexModels.placeArrow(anim, findViewById(R.id.faseShow))

        setupMenu()

        //anima nuvem
        val nuvem: ImageView = findViewById(R.id.imgnuvem)
        val movenuvem = AnimationUtils.loadAnimation(this, R.anim.movenuvem)
        nuvem.startAnimation(movenuvem)
        //anima nuvem2
        val nuvem2: ImageView = findViewById(R.id.imgnuvem2)
        val movenuvem2 = AnimationUtils.loadAnimation(this, R.anim.movenuvem2)
        nuvem2.startAnimation(movenuvem2)

        //verificar a fase do user e coloca no lugar certo
        IndexModels.checkUserCheckpoint(userAvatar)
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
        layListas = findViewById(R.id.lay_listas)
        userAvatar = findViewById(R.id.playerAvatar)

        //apaguei no merge
        databaseReference = FirebaseDatabase.getInstance().reference

        mySharedPrefs = mySharedPrefs(this)
        objectUser = ObjectUser()
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
                R.id.nav_perfil -> {
                    val intent = Intent(this, perfilActivity::class.java)
                    //intent.putExtra("email", "semLogin")
                    startActivity(intent)
                    true
                }
                R.id.nav_course -> {
                    //val intent = Intent(this, AdminActivityNew::class.java)
                    //startActivity(intent)
                    showListedItems("curso")
                    true
                }
                R.id.nav_certificate -> {
                    //val intent = Intent(this, AdminActivityNew::class.java)
                    //startActivity(intent)
                    showListedItems("certificados")
                    true
                }
                R.id.nav_skills -> {
                    val intent = Intent(this, DesempenhoActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_links -> {
                    ControllersUniversais.makeToast(this, "Dosponível em breve")
                    true
                }
                R.id.nav_gestion -> {
                    val intent = Intent(this, AdminActivityNew::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_config -> {
                    ControllersUniversais.makeToast(this, "Dosponível em breve")
                    true
                }
                else -> false
            }
        }
    }

    fun updateCertificatesOnLine(){

        val rootRef = databaseReference.child("funcionarios").child(IndexModels.userBd)
        rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {


            }

            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists()){

                    //TODO("Not yet implemented")
                    var values: String
                    var cont=0

                    values = p0.child("certificados").value.toString()
                    val certificados = values.toInt()

                    while (cont<certificados){
                        cont++
                        var field = "certificado"+cont.toString()
                        val certificado = p0.child(field).value.toString()
                        IndexModels.arrayCertificados.add(certificado)
                        field = "valcert"+cont.toString()
                        val validade = p0.child(field).value.toString()
                        IndexModels.arrayCertificadosValidade.add(validade)
                        mySharedPrefs.addCertificados(certificados, this@IndexActivityNew) //salva no shared para quando estiver offline
                    }
                }
            }
        })
    }

    fun updateCertificatesOffLine(){

        mySharedPrefs.loadCertificates() //carrega os dados nos arrays
        //showListedItems("cert")
    }

    private fun placePlayButtonInitialy (layout: ConstraintLayout){

        IndexModels.btnPlayTheLevel = ImageView(this)
        IndexModels.btnPlayTheLevel.layoutParams = LinearLayout.LayoutParams(80,80)
        IndexModels.btnPlayTheLevel.x = IndexModels.arrayPosicoesX.get(IndexModels.posicaoUser).toFloat()
        IndexModels.btnPlayTheLevel.y = IndexModels.arrayPosicoesY.get(IndexModels.posicaoUser).toFloat()
        IndexModels.btnPlayTheLevel.elevation = 45f
        layout?.addView(IndexModels.btnPlayTheLevel) //adding image to the layout
        Glide.with(this).load(R.drawable.pontovermelho).into(IndexModels.btnPlayTheLevel)

        IndexModels.btnPlayTheLevel.setOnClickListener {
            openIntroQuest(objectQuestions)
        }

        userAvatar.setOnClickListener {
            IndexModels.btnPlayTheLevel.performClick()
        }

    }


    fun openIntroQuest(objectQuestions: ObjectQuestions) {

        IndexModels.openIntroQuest(
            findViewById<ConstraintLayout>(R.id.LayQuestion_intro),
            findViewById<RecyclerView>(R.id.question_intro_recyclerView),
            this,
            objectQuestions
        )
        val btnAbrePergunta: Button = findViewById(R.id.questionIntro_btn)
        btnAbrePergunta.setOnClickListener {
            openProblema(objectQuestions)
        }
    }

    fun openProblema(objectQuestions: ObjectQuestions){

        layIntroQuest.visibility = View.GONE
        // layInicial.visibility = View.GONE
        lay_problema.visibility = View.VISIBLE

        when(objectQuestions.id_intro) {
            1 -> startSound(this, R.raw.question1)
            2 -> startSound(this, R.raw.question1)
            else -> startSound(this, R.raw.question1)
        }

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
                if (IndexControllers.isCorrectAnswer("a", objectQuestions.alternativacorreta)){
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                    afterProblem(true, objectQuestions.id)
                } else {
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                    afterProblem(false, objectQuestions.id)
                }
            }
            btnB.setOnClickListener {
                if (IndexControllers.isCorrectAnswer("b", objectQuestions.alternativacorreta)){
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                    afterProblem(true, objectQuestions.id)
                } else {
                    afterProblem(false, objectQuestions.id)
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                }
            }
            btnC.setOnClickListener {
                if (IndexControllers.isCorrectAnswer("c", objectQuestions.alternativacorreta)){
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                    afterProblem(true, objectQuestions.id)
                } else {
                    afterProblem(false, objectQuestions.id)
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                }
            }
            btnD.setOnClickListener {
                if (IndexControllers.isCorrectAnswer("d", objectQuestions.alternativacorreta)){
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                    afterProblem(true, objectQuestions.id)
                } else {
                    afterProblem(false, objectQuestions.id)
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                }
            }
            btnE.setOnClickListener {
                if (IndexControllers.isCorrectAnswer("e", objectQuestions.alternativacorreta)){
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                    afterProblem(true, objectQuestions.id)
                } else {
                    afterProblem(false, objectQuestions.id)
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                }
            }

        } else if (objectQuestions.type == 2){

            val altura = 80 // isto vai vir do bd
            val largura = 80 //vai vir do bd

            val layProblema: ConstraintLayout = findViewById(R.id.lay_problema)

            val imageView = ImageView(this)
            // setting height and width of imageview
            imageView.layoutParams = LinearLayout.LayoutParams(largura, altura)
            imageView.x = objectQuestions.item1X.toFloat() //setting margin from left
            imageView.y = objectQuestions.item1Y.toFloat() //setting margin from top

            layProblema.addView(imageView) //adding image to the layout
            Glide.with(this).load(R.drawable.navio).into(imageView)
            //a imagem pode vir dentro de opção A

            imageView.setOnClickListener {
                afterProblem(true, objectQuestions.id)
                Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
            }

            layProblema.setOnClickListener {
                afterProblem(false, objectQuestions.id)
                Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
            }

            /*
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
            }*/

        } else {
            //codigo AB
            val layAB: ConstraintLayout = findViewById(R.id.lay_tipoSimNao)
            layAB.visibility = View.VISIBLE

            IndexModels.placeImage(findViewById(R.id.perguntaAB_img), this)

            val txtA: TextView = findViewById(R.id.perguntaAB_opcaoA)
            val txtB: TextView = findViewById(R.id.perguntaAB_opcaoB)

            txtA.setText(objectQuestions.multiplaa)
            txtB.setText(objectQuestions.multiplab)

            txtA.setOnClickListener {
                if (objectQuestions.alternativacorreta =="a"){
                    afterProblem(true, objectQuestions.id)
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                } else {
                    afterProblem(false, objectQuestions.id)
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                }
            }

            txtB.setOnClickListener {
                if (objectQuestions.alternativacorreta == "b"){
                    afterProblem(true, objectQuestions.id)
                    Toast.makeText(this, "Acertou", Toast.LENGTH_SHORT).show()
                } else {
                    afterProblem(false, objectQuestions.id)
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun afterProblem(correct: Boolean, id: Int){

        val layResultado: ConstraintLayout = findViewById(R.id.lay_resultado)
        val layProblema: ConstraintLayout = findViewById(R.id.lay_problema)
        val txt: TextView = findViewById(R.id.resultado_txt)
        val imgAcertoErro: ImageView = findViewById(R.id.resultado_img)
        val msg: TextView = findViewById(R.id.resultado_mensagem)
        val btnSalvarMat: Button = findViewById(R.id.resultado_btnSalvarMat)
        val btnProxima: Button = findViewById(R.id.resultaldo_btnProxima)

        btnSalvarMat.setOnClickListener {
            ControllersUniversais.makeToast(this, "Recurso disponível em breve")
        }

        btnProxima.setOnClickListener {
            //andar com o player
            IndexModels.moveThePlayer(userAvatar)
        }

        layProblema.visibility = View.GONE
        layResultado.visibility = View.VISIBLE

        if (correct){
            txt.setText("Acertou!")
            ConsultsQuestionsModel.somaQuestions1( true, id)
            msg.setText("Mensagem de acerto")
            Glide.with(this).load(R.drawable.acertosimbol).into(imgAcertoErro)
            IndexModels.setTheResultInMap(this, layInicial, true)
        } else {
            txt.setText("Errou")
            msg.setText("Menagem de erro")
            Glide.with(this).load(R.drawable.errosimbol).into(imgAcertoErro)
            ConsultsQuestionsModel.somaQuestions1(false, id)
            IndexModels.setTheResultInMap(this, layInicial, true)
            IndexModels.moveThePlayer(userAvatar)
        }

        /*
        if ("tem video ".equals("tem video")){//pegar do banco de dados
            val cad_youtubelink: ImageView = findViewById(R.id.cad_youtubelink)
            cad_youtubelink.visibility = View.VISIBLE
            cad_tvTemLink.visibility = View.VISIBLE
            cad_youtubelink.setOnClickListener {
                ControllersUniversais.makeToast(this, "Em breve")
            }
            IndexModels.moveThePlayer(userAvatar)
        }
         */
    }

    fun queryConvocacoes(){

        //databaseReference.child("convocacoes").child(adminModels.bd.get(position)).child("userBd")

        val rootRef = databaseReference.child("convocacoes").child(IndexModels.userBd)
        rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists()){

                    //TODO("Not yet implemented")
                    var values: String

                    values = p0.child("dataEmbarque").value.toString()
                    if (AdminControllers.checkCertificateValidit(values)){
                        //esta vencido. N precisa pegar nenhum dado e pode apagar o alerta
                        rootRef.child("convocacoes").child(IndexModels.userBd).removeValue()
                        mySharedPrefs.removeAlert()

                    } else {

                        val recebido = p0.child("recebido").value.toString()
                        if (recebido.equals("nao")){ //se for == sim é porque já está salvo no sharedPrefs

                            //pegar os dados
                            values = p0.child("embarcacao").value.toString()
                            IndexModels.alertaEmbarcacao = values
                            values = p0.child("dataEmbarque").value.toString()
                            IndexModels.alertaDataEmbarque = values
                            rootRef.child("recebido").setValue("sim")

                            mySharedPrefs.setAlertInfo(IndexModels.alertaDataEmbarque, IndexModels.alertaEmbarcacao)

                        }
                        verificaAlertaTreinamento()
                    }

                    EncerraDialog()
                }
            }
        })
    }

    fun verificaAlertaTreinamento(){

        mySharedPrefs.getAlertInfo()
        if (!IndexModels.alertaEmbarcacao.equals("nao")){ //se for diferente de não é porque tem alerta

            if (AdminControllers.checkCertificateValidit(IndexModels.alertaDataEmbarque)){
                //esta vencido. N precisa pegar nenhum dado e pode apagar o alerta
                //nao fazer nada. Nao vai exibir o botão mas tb nao apaga no bd. Vai apagar quando tiver internet
            } else {

                val btnAlerta: Button = findViewById(R.id.btnAlertatreino)
                btnAlerta.visibility = View.VISIBLE
                btnAlerta.setOnClickListener {
                    //abrir procedimentos de treino
                    showListedItems("curso") //neste momento vai abrir a lista de cursos
                }
            }
        }
    }

    fun showListedItems(tipo: String){

        //ControllersUniversais.openCloseLay(layInicial, layListas)
        layListas.visibility = View.VISIBLE
        toolbar.visibility = View.GONE

        val btnVoltar: Button = findViewById(R.id.lista_itens_btnVoltar)
        btnVoltar.setOnClickListener {
            //ControllersUniversais.openCloseLay(layListas, layInicial)
            layListas.visibility = View.GONE
            toolbar.visibility = View.VISIBLE
        }

        val recyclerView: RecyclerView = findViewById(R.id.listaCurso_recyclerView)
        val textView: TextView = findViewById(R.id.lista_items_tvTitulo)

        if (tipo.equals("curso")){
            IndexModels.loadCourses()
            textView.setText("Cursos")

        } else {
            textView.setText("Certificados")
        }

        mountRecyclerViewCourses(recyclerView, tipo)

        recyclerView.addOnItemTouchListener(RecyclerTouchListener(this, recyclerView!!, object: ClickListener{

            override fun onClick(view: View, position: Int) {
                Log.d("teste", IndexModels.arrayCursos.get(position))
            }

            override fun onLongClick(view: View?, position: Int) {

            }
        }))
    }

    private fun mountRecyclerViewCourses(recyclerView: RecyclerView, tipo: String){

        var adapter: ListCursosAdapter= ListCursosAdapter(this, IndexModels.arrayCursos, IndexModels.arrayCertificadosValidade, tipo) //arrayCertificadosValidade não será usado aqui. Está apenas preenchendo parametro

        if (tipo.equals("curso")){
            adapter = ListCursosAdapter(this, IndexModels.arrayCursos, IndexModels.arrayCertificadosValidade, tipo) //arrayCertificadosValidade não será usado aqui. Está apenas preenchendo parametro
        } else {
            adapter = ListCursosAdapter(this, IndexModels.arrayCertificados, IndexModels.arrayCertificadosValidade, tipo) //arrayCertificadosValidade não será usado aqui. Está apenas preenchendo parametro
        }

        //define o tipo de layout (linerr, grid)
        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)

        //coloca o adapter na recycleview
        recyclerView.adapter = adapter

        recyclerView.layoutManager = linearLayoutManager

        // Notify the adapter for data change.
        adapter.notifyDataSetChanged()
    }

    fun openPopUpCadInfo (btnSim: String, btnNao: String) {

        val layout: ConstraintLayout = findViewById(R.id.layPopup)
        layout.visibility = View.VISIBLE

        // Get the widgets reference from custom view
        val buttonPopupN = findViewById<Button>(R.id.popupInfos_btnNao)
        val buttonPopupS = findViewById<Button>(R.id.popupInfos_btnSim)

        buttonPopupN.setText(btnNao)
        buttonPopupS.setText(btnSim)

        layout.setOnClickListener {
            layout.visibility = View.GONE
        }

        buttonPopupN.setOnClickListener {
            layout.visibility = View.GONE
        }

        buttonPopupS.setOnClickListener {

            layout.visibility = View.GONE
            val intent = Intent(this, perfilActivity::class.java)
            intent.putExtra("infos", "sim")
            startActivity(intent)
        }
    }

    fun ChamaDialog() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        ) //este serve para bloquear cliques que pdoeriam dar erros
        val layout = findViewById<RelativeLayout>(R.id.LayoutProgressBar)
        layout.visibility = View.VISIBLE
        val spinner = findViewById<ProgressBar>(R.id.progressBar1)
        spinner.visibility = View.VISIBLE
    }

    //este método torna invisivel um layout e encerra o dialogbar spinner.
    fun EncerraDialog() {
        val layout = findViewById<RelativeLayout>(R.id.LayoutProgressBar)
        val spinner = findViewById<ProgressBar>(R.id.progressBar1)
        layout.visibility = View.GONE
        spinner.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE) //libera os clicks
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