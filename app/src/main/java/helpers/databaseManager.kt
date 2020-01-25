package helpers

import android.util.Log.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class databaseManager {

    private var db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val TAG = "DB MANAGER"


    fun createUser(userID: String, userName: String) {


        // Create a new user with a name, email and UID
        val user: UserInfo = UserInfo()

        user.name to userName
        user.id to auth.currentUser?.uid
        user.email to auth.currentUser?.email


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

    //Check this further. This is a bookmark
    fun readData(): UserInfo? {
        val auth = FirebaseAuth.getInstance()
        var user: UserInfo? = UserInfo()
        db.collection("users").document(auth.currentUser!!.uid)
            .get()
            .addOnCompleteListener { task ->
                val data: MutableMap<String, Any?>? = task.result?.data
                if (data.isNullOrEmpty()) {
                    d(TAG, "Document Result is Null or Empty")
                    user = UserInfo("name", "email", "id")
                } else {
                    d(TAG, "Document is not Empty. Adding to user data class.")
                    user = UserInfo(
                        name = data["name"].toString(),
                        email = data["email"].toString(),
                        id = data["id"].toString()
                    )
/*                    user.name = data["name"].toString()
                    user.email = data["email"].toString()
                    user.id = data["id"].toString()*/

                }
            }
        //Without this kotlin thinks it's not initialized??
        //Work more with initializing data classes

        return user

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
        var name: String? = "name",
        var email: String? = "name@name.name",
        var id: String? = "",
        var Favs1: String? = "",
        var SevenDay1: String? = ""

    ) {
        constructor() : this("")

    }
}