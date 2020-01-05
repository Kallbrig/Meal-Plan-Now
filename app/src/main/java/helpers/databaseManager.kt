package helpers

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore


class databaseManager {

    lateinit var docId: String


    // Access a Cloud Firestore instance from your Activity
    private var db = FirebaseFirestore.getInstance()
    private val usersDb = db.collection("users")
    private val favsList = db.document("/users/93FjtHSr5XGj5Ba27ayH/Favs/nec0R50QBFUbWbtAzjNH")


    private val TAG = "DB MANAGER"


    fun createDB() {


        usersDb.add(hashMapOf("Id" to "12345", "Name" to "Jack's Big PP"))
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }


    }

    fun createUser(userID: String) {
        // Create a new user with a first and last name
        // Create a new user with a first and last name

        val user: MutableMap<String, Any> = HashMap()


// Add a new document with a generated ID
        // Add a new document with a generated ID
        db.collection("users").document(userID).set(user)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    TAG,
                    "DocumentSnapshot added with ID: $documentReference"
                )
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }


    }

    fun addNewUser(nickname: String, email: String, userID: String) {
        lateinit var docId: String

        val user: MutableMap<String, Any> = HashMap()

        user["name"] = nickname
        user["id"] = userID
        user["email"] = email

        user["prefCat1"] = ""
        user["prefCat2"] = ""
        user["prefCat3"] = ""

        user["favs0"] = ""
        user["favs1"] = ""
        user["favs2"] = ""
        user["favs3"] = ""
        user["favs4"] = ""
        user["favs5"] = ""
        user["favs6"] = ""
        user["favs7"] = ""
        user["favs8"] = ""
        user["favs9"] = ""
        user["favs10"] = ""
        user["favs11"] = ""
        user["favs12"] = ""
        user["favs13"] = ""
        user["favs14"] = ""
        user["favs15"] = ""
        user["favs16"] = ""
        user["favs17"] = ""
        user["favs18"] = ""
        user["favs19"] = ""

        user["sevenDay0"] = ""
        user["sevenDay1"] = ""
        user["sevenDay2"] = ""
        user["sevenDay3"] = ""
        user["sevenDay4"] = ""
        user["sevenDay5"] = ""
        user["sevenDay6"] = ""

        //Adds new user to Db with nickname, email, and userID to a document with id = UserID
        db.collection("users").document(userID).set(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }

    }

    fun readData() {
        db.collection("users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.d(TAG, document.id + " => " + document.data)
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
    }


    fun getUser() {


    }
}