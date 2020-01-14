package Prototype.Design

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.i
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import helpers.databaseManager
import org.jetbrains.anko.doAsync

class SignUp : AppCompatActivity() {

    val TAG = "LOGIN VIEW"
    private var user: FirebaseUser? = null
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        user = auth.currentUser


        //Sign Up Button onClickListener
        findViewById<Button>(R.id.signUpButSignUp).setOnClickListener {
            signUp()
        }
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
            if (email == "" || password == "") {
                makeText(this, "Please Input an Email & Password", LENGTH_SHORT).show()
            }

            //If email and password are not blank, proceed and log a message
            else {
                Log.d(TAG, "Email and password is pulled: sign up")

                //Actual sign up happens here
                //Calls auth which is a Firebase Instance global variable
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            data.addNewUser(nickname, email, user!!.uid)

                            makeText(
                                baseContext,
                                "Sign Up Success. Please Check Your Email!",
                                Toast.LENGTH_LONG
                            ).show()
                            user = auth.currentUser
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
                    Log.d(TAG, "Verification Email Sent - " + user?.email)
                } else {
                    Log.e(TAG, (task.exception as FirebaseException).message.toString())
                }
            }
        }


    }


}
