package Prototype.Design

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView


class DetailedView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_view)


        var bgImg = findViewById<ImageView>(R.id.bgImg)

        var mealPreview = findViewById<ImageView>(R.id.mealPreview)

        var titleBar = findViewById<TextView>(R.id.titleBar)
        var mealName = findViewById<TextView>(R.id.mealName)

        var mealCat = findViewById<TextView>(R.id.mealCat)

        mealName.text = intent.getStringExtra("id")
        titleBar.text = intent.getStringExtra("id")
        bgImg.setImageBitmap(intent.getParcelableExtra("img") as Bitmap)
        mealCat.text = intent.getStringExtra("cat")

        Log.i("?", "Background Image Set!")
        mealPreview.setImageBitmap(intent.getParcelableExtra("img") as Bitmap)

        var backBut = findViewById<ImageButton>(R.id.backButton)

        backBut.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))

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


