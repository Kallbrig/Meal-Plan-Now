package Prototype.Design

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.util.Log.i
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult


class Search : AppCompatActivity() {

    private var TAG = "SEARCH"
    private lateinit var fadein: Animation

    private lateinit var cardImgs: ArrayList<ImageView>
    private lateinit var card11Img: ImageView
    private lateinit var card12Img: ImageView
    private lateinit var card13Img: ImageView
    private lateinit var card21Img: ImageView
    private lateinit var card22Img: ImageView
    private lateinit var card23Img: ImageView
    private lateinit var card31Img: ImageView
    private lateinit var card32Img: ImageView
    private lateinit var card33Img: ImageView

    private lateinit var cardTexts: ArrayList<TextView>
    private lateinit var card11Text: TextView
    private lateinit var card12Text: TextView
    private lateinit var card13Text: TextView
    private lateinit var card21Text: TextView
    private lateinit var card22Text: TextView
    private lateinit var card23Text: TextView
    private lateinit var card31Text: TextView
    private lateinit var card32Text: TextView
    private lateinit var card33Text: TextView

    private lateinit var cards: ArrayList<CardView>
    private lateinit var card11: CardView
    private lateinit var card12: CardView
    private lateinit var card13: CardView
    private lateinit var card21: CardView
    private lateinit var card22: CardView
    private lateinit var card23: CardView
    private lateinit var card31: CardView
    private lateinit var card32: CardView
    private lateinit var card33: CardView

    private lateinit var searchButSearch: ImageButton
    private lateinit var favsButSearch: ImageButton
    private lateinit var sevenDayButSearch: ImageButton
    private lateinit var backBut: ImageButton

    private lateinit var searchBut: ImageButton
    private lateinit var searchTextBox: EditText
    private var searchResponse = ArrayList<ArrayList<String>>(9)
    private lateinit var api: apiConnection


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        api = apiConnection()
        setViews()

        fadein = AlphaAnimation(0f, 1f)
        fadein.interpolator = DecelerateInterpolator()
        fadein.duration = 2000


    }


    //Sets View Variables and puts them into the ArrayLists. used to shorten and simplify onCreate()
    //Takes no Arguments
    //Returns nothing
    private fun setViews() {

        searchTextBox = findViewById<EditText>(R.id.searchTextBox)
        searchBut = findViewById<ImageButton>(R.id.SearchButton)

        card11Img = findViewById(R.id.Card11img)
        card12Img = findViewById(R.id.Card12img)
        card13Img = findViewById(R.id.Card13img)
        card21Img = findViewById(R.id.Card21img)
        card22Img = findViewById(R.id.Card22img)
        card23Img = findViewById(R.id.Card23img)
        card31Img = findViewById(R.id.Card31img)
        card32Img = findViewById(R.id.Card32img)
        card33Img = findViewById(R.id.Card33img)

        cardImgs = ArrayList<ImageView>(9)
        cardImgs.addAll(
            arrayListOf(
                card11Img,
                card12Img,
                card13Img,
                card21Img,
                card22Img,
                card23Img,
                card31Img,
                card32Img,
                card33Img
            )
        )

        card11Text = findViewById<TextView>(R.id.Card11desc)
        card12Text = findViewById<TextView>(R.id.Card12desc)
        card13Text = findViewById<TextView>(R.id.Card13desc)
        card21Text = findViewById<TextView>(R.id.Card21desc)
        card22Text = findViewById<TextView>(R.id.Card22desc)
        card23Text = findViewById<TextView>(R.id.Card23desc)
        card31Text = findViewById<TextView>(R.id.Card31desc)
        card32Text = findViewById<TextView>(R.id.Card32desc)
        card33Text = findViewById<TextView>(R.id.Card33desc)

        cardTexts = ArrayList(9)
        cardTexts.addAll(
            arrayListOf(
                card11Text,
                card12Text,
                card13Text,
                card21Text,
                card22Text,
                card23Text,
                card31Text,
                card32Text,
                card33Text
            )
        )

        card11 = findViewById<CardView>(R.id.Card11)
        card12 = findViewById<CardView>(R.id.Card12)
        card13 = findViewById<CardView>(R.id.Card13)
        card21 = findViewById<CardView>(R.id.Card21)
        card22 = findViewById<CardView>(R.id.Card22)
        card23 = findViewById<CardView>(R.id.Card23)
        card31 = findViewById<CardView>(R.id.Card31)
        card32 = findViewById<CardView>(R.id.Card32)
        card33 = findViewById<CardView>(R.id.Card33)

        cards = ArrayList(9)
        cards.addAll(
            arrayListOf(
                card11,
                card12,
                card13,
                card21,
                card22,
                card23,
                card31,
                card32,
                card33
            )
        )

        searchButSearch = findViewById(R.id.searchButSearch)
        searchButSearch.alpha = 0f


        favsButSearch = findViewById(R.id.favsButSearch)
        favsButSearch.setOnClickListener {
            createFavsIntent()
        }

        sevenDayButSearch = findViewById(R.id.sevenDayButSearch)
        sevenDayButSearch.setOnClickListener {
            createSevenDayIntent()
        }

        searchBut.setOnClickListener {
            search()
        }

        backBut = findViewById(R.id.backButton)
        backBut.setOnClickListener {
            finish()
        }


    }


    //Function that searches the information. this is the only function that needs to be called from Search
    //Takes a View as an Argument. Primarily the Search button.
    //Does not return anything
    private fun search() {
        i(TAG, "Search has Begun...")
        var searchKeyword = searchTextBox.text.toString()


        if (searchKeyword == "") {
            makeText(this, "Search Must Contain At Least 1 Character.", LENGTH_LONG).show()
            i(TAG, "Search box is Empty")
        } else {
            searchResponse = api.searchByName(searchKeyword)
            if (searchResponse.isEmpty()) {
                makeText(this, "No Results! Please Try Again!", LENGTH_LONG).show()
                i(TAG, "Empty Search - Keyword: $searchKeyword")
            } else {
                i(TAG, "Setting Search View Content")
                setSearchViewContent()
            }
        }
    }


    //This function takes fetched information and fills it into the activities in Search
    //Takes no arguments. Only uses Global Variables
    //Returns nothing
    private fun setSearchViewContent() {

        for (i in 0 until 9) {
            if (cards[i].alpha != 0f) {
                //cards[i].alpha = 0f
                cards[i].setOnClickListener(null)
            }
        }

        val upperBound: Int = doAsyncResult {

            if ((searchResponse.size - 1) >= 9) {
                return@doAsyncResult 8
            } else {
                return@doAsyncResult searchResponse.size - 1
            }
        }.get()

        for (i in 0..upperBound) {

            cardTexts[i].text = searchResponse[i][0]
            cardImgs[i].setImageDrawable(api.getImgDrawable(searchResponse[i][1]))
            cards[i].alpha = 1f

            cards[i].setOnClickListener {
                createDetailIntent(
                    searchResponse[i][2],
                    cardImgs[i].drawable.toBitmap(300, 400)
                )

            }

        }


    }

    //Opens Detailed View
    //Takes meal Id # as a string and meal Image as a bitmap as arguments
    //Starts detailedIntent but doesn't return anything
    private fun createDetailIntent(id: String, img: Bitmap) {
        i(TAG, "Detailed Intent Created")
        val intent = Intent(this, DetailedView::class.java)
        intent.putExtra("img", img)
        intent.putExtra("id", id)


        // log message
        i(TAG, "Put Extras - about to start DetailedView with meal ID#$id")

        startActivity(intent)

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


}
