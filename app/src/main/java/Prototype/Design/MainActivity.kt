package Prototype.Design

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.util.concurrent.Executors
import Prototype.Design.apiConnection
import android.content.AsyncQueryHandler
import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import android.net.Uri
import kotlinx.android.synthetic.main.activity_main.*
import com.squareup.picasso.Picasso
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.UrlQuerySanitizer
import android.os.Looper
import android.util.Log
import org.jetbrains.anko.*
import java.util.concurrent.Future


class MainActivity : AppCompatActivity() {



    val TAG = "MAINACTIVITY"
    val api = apiConnection()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //This is where I'm trying to store all meal info at the moment.
        //This could be inefficient, but that's something i'm trying to figure out as i go.
        //lateinit var MealInfoList:ArrayList<ArrayList<String>>


        var Card11 = findViewById<CardView>(R.id.Card11)
        var Card12 = findViewById<CardView>(R.id.Card12)
        var Card13 = findViewById<CardView>(R.id.Card13)
        var Card14 = findViewById<CardView>(R.id.Card14)
        var Card15 = findViewById<CardView>(R.id.Card15)
        var Card16 = findViewById<CardView>(R.id.Card16)

        var Card21 = findViewById<CardView>(R.id.Card21)
        var Card22 = findViewById<CardView>(R.id.Card22)
        var Card23 = findViewById<CardView>(R.id.Card23)
        var Card24 = findViewById<CardView>(R.id.Card24)
        var Card25 = findViewById<CardView>(R.id.Card25)
        var Card26 = findViewById<CardView>(R.id.Card26)

        var Card31 = findViewById<CardView>(R.id.Card31)
        var Card32 = findViewById<CardView>(R.id.Card32)
        var Card33 = findViewById<CardView>(R.id.Card33)
        var Card34 = findViewById<CardView>(R.id.Card34)
        var Card35 = findViewById<CardView>(R.id.Card35)
        var Card36 = findViewById<CardView>(R.id.Card36)

        var favsButton = findViewById<ImageButton>(R.id.favsButton)
        var sevenDayButton = findViewById<Button>(R.id.sevenDayButton)                //This is a button used for testing only that will be removed later - it currently leads you to the otherwise inaccessable seven day view

