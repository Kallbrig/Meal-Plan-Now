package helpers


import android.util.Log.d
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.jetbrains.anko.doAsync

class authManager {
    private val TAG = "AUTH MANAGER"
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    var data = databaseManager()

    init {
        if (auth.currentUser != null) {


            //No, work on it
            //data.readData(auth.currentUser!!.uid).result?.getString("name")

            //THIS ONE
            //d(TAG,data.readData(auth.currentUser!!.uid).result?.getString("name") +  "Signed In")
        }

    }

    fun signUpUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
        sendVerificationEmail()

    }

    fun SignInUser(email: String, password: String) {

        d(TAG, "Email and password is pulled: sign up")

        //Verifying that no one is logged in
        if (auth.currentUser != null) {
            auth.signOut()
        }

        //Logging in with Email and Password.
        auth.signInWithEmailAndPassword(email, password)
    }


    private fun sendVerificationEmail() {
        doAsync {
            auth.currentUser?.sendEmailVerification()
        }
        d(TAG, "Verification Email Sent")
    }


}