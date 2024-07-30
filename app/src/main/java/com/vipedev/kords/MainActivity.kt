package com.vipedev.kords

import android.annotation.SuppressLint
import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.database.DataSnapshot
import com.vipedev.kords.chords.screen.ChordsViewModel
import com.vipedev.kords.loading_screen.LoadingScreen
import com.vipedev.kords.chords.database.ChordsDao
import com.vipedev.kords.chords.database.convertToChords
import com.vipedev.kords.chords.database.fetchChordsData
import com.vipedev.kords.settings_screen.SettingsViewModel
import com.vipedev.kords.settings_screen.StorePreferences
import com.vipedev.kords.ui.theme.KordsJetpackTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


class MainActivity : ComponentActivity() {

    private val chordsDao: ChordsDao = ChordsDao()
    private var updated: Boolean = false

    fun localeSelection(context: Context, localeTag: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(localeTag)
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(localeTag)
            )
        }
    }


    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            val dataStore = StorePreferences(LocalContext.current)
            val useSystemTheme : Boolean = dataStore.getUseSystemTheme.collectAsState(initial = true).value!!
            val useDarkTheme : Boolean = dataStore.getUseDarkTheme.collectAsState(initial = true).value!!
            val darkTheme: Boolean = if (useSystemTheme) {
                isSystemInDarkTheme()
            } else {
                useDarkTheme
            }
            KordsJetpackTheme (darkTheme = darkTheme) {
                val items = listOf(
                    BottomNavigationItem(title = stringResource(id = R.string.chords_nav_item),
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                        hasNews = false),
                    BottomNavigationItem(title = stringResource(id = R.string.contact_nav_item),
                        selectedIcon = Icons.Filled.Email,
                        unselectedIcon = Icons.Outlined.Email,
                        hasNews = false),
                    BottomNavigationItem(title = stringResource(id = R.string.settings_nav_item),
                        selectedIcon = Icons.Filled.Settings,
                        unselectedIcon = Icons.Outlined.Settings,
                        hasNews = true)
                )

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
                    val viewModel: ChordsViewModel = viewModel(factory = MyViewModelFactory(chordsDao, this))
                    val settingsViewModel = SettingsViewModel(dataStore = dataStore)
                    MainScreen(items = items, viewModel = viewModel, dataStore = dataStore, settingsViewModel = settingsViewModel)
                }

            }
        }
    }
}

class MyViewModelFactory(private val db: ChordsDao, private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = ChordsViewModel(db, context) as T
}