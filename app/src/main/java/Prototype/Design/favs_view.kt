package Prototype.Design

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.util.Log.i
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import com.google.firebase.database.FirebaseDatabase
import helpers.authManager
import helpers.databaseManager


lateinit var searchButFavs: ImageButton
lateinit var favsButFavs: ImageButton
lateinit var sevenDayButFavs: ImageButton
private const val TAG: String = "FAVORITES VIEW"
private var data = databaseManager()
private val auth = authManager()


class favs_view : AppCompatActivity() {

    //Holds all cardviews on favs View page
    var favsCards = ArrayList<CardView>(6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favs_view)

        //Back Button
        setBackBut()

        //var user = auth.currentUser

        //var theInformation = data.readData(user!!.uid).result as Map<String, Object>

        //d(TAG, theInformation.keys.toString())


        //Adding all Cards from Favs_View to the arraylist that will store them. This makes them iteratable
        favsCards.addAll(
            arrayListOf(
                findViewById(R.id.Card1),
                findViewById(R.id.Card2),
                findViewById(R.id.Card3),
                findViewById(R.id.Card4),
                findViewById(R.id.Card5),
                findViewById(R.id.Card6)
            )
        )




        for (i in 0 until 6)

            favsCards[i].setOnClickListener {
                var Img = findViewById<ImageView>(R.id.img1Favs).drawable.toBitmap(300, 400)
                var Carddesc = findViewById<TextView>(R.id.mealName1).text as String
                createDetailIntent(Carddesc, Img)
            }

        searchButFavs = findViewById(R.id.searchButFavs)
        searchButFavs.setOnClickListener {
            createSearchIntent()
        }

        sevenDayButFavs = findViewById(R.id.sevenDayButFavs)
        sevenDayButFavs.setOnClickListener {
            createSevenDayIntent()
        }


    }


    //Creates and starts an intent that takes you to detailedView.
    //Requires Meal ID # and the Meal Image as a bitmap as arguments
    //
    fun createDetailIntent(id: String, img: Bitmap) {
        println("Intent Created")
        val intent = Intent(this, DetailedView::class.java)
        intent.putExtra("img", img)
        intent.putExtra("id", id)
        // log message
        Log.i(TAG, "Put Extras - about to start DetailedView with meal ID#" + id)
        startActivity(intent)
    }

    //
    fun setBackBut() {
        var backBut = findViewById<ImageButton>(R.id.backButton)
        backBut.setOnClickListener {
            finish()
        }
    }

    private fun createSearchIntent() {
        val intent = Intent(this, Search::class.java)
        startActivity(intent)
    }

    private fun createFavsIntent() {
        val intent = Intent(this, favs_view::class.java)
        startActivity(intent)
    }

    private fun createSevenDayIntent() {
        val intent = Intent(this, sevenDay::class.java)
        startActivity(intent)
    }


    private fun addToFavs(meal: String) {
        i(TAG, meal)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")
        i(TAG, myRef.toString())

    }

}





