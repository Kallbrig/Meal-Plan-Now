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
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.UrlQuerySanitizer
import android.os.Looper
import android.util.Log
import androidx.annotation.IntegerRes
import androidx.core.content.ContextCompat.*
import androidx.core.view.drawToBitmap
import kotlinx.android.synthetic.main.activity_detailed_view.*
import org.jetbrains.anko.*
import org.w3c.dom.Text
import java.util.concurrent.Future


class MainActivity : AppCompatActivity() {


    //Log Tag
    val TAG = "MAINACTIVITY"
    //Api Connection Object
    val api = apiConnection()
    // Meal categories to display on the main page. just an array of categories that will be passed to parseCat()
    var mainMealCats = ArrayList<String>(4)
    //Row1 Meals. Goes inside of mainMealCats
    var cardRow1: ArrayList<CardView> = ArrayList<CardView>(7)
    var row1Cat: String = ""
    //Row2 Meals. Goes inside of mainMealCats
    var cardRow2: ArrayList<CardView> = ArrayList<CardView>(7)
    var row2Cat: String = ""
    //Row3 Meals. Goes inside of mainMealCats
    var cardRow3: ArrayList<CardView> = ArrayList<CardView>(7)
    var row3Cat: String = ""
    var cardRow1Img = ArrayList<ImageView>(6)
    var cardRow2Img = ArrayList<ImageView>(6)
    var cardRow3Img = ArrayList<ImageView>(6)
    var cardRow1Name = ArrayList<TextView>(6)
    var cardRow2Name = ArrayList<TextView>(6)
    var cardRow3Name = ArrayList<TextView>(6)
    var cardRow1Id = ArrayList<String>(6)
    var cardRow2Id = ArrayList<String>(6)
    var cardRow3Id = ArrayList<String>(6)
    //    var favsButton = findViewById<Button>(R.id.favsButton)
//    var sevenDayButton = findViewById<Button>(R.id.sevenDayButton)
    lateinit var row1Name: TextView
    lateinit var row2Name: TextView
    lateinit var row3Name: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        row1Name = findViewById<TextView>(R.id.Row1Name)
        row2Name = findViewById<TextView>(R.id.Row2Name)
        row3Name = findViewById<TextView>(R.id.Row3Name)


        onCreateActivities()
        Log.i(TAG, "onCreateActivities has finished")


        val mealCat = arrayListOf<String>(
            "Chicken",
            "Beef",
            "Dessert",
            "Lamb",
            "Miscellaneous",
            "Pasta",
            "Pork",
            "Seafood",
            "Side",
            "Starter",
            "Vegan",
            "Vegetarian",
            "Breakfast",
            "Goat"
        )



        for (i in 0..2) {

            var new = mealCat.random()

            while (new == "Goat" || new == "Vegan") {
                new = mealCat.random()
            }

            if (!mainMealCats.contains(new)) {
                mainMealCats.add(new)
                if (row1Cat == "") {
                    row1Cat = new
                } else if (row2Cat == "") {
                    row2Cat = new
                } else if (row3Cat == "") {
                    row3Cat = new
                }
            }
        }


        var mainCats = ArrayList<ArrayList<ArrayList<String>>>(3)

        for (i in 0..2) {


            mainCats.add(api.getCat(mainMealCats[i]))

        }
        Log.i(TAG, mainCats.size.toString())




        for (i in 0 until 6) {
            Row1Name.text = row1Cat
            cardRow1Name[i].text = mainCats[0][i][0]
            cardRow1Id.add(mainCats[0][i][2])
            Row2Name.text = row2Cat
            cardRow2Name[i].text = mainCats[1][i][0]
            Row3Name.text = row3Cat
            cardRow3Name[i].text = mainCats[2][i][0]
        }


        // Row 1  setonclicklistener setter

        // NOT FUNCTIONING

        for (i in 0 until 5){
            cardRow1[i].setOnClickListener(){
                createDetailIntent(cardRow1Id[i],cardRow1Img[i].image!!.toBitmap(300,400))
            }
        }



