package com.vipedev.kords

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.database.DataSnapshot
import com.vipedev.kords.chordscreen.ChordScreen
import com.vipedev.kords.chordscreen.ChordsViewModel
import com.vipedev.kords.database.ChordsDao
import com.vipedev.kords.database.convertToChords
import com.vipedev.kords.database.fetchChordsData
import com.vipedev.kords.ui.theme.KordsJetpackTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


class MainActivity : ComponentActivity() {

    private val chordsDao: ChordsDao = ChordsDao()
    private var updated: Boolean = false

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            KordsJetpackTheme {
                var isLoading by rememberSaveable { mutableStateOf(true) }

                LaunchedEffect(isLoading) // Unit is the key which determines when the effect should be run
                {
                    val dataSnapshot: DataSnapshot? = fetchChordsData()

                    if (dataSnapshot == null) {
                        delay(5.seconds)
                    } else {
                        if (!updated) {
                            chordsDao.initChordList(convertToChords(dataSnapshot))
                            updated = true
                        }

                    }

                    isLoading = false
                }

                if (isLoading) {
                    LoadingScreen()
                } else {
                    val viewModel: ChordsViewModel =
                        viewModel(factory = MyViewModelFactory(chordsDao, this))
                    //viewModel.chordList = db.dao.getAllChords().value
                    //println("chordsList : ${viewModel.chordList}")
                    ChordScreen(viewModel)
                }
            }
        }
    }
}

class MyViewModelFactory(private val db: ChordsDao, private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = ChordsViewModel(db, context) as T
}