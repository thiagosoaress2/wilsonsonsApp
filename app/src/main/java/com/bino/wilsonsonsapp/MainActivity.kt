package com.bino.wilsonsonsapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.text.TextWatcher
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bino.wilsonsonsapp.Controllers.SetupDatabase
import com.bino.wilsonsonsapp.Models.AdminModels
import com.bino.wilsonsonsapp.Utils.mySharedPrefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var telainicial: ConstraintLayout
    private lateinit var telaDeVerificacao: ConstraintLayout
    private lateinit var telaLoginMailNew: ConstraintLayout

    lateinit var auth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!mySharedPrefs(this).getCopyDatabase()){SetupDatabase(this)}
        auth = FirebaseAuth.getInstance()
        loadComponents()

        metodosIniciais()
    }

    fun loadComponents() {

        telainicial = findViewById<ConstraintLayout>(R.id.layInicial)
        telaDeVerificacao = findViewById<ConstraintLayout>(R.id.layLoginWithMail_VerificationMail)
        telaLoginMailNew = findViewById<ConstraintLayout>(R.id.layLoginWithEmail_newUser)
    }

    fun metodosIniciais() {

        telaDeVerificacao.visibility = View.GONE

        //fazer login depos
        val btnLoginDepois: Button = findViewById(R.id.btnLoginDepois)
        btnLoginDepois.setOnClickListener {

            telainicial.visibility = View.GONE
            telaDeVerificacao.visibility = View.GONE
            telaLoginMailNew.visibility = View.GONE
            telainicial.visibility = View.VISIBLE

            //val intent = Intent(this, IndexActivity::class.java)
            val intent = Intent(this, IndexActivityNew::class.java)
            intent.putExtra("email", "semLogin")
            startActivity(intent)
        }

        //entrou na reforma - nao obsoleto
        LoginWithEmail()

        //remover este código. Obsoleto
        val btnLoginWithMail = findViewById<Button>(R.id.layInicial_btnSignWithEmail)
        btnLoginWithMail.setOnClickListener {
            if (isNetworkAvailable(this)) {
                LoginWithEmail()
            } else {
                Toast.makeText(this, "Você está sem conexão com a internet.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        val loginFaceVisivel = findViewById<Button>(R.id.layInicial_btnSignWithFace)
        loginFaceVisivel.setOnClickListener {

            Toast.makeText(this, "Em breve", Toast.LENGTH_SHORT).show()
        }


        val btnLoginGoogle: Button = findViewById(R.id.layInicial_btnLoginGoogle)
        btnLoginGoogle.setOnClickListener {
            Toast.makeText(this, "Em breve", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser == null) {
            auth.signOut()
            updateUI(currentUser, "null")
        } else {
            updateUI(currentUser, "unknown")
        }

        LoginWithEmail()
    }

    private fun createAccount(email: String, password: String) {

        if (!validateForm("MailNew")) {
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = auth.currentUser
                    updateUI(user, "mail")
                    trocaTela(telaLoginMailNew, telaDeVerificacao)

                    createNewUser()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("teste", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "A autenticação falhou",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null, "null")
                }

                // [START_EXCLUDE]
                //hideProgressDialog()
                // [END_EXCLUDE]
            }
    }

    private fun sendEmailVerification() {

        verifyEmailButton.isEnabled = false

        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this) { task ->
                // [START_EXCLUDE]
                // Re-enable button
                verifyEmailButton.isEnabled = true

                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext,
                        "E-mail enviado para ${user.email} ",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        baseContext,
                        "Falha no envio do e-mail de verificação.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // [END_EXCLUDE]
            }
    }

    private fun validateForm(tipo: String): Boolean {
        var valid = true


        if (tipo.equals("mail")) {
            val fieldEmail = findViewById<EditText>(R.id.fieldEmail)
            val fieldPassword = findViewById<EditText>(R.id.fieldPassword)
            //val email = fieldEmail.text.toString()
            val password = fieldPassword.text.toString()

            if (fieldEmail.text.toString().isEmpty()) {
                fieldEmail.error = "Obrigatório"
                valid = false
            } else {
                //fieldEmail.error = null
            }

            if (!fieldEmail.text.toString().contains("@")) {
                fieldEmail.error = "E-mail inválido"
                valid = false
            } else {
                //fieldEmail.error = null
            }

            if (!fieldEmail.text.toString().contains(".")) {
                fieldEmail.error = "E-mail inválido"
                valid = false
            } else {
                //fieldEmail.error = null
            }

            if (TextUtils.isEmpty(password)) {
                fieldPassword.error = "Obrigatório"
                valid = false
            } else {
                //fieldPassword.error = null
            }

            if (password.length < 6) {
                fieldPassword.error = "A senha deve conter pelo menos 6 dígitos"
                valid = false
            } else {
                //fieldPassword.error = null
            }
        }

        if (tipo.equals("MailNew")) {

            val fieldEmail = findViewById<EditText>(R.id.fieldEmail_newUser)
            val fieldPassword = findViewById<EditText>(R.id.fieldPassword_newUser)
            //val email = fieldEmail.text.toString()
            val password = fieldPassword.text.toString()

            if (fieldEmail.text.toString().isEmpty()) {
                fieldEmail.error = "Obrigatório"
                valid = false
            } else {
                //fieldEmail.error = null
            }

            if (!fieldEmail.text.toString().contains("@")) {
                fieldEmail.error = "E-mail inválido"
                valid = false
            } else {
                //fieldEmail.error = null
            }

            if (!fieldEmail.text.toString().contains(".")) {
                fieldEmail.error = "E-mail inválido"
                valid = false
            } else {
                //fieldEmail.error = null
            }

            if (TextUtils.isEmpty(password)) {
                fieldPassword.error = "Obrigatório"
                valid = false
            } else {
                //fieldPassword.error = null
            }

            if (password.length < 6) {
                fieldPassword.error = "A senha deve ter pelo menos 6 dígitos"
                valid = false
            } else {
                //fieldPassword.error = null
            }

            val confirmaPassword = fieldPasswordConfirmation_newUser.text.toString()
            if (TextUtils.isEmpty(confirmaPassword)) {
                fieldPasswordConfirmation_newUser.error = "Obrigatório"
                valid = false
            } else if (!confirmaPassword.equals(password)) {
                fieldPasswordConfirmation_newUser.error = "As senhas são diferentes"
                valid = false
            }
        }
        return valid
        EncerraDialog()
    }

    private fun signIn(email: String, password: String) {

        ChamaDialog()
        if (!validateForm("mail")) {
            return
            EncerraDialog()
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val user = auth.currentUser
                    updateUI(user, "mail")
                    EncerraDialog()
                } else {

                    Toast.makeText(this, "E-mail ou senha inválidos", Toast.LENGTH_SHORT).show()
                    EncerraDialog()
                }

                if (!task.isSuccessful) {
                    Toast.makeText(this, "Algo deu errado", Toast.LENGTH_SHORT).show()
                    EncerraDialog()
                }
            }
    }

    private fun updateUI(user: FirebaseUser?, tipoLogin: String) {

        EncerraDialog()

        if (tipoLogin.equals("mail")) {

            //hideProgressDialog()
            if (user != null) {

                if (!user.isEmailVerified) {

                    trocaTela(telaDeVerificacao, telaLoginMailNew)
                    sendEmailVerification()
                    emailVerificationCheckMeth()

                } else {

                    val user: FirebaseUser? = auth.currentUser
                    val emailAddress = user?.email
                    val intent = Intent(this, IndexActivityNew::class.java)

                    intent.putExtra("email", emailAddress)
                    startActivity(intent)

                    telaLoginMailNew.visibility = View.GONE
                    trocaTela(telainicial, telaDeVerificacao)
                    EncerraDialog()
                }
            } else {

                trocaTela(telainicial, telaLoginMailNew)
            }
        } else if (tipoLogin.equals("unknown")) {  //este if é para o caso do usuario entrar depois, então nao sei qual métood de login mas ainda nao verificou email.

            var tipoLoginMeth = getLoginType(user, 0)


            if (tipoLoginMeth.equals("mail")) { //se for email verifica se a pessoa ja verificou o email. Se nao tiver feito abre a lay com verificacao. Senao vai abrir a proxima activity
                if (user != null) {
                    if (!user.isEmailVerified) {
                        //exibe a tela de verificação
                        Log.d("teste", "entrou em unknow")
                        val telaDeVerificacao =
                            findViewById<ConstraintLayout>(R.id.layLoginWithMail_VerificationMail)
                        telaDeVerificacao.visibility = View.VISIBLE
                        //laygenericoInRightToCenter(telaDeVerificacao)
                        emailVerificationCheckMeth() //libera o clique do botão para verificar se o e-mail foi enviado
                        sendEmailVerification()

                    } else {
                        val user: FirebaseUser? = auth.currentUser
                        val emailAddress = user?.email
                        val intent = Intent(this, IndexActivityNew::class.java)

                        intent.putExtra("email", emailAddress)
                        startActivity(intent)

                        telaDeVerificacao.visibility = View.GONE
                        telaLoginMailNew.visibility = View.GONE
                        telainicial.visibility = View.VISIBLE
                        EncerraDialog()
                    }
                }

            } else { //aqui é para o caso de nao ser via email. E neste caso, nao precisa verificar nada. Abre direto a segunda activity
                val intent = Intent(this, IndexActivityNew::class.java)
                val user: FirebaseUser? = auth.currentUser
                val emailAddress = user?.email
                intent.putExtra("email", emailAddress)
                startActivity(intent)

                telaDeVerificacao.visibility = View.GONE
                telaLoginMailNew.visibility = View.GONE
                telainicial.visibility = View.VISIBLE
                EncerraDialog()
            }

        } else if (tipoLogin.equals("facebook")) {
            Log.d("teste", "chegou aqui")
            val intent = Intent(this, IndexActivityNew::class.java)

            val user: FirebaseUser? = auth.currentUser
            val emailAddress = user?.email
            intent.putExtra("email", emailAddress)

            startActivity(intent)
            EncerraDialog()
            telainicial.visibility = View.VISIBLE

        } else {

            telaDeVerificacao.visibility = View.GONE
            telaLoginMailNew.visibility = View.GONE
            telainicial.visibility = View.VISIBLE
            EncerraDialog()
        }
    }

    fun getLoginType(user: FirebaseUser?, n: Int): String {

        var valor: Int = 0
        var provedor: String;

        if (user != null) {
            for (userInfo in user.getProviderData()) {
                if (userInfo.getProviderId().equals("facebook.com")) {
                    valor = 1
                }
            }
        } else {
            valor = 2
        }

        if (valor == 1) { //se entrar neste if é pq é facebook e ai não precisa verificar e-mail
            provedor = "facebook"
        } else {
            provedor = "mail"
        }

        return provedor
    }

    fun openPopUp(
        titulo: String,
        texto: String,
        exibeBtnOpcoes: Boolean,
        btnSim: String,
        btnNao: String,
        call: String
    ) {
        //exibeBtnOpcoes - se for não, vai exibir apenas o botão com OK, sem opção. Senão, exibe dois botões e pega os textos deles de btnSim e btnNao

        //EXIBIR POPUP
        // Initialize a new layout inflater instance
        val inflater: LayoutInflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Inflate a custom view using layout inflater
        val view = inflater.inflate(R.layout.popup_model, null)

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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

        if (exibeBtnOpcoes) {
            //vai exibir os botões com textos e esconder o btn ok
            buttonPopupOk.visibility = View.GONE
            //exibe e ajusta os textos dos botões
            buttonPopupN.text = btnNao
            buttonPopupS.text = btnSim

            // Set a click listener for popup's button widget
            buttonPopupN.setOnClickListener {
                // Dismiss the popup window
                popupWindow.dismiss()
            }
        } else {

            //vai esconder os botões com textos e exibir o btn ok
            buttonPopupOk.visibility = View.VISIBLE
            //exibe e ajusta os textos dos botões
            buttonPopupN.visibility = View.GONE
            buttonPopupS.visibility = View.GONE

            buttonPopupOk.setOnClickListener {
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
        val lay_root: ConstraintLayout = findViewById(R.id.LayPai)

        // Finally, show the popup window on app
        TransitionManager.beginDelayedTransition(lay_root)
        popupWindow.showAtLocation(
            lay_root, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )

        //aqui colocamos os ifs com cada call de cada vez que a popup for chamada
        if (call.equals("confirmaNovaSenha")) {

            buttonPopupS.setOnClickListener {
                //faz aqui o que quiser
                //precisa informar o e-mail
                val emailField = findViewById<EditText>(R.id.fieldEmail)

                val auth = FirebaseAuth.getInstance()
                //val user: FirebaseUser? = auth.currentUser
                //val emailAddress = user?.email
                emailField.text.toString()?.let { it1 -> auth.sendPasswordResetEmail(it1) }

                telainicial.visibility = View.VISIBLE
                telaLoginMailNew.visibility = View.GONE
                telaDeVerificacao.visibility = View.GONE

                openPopUp(
                    "E-mail enviado.",
                    "Foi enviado um e-mail para " + emailField.text.toString() + " com sua nova senha.",
                    false,
                    "Ok",
                    "OK",
                    "confirmaNovaSenha"
                )
                popupWindow.dismiss()
            }
        }
    }

    fun LoginWithEmail() {

        val btnNovoUser = findViewById<TextView>(R.id.layInicial_tvNovoUsuario)
        val txtBtnNovoUser = " <font color='#FFFFFF'> Ainda não está cadastrado? <u><b>Cadastre-se!</u></b></font>"
        btnNovoUser.setText(Html.fromHtml(txtBtnNovoUser))

        btnNovoUser.setOnClickListener {

            trocaTela(telaLoginMailNew, telainicial)

            val fieldEmail_newUser: EditText = findViewById(R.id.fieldEmail_newUser)
            fieldEmail_newUser.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    //TODO("Not yet implemented")
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    //TODO("Not yet implemented")
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    if (fieldEmail_newUser.text.contains("@")) {
                        fieldEmail_newUser.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_icon_cadeado,
                            0
                        );
                    } else {
                        fieldEmail_newUser.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_icon_cadeado_vermelho,
                            0
                        );
                    }
                }
            }
            )

            val fieldPassword_newUser: EditText = findViewById(R.id.fieldPassword_newUser)
            fieldPassword_newUser.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    //TODO("Not yet implemented")
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    //TODO("Not yet implemented")
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    if (fieldPassword_newUser.text.length == 6) {
                        fieldPassword_newUser.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_icon_cadeado,
                            0
                        );
                    } else {
                        fieldPassword_newUser.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_icon_cadeado_vermelho,
                            0
                        );
                    }
                }
            })

            val fieldPasswordConfirmation_newUser: EditText =
                findViewById(R.id.fieldPasswordConfirmation_newUser)
            fieldPasswordConfirmation_newUser.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    //TODO("Not yet implemented")
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    //TODO("Not yet implemented")
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    if (fieldPasswordConfirmation_newUser.text.toString()
                            .equals(fieldPassword_newUser.text.toString())
                    ) {
                        fieldPasswordConfirmation_newUser.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_icon_cadeado,
                            0
                        );
                    } else {
                        fieldPasswordConfirmation_newUser.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_icon_cadeado_vermelho,
                            0
                        );
                    }
                }
            })

            val btnVoltar: Button = findViewById(R.id.createWithEmail_btnCancel)
            btnVoltar.setOnClickListener {

                trocaTela(telaLoginMailNew, telainicial)

                btnVoltar.setOnClickListener { null }
                fieldEmail_newUser.addTextChangedListener(null)
                fieldPassword_newUser.addTextChangedListener(null)
                fieldPasswordConfirmation_newUser.addTextChangedListener(null)
            }
        }

        val btnNovoUser2 = findViewById<TextView>(R.id.layInicial_tvNovoUsuario)
        btnNovoUser2.setOnClickListener {
            telaLoginMailNew.visibility = View.VISIBLE
        }

        val signInEmail = findViewById<Button>(R.id.emailSignInButton) //botão de signin
        signInEmail.setOnClickListener {
            val etEmail = findViewById<EditText>(R.id.fieldEmail);
            val etPassword = findViewById<EditText>(R.id.fieldPassword);

            signIn(etEmail.text.toString(), etPassword.text.toString())
            hideKeyboard()
        }

        val emailCreateAccountBtn =
            findViewById<Button>(R.id.emailCreateAccountButton) //cria usuario
        emailCreateAccountBtn.setOnClickListener {
            hideKeyboard()
            val etEmail = findViewById<EditText>(R.id.fieldEmail_newUser)
            val etPassword = findViewById<EditText>(R.id.fieldPassword_newUser);

            createAccount(
                etEmail.text.toString(),
                etPassword.text.toString()
            ) //a verificação é feita dentro de validateForm
        }

        val emailVerifyCheck =
            findViewById<Button>(R.id.verifyEmailButtonCheck) //botao que o user aperta quando ja vericou o email
        val verifyEmailButton = findViewById<Button>(R.id.verifyEmailButton) //botão para reenviar
        val verifyVoltar = findViewById<Button>(R.id.verification_btnVoltar)


        verifyVoltar.setOnClickListener {

            trocaTela(telaDeVerificacao, telainicial)
        }

        verifyEmailButton.setOnClickListener {
            sendEmailVerification()
        }

        emailVerificationCheckMeth()

        emailVerifyCheck.setOnClickListener {
            val user = auth.currentUser
            if (user != null) {
                user!!.reload()

                if (!user.isEmailVerified) {
                    Toast.makeText(this, "O e-mail ainda não foi verificado.", Toast.LENGTH_SHORT)
                } else {
                    updateUI(user, "mail")
                }
            }
        }


        val novaSenha = findViewById<TextView>(R.id.tvPerdeuSenha)
        novaSenha.setOnClickListener {

            val emailField = findViewById<EditText>(R.id.fieldEmail)
            if (emailField.text.toString().isEmpty()) {
                emailField.requestFocus()
                emailField.setError("Informe o e-mail para enviar o reset da senha.")
            } else if (!emailField.text.toString().contains("@")) {
                emailField.requestFocus()
                emailField.setError("Informe um e-mail válido")
            } else {
                openPopUp(
                    "ATENÇÃO",
                    "Você deseja receber uma nova senha por e-mail?",
                    true,
                    "Sim, quero",
                    "Não",
                    "confirmaNovaSenha"
                )
            }
        }
    }

    fun emailVerificationCheckMeth() {

        val emailVerifyCheck =
            findViewById<Button>(R.id.verifyEmailButtonCheck) //botao que o user aperta quando ja vericou o email
        emailVerifyCheck.setOnClickListener {
            val user = auth.currentUser
            if (user != null) {
                user!!.reload()

                if (!user.isEmailVerified) {
                    Toast.makeText(this, "O e-mail ainda não foi verificado.", Toast.LENGTH_SHORT)
                } else {
                    updateUI(user, "mail")
                }
            }
        }
    }

    fun createNewUser() {

        databaseReference = FirebaseDatabase.getInstance().reference
        val user: FirebaseUser? = auth.currentUser
        val emailAddress = user?.email

        val newCad: DatabaseReference = databaseReference.child("usuarios").push()
        val userBD = newCad.key.toString()
        newCad.child("email").setValue(emailAddress)
        newCad.child("Estado").setValue("nao")
        newCad.child("certificados").setValue(0)
        newCad.child("tipo").setValue("colaborador")
        newCad.child("avaliacoes").setValue(0)
        newCad.child("img").setValue("nao")
        newCad.child("nome").setValue("nao")
        newCad.child("contato").setValue("nao")
        newCad.child("pontos").setValue("0")
        newCad.child("skillrel").setValue("0")
        newCad.child("skilltec").setValue("0")
        newCad.child("skillseg").setValue("0")

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

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    /* To hide Keyboard */
    fun hideKeyboard() {
        try {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun trocaTela(layoutSai: ConstraintLayout, layoutEntra: ConstraintLayout) {

        val saindo = AnimationUtils.loadAnimation(this, R.anim.layout_slideout)
        val entrando = AnimationUtils.loadAnimation(this, R.anim.layout_slidein)
        layoutEntra.visibility = View.VISIBLE
        layoutSai.startAnimation(saindo)
        layoutEntra.startAnimation(entrando)
        layoutSai.visibility = View.GONE
    }
}