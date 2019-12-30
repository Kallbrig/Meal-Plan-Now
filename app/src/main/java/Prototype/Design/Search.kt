package Prototype.Design

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log.d
import android.util.Log.i
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import org.jetbrains.anko.doAsyncResult


class Search : AppCompatActivity() {

    private var TAG = "SEARCH"
    private lateinit var searchTextBoxInSearchBar: EditText
    private lateinit var cards: ArrayList<CardView>
    private lateinit var cardTexts: ArrayList<TextView>
    private lateinit var cardImgs: ArrayList<ImageView>
    private var listOfSearchResponses = ArrayList<ArrayList<String>>(9)
    private lateinit var api: apiConnection


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setViews()

    }

    override fun onStart() {
        super.onStart()
        //setViews()

        api = apiConnection()

    }


    //Sets View Variables and puts them into the ArrayLists. used to shorten and simplify onCreate()
    //Takes no Arguments
    //Returns nothing
    private fun setViews() {


        var searchButInSearchBar: ImageButton = findViewById<ImageButton>(R.id.SearchButton)

        cardImgs = ArrayList(9)
        cardImgs.addAll(
            arrayListOf(
                findViewById(R.id.Card11img),
                findViewById(R.id.Card12img),
                findViewById(R.id.Card13img),
                findViewById(R.id.Card21img),
                findViewById(R.id.Card22img),
                findViewById(R.id.Card23img),
                findViewById(R.id.Card31img),
                findViewById(R.id.Card32img),
                findViewById(R.id.Card33img)
            )
        )

        cardTexts = ArrayList(9)
        cardTexts.addAll(
            arrayListOf(
                findViewById(R.id.Card11desc),
                findViewById(R.id.Card12desc),
                findViewById(R.id.Card13desc),
                findViewById(R.id.Card21desc),
                findViewById(R.id.Card22desc),
                findViewById(R.id.Card23desc),
                findViewById(R.id.Card31desc),
                findViewById(R.id.Card32desc),
                findViewById(R.id.Card33desc)
            )
        )

        cards = ArrayList(9)
        cards.addAll(
            arrayListOf(
                findViewById<CardView>(R.id.Card11),
                findViewById(R.id.Card12),
                findViewById(R.id.Card13),
                findViewById(R.id.Card21),
                findViewById(R.id.Card22),
                findViewById(R.id.Card23),
                findViewById(R.id.Card31),
                findViewById(R.id.Card32),
                findViewById(R.id.Card33)
            )
        )

        var searchButInSearchViewHeader: ImageButton = findViewById(R.id.searchButSearch)
        searchButInSearchViewHeader.alpha = 0f


        var favsButInSearchViewHeader: ImageButton = findViewById(R.id.favsButSearch)
        favsButInSearchViewHeader.setOnClickListener {
            createFavsIntent()
        }

        var sevenDayButInSearchViewHeader: ImageButton = findViewById(R.id.sevenDayButSearch)
        sevenDayButInSearchViewHeader.setOnClickListener {
            createSevenDayIntent()
        }

        searchButInSearchBar.setOnClickListener {
            search()
        }

        var backButInSearchViewHeader: ImageButton = findViewById(R.id.backButton)
        backButInSearchViewHeader.setOnClickListener {
            finish()
        }


    }


    //Function that searches the information. this is the only function that needs to be called from Search
    //Takes a View as an Argument. Primarily the Search button.
    //Does not return anything
    private fun search() {
        searchTextBoxInSearchBar = findViewById<EditText>(R.id.searchTextBox)
        i(TAG, "Search has Begun...")
        var searchKeyword = searchTextBoxInSearchBar.text.toString()

        if (searchKeyword == "") {
            makeText(this, "Search Must Contain At Least 1 Character.", LENGTH_LONG).show()
            i(TAG, "Search box is Empty")
        } else {
            listOfSearchResponses = api.searchByName(searchKeyword)
            if (listOfSearchResponses.isEmpty()) {
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
                cards[i].alpha = 0f
                cards[i].setOnClickListener(null)
            }
        }

        val upperBound: Int = doAsyncResult {

            if ((listOfSearchResponses.size - 1) >= 9) {
                return@doAsyncResult 8
            } else {
                return@doAsyncResult listOfSearchResponses.size - 1
            }
        }.get()

        for (i in 0..upperBound) {

            cardTexts[i].text = listOfSearchResponses[i][0]
            cardImgs[i].setImageDrawable(api.getImgDrawable(listOfSearchResponses[i][1]))
            cards[i].alpha = 1f

            cards[i].setOnClickListener {
                createDetailIntent(
                    listOfSearchResponses[i][2],
                    cardImgs[i].drawable.toBitmap(300, 400)
                )

            }

        }


    }

    //Opens Detailed View
    //Takes meal Id # as a string and meal Image as a bitmap as arguments
    //Starts detailedIntent but doesn't return anything
    private fun createDetailIntent(id: String, img: Bitmap) {
        d(TAG, "Detailed Intent Created")
        val intent = Intent(this, DetailedView::class.java)
        intent.putExtra("img", img)
        intent.putExtra("id", id)


        // log message
        d(TAG, "Put Extras - about to start DetailedView with meal ID#$id")

        startActivity(intent)

    }

    private fun createSearchIntent() {
        startActivity(Intent(this, Search::class.java))
    }

    private fun createFavsIntent() {
        startActivity(Intent(this, favs_view::class.java))
    }

    private fun createSevenDayIntent() {
        startActivity(Intent(this, sevenDay::class.java))
    }


}
