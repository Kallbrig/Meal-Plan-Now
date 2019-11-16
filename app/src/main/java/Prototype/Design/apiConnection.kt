package Prototype.Design

import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.util.concurrent.Executors
import Prototype.Design.MainActivity
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.Image
import android.system.Os.shutdown
import android.text.Html
import java.net.HttpURLConnection
import java.net.URLConnection
import android.os.AsyncTask
import java.util.concurrent.CompletableFuture
import android.os.Bundle
import android.app.Activity




class apiConnection {

     protected var jsonA:JSONArray = JSONArray()
     protected var parsedArray:ArrayList<ArrayList<String>> = ArrayList<ArrayList<String>>()



/*  public  fun getMealById(mealId:String){
        Executors.newSingleThreadExecutor().execute{
            var jsonO = JSONObject(URL("https://www.themealdb.com/api/json/v2/9973533/lookup.php?i=" + mealId).readText()).getJSONArray("meals").getJSONObject(0)
            var mealInfo =  parseIndMeal(jsonO)
        }
    }*/

  fun getMealByName(mealName:String){

        //This is the Json Object containing the meal information
        var jsonO = JSONObject(URL("https://www.themealdb.com/api/json/v2/9973533/search.php?s=" + mealName).readText()).getJSONArray("meals").getJSONObject(0)


        parseIndMeal(jsonO)


    }

    //Fetches Categories from API
        fun getCat(catName:String) {
        jsonA =
            JSONObject(URL("https://www.themealdb.com/api/json/v2/9973533/filter.php?c=" + catName).readText()).getJSONArray(
                "meals"
            )
    }



    private fun setparsedArray(parsedArray:ArrayList<ArrayList<String>>){
        // For Debugging //println("ParsedArray[0] = " + parsedArray.get(0))
        if (!parsedArray.isNullOrEmpty()){
            this.parsedArray = parsedArray
            println("setparsedArray() has succeeded  -  Size = " + this.parsedArray.size)

        } else {
            println("!!!!!!setparsedArray() was passed a null or empty argument!!!!!!")
        }
    }

    fun setjsonA(jsonA:JSONArray){
        if (jsonA.toString(0) != ""){
            this.jsonA = jsonA
            println("setJsondA() has succeeded  -  length = " + jsonA.length())

        } else {
            println("!!!!!!setjsonA() was passed an empty argument!!!!!!")
        }
    }

   //  public fun getImg(imgURLStr:String):Drawable{
   //  }




   private fun parseIndMeal(jsonO: JSONObject):ArrayList<String>{
        lateinit var mealInfo:ArrayList<String>


        mealInfo.add(jsonO.getString("strMeal") as String)
        mealInfo.add(jsonO.getString("strMealThumb"))
        mealInfo.add(jsonO.getString("idmeal"))
        mealInfo.add(jsonO.getString("strArea"))
        mealInfo.add(jsonO.getString("strInstructions"))

        //Still need ingredients fetched and stored

        return mealInfo
    }

}


//Function to parse a category search. takes the Json Array produced by the getCat() function as an argument.
    private fun parseCategory(jsonA: JSONArray):ArrayList<ArrayList<String>>{
        var oneMealInfo = ArrayList<String>(3)
        var fullMealInfo = ArrayList<ArrayList<String>>(6)



//            for(i in 0 until 5) {
    var i = 0
    while (i<5){
                oneMealInfo.add(jsonA.getJSONObject(i).getString("strMeal"))
                oneMealInfo.add(jsonA.getJSONObject(i).getString("idMeal"))
                oneMealInfo.add(jsonA.getJSONObject(i).getString("strMealThumb"))
                fullMealInfo.add(i, oneMealInfo)


               // println(oneMealInfo)

            i++

            }

    return fullMealInfo
    }