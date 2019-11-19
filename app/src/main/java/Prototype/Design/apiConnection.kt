package Prototype.Design

import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors


class apiConnection {


    // GLOBAL VARIABLES
    //

    protected var jsonA: JSONArray = JSONArray()
    protected var parsedArray: ArrayList<ArrayList<String>> = ArrayList()
    private val TAG = "APICONNECTION"




    // THIS HASN'T BEEN STARTED. NEEDS WORK NOW THAT API CALLS WORK
    //

    fun getMealById(mealId: String) {
        var mealInfo = ArrayList<String>()

        doAsync {

        }
    }






    // THIS DOES NOT WORK
    // IT RETURNS A EMPTY ARRAY INSTEAD OF ONE FULL OF THE MEAL INFORMATION

    fun getMealByName(mealName: String): ArrayList<String> {
        //This is the Json Object containing the meal information
        var parsedArray = ArrayList<String>()

        doAsync {
            var jsonO = JSONObject(URL("https://www.themealdb.com/api/json/v2/9973533/search.php?s=" + mealName).readText()).getJSONArray("meals").getJSONObject(0)

            parsedArray = parseIndMeal(jsonO)
        }

        println("????????????????!!!!!!!!!!!!!!!!!!!!!" + parsedArray)


        return parsedArray
    }



    // UNSURE IF THIS WORKS
    // TESTING AND CHECKING WILL BE REQUIRED

    fun getCat(catName: String): ArrayList<ArrayList<String>> {
        doAsync {
            var jsonA =
                JSONObject(URL("https://www.themealdb.com/api/json/v2/9973533/filter.php?c=" + catName).readText()).getJSONArray(
                    "meals"
                )

            setjsonA(jsonA)

        }
        Log.i(TAG, jsonA.toString())
        return parseCategory(jsonA)
    }



    // SLOW DOWN COWBOY. FIX THE OTHER SHIT FIRST
    //

    private fun parseIndMeal(jsonO: JSONObject): ArrayList<String> {
        var mealInfo: ArrayList<String> = ArrayList()
        //println("JsonO in parseIndMeal() - ")


        if (jsonO.has("strMeal")) {
            mealInfo.add(jsonO.getString("strMeal"))
        } else {
            println("JsonO is incomplete")
        }
        if (jsonO.has("strMealThumb")) {
            mealInfo.add(jsonO.getString("strMealThumb"))
        } else {
            println("JsonO is incomplete")
        }
        if (jsonO.has("idMeal")) {
            mealInfo.add(jsonO.getString("idMeal"))
        } else {
            println("JsonO is incomplete")
        }
        if (jsonO.has("strArea")) {
            mealInfo.add(jsonO.getString("strArea"))
        } else {
            println("JsonO is incomplete")
        }
        if (jsonO.has("strInstructions")) {
            mealInfo.add(jsonO.getString("strInstructions"))
        } else {
            println("JsonO is incomplete")
        }

        mealInfo.add(jsonO.getString("strArea"))
        mealInfo.add(jsonO.getString("strInstructions"))

        //Still need ingredients fetched and stored
        //println("mealInfo in parseIndMeal() - " + mealInfo)
        return mealInfo
    }




    //Function to parse a category search. takes the Json Array produced by the getCat() function as an argument.
    // UNLESS I'M WRONG, THIS DOES FUNCTION PROPERLY
    // CHECK IT ANYWAY

    private fun parseCategory(jsonACategory: JSONArray): ArrayList<ArrayList<String>> {
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
    }


    //Setters for internal use only
    //THESE ARE BASIC SETTERS. THEY DEFINITELY FUNCTION.

    private fun setparsedArray(parsedArray: ArrayList<ArrayList<String>>) {
        // For Debugging //println("ParsedArray[0] = " + parsedArray.get(0))
        if (!parsedArray.isNullOrEmpty()) {
            this.parsedArray = parsedArray
            Log.i(TAG, "GOOD: setparsedArray() has succeeded  -  Size = " + this.parsedArray)

        } else {
            Log.e(TAG, "BAD: setparsedArray() was passed a null or empty argument")
        }
    }

    private fun setjsonA(jsonA: JSONArray) {
        if (jsonA.toString() != "") {
            this.jsonA = jsonA

            Log.i(TAG, "GOOD: setjsonA() was passed a nonempty JsonArray")


        } else {
            Log.e(TAG, "BAD: setjsonA() was passed an empty argument")
        }
    }
}