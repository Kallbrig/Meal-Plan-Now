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

        intent.hasExtra("email")
        if (intent.hasExtra("email")) {
            email.setText(intent.extras?.getString("email"))
        }
        if (intent.hasExtra("password")) {
            password.setText(intent.extras?.getString("password"))
        }

        //Sign Up Button onClickListener
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

    fun signUpUser() {
        d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")

        var email = findViewById<EditText>(R.id.signUpEmail).text.toString()
        var password = findViewById<EditText>(R.id.signUpPassword).text.toString()
        var nickname = findViewById<EditText>(R.id.signUpNickName).text.toString()
        var reEnterPassword = findViewById<EditText>(R.id.signUpReEnterPassword).text.toString()


        if ((password != reEnterPassword)) {
            makeText(this, "Passwords Do Not Match! Please Try Again!", LENGTH_SHORT).show()
        } else {

            //Checks if either editText is blank and shows a toast to the user if they are.
            if (email == "" || password == "" || nickname == "") {
                makeText(this, "Please Input an Email, Password, and Nickname", LENGTH_SHORT).show()
            }

            //If email and password are not blank, proceed and log a message
            else {
                d(TAG, "Email and password is pulled: sign up")
                val data = databaseManager()
                val db = FirebaseFirestore.getInstance()

                fauth.createUserWithEmailAndPassword(email, password)
                user = fauth.currentUser

                db.collection("users").document(user!!.uid).set("name" to "name")
                data.addNewUser(nickname, email, user!!.uid)
                sendVerificationEmail()
                //makeText(this, "Check your Email", LENGTH_SHORT).show()
            }
        }
        d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
    }


    //Creates new user with email,password, nickname
    //Creates firestore entry with the email, nickname, and userID
    //Shows toast with success or failure
    private fun signUp() {

        val data = databaseManager()

        var email = findViewById<EditText>(R.id.signUpEmail).text.toString()
        var password = findViewById<EditText>(R.id.signUpPassword).text.toString()
        var nickname = findViewById<EditText>(R.id.signUpNickName).text.toString()
        var reEnterPassword = findViewById<EditText>(R.id.signUpReEnterPassword).text.toString()

        if ((password != reEnterPassword)) {
            makeText(this, "Passwords Do Not Match! Please Try Again!", LENGTH_SHORT).show()
        } else {

            //Checks if either editText is blank and shows a toast to the user if they are.
            if (email == "" || password == "" || nickname == "") {
                makeText(this, "Please Input an Email, Password, and Nickname", LENGTH_SHORT).show()
            }

            //If email and password are not blank, proceed and log a message
            else {
                d(TAG, "Email and password have been pulled: sign up")


                //Actual sign up happens here
                //Calls auth which is a Firebase Instance global variable
                fauth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update with the signed-in user's information
                            d(TAG, "createUserWithEmail:success")
                            data.addNewUser(nickname, email, user!!.uid)

                            makeText(
                                baseContext,
                                "Sign Up Success. Please Check Your Email!",
                                Toast.LENGTH_LONG
                            ).show()
                            user = fauth.currentUser
                            sendVerificationEmail()
                            finish()

                        } else {
                            //If sign in fails, log message and toast with error message
                            Log.w(
                                TAG,
                                "createUserWithEmail:failure",
                                task.exception
                            )
                            val e = task.exception as FirebaseAuthException?
                            makeText(
                                this,
                                "Failed Registration: " + e!!.message,
                                LENGTH_SHORT
                            ).show()
                        }
                        email = ""
                        password = ""
                    }
            }
        }
    }


    //Sends Verification email to the current user
    private fun sendVerificationEmail() {
        doAsync {
            user?.sendEmailVerification()!!.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    d(TAG, "Verification Email Sent - " + user?.email)
                } else {
                    Log.e(TAG, (task.exception as FirebaseException).message.toString())
                }
            }
        }


    }


}
