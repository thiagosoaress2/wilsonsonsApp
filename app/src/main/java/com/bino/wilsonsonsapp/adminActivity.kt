package com.bino.wilsonsonsapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bino.wilsonsonsapp.Controllers.adminControllers
import com.bino.wilsonsonsapp.Models.adminModels
import com.bino.wilsonsonsapp.Utils.CircleTransform
import com.bino.wilsonsonsapp.Utils.listFuncPorEstadoAdapter
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import java.util.*

class adminActivity : AppCompatActivity() {

    lateinit var paginaIndex: ConstraintLayout
    lateinit var paginaColabAptos: ConstraintLayout
    lateinit var paginaDetails: ConstraintLayout

    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        loadComponents()
        paginaColabAptos()

        val btn: Button = findViewById(R.id.btnConsultColaboradoresAptos)
        btn.setOnClickListener {
            adminModels.openCloseLay(paginaIndex, paginaColabAptos)
        }

    }

    fun loadComponents(){
        paginaIndex = findViewById(R.id.paginaIndex)
        paginaColabAptos = findViewById(R.id.paginaColabAptos)
        paginaDetails = findViewById(R.id.layInfosDetalhadas)
        databaseReference = FirebaseDatabase.getInstance().reference
    }

    fun paginaColabAptos(){

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
                adminModels.estado = estadoSelecionado
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

        adminModels.nome.clear()
        adminModels.certificados.clear()
        adminModels.validCert.clear()
        adminModels.funcao.clear()
        adminModels.bd.clear()

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
                                        adminModels.certificados.add(bd+"!*!??#"+values)
                                        field = "valcert"+(cont+1).toString()
                                        values = querySnapshot.child(field).value.toString()
                                        adminModels.validCert.add(values)

                                        adminModels.bd.add(bd)

                                        values = querySnapshot.child("nome").value.toString()
                                        adminModels.nome.add(values)

                                        values = querySnapshot.child("funcao").value.toString()
                                        adminModels.funcao.add(values)

                                        values = querySnapshot.child("contato").value.toString()
                                        adminModels.whats.add(values)

                                        values = querySnapshot.child("img").value.toString()
                                        adminModels.img.add(values)
                                    }
                                } else {
                                    //entra aqui se nao tiver filtro
                                    adminModels.certificados.add(bd+"!*!??#"+values)
                                    field = "valcert"+(cont+1).toString()
                                    values = querySnapshot.child(field).value.toString()
                                    adminModels.validCert.add(values)

                                    adminModels.bd.add(bd)

                                    values = querySnapshot.child("nome").value.toString()
                                    adminModels.nome.add(values)

                                    values = querySnapshot.child("funcao").value.toString()
                                    adminModels.funcao.add(values)

                                    values = querySnapshot.child("contato").value.toString()
                                    adminModels.whats.add(values)

                                    values = querySnapshot.child("img").value.toString()
                                    adminModels.img.add(values)
                                }

                                cont++
                            }




                        }

                    } else {
                        EncerraDialog()
                        adminControllers.makeToast(this@adminActivity, "Nenhum colaborador encontrado")
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

        var adapter: listFuncPorEstadoAdapter = listFuncPorEstadoAdapter(this, adminModels.nome, adminModels.funcao, adminModels.certificados, adminModels.validCert, adminModels.bd)
        var recyclerView: RecyclerView = findViewById(R.id.colabAptos_recyclerView)
        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
        adapter.notifyDataSetChanged()

        recyclerView.addOnItemTouchListener(RecyclerTouchListener(this, recyclerView!!, object: ClickListener{

            override fun onClick(view: View, position: Int) {
                Log.d("teste", "clicou em "+adminModels.nome.get(position))
                openInfoWindow(position)
            }

            override fun onLongClick(view: View?, position: Int) {

            }
        }))


    }

    fun openInfoWindow(position: Int){

        adminModels.openCloseLay(paginaColabAptos, paginaDetails)

        paginaDetails.setOnClickListener {
            adminModels.openCloseLay(paginaDetails, paginaColabAptos)
        }

        val img: ImageView = findViewById(R.id.details_img)
        val tvNome: TextView = findViewById(R.id.details_nome)
        val tvfuncao: TextView = findViewById(R.id.details_funcao)
        val tvContato: TextView = findViewById(R.id.details_contato)
        val tvCertificados: TextView = findViewById(R.id.details_certificados)

        try {
            Glide.with(applicationContext)
                .load(adminModels.img.get(position))
                .thumbnail(0.9f)
                .skipMemoryCache(true)
                .transform(CircleTransform(this)) // applying the image transformer
                .into(img)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        tvNome.setText(adminModels.nome.get(position))
        tvfuncao.setText(adminModels.funcao.get(position))
        tvContato.setText(adminModels.whats.get(position))

        var cont=0
        var indexes: MutableList<Int> = ArrayList()
        while (cont<adminModels.certificados.size){  //descobrir quantas vezes teremos que entrar pra pegar o dado
            if (adminModels.certificados.get(cont).contains(adminModels.bd.get(position))){
                indexes.add(cont)
            }
            cont++
        }


        cont=0
        while (cont<indexes.size){


            val tokens = StringTokenizer(adminModels.certificados.get(indexes.get(cont)).toString(), "!*!??#") //”*” este é delim
            val desc = tokens.nextToken() // descartar
            val certificado = tokens.nextToken() // valor que queremos
            if (cont==0){
                //holder.tvCertf.text = certificado+" - val: "+arrayValidade.get(cont)
                if (adminControllers.checkCertificateValidit(adminModels.validCert.get(indexes.get(cont)))){
                    Log.d("teste", "entrou no vermelho na primeira")
                    val valvencida = " <font color='#FF0000'>"+certificado+" - val: "+adminModels.validCert.get(indexes.get(cont)).toString()+"</font>"
                    tvCertificados.setText(Html.fromHtml(valvencida));
                    //holder.tvCertf.text = valvencida
                } else {
                    tvCertificados.text = certificado+" - val: "+adminModels.validCert.get(indexes.get(cont))
                }
            } else {
                val textoInicial = tvCertificados.text.toString()
                if (adminControllers.checkCertificateValidit(adminModels.validCert.get(indexes.get(cont)))) {
                    val valvencida =
                        " <font color='#FF0000'>" + certificado + " - val: " + adminModels.validCert.get(
                            indexes.get(cont)
                        ).toString() + "</font>"
                    //holder.tvCertf.text = textoInicial+"\n"+valvencida
                    tvCertificados.setText(Html.fromHtml(textoInicial + "\n" + valvencida));
                } else {
                    tvCertificados.text =
                        textoInicial + "\n" + certificado + " - val: " + adminModels.validCert.get(
                            indexes.get(cont)
                        )
                }

            }
            cont++
        }


        val btnContato: Button = findViewById(R.id.btnContato)
        btnContato.setOnClickListener {
            openWhatsApp(adminModels.whats.get(position), "Olá. Queremos você na próxima embaracação")
        }

    }

    //mandar mensagem pra outro caminhoneiro
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

    //este método torna invisivel um layout e encerra o dialogbar spinner.
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