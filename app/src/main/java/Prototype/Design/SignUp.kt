package Prototype.Design

import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import helpers.authManager
import helpers.databaseManager
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.doAsync

class SignUp : AppCompatActivity() {

    val TAG = "LOGIN VIEW"
    private var user: FirebaseUser? = null
    private val fauth: FirebaseAuth = FirebaseAuth.getInstance()
    private val auth = authManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        user = fauth.currentUser
        val titleBar = findViewById<TextView>(R.id.titleBarSignUp)
        titleBar.text = getString(R.string.appName)


        val email = findViewById<EditText>(R.id.signUpEmail)
        val password = findViewById<EditText>(R.id.signUpPassword)

        if (intent.hasExtra("email")) {
            email.setText(intent.extras?.getString("email"))
        }
        if (intent.hasExtra("password")) {
            password.setText(intent.extras?.getString("password"))
        }

        //Sign Up Button onClickListener
        //Uses authManager helper class method signUpUser(email,password,nickname) to create new user
        findViewById<Button>(R.id.signUpButSignUp).setOnClickListener {
            auth.signUpUser(
                email.text.toString(),
                password.text.toString(),
                signUpNickName.text.toString()
            )
        }

        val backBut = findViewById<ImageButton>(R.id.backButtonSignUp)
        backBut.setOnClickListener {
            email.text.clear()
            password.text.clear()
            signUpNickName.text.clear()
            signUpReEnterPassword.text.clear()
            finish()
        }

        //FOR TESTING ONLY
        //REMOVE THIS BECAUSE IT'S FOR TESTING ONLY
        signUpNickName.setText("Chase")
        signUpEmail.setText("chase.allbright@gmail.com")
        signUpPassword.setText("Kca24987")
        signUpReEnterPassword.setText("Kca24987")

    }

}
