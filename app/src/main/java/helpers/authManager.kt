package helpers


import android.content.Context
import android.util.Log
import android.util.Log.i
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.jetbrains.anko.doAsync

class authManager {
    private val TAG = "AUTH MANAGER"
    lateinit var auth: FirebaseAuth
    var user: FirebaseUser? = null

    fun signUpUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
        sendVerificationEmail()

    }

    fun SignInUser(email: String, password: String) {

        i(TAG, "Email and password is pulled: sign up")

        //Verifying that no one is logged in
        auth.signOut()

        //Logging in with Email and Password.
        auth.signInWithEmailAndPassword(email, password)
    }


    private fun sendVerificationEmail() {
        doAsync {
            user?.sendEmailVerification()
        }
        i(TAG, "Verification Email Sent")
    }


}