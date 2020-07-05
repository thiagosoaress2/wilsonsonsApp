package com.bino.wilsonsonsapp

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bino.wilsonsonsapp.Controllers.AdminControllers
import com.bino.wilsonsonsapp.Controllers.ControllersUniversais
import com.bino.wilsonsonsapp.Models.AdminModels
import com.bino.wilsonsonsapp.Models.IndexModels
import com.bino.wilsonsonsapp.Models.ObjectUser
import com.bino.wilsonsonsapp.Utils.CircleTransform
import com.bino.wilsonsonsapp.Utils.ListFuncComCertVencendoAdapter
import com.bino.wilsonsonsapp.Utils.ListFuncPorEstadoAdapter
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.google.firebase.database.*
import java.util.*

class AdminActivityNew : AppCompatActivity() {

    lateinit var paginaIndex: ConstraintLayout
    lateinit var paginaColabAptos: ConstraintLayout
    lateinit var paginaDetails: ConstraintLayout
    lateinit var paginainfo1: ConstraintLayout
    lateinit var paginainfo2: ConstraintLayout
    lateinit var paginaValidades: ConstraintLayout
    lateinit var btnOpenCertfsList: Button
    lateinit var btnOpenConsultColab: Button
    lateinit var btnFecharActivity: Button

    lateinit var databaseReference: DatabaseReference
    lateinit var objectUser: ObjectUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_new)

        loadComponents()

    }

    override fun onStart() {
        super.onStart()

        btnOpenConsultColab.setOnClickListener {
            paginaColabAptos()
        }

        btnOpenCertfsList.setOnClickListener {
            openListCertfs()
        }

        btnFecharActivity.setOnClickListener {
            finish()
        }


    }

    fun loadComponents(){
        paginaIndex = findViewById(R.id.paginaIndex)
        paginaColabAptos = findViewById(R.id.paginaColabAptos)
        paginaDetails = findViewById(R.id.layInfosDetalhadas)
        paginainfo1 = findViewById(R.id.layInfo2)
        paginainfo2 = findViewById(R.id.layInfo2)
        paginaValidades = findViewById(R.id.layValidadColabs)
        databaseReference = FirebaseDatabase.getInstance().reference
        btnOpenCertfsList = findViewById(R.id.btnListValidt)
        btnOpenConsultColab = findViewById(R.id.btnConsultColaboradoresAptos)
        btnFecharActivity = findViewById(R.id.indexBtnFechar)

        objectUser = ObjectUser()
    }

    fun openListCertfs(){
        paginaValidades.visibility = View.VISIBLE
        paginaIndex.visibility = View.GONE

        val btnVoltar: Button = findViewById(R.id.validad_btnVoltar)
        val btnFechar: Button = findViewById(R.id.validad_btnFechar)

        btnFechar.setOnClickListener {
            finish()
        }

        btnVoltar.setOnClickListener {
            paginaValidades.visibility = View.GONE
            paginaIndex.visibility = View.VISIBLE
        }

        queryInicialCertVencendo()

    }

    fun queryInicialCertVencendo(){

        ChamaDialog()
        val rootRef = databaseReference.child("funcionarios")
        rootRef.orderByChild("tipo").equalTo("colaborador")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    if (dataSnapshot.exists()) {

                        for (querySnapshot in dataSnapshot.children) {


                            var values: String = "nao"

                            val bd = querySnapshot.key.toString()

                            values = querySnapshot.child("nome").value.toString()
                            AdminModels.nome.add(values)

                            values = querySnapshot.child("funcao").value.toString()
                            AdminModels.funcao.add(values)

                            values = querySnapshot.child("contato").value.toString()
                            AdminModels.whats.add(values)

                            values = querySnapshot.child("img").value.toString()
                            AdminModels.img.add(values)

                            values = querySnapshot.child("certificados").value.toString()
                            val qntCert = values.toInt()

                            var cont=0
                            while (cont<qntCert){
                                var field = "certificado"+(cont+1).toString()
                                values = querySnapshot.child(field).value.toString()

                                //entra aqui se nao tiver filtro
                                AdminModels.certificados.add(bd+"!*!??#"+values)
                                field = "valcert"+(cont+1).toString()
                                values = querySnapshot.child(field).value.toString()
                                AdminModels.validCert.add(values)

                                AdminModels.bd.add(bd)

                                cont++
                            }


                        }

                    }

                    EncerraDialog()
                    montaRecyclerListaCertifsVencendo()

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    EncerraDialog()
                    // ...
                }
            })

    }

    fun montaRecyclerListaCertifsVencendo (){

        var adapter: ListFuncComCertVencendoAdapter = ListFuncComCertVencendoAdapter(this, AdminModels.nome, AdminModels.funcao, AdminModels.certificados, AdminModels.validCert, AdminModels.bd)
        var recyclerView: RecyclerView = findViewById(R.id.paginaIndex_recyclerView_certificadosObservers)
        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
        adapter.notifyDataSetChanged()

        recyclerView.addOnItemTouchListener(RecyclerTouchListener(this, recyclerView!!, object: ClickListener{

            override fun onClick(view: View, position: Int) {
                openPopUp("Enviar mensagem", "Você deseja enviar uma mensagem para este colaborador?", true, "Sim, enviar", "Não", "falar", AdminModels.whats.get(position))
            }

            override fun onLongClick(view: View?, position: Int) {

            }
        }))


    }

    fun openPopUp (titulo: String, texto:String, exibeBtnOpcoes:Boolean, btnSim: String, btnNao: String, call: String, whatsapp: String) {
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
        val buttonPopupN = view.findViewById<Button>(R.id.popupBtnNao)
        val buttonPopupS = view.findViewById<Button>(R.id.popupBtnSim)
        val buttonPopupOk = view.findViewById<Button>(R.id.popupBtnOk)
        val txtTitulo = view.findViewById<TextView>(R.id.popupTitulo)
        val txtTexto = view.findViewById<TextView>(R.id.popupTexto)
        val background: ConstraintLayout = view.findViewById(R.id.lay_root)

        background.setOnClickListener {
            popupWindow.dismiss()
        }


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

            if (call.equals("falar")){
                buttonPopupS.setOnClickListener {
                    openWhatsApp(whatsapp, "Olá, tudo bem? Você tem um certificado vencendo. Estamos avisando para ter ciência e praticar. Abraços.")
                }
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
            popupWindow.dismiss()
        }

        //lay_root é o layout parent que vou colocar a popup
        val lay_root: ConstraintLayout = findViewById(R.id.paginaIndex)

        // Finally, show the popup window on app
        TransitionManager.beginDelayedTransition(lay_root)
        popupWindow.showAtLocation(
            lay_root, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )


    }

    fun paginaColabAptos(){

        val btnVoltar: Button = findViewById(R.id.aptos_btnVoltar)
        val btnFechar: Button = findViewById(R.id.aptos_btnFechar)

        paginaColabAptos.visibility = View.VISIBLE

        btnFechar.setOnClickListener {
            finish()
        }

        btnVoltar.setOnClickListener {
            paginaIndex.visibility = View.VISIBLE
            paginaColabAptos.visibility = View.GONE
        }


        AdminModels.openCloseLay(paginaIndex, paginaColabAptos)

        var list_of_items = arrayOf(
            "Selecione Estado",
            "RJ",
            "AC",
            "AL",
            "AP",
            "AM",
            "BA",
            "CE",
            "DF",
            "ES",
            "GO",
            "MA",
            "MT",
            "MS",
            "MG",
            "PA",
            "PB",
            "PR",
            "PE",
            "PI",
            "RJ",
            "RN",
            "RS",
            "RO",
            "RR",
            "SC",
            "SP",
            "SE",
            "TO")

        //var list_of_items = turmasDaEscola

        var estadoSelecionado = "Selecione Estado"
        val spinnerEstado: Spinner = findViewById(R.id.spinner)
        spinnerEstado.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list_of_items)
        spinnerEstado.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?,  position: Int, id: Long
            ) {
                estadoSelecionado = list_of_items[position]
                AdminModels.estado = estadoSelecionado
            }
        }

        val editFiltro: EditText = findViewById(R.id.editFiltro)
        val btnBuscar: Button = findViewById(R.id.btnBuscar)
        btnBuscar.setOnClickListener {

            if (!estadoSelecionado.equals("Selecione Estado")){

                if (editFiltro.text.isEmpty()){
                    queryFindMyWorkers(estadoSelecionado, false, editFiltro.text.toString())
                } else {
                    queryFindMyWorkers(estadoSelecionado, true, editFiltro.text.toString())
                }

            }
        }

    }

    fun queryFindMyWorkers(estado: String, filtro: Boolean, filtroTxt: String) {

        AdminModels.nome.clear()
        AdminModels.certificados.clear()
        AdminModels.validCert.clear()
        AdminModels.funcao.clear()
        AdminModels.bd.clear()
        AdminModels.skillTec.clear()
        AdminModels.skillSeg.clear()
        AdminModels.skillRel.clear()

        ChamaDialog()
        val rootRef = databaseReference.child("funcionarios")
        rootRef.orderByChild("Estado").equalTo(estado)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    if (dataSnapshot.exists()) {

                        for (querySnapshot in dataSnapshot.children) {


                            var values: String = "nao"

                            val bd = querySnapshot.key.toString()

                            values = querySnapshot.child("certificados").value.toString()
                            val qntCert = values.toInt()

                            var cont=0
                            while (cont<qntCert){
                                var field = "certificado"+(cont+1).toString()
                                values = querySnapshot.child(field).value.toString()
                                if (filtro){
                                    if (filtroTxt.equals(values)){
                                        //entra aqui se for filtrado e bateu os requisitos
                                        AdminModels.certificados.add(bd+"!*!??#"+values)
                                        field = "valcert"+(cont+1).toString()
                                        values = querySnapshot.child(field).value.toString()
                                        AdminModels.validCert.add(values)

                                        AdminModels.bd.add(bd)

                                        values = querySnapshot.child("nome").value.toString()
                                        AdminModels.nome.add(values)

                                        values = querySnapshot.child("funcao").value.toString()
                                        AdminModels.funcao.add(values)

                                        values = querySnapshot.child("contato").value.toString()
                                        AdminModels.whats.add(values)

                                        values = querySnapshot.child("img").value.toString()
                                        AdminModels.img.add(values)

                                        values = querySnapshot.child("skillrel").value.toString()
                                        AdminModels.skillRel.add(values)

                                        values = querySnapshot.child("skillseg").value.toString()
                                        AdminModels.skillSeg.add(values)

                                        values = querySnapshot.child("skilltec").value.toString()
                                        AdminModels.skillTec.add(values)
                                    }
                                } else {
                                    //entra aqui se nao tiver filtro
                                    AdminModels.certificados.add(bd+"!*!??#"+values)
                                    field = "valcert"+(cont+1).toString()
                                    values = querySnapshot.child(field).value.toString()
                                    AdminModels.validCert.add(values)

                                    AdminModels.bd.add(bd)

                                    values = querySnapshot.child("nome").value.toString()
                                    AdminModels.nome.add(values)

                                    values = querySnapshot.child("funcao").value.toString()
                                    AdminModels.funcao.add(values)

                                    values = querySnapshot.child("contato").value.toString()
                                    AdminModels.whats.add(values)

                                    values = querySnapshot.child("img").value.toString()
                                    AdminModels.img.add(values)

                                    values = querySnapshot.child("skillrel").value.toString()
                                    AdminModels.skillRel.add(values)

                                    values = querySnapshot.child("skillseg").value.toString()
                                    AdminModels.skillSeg.add(values)

                                    values = querySnapshot.child("skilltec").value.toString()
                                    AdminModels.skillTec.add(values)
                                }

                                cont++
                            }




                        }

                    } else {
                        EncerraDialog()
                        ControllersUniversais.makeToast(this@AdminActivityNew, "Nenhum colaborador encontrado")
                    }

                    EncerraDialog()
                    montaRecyclerListaPorEstado()

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    EncerraDialog()
                    // ...
                }
            })

    }

    fun montaRecyclerListaPorEstado (){

        var adapter: ListFuncPorEstadoAdapter = ListFuncPorEstadoAdapter(this, AdminModels.nome, AdminModels.funcao, AdminModels.certificados, AdminModels.validCert, AdminModels.bd)
        var recyclerView: RecyclerView = findViewById(R.id.colabAptos_recyclerView)
        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
        adapter.notifyDataSetChanged()

        recyclerView.addOnItemTouchListener(RecyclerTouchListener(this, recyclerView!!, object: ClickListener{

            override fun onClick(view: View, position: Int) {
                Log.d("teste", "clicou em "+AdminModels.nome.get(position))
                openInfoWindow(position)
            }

            override fun onLongClick(view: View?, position: Int) {

            }
        }))


    }

    fun openInfoWindow(position: Int){

        AdminModels.openCloseLay(paginaColabAptos, paginaDetails)

        paginaDetails.setOnClickListener {
            AdminModels.openCloseLay(paginaDetails, paginaColabAptos)
        }

        val img: ImageView = findViewById(R.id.details_img)
        val tvNome: TextView = findViewById(R.id.details_nome)
        val tvfuncao: TextView = findViewById(R.id.details_funcao)
        val tvContato: TextView = findViewById(R.id.details_contato)
        val tvCertificados: TextView = findViewById(R.id.details_certificados)

        try {
            Glide.with(applicationContext)
                .load(AdminModels.img.get(position))
                .thumbnail(0.9f)
                .skipMemoryCache(true)
                .transform(CircleTransform(this)) // applying the image transformer
                .into(img)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        tvNome.setText(AdminModels.nome.get(position))
        tvfuncao.setText(AdminModels.funcao.get(position))
        tvContato.setText(AdminModels.whats.get(position))

        var cont=0
        var indexes: MutableList<Int> = ArrayList()
        while (cont<AdminModels.certificados.size){  //descobrir quantas vezes teremos que entrar pra pegar o dado
            if (AdminModels.certificados.get(cont).contains(AdminModels.bd.get(position))){
                indexes.add(cont)
            }
            cont++
        }


        cont=0
        while (cont<indexes.size){


            val tokens = StringTokenizer(AdminModels.certificados.get(indexes.get(cont)).toString(), "!*!??#") //”*” este é delim
            val desc = tokens.nextToken() // descartar
            val certificado = tokens.nextToken() // valor que queremos
            if (cont==0){
                //holder.tvCertf.text = certificado+" - val: "+arrayValidade.get(cont)
                if (AdminControllers.checkCertificateValidit(AdminModels.validCert.get(indexes.get(cont)))){
                    Log.d("teste", "entrou no vermelho na primeira")
                    val valvencida = " <font color='#FF0000'>"+certificado+" - val: "+AdminModels.validCert.get(indexes.get(cont)).toString()+"</font>"
                    tvCertificados.setText(Html.fromHtml(valvencida));
                    //holder.tvCertf.text = valvencida
                } else {
                    tvCertificados.text = certificado+" - val: "+AdminModels.validCert.get(indexes.get(cont))
                }
            } else {
                val textoInicial = tvCertificados.text.toString()
                if (AdminControllers.checkCertificateValidit(AdminModels.validCert.get(indexes.get(cont)))) {
                    val valvencida =
                        " <font color='#FF0000'>" + certificado + " - val: " + AdminModels.validCert.get(
                            indexes.get(cont)
                        ).toString() + "</font>"
                    //holder.tvCertf.text = textoInicial+"\n"+valvencida
                    tvCertificados.setText(Html.fromHtml(textoInicial + "\n" + valvencida));
                } else {
                    tvCertificados.text =
                        textoInicial + "\n" + certificado + " - val: " + AdminModels.validCert.get(
                            indexes.get(cont)
                        )
                }

            }
            cont++
        }

        mountChart(position)

        val btnContato: Button = findViewById(R.id.btnConvocar)
        btnContato.setOnClickListener {
            //openWhatsApp(adminModels.whats.get(position), "Olá. Queremos você na próxima embaracação")
            convocation(position)
        }




    }

    fun mountChart(position: Int){

        val pieChart = findViewById<PieChart>(R.id.pieChart)

        val NoOfEmp = ArrayList<PieEntry>()

        var entry1 = AdminModels.skillSeg.get(position).toFloat()
        NoOfEmp.add(PieEntry((entry1), "Segurança"))
        entry1 = AdminModels.skillRel.get(position).toFloat()
        NoOfEmp.add(PieEntry((entry1), "Relacionamento"))
        entry1 = AdminModels.skillSeg.get(position).toFloat()
        NoOfEmp.add(PieEntry((entry1), "Técnica"))

        val dataSet = PieDataSet(NoOfEmp, "")

        pieChart.getDescription().setEnabled(false)
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0F, 40F)
        dataSet.selectionShift = 5f
        dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data = PieData(dataSet)
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        pieChart.data = data
        pieChart.highlightValues(null)
        pieChart.invalidate()
        pieChart.animateXY(1000, 1000)

    }

    fun convocation(position: Int){

        AdminModels.openCloseLay(paginainfo1, paginainfo2)

        var list_of_items = arrayOf("Selecione:", "SSKN 3008", "TSKN 2608", "Outra")

        var embarcacaoSelecionada = "Selecione:"
        val spinnerEstado: Spinner = findViewById(R.id.spinnerEmbarcacao)
        spinnerEstado.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list_of_items)
        spinnerEstado.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?,  position: Int, id: Long
            ) {
                embarcacaoSelecionada = list_of_items[position]

            }
        }

        val diasParaEmbarque: EditText = findViewById(R.id.layInfo2_diasParaEmbarque)
        val btnContato: Button = findViewById(R.id.layInfor2_btnContato)

        btnContato.setOnClickListener {
            if (embarcacaoSelecionada.equals("Selecione:")){
                ControllersUniversais.makeToast(this, "Selecione a embarcação")
            } else if (diasParaEmbarque.text.isEmpty()){
                diasParaEmbarque.setError("!")
            } else {
                openWhatsApp(AdminModels.whats.get(position), "Olá, tudo bem? Esperamso que sim. Queremos avisar você para se preparar. \nVocê embarca no modelo "+embarcacaoSelecionada+" em "+diasParaEmbarque.text+" dias. Você pode se preparar para aumentar a familiarização no app de treinamento. Procure o alerta na página principal. Nós estamos separando treinamentos especiais para você.")

                databaseReference.child("convocacoes").child(AdminModels.bd.get(position)).child("userBd").setValue(AdminModels.bd.get(position))
                databaseReference.child("convocacoes").child(AdminModels.bd.get(position)).child("embarcacao").setValue(embarcacaoSelecionada)
                //val data = diasParaEmbarque.text.toString()
                //val dataFinal = indexControllers.GetfutureDate(data.toInt())
                databaseReference.child("convocacoes").child(AdminModels.bd.get(position)).child("dataEmbarque").setValue(ControllersUniversais.GetfutureDate(diasParaEmbarque.text.toString().toInt()))
                databaseReference.child("convocacoes").child(AdminModels.bd.get(position)).child("recebido").setValue("nao")
                databaseReference.child("convocacoes").child(AdminModels.bd.get(position)).child("criador").setValue(IndexModels.userBd)
                AdminModels.openCloseLay(paginainfo2, paginaColabAptos)
                ControllersUniversais.makeToast(this, "Um alerta foi criado")
            }
        }

    }

    fun openWhatsApp(number: String, message: String) {

        val pm: PackageManager = getPackageManager();
        try {
            val waIntent: Intent = Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            val text: String = message

            val toNumber =
                "55" + number // Replace with mobile phone number without +Sign or leading zeros, but with country code
            //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://api.whatsapp.com/send?phone=$toNumber&text=$text")
            startActivity(intent)

        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(
                this,
                "WhatsApp não está instalado neste celular",
                Toast.LENGTH_SHORT
            )
                .show();
        } catch (e: Exception) {

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

    fun EncerraDialog() {
        val layout = findViewById<RelativeLayout>(R.id.LayoutProgressBar)
        val spinner = findViewById<ProgressBar>(R.id.progressBar1)
        layout.visibility = View.GONE
        spinner.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE) //libera os clicks
    }

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