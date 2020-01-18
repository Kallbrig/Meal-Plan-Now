package helpers


import android.util.Log.d
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

    fun signUpUser(email: String, password: String) {
        val db = FirebaseFirestore.getInstance()
        var fauth = FirebaseAuth.getInstance()

        var userID: String = ""

        fauth.addAuthStateListener { it ->

        }

        fauth.createUserWithEmailAndPassword(email, password)
            .addOnFailureListener(){
            e -> d(TAG, "User Creation Failed - - - $e")
        }
            .addOnCompleteListener{
                t -> d(TAG, "User Creation Done - - - ${t}")
            }

        d(TAG, "!!!!!!!!!!!!!!!!$userID")


/*
        auth = FirebaseAuth.getInstance()


        d(TAG, auth.currentUser!!.uid)


            db
              .collection("users")
                  .document(auth.currentUser!!.uid)
                      .set("name" to "name")

            .addOnFailureListener { e ->
                d(TAG, e.toString())
            }

            .addOnCompleteListener { t ->
                d(TAG, t.toString())
            }*/
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