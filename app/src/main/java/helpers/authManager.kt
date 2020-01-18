package helpers


import android.util.Log.d
import android.util.Log.w
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.jetbrains.anko.doAsync

class authManager {
    private val TAG = "AUTH MANAGER"
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    var data = databaseManager()

    init {
        if (auth.currentUser != null) {


            //No, work on it
            //data.readData(auth.currentUser!!.uid).result?.getString("name")

            //THIS ONE
            //d(TAG,data.readData(auth.currentUser!!.uid).result?.getString("name") +  "Signed In")
        }

    }

    fun signUpUser(email: String, password: String, userName: String) {
        val db: databaseManager = databaseManager()
        val fauth: FirebaseAuth = FirebaseAuth.getInstance()
        var userAuth: FirebaseAuth

        fauth.addAuthStateListener {
            d(TAG, "Auth State Has Changed!")
        }

        fauth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { t ->
                if (t.isSuccessful) {
                    userAuth = auth

                    //TESTING USE ONLY. REMOVE PERSONAL INFORMATION BEFORE RELEASE
                    d(TAG, "create User With Email: Success! - - - ${userAuth.currentUser!!.email}")
                    d(TAG, "UID - - - ${userAuth.currentUser!!.uid}")
                    db.createUser(userAuth.currentUser!!.uid, userName)


                } else {
                    w(TAG, "create User With Email: Failure!", t.exception)

                }
            }

        sendVerificationEmail()

    }

    fun getUserData() {
        val db = FirebaseFirestore.getInstance()
        doAsync {
            var suck = db.collection("users").document(auth.currentUser!!.uid)
            suck.get()
            d(TAG, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$suck")
        }
    }


    fun SignInUser(email: String, password: String) {

        d(TAG, "Sign in Has Begun")

        //Verifying that no one is logged in
        /* if (auth.currentUser != null) {
             auth.signOut()
         }
 */
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