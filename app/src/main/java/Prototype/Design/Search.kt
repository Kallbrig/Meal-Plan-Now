package Prototype.Design

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap


class Search : AppCompatActivity() {

    lateinit var card11: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        card11 = findViewById<ImageView>(R.id.img1Favs).drawable.toBitmap(300, 400)


    }
}
