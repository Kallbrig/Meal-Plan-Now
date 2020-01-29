package helpers

import android.util.Log.d
import android.util.Log.w
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class databaseManager {

    private var db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val TAG = "DB MANAGER"
    private lateinit var user: UserInfo


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
    fun readData(): UserInfo {

        val auth = FirebaseAuth.getInstance()

        user = UserInfo()

        //this path is correct and works in other functions.
        db.collection("users").document(auth.currentUser!!.uid)
            .get()
            .addOnCompleteListener { task ->

                val data: MutableMap<String, Any?> = task.result?.data!!

                if (data.isNullOrEmpty()) {
                    d(TAG, "Document Result is Null or Empty")

                } else {
                    d(TAG, "Document is not Empty. Adding to user data class.")

                    //This seems to set name and ID, but not email
                    user.name = data["name"].toString()
                    user.email = data["email"].toString()
                    user.id = data["id"].toString()

                    //This logs correct response
                    d(TAG, user.name!!)

                    //This logs correct response
                    d(TAG, data.toString())

                }
            }
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
        var id: String? = "id",
        var Favs1: String? = "favs",
        var SevenDay1: String? = "sevenday"

    )
}