package Prototype.Design


import android.content.Intent
import android.os.Bundle
import android.util.Log.*

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import org.jetbrains.anko.doAsync


private val TAG: String = "LOGIN ACTIVITY"
lateinit var auth: FirebaseAuth
var user: FirebaseUser? = null

class login_view : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_view)
        i(TAG, "Login Activity Started")
        var titleBarText = findViewById<TextView>(R.id.titleBarLogin)
        titleBarText.text = "MealPlanNow!"

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser

        var loginBut = findViewById<Button>(R.id.loginBut)
        var signUpBut = findViewById<Button>(R.id.signUpBut)

        loginBut.setOnClickListener {

            var email = findViewById<EditText>(R.id.signInEmail).text.toString()
            var password = findViewById<EditText>(R.id.signInPassword).text.toString()

            if (email == "" || password == "") {
                makeText(this, "Please Input an Email & Password", LENGTH_SHORT).show()

            } else {
                i(TAG, "Sign In Initiated. Hang on!")
/*            email = "chase.allbright@outlook.com"
            password = "1234567890"*/


                if (auth.currentUser?.email.toString() != email && auth.currentUser?.email.toString() != "null") {
                    i(TAG, "Signing " + user?.email + " out.")
                    auth.signOut()
                }

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            user = auth.currentUser
                            makeText(this, "Sign in Successful. Welcome!", LENGTH_SHORT).show()
                            createMainIntent()
                        } else {
                            val e = task.exception as FirebaseAuthException?
                            makeText(
                                this,
                                "Failed Sign In: " + e!!.message,
                                LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }


        signUpBut.setOnClickListener {

            var email = findViewById<EditText>(R.id.signInEmail).text.toString()
            var password = findViewById<EditText>(R.id.signInPassword).text.toString()

            if (email == "" || password == "") {
                makeText(this, "Please Input an Email & Password", LENGTH_SHORT).show()

            } else {
                i(TAG, "Email and password is pulled: sign up")
/*            email = "chase.allbright@outlook.com"
            password = "1234567890"*/

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            d(TAG, "createUserWithEmail:success")
                            makeText(
                                baseContext,
                                "Sign Up Success. Please Check Your Email!",
                                LENGTH_LONG
                            ).show()
                            user = auth.currentUser
                            sendVerificationEmail()

                        } else {
                            // If sign in fails, display a message to the user.
                            w(TAG, "createUserWithEmail:failure", task.exception)
                            //Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                            val e = task.exception as FirebaseAuthException?
                            makeText(
                                this,
                                "Failed Registration: " + e!!.message,
                                LENGTH_SHORT
                            ).show()

                            //UPDATE UI FAIL
                        }
                    }
            }
        }

    }

    /* override fun onStart() {
           super.onStart()
           // Check if user is signed in (non-null) and update UI accordingly.

         if (user != null) {
               createMainIntent()
         } else {
             e(TAG, "Current User is null")
         }
       }*/


    /*  fun emailSignUpBut(v: View) {
          //var loginBut = findViewById<Button>(R.id.loginBut)
          //var SignUpBut = findViewById<Button>(R.id.signUpBut)
          var email = findViewById<EditText>(R.id.signInEmail).text.toString()
          var password = findViewById<EditText>(R.id.signInPassword).text.toString()
          i(TAG, "Email and password is pulled: sign up")


          auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
              if (task.isSuccessful) {
                  // Sign in success, update UI with the signed-in user's information
                  d(TAG, "createUserWithEmail:success")
                  makeText(baseContext, "Authentication Success", LENGTH_LONG).show()
                  user = auth.currentUser
                  sendVerificationEmail()

              } else {
                  // If sign in fails, display a message to the user.
                  w(TAG, "createUserWithEmail:failure", task.exception)
                  //Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                  val e = task.exception as FirebaseAuthException?
                  makeText(this, "Failed Registration: " + e!!.message, Toast.LENGTH_SHORT).show()

                  //UPDATE UI FAIL
              }
          }
      }
  */

    private fun sendVerificationEmail() {
        doAsync {
            user?.sendEmailVerification()/*?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    var t = makeText(this, "Please Verify Your Email", LENGTH_LONG)
                    t.show()
                } else {
                    var t = makeText(this, (task.exception as FirebaseAuthException).message, LENGTH_LONG)
                    t.show()
                }
            }*/
        }
        i(TAG, "Verification Email Sent")
    }


/*    fun signInBut(v: View) {
        var email = findViewById<EditText>(R.id.signInEmail).text.toString()
        var password = findViewById<EditText>(R.id.signInPassword).toString()
        i(TAG, "Email and password is pulled: sign up")

        auth.signOut()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {

                user = auth.currentUser
                createMainIntent()

            } else {

                val e = task.exception as FirebaseAuthException?
                makeText(this, "Failed Sign In: " + e!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }*/

    private fun createMainIntent() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}

