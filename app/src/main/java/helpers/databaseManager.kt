package helpers

import android.util.Log.d
import android.util.Log.w
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.jetbrains.anko.doAsync


class databaseManager {

    lateinit var docId: String


    // Access a Cloud Firestore instance from your Activity
    private var db = FirebaseFirestore.getInstance()
    private val usersDb = db.collection("users")
    //private val favsList = db.document("/users/")


    private val TAG = "DB MANAGER"


    fun createDB() {


        usersDb.add(hashMapOf("Id" to "12345", "Name" to "Jack's Big PP"))
            .addOnSuccessListener { documentReference ->
                d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                w(TAG, "Error adding document", e)
            }


    }

    fun createUser(userID: String) {
        // Create a new user with a name
        val user = hashMapOf("name" to "name")


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

    fun addNewUser(nickname: String, email: String, userID: String) {
        lateinit var docId: String

        val user: MutableMap<String, Any> = HashMap()

        user["name"] = nickname
        user["id"] = userID
        user["email"] = email

        user["prefCat1"] = ""
        user["prefCat2"] = ""
        user["prefCat3"] = ""

        user["favs"] = ""
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
                d(
                    TAG,
                    "DocumentSnapshot added with ID: $documentReference"
                )
            }
            .addOnFailureListener { e -> w(TAG, "Error adding document", e) }

    }

    fun addToFavs(userID: String, mealIDToAddToFavs: String) {
        db.collection("users").document(userID).update(
            "favs",
            ",$mealIDToAddToFavs"
        )
    }


    //This function successfully gets user data from Firestore and logs it and the userID
    //Don't mess this up. Copy is below
    fun readData(userID: String) {
        val auth = FirebaseAuth.getInstance()
        doAsync {
            var finalInfo: MutableMap<String, String>
            var info = db.collection("users").document(auth.currentUser!!.uid)
            d(TAG, auth.currentUser!!.uid)
            info.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        d(TAG, "DocumentSnapshot data: ${document.data}")
                    } else {
                        d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    d(TAG, "get failed with ", exception)
                }

        }

    }


    fun getUser() {


    }
}