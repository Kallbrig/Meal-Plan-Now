package Prototype.Design

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

private val TAG: String = "LOGIN ACTIVITY"

class login_view : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_view)
        Log.i(TAG, "Login Activity Started")
        var titleBarText = findViewById<TextView>(R.id.titleBarLogin)
        titleBarText.text = "MealPlanNow!"

    }
}
