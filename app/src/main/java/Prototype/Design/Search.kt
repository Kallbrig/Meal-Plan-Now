package Prototype.Design

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.widget.ImageViewCompat


class Search : AppCompatActivity() {

    lateinit var card11Img: ImageView
    lateinit var card12Img: ImageView
    lateinit var card13Img: ImageView
    lateinit var card21Img: ImageView
    lateinit var card22Img: ImageView
    lateinit var card23Img: ImageView
    lateinit var card31Img: ImageView
    lateinit var card32Img: ImageView
    lateinit var card33Img: ImageView

    lateinit var card11Text: TextView
    lateinit var card12Text: TextView
    lateinit var card13Text: TextView
    lateinit var card21Text: TextView
    lateinit var card22Text: TextView
    lateinit var card23Text: TextView
    lateinit var card31Text: TextView
    lateinit var card32Text: TextView
    lateinit var card33Text: TextView

    lateinit var card11: CardView
    lateinit var card12: CardView
    lateinit var card13: CardView
    lateinit var card21: CardView
    lateinit var card22: CardView
    lateinit var card23: CardView
    lateinit var card31: CardView
    lateinit var card32: CardView
    lateinit var card33: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        var api = apiConnection()

        var suck = api.SearchByName("Brown")

        println(suck)

        card11Img = findViewById(R.id.Card11img)
        card12Img = findViewById(R.id.Card12img)
        card13Img = findViewById(R.id.Card13img)
        card21Img = findViewById(R.id.Card21img)
        card22Img = findViewById(R.id.Card22img)
        card23Img = findViewById(R.id.Card23img)
        card31Img = findViewById(R.id.Card31img)
        card32Img = findViewById(R.id.Card32img)
        card33Img = findViewById(R.id.Card33img)

        card11Text = findViewById<TextView>(R.id.Card11desc)
        card12Text = findViewById<TextView>(R.id.Card12desc)
        card13Text = findViewById<TextView>(R.id.Card13desc)
        card21Text = findViewById<TextView>(R.id.Card21desc)
        card22Text = findViewById<TextView>(R.id.Card22desc)
        card23Text = findViewById<TextView>(R.id.Card23desc)
        card31Text = findViewById<TextView>(R.id.Card31desc)
        card32Text = findViewById<TextView>(R.id.Card32desc)
        card33Text = findViewById<TextView>(R.id.Card33desc)

        card11 = findViewById<CardView>(R.id.Card11)
        card12 = findViewById<CardView>(R.id.Card12)
        card13 = findViewById<CardView>(R.id.Card13)
        card21 = findViewById<CardView>(R.id.Card21)
        card22 = findViewById<CardView>(R.id.Card22)
        card23 = findViewById<CardView>(R.id.Card23)
        card31 = findViewById<CardView>(R.id.Card31)
        card32 = findViewById<CardView>(R.id.Card32)
        card33 = findViewById<CardView>(R.id.Card33)

    }

    fun search() {

    }
}
