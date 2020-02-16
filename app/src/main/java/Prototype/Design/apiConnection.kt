package Prototype.Design

import android.graphics.drawable.Drawable
import android.util.Log.d
import android.util.Log.e
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toDrawable
import org.jetbrains.anko.doAsyncResult
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class apiConnection {


    private val TAG = "APICONNECTION"

    //fetches meal information using an ID Number
    //Requires the Meal ID # as an Argument
    //Returns an ArrayList<String> Containing the single Meal Information.
    fun getMealById(mealId: String): ArrayList<String> {

        return doAsyncResult {
            var jsonO =
                JSONObject(URL("https://www.themealdb.com/api/json/v2/9973533/lookup.php?i=$mealId").readText()).getJSONArray(
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
                JSONObject(URL("https://www.themealdb.com/api/json/v2/9973533/search.php?s=$mealName").readText()).getJSONArray(
                    "meals"
                ).getJSONObject(0)

            return@doAsyncResult parseIndMeal(jsonO)
        }.get()
    }


    // Fetches an entire Category from the API
    //

    fun getCat(catName: String): ArrayList<ArrayList<String>> {
        return doAsyncResult {
            var jsonArrayOfMealCatInfo =
                JSONObject(URL("https://www.themealdb.com/api/json/v2/9973533/filter.php?c=$catName").readText()).getJSONArray(
                    "meals"
                )

            var fullMealInfoSingleCat =
                ArrayList<ArrayList<String>>(jsonArrayOfMealCatInfo.length())

            for (i in 0 until jsonArrayOfMealCatInfo.length()) {
                fullMealInfoSingleCat.add(parseCatIndMeal(jsonArrayOfMealCatInfo.getJSONObject(i)))
            }
            return@doAsyncResult fullMealInfoSingleCat
        }.get()
    }


    fun getImgDrawable(imgUrl: String): Drawable {
        return doAsyncResult {


            // App will no longer crash due to null URL, but will display the beef wellington image.
            //Fix this later
            d(TAG, "URL =  $imgUrl")
            if (imgUrl != "null") {
                val inputStream = URL(imgUrl).openStream()
                val drawableResponse = Drawable.createFromStream(inputStream, null)

                inputStream.close()
                return@doAsyncResult drawableResponse
            } else {
                e(TAG, "Error getting image. Null URL")
                return@doAsyncResult R.drawable.beefwellington as Drawable
            }





        }.get()
    }


    private fun parseCatIndMeal(jsonO: JSONObject): ArrayList<String> {
        val mealInfo: ArrayList<String> = ArrayList(5)

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


    //This parses individual meals data into an ArrayList<String>
    //requires a Json Object as an argument. Only used by getMealById and getMealByName internally.
    //returns an ArrayList<String> full of all Individual Meal Information

    //format of each meal is: [0]mealName, [1]mealImgURL, [2]Meal Id #, [3]mealArea, [4]Instructions, [5]Ingredients1, [6]Ingredients2, [7]...
    private fun parseIndMeal(jsonO: JSONObject): ArrayList<String> {
        var mealInfo: ArrayList<String> = ArrayList(25)
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
        if (jsonO.has("strMeasure1") && !jsonO.getString("strMeasure1").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure1") + " " + jsonO.getString("strIngredient1"))
        }
        if (jsonO.has("strMeasure2") && !jsonO.getString("strMeasure2").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure2") + " " + jsonO.getString("strIngredient2"))
        }
        if (jsonO.has("strMeasure3") && !jsonO.getString("strMeasure3").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure3") + " " + jsonO.getString("strIngredient3"))
        }
        if (jsonO.has("strMeasure4") && !jsonO.getString("strMeasure4").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure4") + " " + jsonO.getString("strIngredient4"))
        }
        if (jsonO.has("strMeasure5") && !jsonO.getString("strMeasure5").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure5") + " " + jsonO.getString("strIngredient5"))
        }
        if (jsonO.has("strMeasure6") && !jsonO.getString("strMeasure6").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure6") + " " + jsonO.getString("strIngredient6"))
        }
        if (jsonO.has("strMeasure7") && !jsonO.getString("strMeasure7").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure7") + " " + jsonO.getString("strIngredient7"))
        }
        if (jsonO.has("strMeasure8") && !jsonO.getString("strMeasure8").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure8") + " " + jsonO.getString("strIngredient8"))
        }
        if (jsonO.has("strMeasure9") && !jsonO.getString("strMeasure9").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure9") + " " + jsonO.getString("strIngredient9"))
        }
        if (jsonO.has("strMeasure10") && !jsonO.getString("strMeasure10").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure10") + " " + jsonO.getString("strIngredient10"))
        }
        if (jsonO.has("strMeasure11") && !jsonO.getString("strMeasure11").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure11") + " " + jsonO.getString("strIngredient11"))
        }
        if (jsonO.has("strMeasure12") && !jsonO.getString("strMeasure12").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure12") + " " + jsonO.getString("strIngredient12"))
        }
        if (jsonO.has("strMeasure13") && !jsonO.getString("strMeasure13").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure13") + " " + jsonO.getString("strIngredient13"))
        }
        if (jsonO.has("strMeasure14") && !jsonO.getString("strMeasure14").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure14") + " " + jsonO.getString("strIngredient14"))
        }
        if (jsonO.has("strMeasure15") && !jsonO.getString("strMeasure15").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure15") + " " + jsonO.getString("strIngredient15"))
        }
        if (jsonO.has("strMeasure16") && !jsonO.getString("strMeasure16").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure16") + " " + jsonO.getString("strIngredient16"))
        }
        if (jsonO.has("strMeasure17") && !jsonO.getString("strMeasure17").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure17") + " " + jsonO.getString("strIngredient17"))
        }
        if (jsonO.has("strMeasure18") && !jsonO.getString("strMeasure18").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure18") + " " + jsonO.getString("strIngredient18"))
        }
        if (jsonO.has("strMeasure19") && !jsonO.getString("strMeasure19").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure19") + " " + jsonO.getString("strIngredient19"))
        }
        if (jsonO.has("strMeasure20") && !jsonO.getString("strMeasure20").isNullOrEmpty()) {
            mealInfo.add(jsonO.getString("strMeasure20") + " " + jsonO.getString("strIngredient20"))
        }
        return mealInfo
    }


    //This function queries the API for a response using a search term
    //Requires a search term as an Argument
    //Returns an ArrayList<ArrayList<String>> containing meal information parsed using parseIndMeal()

    //format of return is 'ReturnVal[0] - first meal, ReturnVal[1] - Second Meal ...
    //format of each meal is '[0]mealName, [1]mealImgURL, [2]Meal Id #, [3]mealArea, [4]Instructions, [5]Ingredients1, [6]Ingredients2, [7]...'
    fun searchByName(mealName: String): ArrayList<ArrayList<String>> {

        return doAsyncResult {

            var mealInfoLocal = ArrayList<ArrayList<String>>(9)
            var jsonA: JSONArray? = null

            var jsonObj: JSONObject? =
                JSONObject(URL("https://www.themealdb.com/api/json/v2/9973533/search.php?s=$mealName").readText())



            if (jsonObj!!.toString() != "{\"meals\":null}") {
                jsonA = jsonObj.getJSONArray("meals")


                for (i in 0 until jsonA!!.length()) {
                    mealInfoLocal.add(parseIndMeal(jsonA.getJSONObject(i)))

                }
            } else {
                e(TAG, "searchByName() returned null")
            }

            return@doAsyncResult mealInfoLocal


        }.get()

    }


}