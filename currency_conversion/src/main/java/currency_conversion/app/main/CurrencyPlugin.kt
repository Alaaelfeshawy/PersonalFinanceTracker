package currency_conversion.app.main

import androidx.compose.runtime.Composable
import example.app.currency_conversion.navigation.CurrencyConversionNavHost

interface CurrencyNavigationContract {
    fun screen(): @Composable () -> Unit
}

object CurrencyPlugin : CurrencyNavigationContract {

    override fun screen(): @Composable () -> Unit = {
        CurrencyConversionNavHost()
    }

}
