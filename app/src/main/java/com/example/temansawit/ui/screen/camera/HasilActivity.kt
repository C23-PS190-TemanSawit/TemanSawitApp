package com.example.temansawit.ui.screen.camera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.temansawit.R
import com.example.temansawit.ui.theme.TemanSawitTheme

class HasilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContent {
            TemanSawitTheme {

                HasilScreen()
            }
        }
    }
}