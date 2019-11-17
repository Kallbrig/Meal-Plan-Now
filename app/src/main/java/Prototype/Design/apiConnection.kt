package Prototype.Design

import android.os.AsyncTask
import org.jetbrains.anko.doAsyncResult
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors


class apiConnection {

    protected var jsonA: JSONArray = JSONArray()
    protected var parsedArray: ArrayList<ArrayList<String>> = ArrayList()


    fun getMealById(mealId: String): ArrayList<String> {
        var mealInfo = ArrayList<String>()



/*        AsyncTask.execute {
            var jsonO =
                JSONObject(URL("https://www.themealdb.com/api/json/v2/9973533/lookup.php?i=" + mealId).readText()).getJSONArray(
                    "meals"
                ).getJSONObject(0)
            mealInfo = parseIndMeal(jsonO)
            return@execute
        }*/

        println("GetMealById() -" + mealInfo)

        when (mealInfo.isNullOrEmpty()) {
            true -> return ArrayList()
            false -> return mealInfo

        }
    }

    fun getMealByName(mealName: String) {

        //This is the Json Object containing the meal information
        var jsonO =
            JSONObject(URL("https://www.themealdb.com/api/json/v2/9973533/search.php?s=" + mealName).readText()).getJSONArray(
                "meals"
            ).getJSONObject(0)


        parseIndMeal(jsonO)


    }

    //Fetches Categories from API
    fun getCat(catName: String) {
        jsonA =
            JSONObject(URL("https://www.themealdb.com/api/json/v2/9973533/filter.php?c=" + catName).readText()).getJSONArray(
                "meals"
            )
    }


    //  public fun getImg(imgURLStr:String):Drawable{
    //  }


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
    private fun parseCategory(jsonA: JSONArray): ArrayList<ArrayList<String>> {
        var oneMealInfo = ArrayList<String>(3)
        var fullMealInfo = ArrayList<ArrayList<String>>(6)


//            for(i in 0 until 5) {
        var i = 0
        while (i < 5) {
            oneMealInfo.add(jsonA.getJSONObject(i).getString("strMeal"))
            oneMealInfo.add(jsonA.getJSONObject(i).getString("idMeal"))
            oneMealInfo.add(jsonA.getJSONObject(i).getString("strMealThumb"))
            fullMealInfo.add(i, oneMealInfo)


            // println(oneMealInfo)

            i++

        }

        return fullMealInfo
    }


    //Setters for internal use only

    private fun setparsedArray(parsedArray: ArrayList<ArrayList<String>>) {
        // For Debugging //println("ParsedArray[0] = " + parsedArray.get(0))
        if (!parsedArray.isNullOrEmpty()) {
            this.parsedArray = parsedArray
            println("setparsedArray() has succeeded  -  Size = " + this.parsedArray.size)

        } else {
            println("!!!!!!setparsedArray() was passed a null or empty argument!!!!!!")
        }
    }

    private fun setjsonA(jsonA: JSONArray) {
        if (jsonA.toString(0) != "") {
            this.jsonA = jsonA
            println("setJsondA() has succeeded  -  length = " + jsonA.length())

        } else {
            println("!!!!!!setjsonA() was passed an empty argument!!!!!!")
        }
    }
}