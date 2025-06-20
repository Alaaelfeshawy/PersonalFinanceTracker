package example.app.currency_conversion.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface CurrencyConversionRoutes{
    @Serializable
    data object CurrencyConversionScreen : CurrencyConversionRoutes
}