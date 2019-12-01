package Prototype.Design

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton

class sevenDay : AppCompatActivity() {

    var api = apiConnection()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seven_day)


        var favsBut = findViewById<ImageButton>(R.id.favsButSevenDay)
        favsBut.setOnClickListener {

            var i = Intent(this, favs_view::class.java)
            startActivity(i)

        }
        var backBut = findViewById<ImageButton>(R.id.backButSevenDay)
        backBut.setOnClickListener {
            finish()
        }

        if (intent.extras?.get("addrem") == "AddSevenDay") {
            var add = intent.extras?.get("addrem") as String

            var mealToAdd = api.getMealById(intent.extras?.get("id") as String)

            // The following is what i hope will be a good representation of what will happen when a user wants to add a meal to their 7 day/favs List. These methods are not built yet.
            //They may not even be placed in this Activity, a better place could be it's own class. Or they could be called via startActivityforResult(). I haven't decided.
            //var mealInfo = query(add)
            //var parsedMealInfo = parse(mealInfo) as ArrayList<String>


        }

    }


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
}