        Log.i(TAG, "About to run testFun(args)")
        testFun()
        createDetailIntent("55555", cardRow1Img[0].image!!.toBitmap(200,300))

    }

    fun onCreateActivities() {


        cardRow1.add(findViewById<CardView>(R.id.Card11))
        cardRow1.add(findViewById<CardView>(R.id.Card12))
        cardRow1.add(findViewById<CardView>(R.id.Card13))
        cardRow1.add(findViewById<CardView>(R.id.Card14))
        cardRow1.add(findViewById<CardView>(R.id.Card15))
        cardRow1.add(findViewById<CardView>(R.id.Card16))



        cardRow2.add(findViewById<CardView>(R.id.Card21))
        cardRow2.add(findViewById<CardView>(R.id.Card22))
        cardRow2.add(findViewById<CardView>(R.id.Card23))
        cardRow2.add(findViewById<CardView>(R.id.Card24))
        cardRow2.add(findViewById<CardView>(R.id.Card25))
        cardRow2.add(findViewById<CardView>(R.id.Card26))


        cardRow3.add(findViewById<CardView>(R.id.Card31))
        cardRow3.add(findViewById<CardView>(R.id.Card32))
        cardRow3.add(findViewById<CardView>(R.id.Card33))
        cardRow3.add(findViewById<CardView>(R.id.Card34))
        cardRow3.add(findViewById<CardView>(R.id.Card35))
        cardRow3.add(findViewById<CardView>(R.id.Card36))



        cardRow1Img.add(findViewById<ImageView>(R.id.Card11img))
        cardRow1Img.add(findViewById<ImageView>(R.id.Card12img))
        cardRow1Img.add(findViewById<ImageView>(R.id.Card13img))
        cardRow1Img.add(findViewById<ImageView>(R.id.Card14img))
        cardRow1Img.add(findViewById<ImageView>(R.id.Card15img))
        cardRow1Img.add(findViewById<ImageView>(R.id.Card16img))



        cardRow2Img.add(findViewById<ImageView>(R.id.Card21img))
        cardRow2Img.add(findViewById<ImageView>(R.id.Card22img))
        cardRow2Img.add(findViewById<ImageView>(R.id.Card23img))
        cardRow2Img.add(findViewById<ImageView>(R.id.Card24img))
        cardRow2Img.add(findViewById<ImageView>(R.id.Card25img))
        cardRow2Img.add(findViewById<ImageView>(R.id.Card26img))



        cardRow3Img.add(findViewById<ImageView>(R.id.Card31img))
        cardRow3Img.add(findViewById<ImageView>(R.id.Card32img))
        cardRow3Img.add(findViewById<ImageView>(R.id.Card33img))
        cardRow3Img.add(findViewById<ImageView>(R.id.Card34img))
        cardRow3Img.add(findViewById<ImageView>(R.id.Card35img))
        cardRow3Img.add(findViewById<ImageView>(R.id.Card36img))

        cardRow1Name.add(findViewById<TextView>(R.id.Card11desc))
        cardRow1Name.add(findViewById<TextView>(R.id.Card12desc))
        cardRow1Name.add(findViewById<TextView>(R.id.Card13desc))
        cardRow1Name.add(findViewById<TextView>(R.id.Card14desc))
        cardRow1Name.add(findViewById<TextView>(R.id.Card15desc))
        cardRow1Name.add(findViewById<TextView>(R.id.Card16desc))



        cardRow2Name.add(findViewById<TextView>(R.id.Card21desc))
        cardRow2Name.add(findViewById<TextView>(R.id.Card22desc))
        cardRow2Name.add(findViewById<TextView>(R.id.Card23desc))
        cardRow2Name.add(findViewById<TextView>(R.id.Card24desc))
        cardRow2Name.add(findViewById<TextView>(R.id.Card25desc))
        cardRow2Name.add(findViewById<TextView>(R.id.Card26desc))



        cardRow3Name.add(findViewById<TextView>(R.id.Card31desc))
        cardRow3Name.add(findViewById<TextView>(R.id.Card32desc))
        cardRow3Name.add(findViewById<TextView>(R.id.Card33desc))
        cardRow3Name.add(findViewById<TextView>(R.id.Card34desc))
        cardRow3Name.add(findViewById<TextView>(R.id.Card35desc))
        cardRow3Name.add(findViewById<TextView>(R.id.Card36desc))


    }


    // Creates and starts an intent that takes you to detailedView. Requires the MealName and mealImg as arguments. they are used on the detailedView Activity.
    fun createDetailIntent(id: String, img: Bitmap) {
        println("Intent Created")
        val intent = Intent(this, DetailedView::class.java)
        intent.putExtra("img", img)
        intent.putExtra("id", id)
        //intent.putExtra("prevIntent", MainActivity::class.java)



        // log message
        Log.i(TAG, "Put Extras - about to start DetailedView with meal ID#" + id)

        startActivity(intent)

    }


    // Creates and starts an intent that takes you to favsView. Takes No arguments.
    fun createFavsIntent() {
        val intent = Intent(this, favs_view::class.java)
        intent.putExtra("prevIntent", MainActivity::class.java)
        startActivity(intent)
    }

    fun testFun() {

    }
}



















