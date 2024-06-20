package com.example.kordsjetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kordsjetpack.ui.theme.KordsJetpackTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            KordsJetpackTheme {
                val viewModel = viewModel<ChordsViewModel>()
                ChordScreen(viewModel)
            }
        }
    }


}