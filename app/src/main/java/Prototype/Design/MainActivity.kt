package Prototype.Design

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.*

private val TAG = "MAINACTIVITY"
private val api = apiConnection()
// Meal categories to display on the main page. just an array of categories that will be passed to parseCat()
private var mealCatsOnMain = ArrayList<String>(4)
//Row1 Meals. Goes inside of mainMealCats
private var cardRow1: ArrayList<CardView> = ArrayList<CardView>(7)
private var row1CatName: String = ""
//Row2 Meals. Goes inside of mainMealCats
private var cardRow2: ArrayList<CardView> = ArrayList<CardView>(7)
private var row2CatName: String = ""
//Row3 Meals. Goes inside of mainMealCats
private var cardRow3: ArrayList<CardView> = ArrayList<CardView>(7)
private var row3CatName: String = ""

private var cardRow1Img = ArrayList<ImageView>(6)
private var cardRow2Img = ArrayList<ImageView>(6)
private var cardRow3Img = ArrayList<ImageView>(6)
private var cardRow1Name = ArrayList<TextView>(6)
private var cardRow2Name = ArrayList<TextView>(6)
private var cardRow3Name = ArrayList<TextView>(6)
private var cardRow1Id = ArrayList<String>(6)
private var cardRow2Id = ArrayList<String>(6)
private var cardRow3Id = ArrayList<String>(6)
private lateinit var row1Name: TextView
private lateinit var row2Name: TextView
private lateinit var row3Name: TextView


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TitleBar Text is Logout Button
        var mainLogo = findViewById<TextView>(R.id.titleBar)
        mainLogo.setOnClickListener {
            var fauth = FirebaseAuth.getInstance()
            fauth.signOut()
            val intent = Intent(this, login_view::class.java)
            startActivity(intent)
        }

        var searchButMain = findViewById<ImageButton>(R.id.searchButMain)
        searchButMain.setOnClickListener() {
            createSearchIntent()
        }
        var favsButMain = findViewById<ImageButton>(R.id.favsButMain)
        favsButMain.setOnClickListener() {
            createFavsIntent()
        }
        var sevenDayButMain = findViewById<ImageButton>(R.id.sevenDayButMain)
        sevenDayButMain.setOnClickListener() {
            createSevenDayIntent()
        }


    }

    override fun onStart() {
        super.onStart()


        //Determines the meals that will appear on the MainActivity by setting mainMealCats and rowCats
        setMealCat()

        //shortens OnCreate.
        cardArraySetter()

        //Sets onclicklisteners for all cards
        setOnClick()

        val fullMealCatList = arrayListOf<String>(
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


        //These Categories don't have enough meals to fill out a row on Main. Fix and then Reinstate these Categories.
        fullMealCatList.removeAll(listOf("Vegan", "Starter", "Goat"))



        for (i in 0..3) {
            var catToFetch = fullMealCatList.random()
            fullMealCatList.remove(catToFetch)
            mealCatsOnMain.add(catToFetch)
        }


        val start = System.currentTimeMillis()
        val end = System.currentTimeMillis()
        Log.i(TAG, "Time to Determine Categories  ---  " + (end - start).toString())

        //After Api Calls, this functions sets the content of each cards with the fetched information
        setContent()

    }


    //Determines the meals that will appear on the MainActivity
    //No Arguments needed
    //No return
    private fun setMealCat() {

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
        for (i in 0..3) {

            var new = mealCat.random()

            while (new == "Goat" || new == "Vegan" || new == "Starter" || mealCatsOnMain.contains(
                    new
                )
            ) {
                new = mealCat.random()
            }

            mealCatsOnMain.add(new)

            if (row1CatName == "") {
                row1CatName = new
            } else if (row2CatName == "") {
                row2CatName = new
            } else if (row3CatName == "") {
                row3CatName = new
            }

        }
    }


    //After Api Calls, this functions sets the content of each cards with the fetched information
    //No Arguments needed
    //
    private fun setContent() {
        var mainCats = ArrayList<ArrayList<ArrayList<String>>>(3)

        for (i in 0 until 3) {
            mainCats.add(api.getCat(mealCatsOnMain[i]))
        }

        for (i in 0 until 6) {

            Row1Name.text = row1CatName
            Row2Name.text = row2CatName
            Row3Name.text = row3CatName


            var compareMealRow1 = mainCats[0].random()
            mainCats[0].remove(compareMealRow1)

            var compareMealRow2 = mainCats[1].random()
            mainCats[1].remove(compareMealRow2)

            var compareMealRow3 = mainCats[2].random()
            mainCats[2].remove(compareMealRow3)

            cardRow1Name[i].text = compareMealRow1[0]
            cardRow1Img[i].setImageDrawable(api.getImgDrawable(compareMealRow1[1]))
            cardRow1Id.add(compareMealRow1[2])

            cardRow2Name[i].text = compareMealRow2[0]
            cardRow2Img[i].setImageDrawable(api.getImgDrawable(compareMealRow2[1]))
            cardRow2Id.add(compareMealRow2[2])

            cardRow3Name[i].text = compareMealRow3[0]
            cardRow3Img[i].setImageDrawable(api.getImgDrawable(compareMealRow3[1]))
            cardRow3Id.add(compareMealRow3[2])


        }
    }


    //Sets onclicklisteners for all cards
    //Takes No Arguments
    //
    private fun setOnClick() {
        doAsync() {
            for (i in 0..5) {


                cardRow1[i].setOnClickListener() {
                    createDetailIntent(cardRow1Id[i], cardRow1Img[i].image!!.toBitmap(300, 400))
                    //Log.i(TAG, i.toString() + "while Loop setting onclick Listeners")
                }

                cardRow2[i].setOnClickListener() {
                    createDetailIntent(cardRow2Id[i], cardRow2Img[i].image!!.toBitmap(300, 400))
                    //Log.i(TAG, i.toString() + "while Loop setting onclick Listeners")
                }
                cardRow3[i].setOnClickListener() {
                    createDetailIntent(cardRow3Id[i], cardRow3Img[i].image!!.toBitmap(300, 400))
                    //Log.i(TAG, i.toString() + "while Loop setting onclick Listeners")
                }

            }
        }
    }


    //Adds all views to thier correct arrays
    //Takes No Arguments
    //
    private fun cardArraySetter() {


        doAsync {
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



            row1Name = findViewById<TextView>(R.id.Row1Name)
            row2Name = findViewById<TextView>(R.id.Row2Name)
            row3Name = findViewById<TextView>(R.id.Row3Name)


        }
    }


    //Creates and starts an intent that takes you to detailedView.
    //Requires Meal ID # and the Meal Image as a bitmap as arguments
    //
    private fun createDetailIntent(id: String, img: Bitmap) {
        Log.i(TAG, "Detailed Intent Created")
        val intent = Intent(this, DetailedView::class.java)
        intent.putExtra("img", img)
        intent.putExtra("id", id)


        // log message
        Log.i(TAG, "Put Extras - about to start DetailedView with meal ID#$id")

        startActivity(intent)

    }


    // Creates and starts an intent that takes you to favsView.
    // Takes No arguments.
    //
    private fun createFavsIntent() {
        val intent = Intent(this, favs_view::class.java)
        startActivity(intent)
    }


    //creates and starts an intent that takes you to Search
    //Takes No Arguments
    //
    private fun createSearchIntent() {
        val intent = Intent(this, Search::class.java)
        startActivity(intent)
    }

    private fun createSevenDayIntent() {
        val intent = Intent(this, sevenDay::class.java)
        startActivity(intent)
    }

}



















