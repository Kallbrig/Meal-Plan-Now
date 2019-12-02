package Prototype.Design

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_detailed_view.view.*
import org.jetbrains.anko.imageBitmap
import java.net.URL


class DetailedView : AppCompatActivity() {

    val TAG = "DETAILED VIEW"
    var api = apiConnection()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_view)

        Log.i(TAG, "Detailed View Started")


        var bgImg = findViewById<ImageView>(R.id.bgImg)
        var mealPreview = findViewById<ImageView>(R.id.mealPreview)
        var titleBar = findViewById<TextView>(R.id.titleBar)
        var mealName = findViewById<TextView>(R.id.mealName)
        var mealCat = findViewById<TextView>(R.id.mealCat)
        var backBut = findViewById<ImageButton>(R.id.backButton)
        var mealInstructions = findViewById<TextView>(R.id.mealInstructions)
        var shareBut = findViewById<LinearLayout>(R.id.addToFavs)
        var downloadBut = findViewById<LinearLayout>(R.id.addToSevenDay)

        shareBut.setOnClickListener {

            val sendIntent: Intent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this Meal!")
            sendIntent.type = "text/plain"

            Log.i(TAG, "Share Intent Created")

            val shareIntent = Intent.createChooser(sendIntent, null)

            startActivity(shareIntent)

        }


        var meal = api.getMealById(intent.getStringExtra("id")!!)

        mealName.text = meal[0]
        Log.i(TAG, "Background Image URL = " + meal[1])
        //mealPreview.imageBitmap = (api.getImgBitmap(meal[1],this))
        var mealImg = api.getImgDrawable(meal[1])
        bgImg.setImageDrawable(mealImg)
        mealPreview.setImageDrawable(mealImg)
        titleBar.text = meal[0]
        mealInstructions.text = meal[4]
        mealCat.text = meal[3]

        backBut.setOnClickListener {
            finish()
        }

    }


    /* fun backButtonPress(backbut:ImageButton){


             var prevA = intent.getStringExtra("prevIntent") as String
             val i:Intent
             if (prevA == "MainActivity::class.java"){
                 i = Intent(this, MainActivity::class.java)
             } else if(prevA == "DetailedView::class.java") {
                 i = Intent(this, DetailedView::class.java)
             } else if(prevA == "favs_view::class.java"){
                 i = Intent(this, favs_view::class.java)
             } else if(prevA == "sevenDay::class.java"){
                 i = Intent(this, sevenDay::class.java)
             }
             else{
                 i = Intent(this, MainActivity::class.java)
             }
             startActivity(i)

     }

     */


}


