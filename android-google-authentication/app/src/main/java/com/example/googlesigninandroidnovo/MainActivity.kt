package com.example.googlesigninandroidnovo
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.actionCodeSettings
import com.squareup.picasso.Picasso
import com.google.android.material.textfield.TextInputLayout


const val RC_SIGN_IN = 123

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestScopes(Scope(Scopes.DRIVE_APPFOLDER))
            .requestEmail()
            .requestProfile()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        firebaseAuth= FirebaseAuth.getInstance()

        val name = findViewById<TextView>(R.id.tv_name)
        //  val email = findViewById<EditText>(R.id.cadastro_usuario_email).text.toString()
        //  val senha = findViewById<EditText>(R.id.cadastro_usuario_senha).text.toString()

        val textInputLayoutEmail = findViewById<TextInputLayout>(R.id.cadastro_usuario_email)
        val email: Editable = textInputLayoutEmail.editText!!.text

        val textInputLayoutSenha = findViewById<TextInputLayout>(R.id.cadastro_usuario_senha)
        val senha: Editable = textInputLayoutSenha.editText!!.text

        val cadastrarButton = findViewById<Button>(R.id.cadastro_usuario_botao_cadastrar)
        cadastrarButton.setOnClickListener{
            //  val senha = "teste123"
            // val email = "matheusruggiero1996@gmail.com"
            val tarefa = firebaseAuth.createUserWithEmailAndPassword(email.toString(),senha.toString())
            tarefa.addOnSuccessListener {
                Toast.makeText(this, "Usuário foi cadastrado com sucesso", Toast.LENGTH_SHORT).show()
            }
            tarefa.addOnFailureListener{
                Log.e(TAG,"onCreate: ", it)
                Toast.makeText(this,"Falha ao cadastrar $it",Toast.LENGTH_SHORT).show()
                println("email $email")
            }

        }





        //val personId = findViewById<TextView>(R.id.personId)
        // val personEmail = findViewById<TextView>(R.id.personEmail)


        // Set the dimensions of the sign-in button.
        val signInButton = findViewById<SignInButton>(R.id.sign_in_button)
        signInButton.visibility = View.VISIBLE
        signInButton.setSize(SignInButton.SIZE_STANDARD)

        signInButton.setOnClickListener{
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        val acct = GoogleSignIn.getLastSignedInAccount(this)

        if (acct != null) {
            signInButton.visibility = View.GONE
            name.text = acct.displayName
            name.visibility = View.VISIBLE

            val personName = acct.displayName
            val personGivenName = acct.givenName
            val personFamilyName = acct.familyName
            //personEmail.text = acct.email
            // personId.text = acct.id
            val personPhoto: Uri? = acct.photoUrl
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            try {
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                val authCode = account.serverAuthCode

                // Show signed-un UI
                handleSignInResult(task)

                // TODO(developer): send code to server and exchange for access/refresh/ID tokens
            } catch (e: ApiException) {
                println("CAIU NO EXCPETION DO onActivityResult")
                Log.w(TAG, "Sign-in failed", e)

            }

           // handleSignData(data)
        }
    }

    private fun handleSignData(data: Intent?) {
        // The Task returned from this call is always completed, no need to attach
        // a listener.
        GoogleSignIn.getSignedInAccountFromIntent(data)
            .addOnCompleteListener {
                println( "isSuccessful ${it.isSuccessful}")
                if (it.isSuccessful){
                    // user successfully logged-in
                    println("account ${it.result?.account}")
                    println("displayName ${it.result?.displayName}")
                    println("Email ${it.result?.email}")
                } else {
                    // authentication failed
                    println("exception ${it.exception}")
                }
            }
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            println("ENTROU NO TRY")
            val account = completedTask.getResult(ApiException::class.java)
            println("google token --> ${account.idToken}")
            // Signed in successfully, show authenticated UI.

            val signInButton = findViewById<SignInButton>(R.id.sign_in_button)
            signInButton.visibility = View.GONE
            val name = findViewById<TextView>(R.id.tv_name)
            // val personId = findViewById<TextView>(R.id.personId)
            // val personEmail = findViewById<TextView>(R.id.personEmail)
            val personPhoto: Uri? = account.photoUrl
            //findViewById<ImageView>(R.id.personPhoto)


            name.text = account.displayName
            name.visibility = View.VISIBLE
//
//            personId.text = account.id
//            personId.visibility = View.VISIBLE
//
//            personEmail.text = account.email
//            personEmail.visibility = View.VISIBLE

//
//            val photo = findViewById<ImageView>(R.id.personPhoto)
//            Picasso.with(this).load(personPhoto).into(photo)

            UpdateUI(account)

        } catch (e: ApiException) {
            println("CAIU NO EXCPETION DO handleSignInResult")
            println("exception ${e.statusCode}")
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(ContentValues.TAG, "signInResult:failed code=" + e.statusCode)
            e.printStackTrace()

            val signInButton = findViewById<SignInButton>(R.id.sign_in_button)
            signInButton.visibility = View.VISIBLE
            val name = findViewById<TextView>(R.id.tv_name)
            name.text = ""
            name.visibility = View.GONE
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount){
        val credential= GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {task->
            if(task.isSuccessful) {
                // SavedPreference.setEmail(this,account.email.toString())
                // SavedPreference.setUsername(this,account.displayName.toString())
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}