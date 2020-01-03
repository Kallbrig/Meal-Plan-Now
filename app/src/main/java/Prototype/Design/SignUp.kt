package Prototype.Design

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        user = auth.currentUser
        val data = databaseManager()

        findViewById<Button>(R.id.signUpButSignUp).setOnClickListener {

            signUp()

        }
    }

    private fun signUp() {

        val data = databaseManager()

        var email = findViewById<EditText>(R.id.signUpEmail).text.toString()
        var password = findViewById<EditText>(R.id.signUpPassword).text.toString()
        var nickname = findViewById<EditText>(R.id.signUpNickName).text.toString()
        var reEnterEmail = findViewById<EditText>(R.id.signUpReEnterPassword).text.toString()

        if ((email != reEnterEmail)) {
            makeText(this, "Emails Do Not Match! Please Try Again!", LENGTH_SHORT).show()
        } else {


            if (email == "" || password == "") {
                makeText(this, "Please Input an Email & Password", LENGTH_SHORT).show()

            } else {
                Log.d(TAG, "Email and password is pulled: sign up")

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            data.addBlankUser()
                            data.addData()


                            makeText(
                                baseContext,
                                "Sign Up Success. Please Check Your Email!",
                                Toast.LENGTH_LONG
                            ).show()
                            user = auth.currentUser
                            sendVerificationEmail()

                        } else {
                            // If sign in fails, display a message to the user.
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

                            //UPDATE UI FAIL
                        }
                        email = ""
                        password = ""
                    }
            }
        }
    }

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
