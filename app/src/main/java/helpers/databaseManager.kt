package helpers

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore


class databaseManager {


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

    fun addData() {
        // Create a new user with a first and last name
        // Create a new user with a first and last name
        val user: MutableMap<String, Any> = HashMap()
        user["first"] = "Ada"
        user["last"] = "Lovelace"
        user["born"] = 1815

// Add a new document with a generated ID
        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    TAG,
                    "DocumentSnapshot added with ID: " + documentReference.id
                )
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
    }

    fun addMoreData() {
        // Create a new user with a first, middle, and last name
        // Create a new user with a first, middle, and last name
        val user: MutableMap<String, Any> = HashMap()
        user["first"] = "Alan"
        user["middle"] = "Mathison"
        user["last"] = "Turing"
        user["born"] = 1912

// Add a new document with a generated ID
        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    TAG,
                    "DocumentSnapshot added with ID: " + documentReference.id
                )
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





            usersDb.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

    }
}