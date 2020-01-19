package helpers

import android.util.Log.d
import android.util.Log.w
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult


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
    fun readData(): MutableMap<String, Any> {

        return doAsyncResult {
            //var finalInfo: MutableMap<String, String>
            val auth = FirebaseAuth.getInstance()
            val info = db.collection("users").document(auth.currentUser!!.uid)
            var infoStorage: MutableMap<String, Any> = mutableMapOf()
            d(TAG, auth.currentUser!!.uid)

            info.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        //This returns data correctly
                        d(TAG, "DocumentSnapshot data: ${document.data}")

                        //for some reason this assignment doesn't work
                        //it's returning a null map
                        infoStorage = document.data!!
                    }
                }
                .addOnFailureListener { exception ->
                    d(TAG, "get failed with ", exception)
                    infoStorage = mutableMapOf("name" to "none")
                }


            return@doAsyncResult infoStorage
        }.get()



        }


    fun getUser() {


    }
}