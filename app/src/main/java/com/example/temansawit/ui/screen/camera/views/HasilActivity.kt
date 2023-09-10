package com.example.temansawit.ui.screen.camera.views

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.temansawit.ui.screen.camera.HasilScreen
import com.example.temansawit.ui.theme.TemanSawitTheme
import java.io.File

class HasilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContent {
            TemanSawitTheme {
                HasilScreen( if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getSerializableExtra("picture", File::class.java)

                } else {
                    @Suppress("DEPRECATION")
                    intent.getSerializableExtra("picture")
                } as? File,
                    intent.getFloatExtra("ripe", 0F),
                    intent.getFloatExtra("underripe", 0F),
                    intent.getFloatExtra("unripe", 0F),
                    intent.getFloatExtra("overripe", 0F),
                    intent.getFloatExtra("rotten", 0F),
                    intent.getFloatExtra("empty_bunch", 0F),
                ){
                    finish()
                }

            }
        }
    }
}