        sevenDayButton.setOnClickListener {
            var i = Intent(this, sevenDay::class.java)
            startActivity(i)
        }
        favsButton.setOnClickListener {
            createFavsIntent()
        }
        // Row 1 OnClickListeners
        Card11.setOnClickListener {
            var Img = findViewById<ImageView>(R.id.Card11img).image!!.toBitmap(300, 400)
            var Carddesc = findViewById<TextView>(R.id.Card11desc).text as String
            createDetailIntent(Carddesc, Img, "Chicken")

        }
        Card12.setOnClickListener {
            var Img = findViewById<ImageView>(R.id.Card12img).drawable.toBitmap(300, 400)
            var Carddesc = findViewById<TextView>(R.id.Card12desc).text as String
            createDetailIntent(Carddesc, Img, "Chicken")
        }
        Card13.setOnClickListener {
            var Img = findViewById<ImageView>(R.id.Card13img).drawable.toBitmap(300, 400)
            var Carddesc = findViewById<TextView>(R.id.Card13desc).text as String
            createDetailIntent(Carddesc, Img, "Chicken")
        }
        Card14.setOnClickListener {
            var Img = findViewById<ImageView>(R.id.Card14img).drawable.toBitmap(300, 400)
            var Carddesc = findViewById<TextView>(R.id.Card14desc).text as String
            createDetailIntent(Carddesc, Img, "Chicken")
        }
        Card15.setOnClickListener {
            var Img = findViewById<ImageView>(R.id.Card15img).drawable.toBitmap(300, 400)
            var Carddesc = findViewById<TextView>(R.id.Card15desc).text as String
            createDetailIntent(Carddesc, Img, "Chicken")
        }
        Card16.setOnClickListener {
            var Img = findViewById<ImageView>(R.id.Card16img).drawable.toBitmap(300, 400)
            var Carddesc = findViewById<TextView>(R.id.Card16desc).text as String
            createDetailIntent(Carddesc, Img, "Chicken")
        }
        // Row 2 OnclickListeners
        Card21.setOnClickListener {
            var Img = findViewById<ImageView>(R.id.Card21img).drawable.toBitmap(300, 400)
            var Carddesc = findViewById<TextView>(R.id.Card21desc).text as String
            createDetailIntent(Carddesc, Img, "Seafood")

        }
        Card22.setOnClickListener {
            var Img = findViewById<ImageView>(R.id.Card22img).drawable.toBitmap(300, 400)
            var Carddesc = findViewById<TextView>(R.id.Card22desc).text as String
            createDetailIntent(Carddesc, Img, "Seafood")
        }
        Card23.setOnClickListener {
            var Img = findViewById<ImageView>(R.id.Card23img).drawable.toBitmap(300, 400)
            var Carddesc = findViewById<TextView>(R.id.Card23desc).text as String
            createDetailIntent(Carddesc, Img, "Seafood")
        }
        Card24.setOnClickListener {
            var Img = findViewById<ImageView>(R.id.Card24img).drawable.toBitmap(300, 400)
            var Carddesc = findViewById<TextView>(R.id.Card24desc).text as String
            createDetailIntent(Carddesc, Img, "Seafood")
        }
        Card25.setOnClickListener {
            var Img = findViewById<ImageView>(R.id.Card25img).drawable.toBitmap(300, 400)
            var Carddesc = findViewById<TextView>(R.id.Card25desc).text as String
            createDetailIntent(Carddesc, Img, "Seafood")
        }
        Card26.setOnClickListener {
            var Img = findViewById<ImageView>(R.id.Card26img).drawable.toBitmap(300, 400)
            var Carddesc = findViewById<TextView>(R.id.Card26desc).text as String
            createDetailIntent(Carddesc, Img, "Seafood")
        }
        // Row 3 OnclickListeners
        Card31.setOnClickListener {
            var Img = findViewById<ImageView>(R.id.Card31img).drawable.toBitmap(300, 400)
            var Carddesc = findViewById<TextView>(R.id.Card31desc).text as String
            createDetailIntent(Carddesc, Img, "Dessert")

        }
        Card32.setOnClickListener {
            var Img = findViewById<ImageView>(R.id.Card32img).drawable.toBitmap(300, 400)
            var Carddesc = findViewById<TextView>(R.id.Card32desc).text as String
            createDetailIntent(Carddesc, Img, "Dessert")
        }
        Card33.setOnClickListener {
            var Img = findViewById<ImageView>(R.id.Card33img).drawable.toBitmap(300, 400)
            var Carddesc = findViewById<TextView>(R.id.Card33desc).text as String
            createDetailIntent(Carddesc, Img, "Dessert")
        }
        Card34.setOnClickListener {
            var Img = findViewById<ImageView>(R.id.Card34img).drawable.toBitmap(300, 400)
            var Carddesc = findViewById<TextView>(R.id.Card34desc).text as String
            createDetailIntent(Carddesc, Img, "Dessert")
        }
        Card35.setOnClickListener {
            var Img = findViewById<ImageView>(R.id.Card35img).drawable.toBitmap(300, 400)
            var Carddesc = findViewById<TextView>(R.id.Card35desc).text as String
            createDetailIntent(Carddesc, Img, "Dessert")
        }
        Card36.setOnClickListener {
            var Img = findViewById<ImageView>(R.id.Card36img).drawable.toBitmap(300, 400)
            var Carddesc = findViewById<TextView>(R.id.Card36desc).text as String
            createDetailIntent(Carddesc, Img, "Dessert")
        }

        //Fetches a category from the API. This will be used in the Search/browse functions.


        Log.i(TAG, "About to run testFun(args)")
        testFun("52772")


    }


    // Creates and starts an intent that takes you to detailedView. Requires the MealName and mealImg as arguments. they are used on the detailedView Activity.
    fun createDetailIntent(id: String, img: Bitmap, cat: String) {
        println("Intent Created")
        val intent = Intent(this, DetailedView::class.java)
        intent.putExtra("img", img)
        intent.putExtra("id", id)
        //intent.putExtra("prevIntent", MainActivity::class.java)
        intent.putExtra("cat", cat)


        // log message
        println("Put Extras!")

        startActivity(intent)

    }


    // Creates and starts an intent that takes you to favsView. Takes No arguments.
    fun createFavsIntent() {
        val intent = Intent(this, favs_view::class.java)
        intent.putExtra("prevIntent", MainActivity::class.java)
        startActivity(intent)
    }

    fun testFun(args:String){
        var mealInfo = api.getCat("Chicken")
        println(mealInfo)


    }

}



















