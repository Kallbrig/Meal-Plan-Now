package helpers

import android.util.Log.d
import android.util.Log.w
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class databaseManager {

    lateinit var docId: String


    // Access a Cloud Firestore instance from your Activity
    private var db = FirebaseFirestore.getInstance()
    private val usersDb = db.collection("users")
    //private val favsList = db.document("/users/")


    private val TAG = "DB MANAGER"


    fun createUser(userID: String, userName: String) {
        val auth = FirebaseAuth.getInstance()

        // Create a new user with a name, email and UID
        val user = hashMapOf(
            "name" to userName,
            "id" to auth.currentUser?.uid,
            "email" to auth.currentUser?.email
        )

        // Add a new document with the User's UserID provided by Google
        db.collection("users").document(userID).set(user)
            .addOnSuccessListener { documentReference ->
                d(
                    TAG,
                    "DocumentSnapshot added with ID: $documentReference"
                )
            }
            .addOnFailureListener { e -> w(TAG, "Error adding document", e) }


    }


    fun addToFavs(mealIDToAddToFavs: String) {
        val user = FirebaseAuth.getInstance().currentUser
        db.collection("users").document(user!!.uid).update(
            "favs",
            ",$mealIDToAddToFavs"
        )
    }


    //This function successfully gets user data from Firestore and logs it and the userID
    //Don't mess this up. Copy is below
    fun readData() {
        val auth = FirebaseAuth.getInstance()
        var userInfo = UserInfo()
        db.collection("users").document(auth.currentUser!!.uid)
            .get()
            .addOnCompleteListener { task ->

            }

    }

    fun getAllUsers() { // [START get_all_users]
        db.collection("users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        d(TAG, document.id + " => " + document.data)
                    }
                } else {
                    w(TAG, "Error getting documents.", task.exception)
                }
            }
        // [END get_all_users]
    }


    fun getUser() {


    }

    data class UserInfo(
        var name: String? = "",
        var email: String? = ""
    ) {
        constructor() : this("", "")
    }
}