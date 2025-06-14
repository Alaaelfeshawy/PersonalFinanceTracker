package currency_conversion.app.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import example.app.currencu_version.presentation.screen.CurrencyConverterScreen

interface CurrencyNavigationContract {
    fun screen(navController: NavController): @Composable () -> Unit
}

object CurrencyPlugin : CurrencyNavigationContract {

    override fun screen(navController: NavController): @Composable () -> Unit = {
        CurrencyConverterScreen(
            onNavigateToCurrencySelection = {},
            onNavigateBack = { navController.popBackStack() }
        )
    }

}
