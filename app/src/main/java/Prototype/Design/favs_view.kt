package Prototype.Design

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap

class favs_view : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favs_view)


        var Card1 = findViewById<CardView>(R.id.Card1)
        var Card2 = findViewById<CardView>(R.id.Card2)
        var Card3 = findViewById<CardView>(R.id.Card3)
        var Card4 = findViewById<CardView>(R.id.Card4)
        var Card5 = findViewById<CardView>(R.id.Card5)
        var Card6 = findViewById<CardView>(R.id.Card6)



        //Back Button takes you back to Homepage always. I'd like to change this soon.
        var backBut = findViewById<ImageButton>(R.id.backButton)

        backBut.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))

        }

        Card1.setOnClickListener{
            var Img = findViewById<ImageView>(R.id.img1Favs).drawable.toBitmap(300,400)
            var Carddesc = findViewById<TextView>(R.id.mealName1).text as String
            var cardCat = findViewById<TextView>(R.id.mealCat1).text as String
            createDetailIntent(Carddesc, Img, cardCat)

        }
        Card2.setOnClickListener{
            var Img = findViewById<ImageView>(R.id.img2Favs).drawable.toBitmap(300,400)
            var Carddesc = findViewById<TextView>(R.id.mealName2).text as String
            var cardCat = findViewById<TextView>(R.id.mealCat2).text as String
            createDetailIntent(Carddesc, Img, cardCat)
        }
        Card3.setOnClickListener{
            var Img = findViewById<ImageView>(R.id.img3Favs).drawable.toBitmap(300,400)
            var Carddesc = findViewById<TextView>(R.id.mealName3).text as String
            var cardCat = findViewById<TextView>(R.id.mealCat3).text as String
            createDetailIntent(Carddesc, Img, cardCat)
        }
        Card4.setOnClickListener{
            var Img = findViewById<ImageView>(R.id.img4Favs).drawable.toBitmap(300,400)
            var Carddesc = findViewById<TextView>(R.id.mealName4).text as String
            var cardCat = findViewById<TextView>(R.id.mealCat4).text as String
            createDetailIntent(Carddesc, Img, cardCat)
        }
        Card5.setOnClickListener{
            var Img = findViewById<ImageView>(R.id.img5Favs).drawable.toBitmap(300,400)
            var Carddesc = findViewById<TextView>(R.id.mealName5).text as String
            var cardCat = findViewById<TextView>(R.id.mealCat5).text as String
            createDetailIntent(Carddesc, Img, cardCat)
        }
        Card6.setOnClickListener{
            var Img = findViewById<ImageView>(R.id.img6Favs).drawable.toBitmap(300,400)
            var Carddesc = findViewById<TextView>(R.id.mealName6).text as String
            var cardCat = findViewById<TextView>(R.id.mealCat6).text as String
            createDetailIntent(Carddesc, Img, cardCat)
        }

    }


    fun createDetailIntent(id:String,img:Bitmap,cat:String){
        println("Intent Created")
        val intent = Intent(this, DetailedView::class.java)
        intent.putExtra("img", img )
        intent.putExtra("id", id)
        intent.putExtra("cat", cat)
        println("Put Extras!")

        startActivity(intent)

    }
    }




