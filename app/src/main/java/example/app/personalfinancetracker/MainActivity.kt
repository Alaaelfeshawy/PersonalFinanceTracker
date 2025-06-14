package example.app.personalfinancetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import example.app.personalfinancetracker.ui.theme.PersonalFinanceTrackerTheme
import example.app.presentation.navigation.AppNavigator

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PersonalFinanceTrackerTheme {
                AppNavigator()
            }
        }
    }
}