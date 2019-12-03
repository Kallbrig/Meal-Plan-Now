package Prototype.Design

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.drawToBitmap
import androidx.core.widget.ImageViewCompat
import org.jetbrains.anko.editText


class Search : AppCompatActivity() {

    var TAG = "SEARCH"

    lateinit var cardImgs: ArrayList<ImageView>
    lateinit var card11Img: ImageView
    lateinit var card12Img: ImageView
    lateinit var card13Img: ImageView
    lateinit var card21Img: ImageView
    lateinit var card22Img: ImageView
    lateinit var card23Img: ImageView
    lateinit var card31Img: ImageView
    lateinit var card32Img: ImageView
    lateinit var card33Img: ImageView

    lateinit var cardTexts: ArrayList<TextView>
    lateinit var card11Text: TextView
    lateinit var card12Text: TextView
    lateinit var card13Text: TextView
    lateinit var card21Text: TextView
    lateinit var card22Text: TextView
    lateinit var card23Text: TextView
    lateinit var card31Text: TextView
    lateinit var card32Text: TextView
    lateinit var card33Text: TextView

    lateinit var cards: ArrayList<CardView>
    lateinit var card11: CardView
    lateinit var card12: CardView
    lateinit var card13: CardView
    lateinit var card21: CardView
    lateinit var card22: CardView
    lateinit var card23: CardView
    lateinit var card31: CardView
    lateinit var card32: CardView
    lateinit var card33: CardView

    lateinit var searchBut: ImageButton
    lateinit var searchTextBox: EditText
    var searchResponse = ArrayList<ArrayList<String>>(9)
    lateinit var api: apiConnection


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        api = apiConnection()
        setViews()


    }

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


    }

    fun search(v: View) {
        Log.i(TAG, "Search has Begun...")
        var searchKeyword = searchTextBox.text.toString()

        if (searchKeyword.isNullOrEmpty()) {
            Toast.makeText(this, "Invalid! Please Try Again!", Toast.LENGTH_LONG).show()
        } else {
            searchResponse = api.searchByName(searchKeyword)

            if (searchResponse.isNullOrEmpty()) {
                Toast.makeText(this, "There is a problem, please try again.", Toast.LENGTH_LONG)
                    .show()
            }

            setSearchViewContent()
        }
    }

    private fun setSearchViewContent() {

        for (i in 0 until 9) {
            cards[i].alpha = 0f
            cards[i].setOnClickListener(null)
        }

        for (i in 0..searchResponse.size - 1) {
            cardTexts[i].text = searchResponse[i][0]
            cardImgs[i].setImageDrawable(api.getImgDrawable(searchResponse[i][1]))
            cards[i].alpha = 1f
            cards[i].setOnClickListener {
                createDetailIntent(searchResponse[i][2], cardImgs[i].drawable.toBitmap(300, 400))
            }
            println("I've got a huge dick and index 2 is: " + searchResponse[i][2])

        }


/*
        for (i in 0 until searchResponse.size) {

            cardTexts[i].text = searchResponse[i][0]
            cardImgs[i].setImageDrawable(api.getImgDrawable(searchResponse[i][1]))
            cards[i].alpha = 1f

            Log.i(TAG,"Meal ID#: " + api.getMealByName(searchResponse[i][0])[2])
*//*            cards[i].setOnClickListener {
                createDetailIntent(api.getMealById("52772")[2] , cardImgs[i].drawable.toBitmap(300, 400))
            }*//*
        }*/
    }

    private fun createDetailIntent(id: String, img: Bitmap) {
        Log.i(TAG, "Detailed Intent Created")
        val intent = Intent(this, DetailedView::class.java)
        intent.putExtra("img", img)
        intent.putExtra("id", id)


        // log message
        Log.i(TAG, "Put Extras - about to start DetailedView with meal ID#" + id)

        startActivity(intent)

    }


}
