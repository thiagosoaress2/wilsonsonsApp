package com.bino.wilsonsonsapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.bino.wilsonsonsapp.Controllers.ControllersUniversais
import com.bino.wilsonsonsapp.Controllers.perfilController
import com.bino.wilsonsonsapp.Controllers.perfilController.objectsUser
import com.bino.wilsonsonsapp.Models.*
import com.bino.wilsonsonsapp.Utils.*
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_perfil.*
import java.io.*
import java.lang.ref.WeakReference
import java.util.*

class perfilActivity : AppCompatActivity() {

    private val CAMERA_PERMISSION_CODE = 300
    private val WRITE_PERMISSION_CODE = 301
    private val READ_PERMISSION_CODE = 302

    private lateinit var filePath: Uri
    private var urifinal: String = "nao"
    private lateinit var mphotoStorageReference: StorageReference
    private lateinit var mFireBaseStorage: FirebaseStorage

    lateinit var databaseReference: DatabaseReference

    //lateinit var objectsUser : ObjectUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        perfilController.loadData()


    }

    override fun onStart() {
        super.onStart()

        //objectsUser = ObjectUser()

        //pede priemiro a camera e depois vai pedindo um por um
        CameraPermissions.checkPermission(this, CAMERA_PERMISSION_CODE)

        mFireBaseStorage = FirebaseStorage.getInstance()
        mphotoStorageReference = mFireBaseStorage.reference
        databaseReference = FirebaseDatabase.getInstance().reference

        val btnUpload: Button = findViewById(R.id.perfil_btnUpload)
        btnUpload.setOnClickListener {
            if (CameraPermissions.hasPermissions(this) && readFilesPermissions.hasPermissions(this) && writeFilesPermissions.hasPermissions(this)){
                openPopUp("Upload de foto", "Como você vai enviar a foto?", true, "Tirar foto", "Escolher foto")
            } else if (!CameraPermissions.hasPermissions(this)){
                CameraPermissions.checkPermission(this, CAMERA_PERMISSION_CODE)
            } else if (!readFilesPermissions.hasPermissions(this)){
                readFilesPermissions.checkPermission(this, READ_PERMISSION_CODE)
            } else if (!writeFilesPermissions.hasPermissions(this)){
                writeFilesPermissions.checkPermission(this, WRITE_PERMISSION_CODE)
            }

        }

        IndexModels.placeImage(findViewById(R.id.perfil_iv), this)

        val btnVoltar: Button = findViewById(R.id.perfil_voltar)
        btnVoltar.setOnClickListener {
            finish()
        }

        val btnEditar: Button = findViewById(R.id.perfil_btnEditar)
        btnEditar.setOnClickListener {
            openEditLay()
        }

        val situacao =intent.getStringExtra("infos")

        if (situacao!=null){
            btnEditar.performClick()
        }

        val etNome: TextView = findViewById(R.id.perfil_tvNome)
        val etFuncao: TextView = findViewById(R.id.perfil_etFuncao)
        val etContato: TextView = findViewById(R.id.perfil_etContato)

        //perfilController.setBasicInfos(etNome, etFuncao, etContato)
        //aqui atualiza a pagina anterior
        if (perfilController.objectsUser.name != null){
            etNome.setText(perfilController.objectsUser.name)
        } else {
            etNome.visibility = View.INVISIBLE
        }
        if (perfilController.objectsUser.cargo != 0) {
            etFuncao.setText(perfilController.getfunction(perfilController.objectsUser.cargo))

        } else {
            etFuncao.visibility = View.INVISIBLE
        }
        if (perfilController.objectsUser.number != null){
            etContato.setText(perfilController.objectsUser.number)
        } else {
            etContato.visibility = View.INVISIBLE
        }


    }


    fun openEditLay(){

        val layCad: ConstraintLayout = findViewById(R.id.layEditInfo)
        layCad.visibility = View.VISIBLE

        val editNascimento: EditText = findViewById(R.id.cad_etNascimento)
        val imgEdit: ImageView = findViewById(R.id.cad_img)
        val btnUpload: Button = findViewById(R.id.cad_btnUpload)
        val cad_etNome: EditText = findViewById(R.id.cad_etNome)
        val cad_cel: EditText = findViewById(R.id.cad_cel)


        btnUpload.setOnClickListener {
            if (CameraPermissions.hasPermissions(this) && readFilesPermissions.hasPermissions(this) && writeFilesPermissions.hasPermissions(this)){
                openPopUp("Upload de foto", "Como você vai enviar a foto?", true, "Tirar foto", "Escolher foto")
            } else if (!CameraPermissions.hasPermissions(this)){
                CameraPermissions.checkPermission(this, CAMERA_PERMISSION_CODE)
            } else if (!readFilesPermissions.hasPermissions(this)){
                readFilesPermissions.checkPermission(this, READ_PERMISSION_CODE)
            } else if (!writeFilesPermissions.hasPermissions(this)){
                writeFilesPermissions.checkPermission(this, WRITE_PERMISSION_CODE)
            }

        }

        dateWatcher(editNascimento)

        var list_of_items = arrayOf(
            "Selecione Estado",
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
        var estadoSelecionado = 0
        val spinnerEstado: Spinner = findViewById(R.id.cad_spinner_estado)
        spinnerEstado.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list_of_items)
        spinnerEstado.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?,  position: Int, id: Long
            ) {
                estadoSelecionado = position
            }
        }

        var list_of_items2 = arrayOf(
            "Selecione Função",
            "RJ",
            "SP",
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
        var funcaoSelecionada = 0
        val spinner: Spinner = findViewById(R.id.cad_spinnerFuncao)
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list_of_items)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?,  position: Int, id: Long
            ) {
                funcaoSelecionada = position
            }
        }


        perfilController.loadCelFormater(this, cad_cel)


        if (perfilController.objectsUser.photo==null){
            perfilController.loadImage(this, imgEdit, true)
        } else {
            perfilController.loadImage(this, imgEdit, false)
        }


        val btnVoltar: Button = findViewById(R.id.cad_btnVoltar)
        btnVoltar.setOnClickListener {
            layCad.visibility = View.GONE
        }

        val etNome: TextView = findViewById(R.id.perfil_tvNome)
        val etFuncao: TextView = findViewById(R.id.perfil_etFuncao)
        val etContato: TextView = findViewById(R.id.perfil_etContato)

        val btnSalvar: Button = findViewById(R.id.cad_btnSalvar)
        btnSalvar.setOnClickListener {
            if (!urifinal.equals("nao")){
                perfilController.savePhoto(urifinal)
            }
            if (!editNascimento.text.isEmpty()){
                perfilController.saveNasc(editNascimento.text.toString())
            }
            if (!cad_etNome.text.isEmpty()){
                perfilController.saveName(cad_etNome.text.toString())
            }
            if (!cad_cel.text.isEmpty()){
                perfilController.saveCel(cad_cel.text.toString())
            }
            if (estadoSelecionado!=0){
                perfilController.saveEstado(estadoSelecionado)
            }
            if (funcaoSelecionada!=0){
                perfilController.saveFuncao(estadoSelecionado)
                //var objectOccupation: ObjectOccupation = ObjectOccupation()
                //val teste = ConsultsOccupationModel.selectOccupationPerId(funcaoSelecionada)
            }
            //aqui atualiza a pagina anterior
            if (perfilController.objectsUser.name != null){
                etNome.setText(perfilController.objectsUser.name)
            } else {
                etNome.visibility = View.INVISIBLE
            }
            if (perfilController.objectsUser.cargo != 0) {
                etFuncao.setText(perfilController.getfunction(perfilController.objectsUser.cargo))

            } else {
                etFuncao.visibility = View.INVISIBLE
            }
            if (perfilController.objectsUser.number != null){
                etContato.setText(perfilController.objectsUser.number)
            } else {
                etContato.visibility = View.INVISIBLE
            }
            ControllersUniversais.makeToast(this, "As informações foram salvas.")
        }


        val btnFechar: Button = findViewById(R.id.cad_btnFechar)
        btnFechar.setOnClickListener {
            finish()
        }
    }

    fun dateWatcher( editText:EditText) {

        var oldString : String = ""

        editText.addTextChangedListener(object : TextWatcher {
            var changed: Boolean = false

            override fun afterTextChanged(p0: Editable?) {

                changed = false


            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
            {
                //changed=false
                editText.setSelection(p0.toString().length)
            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                var str: String = p0.toString()

                if (str != null) {

                    if (oldString.equals(str)) {
                        //significs que o user esta apagando
                        //do Nothing

                    } else if (str.length == 2) {  //  xx
                        var element0: String = str.elementAt(0).toString()
                        var element1: String = str.elementAt(1).toString()
                        str = element0 + element1 + "/"
                        editText.setText(str)
                        oldString = element0 + element1
                        editText.setSelection(str.length)

                    } else if (str.length == 5) { //  xx/xx

                        var element0: String = str.elementAt(0).toString() //x
                        var element1: String = str.elementAt(1).toString() //-x
                        var element2: String = str.elementAt(2).toString() //--/
                        var element3: String = str.elementAt(3).toString() //--/x
                        var element4: String = str.elementAt(4).toString() //--/-x

                        str = element0 + element1 + element2 + element3 + element4 + "/"
                        editText.setText(str)
                        oldString = element0 + element1 + element2 + element3 + element4
                        editText.setSelection(str.length)

                    } else if (str.length > 10) { // este exemplo é para data no formato xx/xx/xx. Se você quer usar xx/xx/xxxx mudar para if (str.length >10). O resto do código permanece o mesmo.

                        str = str.substring(0, str.length - 1)
                        editText.setText(str)
                        editText.setSelection(str.length)

                    }


                }

            }
        })
    }


    fun takePictureFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, 100)
        }
    }

    fun takePictureFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        startActivityForResult(Intent.createChooser(intent, "Selecione a foto"), 101)
    }


    fun openPopUp (titulo: String, texto:String, exibeBtnOpcoes:Boolean, btnSim: String, btnNao: String) {
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
                takePictureFromGallery()
                popupWindow.dismiss()
            }

            buttonPopupS.setOnClickListener {
                takePictureFromCamera()
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
        val lay_root: ConstraintLayout = findViewById(R.id.layPai)

        // Finally, show the popup window on app
        TransitionManager.beginDelayedTransition(lay_root)
        popupWindow.showAtLocation(
            lay_root, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )

    }

    //retorno da imagem
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //retorno da camera
        //primeiro if resultado da foto tirada pela camera
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {

                val photo: Bitmap = data?.extras?.get("data") as Bitmap
                compressImage(photo)

            }

        } else {
            //resultado da foto pega na galeria
            if (resultCode == RESULT_OK
                && data != null && data.getData() != null
            ) {

                filePath = data.getData()!!
                var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                compressImage(bitmap)


            }
        }
    }

    //aqui vamos reduzir o tamanho antes de enviar pro bd
    private fun compressImage(image: Bitmap) {

        ChamaDialog()
        //400x100 fica com 2,5 kb, 800x200 fica com 5 kb
        val imageProvisoria: Bitmap = calculateInSizeSampleToFitImageView(image, 1000, 1000)

        //image provisoria pode ser colocada no imageview pois já é pequena suficiente.
        val imageviewBanne: ImageView = findViewById(R.id.perfil_iv)
        val imageviewInThisPage: ImageView = findViewById(R.id.cad_img)
        //imageviewBanne.setImageBitmap(imageProvisoria)
        try {
            Glide.with(applicationContext)
                .load(imageProvisoria)
                .thumbnail(0.9f)
                .skipMemoryCache(true)
                .transform(CircleTransform(this@perfilActivity)) // applying the image transformer
                .into(imageviewBanne)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            Glide.with(applicationContext)
                .load(imageProvisoria)
                .thumbnail(0.9f)
                .skipMemoryCache(true)
                .transform(CircleTransform(this@perfilActivity)) // applying the image transformer
                .into(imageviewInThisPage)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        //esta parte é do método antigo. Imagino que ele nao tenha função mais
        val baos = ByteArrayOutputStream()
        var optionsCompress = 20  //taxa de compressao. 100 significa nenhuma compressao
        try {
            //Code here
            while (baos.toByteArray().size / 1024 > 50) {  //Loop if compressed picture is greater than 50kb, than to compression
                baos.reset() //Reset baos is empty baos
                imageProvisoria.compress(
                    Bitmap.CompressFormat.JPEG,
                    optionsCompress,
                    baos
                ) //The compression options%, storing the compressed data to the baos
                optionsCompress -= 25 //Every time reduced by 10
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }


        //aqui faz upload pro storage database
        val tempUri: Uri = getImageUri(this, imageProvisoria)
        filePath = tempUri
        uploadImage()
    }

    fun calculateInSizeSampleToFitImageView (image: Bitmap, imageViewWidth:Int, imageViewHeight:Int) : Bitmap{

        //ESTE BLOCO É PARA PEGAR AS DIMENSOES DA IMAGEM
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }

        //converte a imagem que o usuario escolheu para um Uri e depois para um File
        val file = bitmapToFile(image)
        val fpath = file.path
        BitmapFactory.decodeFile(fpath, options)
        //resultados pegos do método acima
        val imageHeight: Int = options.outHeight
        val imageWidth: Int = options.outWidth
        //FIM DAS DIMENSOES DA IMAGEM

        var adaptedHeight: Int =0
        var adaptedWidh: Int =0
        //vamos primeiro acerta a altura. Poderiamos fazer tudo ao mesmo tempo, mas como estamos trabalhando com possibilidade do height ser diferente do width poderia dar erro
        if (imageHeight > imageViewHeight){

            adaptedHeight = imageHeight / 2
            while (adaptedHeight > imageViewHeight){
                adaptedHeight = adaptedHeight/2
            }

        } else {
            adaptedHeight = imageViewHeight
        }

        if (imageWidth > imageViewWidth){

            adaptedWidh = imageWidth / 2
            while (adaptedWidh > imageViewHeight){
                adaptedWidh = adaptedWidh/2
            }
        } else {
            adaptedWidh = imageViewWidth
        }

        val newBitmap = Bitmap.createScaledBitmap(image, adaptedWidh, adaptedHeight, false)
        return newBitmap

    }

    // Method to save an bitmap to a file
    private fun bitmapToFile(bitmap:Bitmap): Uri {
        // Get the context wrapper
        val wrapper = ContextWrapper(applicationContext)

        // Initialize a new file instance to save bitmap object
        var file = wrapper.getDir("Images",Context.MODE_PRIVATE)
        file = File(file,"${UUID.randomUUID()}.jpg")

        try{
            // Compress the bitmap and save in jpg format
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
            stream.flush()
            stream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }

        // Return the saved bitmap uri
        return Uri.parse(file.absolutePath)
    }

    //pega o uri
    fun  getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.PNG, 35, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)
    }


    //envio da foto
    //existe uma opção especial aqui para o caso de ser alvará
    fun uploadImage(){

        mFireBaseStorage = FirebaseStorage.getInstance()
        mphotoStorageReference = mFireBaseStorage.reference

        mphotoStorageReference =mFireBaseStorage.getReference().child(ControllersUniversais.getDate()+ControllersUniversais.getHour())


        val bmp: Bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath)
        val baos: ByteArrayOutputStream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos)

