package Prototype.Design

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.nfc.Tag
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.onComplete
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors


class apiConnection {


    // GLOBAL VARIABLES
    //

    protected var jsonA: JSONArray? = null
    protected var mealInfo2: JSONObject? = null
    protected var parsedArray: ArrayList<ArrayList<String>>? = null
    protected var mealInfo: ArrayList<String> = ArrayList(5)
    private val TAG = "APICONNECTION"


    //fetches meal information using an ID Number
    //Requires the Meal ID # as an Argument
    //Returns an ArrayList<String> Containing the single Meal Information.
    fun getMealById(mealId: String): ArrayList<String> {
        return doAsyncResult {
            var jsonO =
                JSONObject(URL("https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + mealId).readText()).getJSONArray(
                    "meals"
                ).getJSONObject(0)
            var mealInfoLocal = parseIndMeal(jsonO)
            //  Log.i(TAG, "GetMealById has finished")
            return@doAsyncResult mealInfoLocal
        }.get()
    }


    //fetches meal information using the meal's Name
    //Requires the Meal Name as an Argument
    //Returns an ArrayList<String> containing the single Meal Information
    fun getMealByName(mealName: String): ArrayList<String> {
        return doAsyncResult {
            while (mealName.contains(' ')) {
                mealName.replace(' ', '_')
            }
            var jsonO =
                JSONObject(URL("https://www.themealdb.com/api/json/v1/1/search.php?s=" + mealName).readText()).getJSONArray(
                    "meals"
                ).getJSONObject(0)
            var mealInfoLocal = parseIndMeal(jsonO)
            //  Log.i(TAG, "GetMealById has finished")
            return@doAsyncResult mealInfoLocal
        }.get()
    }


    // UNSURE IF THIS WORKS
    // TESTING AND CHECKING WILL BE REQUIRED

    fun getCat(catName: String): ArrayList<ArrayList<String>> {
        return doAsyncResult {
            var jsonA =
                JSONObject(URL("https://www.themealdb.com/api/json/v2/9973533/filter.php?c=" + catName).readText()).getJSONArray(
                    "meals"
                )
            var fullMealInfoSingleCat = ArrayList<ArrayList<String>>(jsonA.length())
            for (i in 0..jsonA.length() - 1) {
                fullMealInfoSingleCat.add(parseCatIndMeal(jsonA.getJSONObject(i)))
            }
            return@doAsyncResult fullMealInfoSingleCat
        }.get()
    }


    fun getImgDrawable(imgUrl: String): Drawable {
        return doAsyncResult {
            var inputStream = URL(imgUrl).openStream()
            var draw = Drawable.createFromStream(inputStream, null)
            inputStream.close()
            return@doAsyncResult draw
        }.get()
    }


    fun getImgBitmap(imgUrl: String, cont: Context): Bitmap {
        return doAsyncResult {
            return@doAsyncResult Picasso.with(cont).load(imgUrl).get()
        }.get()
    }


    private fun parseCatIndMeal(jsonO: JSONObject): ArrayList<String> {
        var mealInfo: ArrayList<String> = ArrayList(5)

        if (jsonO.has("strMeal")) {
            mealInfo.add(jsonO.getString("strMeal"))
        } else {
            println("JsonO is incomplete: Missing strMeal")
        }
        if (jsonO.has("strMealThumb")) {
            mealInfo.add(jsonO.getString("strMealThumb"))
        } else {
            println("JsonO is incomplete: Missing strMealThumb")
        }
        if (jsonO.has("idMeal")) {
            mealInfo.add(jsonO.getString("idMeal"))
        } else {
            println("JsonO is incomplete: Missing idMeal")
        }


        return mealInfo
    }


    // SLOW DOWN COWBOY. FIX THE OTHER SHIT FIRST
    // THIS STILL NEEDS INGREDIENTS ADDED

    private fun parseIndMeal(jsonO: JSONObject): ArrayList<String> {
        var mealInfo: ArrayList<String> = ArrayList(5)
        //println("JsonO in parseIndMeal() - ")


        if (jsonO.has("strMeal")) {
            mealInfo.add(jsonO.getString("strMeal"))
        } else {
            println("JsonO is incomplete: Missing strMeal")
        }
        if (jsonO.has("strMealThumb")) {
            mealInfo.add(jsonO.getString("strMealThumb"))
        } else {
            println("JsonO is incomplete: Missing strMealThumb")
        }
        if (jsonO.has("idMeal")) {
            mealInfo.add(jsonO.getString("idMeal"))
        } else {
            println("JsonO is incomplete: Missing idMeal")
        }
        if (jsonO.has("strArea")) {
            mealInfo.add(jsonO.getString("strArea"))
        } else {
            println("JsonO is incomplete: Missing strArea")
        }
        if (jsonO.has("strInstructions")) {
            mealInfo.add(jsonO.getString("strInstructions"))
        } else {
            println("JsonO is incomplete: Missing strInstructions")
        }

        return mealInfo
    }

    fun parseIng() {

    }


    //Function to parse a category search. takes the Json Array produced by the getCat() function as an argument.
    // UNLESS I'M WRONG, THIS DOES FUNCTION PROPERLY
    // CHECK IT ANYWAY

/*     private fun parseCategory(jsonACategory: JSONArray): ArrayList<ArrayList<String>> {

         var oneMealInfo = ArrayList<String>(3)
         var fullMealInfo = ArrayList<ArrayList<String>>(6)


//            for(i in 0 until 5) {
         var i = 0
         while (i < 5) {
             oneMealInfo.add(jsonACategory.getJSONObject(i).getString("strMeal"))
             oneMealInfo.add(jsonACategory.getJSONObject(i).getString("idMeal"))
             oneMealInfo.add(jsonACategory.getJSONObject(i).getString("strMealThumb"))
             fullMealInfo.add(i, oneMealInfo)

             i++

         }

         return fullMealInfo
     }*/


/*

     fun parseCategory(jsonACategory:JSONArray){
         doAsyncResult {
             var i = 0
             var max = 0

             return@doAsyncResult returnResult
         }.get()
     }
*/


    //Setters for internal use only
    //THESE ARE BASIC SETTERS. THEY DEFINITELY FUNCTION.

    private fun setparsedArray(parsedArray: ArrayList<ArrayList<String>>) {
        // For Debugging //println("ParsedArray[0] = " + parsedArray.get(0))
        if (!parsedArray.isNullOrEmpty()) {
            this.parsedArray = parsedArray
            //  Log.i(TAG, "GOOD: setparsedArray() has succeeded  -  Size = " + this.parsedArray)

        } else {
            Log.e(TAG, "BAD: setparsedArray() was passed a null or empty argument")
        }
    }

    private fun setjsonA(jsonA: JSONArray) {
        if (jsonA.toString() != "") {
            this.jsonA = jsonA

            //   Log.i(TAG, "GOOD: setjsonA() was passed a nonempty JsonArray")


        } else {
            Log.e(TAG, "BAD: setjsonA() was passed an empty argument")
        }
    }

    fun displayjsonO() {
        Log.i(TAG, "mealInfo --- " + this.mealInfo.toString())
    }


/*
    fun setMI(jjj:ArrayList<String>){
        for(i in jjj.toArray().indices){
            mealInfo.set(i, jjj.get(i))


            Log.i(TAG, mealInfo.get(i))

        }

    }
*/


    fun getMI(): ArrayList<String> {
        return this.mealInfo
    }


}