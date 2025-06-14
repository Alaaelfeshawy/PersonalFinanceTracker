package example.app.currency_conversion.navigation

import androidx.compose.runtime.Composable

interface CurrencyConversionNavigator {
    @Composable

    fun navigateToCurrencySelection(isFromCurrency: Boolean)
    fun navigateBack()
}