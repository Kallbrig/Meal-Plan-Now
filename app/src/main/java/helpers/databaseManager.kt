package helpers

import android.util.Log.d
import android.util.Log.w
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class databaseManager {

    private var db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val TAG = "DB MANAGER"


    fun createUser(userID: String, userName: String) {


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