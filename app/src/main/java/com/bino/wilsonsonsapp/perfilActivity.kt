package com.bino.wilsonsonsapp

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.bino.wilsonsonsapp.Controllers.indexControllers
import com.bino.wilsonsonsapp.Models.ConsultsModel
import com.bino.wilsonsonsapp.Models.indexModels
import com.bino.wilsonsonsapp.Utils.CircleTransform
import com.bino.wilsonsonsapp.Utils.cameraPermissions
import com.bino.wilsonsonsapp.Utils.readFilesPermissions
import com.bino.wilsonsonsapp.Utils.writeFilesPermissions
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

class perfilActivity : AppCompatActivity() {

    private val CAMERA_PERMISSION_CODE = 300
    private val WRITE_PERMISSION_CODE = 301
    private val READ_PERMISSION_CODE = 302

    private lateinit var filePath: Uri
    private var urifinal: String = "nao"
    private lateinit var mphotoStorageReference: StorageReference
    private lateinit var mFireBaseStorage: FirebaseStorage

    lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        mountChart()
    }

    override fun onStart() {
        super.onStart()

        //pede priemiro a camera e depois vai pedindo um por um
        cameraPermissions.checkPermission(this, CAMERA_PERMISSION_CODE)

        mFireBaseStorage = FirebaseStorage.getInstance()
        mphotoStorageReference = mFireBaseStorage.reference
        databaseReference = FirebaseDatabase.getInstance().reference

        val btnUpload: Button = findViewById(R.id.perfil_btnUpload)
        btnUpload.setOnClickListener {
            if (cameraPermissions.hasPermissions(this) && readFilesPermissions.hasPermissions(this) && writeFilesPermissions.hasPermissions(this)){
                openPopUp("Upload de foto", "Como você vai enviar a foto?", true, "Tirar foto", "Escolher foto")
            } else if (!cameraPermissions.hasPermissions(this)){
                cameraPermissions.checkPermission(this, CAMERA_PERMISSION_CODE)
            } else if (!readFilesPermissions.hasPermissions(this)){
                readFilesPermissions.checkPermission(this, READ_PERMISSION_CODE)
            } else if (!writeFilesPermissions.hasPermissions(this)){
                writeFilesPermissions.checkPermission(this, WRITE_PERMISSION_CODE)
            }

        }

        indexModels.placeImage(findViewById(R.id.perfil_iv), this)
    }

    fun mountChart(){

        val pieChart = findViewById<PieChart>(R.id.pieChart)

        val NoOfEmp = ArrayList<PieEntry>()

        /*
        if (arrayDesempenhoEmCadaDisciplina.get(0).equals("0") && arrayDesempenhoEmCadaDisciplina.get(1).equals("0") && arrayDesempenhoEmCadaDisciplina.get(2).equals("0") && arrayDesempenhoEmCadaDisciplina.get(3).equals("0")&& arrayDesempenhoEmCadaDisciplina.get(4).equals("0")){
            NoOfEmp.add(PieEntry(1F, "Português"))
            NoOfEmp.add(PieEntry(1F, "Matemática"))
            NoOfEmp.add(PieEntry(1F, "His/Geo"))
            NoOfEmp.add(PieEntry(1F, "Ciências"))
            NoOfEmp.add(PieEntry(1F, "Ing/Art/Ens.R/Ed.Fi"))

        } else if (arrayDesempenhoEmCadaDisciplina.get(0).contains("null") || arrayDesempenhoEmCadaDisciplina.get(1).contains("null") || arrayDesempenhoEmCadaDisciplina.get(2).contains("null") || arrayDesempenhoEmCadaDisciplina.get(3).contains("null") || arrayDesempenhoEmCadaDisciplina.get(4).contains("null")){

            /*
            NoOfEmp.add(PieEntry(1F, "Português"))
            NoOfEmp.add(PieEntry(1F, "Matemática"))
            NoOfEmp.add(PieEntry(1F, "His/Geo"))
            NoOfEmp.add(PieEntry(1F, "Ciências"))
            NoOfEmp.add(PieEntry(1F, "Ing/Art/Ens.R/Ed.Fi"))
             */
            arrayDesempenhoEmCadaDisciplina.clear()
            Log.d("teste", "chamou query novamente aqui")
            queryGetUserInfos()

        }
        else {
            NoOfEmp.add(
                PieEntry(
                    (arrayDesempenhoEmCadaDisciplina.get(0) + "F").toFloat(),
                    "Português"
                )
            )
            NoOfEmp.add(
                PieEntry(
                    (arrayDesempenhoEmCadaDisciplina.get(1) + "F").toFloat(),
                    "Matemática"
                )
            )
            NoOfEmp.add(
                PieEntry(
                    (arrayDesempenhoEmCadaDisciplina.get(2) + "F").toFloat(),
                    "His/Geo"
                )
            )
            NoOfEmp.add(
                PieEntry(
                    (arrayDesempenhoEmCadaDisciplina.get(3) + "F").toFloat(),
                    "Ciências"
                )
            )
            NoOfEmp.add(
                PieEntry(
                    (arrayDesempenhoEmCadaDisciplina.get(4) + "F").toFloat(),
                    "Ing/Art/Ens.R/Ed.Fi"
                )
            )
        }

         */

        //NoOfEmp.add(PieEntry((arrayDesempenhoEmCadaDisciplina.get(0) + "F").toFloat(),"Português"))

        NoOfEmp.add(PieEntry((50f), "Segurança"))
        NoOfEmp.add(PieEntry((25f), "Relacionamento"))
        NoOfEmp.add(PieEntry((30f), "Técnica"))

        val dataSet = PieDataSet(NoOfEmp, "")

        pieChart.getDescription().setEnabled(false);
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
        pieChart.animateXY(5000, 5000)

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
        val buttonPopupN = view.findViewById<Button>(R.id.btnReclamar)
        val buttonPopupS = view.findViewById<Button>(R.id.BtnRecebimento)
        val buttonPopupOk = view.findViewById<Button>(R.id.popupBtnOk)
        val txtTitulo = view.findViewById<TextView>(R.id.popupTitulo)
        val txtTexto = view.findViewById<TextView>(R.id.popupTexto)

        val background: ConstraintLayout = view.findViewById(R.id.background)

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


        mphotoStorageReference =mFireBaseStorage.getReference().child(indexControllers.getDate()+indexControllers.getHour())


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
                databaseReference.child("usuarios").child(indexModels.userBd).child("img").setValue(urifinal)
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
            cameraPermissions.handlePermissionsResult(requestCode, permissions, grantResults, CAMERA_PERMISSION_CODE)
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