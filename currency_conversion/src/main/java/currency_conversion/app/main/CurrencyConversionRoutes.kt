package currency_conversion.app.main

import kotlinx.serialization.Serializable

@Serializable
sealed interface CurrencyConversionRoutes{
    @Serializable
    data object CurrencyConversionScreen : CurrencyConversionRoutes
    @Serializable
    data object CurrencyConversionScreens : CurrencyConversionRoutes
}

