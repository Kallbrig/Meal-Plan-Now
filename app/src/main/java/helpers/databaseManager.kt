package helpers

import Prototype.Design.user
import android.util.Log
import android.util.Log.i
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult

class databaseManager {


    // Access a Cloud Firestore instance from your Activity
    private val db = FirebaseFirestore.getInstance()
    private val usersDb = db.collection("users")
    private val favsList = db.document("/users/93FjtHSr5XGj5Ba27ayH/Favs/nec0R50QBFUbWbtAzjNH")


    private val TAG = "DB MANAGER"


    fun createDB() {


        usersDb.add(hashMapOf("Id" to "12345", "Name" to "The Black Dick"))
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }


    }

    fun getDB() {


/*


            usersDb.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }*/

    }
}