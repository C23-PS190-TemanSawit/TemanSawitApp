package com.example.temansawit.ui.screen.camera

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.temansawit.R
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
                } as? File
                )
            }
        }
    }
}