package Prototype.Design

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity

class sevenDay : AppCompatActivity() {

    var api = apiConnection()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seven_day)


        var favsBut = findViewById<ImageView>(R.id.favsButSevenDay)
        favsBut.setOnClickListener {

            var i = Intent(this, favs_view::class.java)
            startActivity(i)

        }
        var backBut = findViewById<ImageButton>(R.id.backButton)
        backBut.setOnClickListener {
            finish()
        }

        var favsButSevenDay = findViewById<ImageButton>(R.id.favsButSevenDay)
        favsButSevenDay.setOnClickListener(){
            createFavsIntent()
        }

        var searchButSevenDay = findViewById<ImageButton>(R.id.searchButSevenDay)
        searchButSevenDay.setOnClickListener(){
            createSearchIntent()
        }

        var sevenDayButSevenDay = findViewById<ImageButton>(R.id.sevenDayButSevenDay)


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
