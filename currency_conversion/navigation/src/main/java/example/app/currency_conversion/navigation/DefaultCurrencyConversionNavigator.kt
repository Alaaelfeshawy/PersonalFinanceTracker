//package example.app.currency_conversion.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavHostController
//
//class DefaultCurrencyConversionNavigator(
//    private val navController: NavHostController
//) : CurrencyConversionNavigator {
//
//    @Composable
//    override fun navigateToCurrencySelection(isFromCurrency: Boolean) {
//        CurrencyConversionNavigator(navController)
//    }
//
//    override fun navigateBack() {
//        navController.popBackStack()
//    }
//}