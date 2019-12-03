package Prototype.Design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*


class DetailedView : AppCompatActivity() {

    val TAG = "DETAILED VIEW"
    var api = apiConnection()

    lateinit var bgImg: ImageView
    lateinit var mealPreview: ImageView
    lateinit var titleBar: TextView
    lateinit var mealName: TextView
    lateinit var mealCat: TextView
    lateinit var ingList: ArrayList<TextView>


    lateinit var mealInstructions: TextView
    lateinit var downloadBut: LinearLayout
    lateinit var meal: ArrayList<String>

    lateinit var searchButDetail: ImageButton
    lateinit var favsButDetail: ImageButton
    lateinit var sevenDayButDetail: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_view)

        Log.i(TAG, "Detailed View Started")

        bgImg = findViewById<ImageView>(R.id.bgImg)
        mealPreview = findViewById<ImageView>(R.id.mealPreview)
        titleBar = findViewById<TextView>(R.id.titleBar)
        mealName = findViewById<TextView>(R.id.mealName)
        mealCat = findViewById<TextView>(R.id.mealCat)
        ingList = ArrayList<TextView>(20)


        mealInstructions = findViewById<TextView>(R.id.mealInstructions)
        downloadBut = findViewById<LinearLayout>(R.id.addToSevenDay)

        searchButDetail = findViewById(R.id.searchButDetail)
        searchButDetail.setOnClickListener {
            createSearchIntent()
        }
        favsButDetail = findViewById(R.id.favsButDetail)
        favsButDetail.setOnClickListener {
            createFavsIntent()
        }
        sevenDayButDetail = findViewById(R.id.sevenDayButDetail)
        sevenDayButDetail.setOnClickListener {
            createSevenDayIntent()
        }





        meal = api.getMealById(intent.getStringExtra("id")!!)


        Log.i(TAG, "Background Image URL = " + meal[1])
        //mealPreview.imageBitmap = (api.getImgBitmap(meal[1],this))

        var mealImg = api.getImgDrawable(meal[1])

        //Meal Name Setter
        mealName.text = meal[0]
        titleBar.text = meal[0]

        //Background Image Setter
        bgImg.setImageDrawable(mealImg)

        //Meal Preview Setter
        mealPreview.setImageDrawable(mealImg)

        //Meal Instructions Setter
        mealInstructions.text = meal[4]

        //Meal Category Setter
        mealCat.text = meal[3]

        //Sets onClickListeners for Share and Back Buttons
        setShareBut()
        setBackBut()
        ingListSetter()


    }


    private fun ingListSetter() {
        ingList.addAll(
            arrayListOf(
                findViewById(R.id.ing1),
                findViewById(R.id.ing2),
                findViewById(R.id.ing3),
                findViewById(R.id.ing4),
                findViewById(R.id.ing5),
                findViewById(R.id.ing6),
                findViewById(R.id.ing7),
                findViewById(R.id.ing8),
                findViewById(R.id.ing9),
                findViewById(R.id.ing10),
                findViewById(R.id.ing11),
                findViewById(R.id.ing12),
                findViewById(R.id.ing13),
                findViewById(R.id.ing14),
                findViewById(R.id.ing15),
                findViewById(R.id.ing16),
                findViewById(R.id.ing17),
                findViewById(R.id.ing18),
                findViewById(R.id.ing19),
                findViewById(R.id.ing20)
            )
        )
        var i = 5
        var j = 0
        println(meal.size - 1)
        while (i < meal.size - 1) {
            ingList[j].text = meal[i]
            if (!ingList[j].text.contains("null")) {
                ingList[j].alpha = 1f
            }

            i++
            j++
        }
    }


    //Sets Share button to work.
    //No Arguments
    //No returns

    //Needs to be simplified. it works now, but It's a workaround due to time constraints.
    private fun setShareBut() {
        //var shareBut = findViewById<LinearLayout>(R.id.shareBut)
        var shareButText = findViewById<TextView>(R.id.shareButText)
        var sharebutImg = findViewById<ImageButton>(R.id.shareButImg)


        shareButText.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this Meal! - https://www.themealdb.com/meal.php?c=" + meal[2]
            )
            sendIntent.type = "text/plain"

            Log.i(TAG, "Share Intent Created")

            val shareIntent = Intent.createChooser(sendIntent, null)

            startActivity(shareIntent)
        }

        sharebutImg.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this Meal! - https://www.themealdb.com/meal.php?c=" + meal[2]
            )
            sendIntent.type = "text/plain"

            Log.i(TAG, "Share Intent Created")

            val shareIntent = Intent.createChooser(sendIntent, null)

            startActivity(shareIntent)
        }
    }

    private fun setBackBut() {
        var backBut = findViewById<ImageButton>(R.id.backButton)
        backBut.setOnClickListener {
            finish()
        }

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