//get the uri from the bitmap
        val tempUri: Uri = getImageUri(this, bmp)
//transform the new compressed bmp in filepath uri
        filePath = tempUri

//var file = Uri.fromFile(bitmap)
        var uploadTask = mphotoStorageReference.putFile(filePath)

        val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                    EncerraDialog()

                }
            }
            return@Continuation mphotoStorageReference.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                urifinal = downloadUri.toString()
                //se quiser salvar, é o urifinal que é o link
                //pra salvar no bd e carregar com glide.
                databaseReference.child("usuarios").child(IndexModels.userBd).child("img").setValue(urifinal)
                EncerraDialog()


            } else {
                // Handle failures
                Toast.makeText(this, "um erro ocorreu.", Toast.LENGTH_SHORT).show()
                // ...
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode==CAMERA_PERMISSION_CODE){
            CameraPermissions.handlePermissionsResult(requestCode, permissions, grantResults, CAMERA_PERMISSION_CODE)
            readFilesPermissions.requestPermission(this, READ_PERMISSION_CODE)
        }
        if (requestCode==READ_PERMISSION_CODE){
            readFilesPermissions.handlePermissionsResult(requestCode, permissions, grantResults, READ_PERMISSION_CODE)
            writeFilesPermissions.requestPermission(this, WRITE_PERMISSION_CODE)
        }
        if (requestCode==WRITE_PERMISSION_CODE){
            writeFilesPermissions.handlePermissionsResult(requestCode, permissions, grantResults, WRITE_PERMISSION_CODE)
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

}