//package example.app.currency_conversion.navigation
//
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.navigation.NavController
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.navigation
//import androidx.navigation.compose.rememberNavController
//import androidx.navigation.createGraph
//import example.app.currencu_version.presentation.screen.CurrencyConverterScreen
//
////fun NavGraphBuilder.currencyConversionNavHost(navController: NavController) {
////    navigation<CurrencyConversionRoutes.CurrencyConversionScreens>(startDestination = CurrencyConversionRoutes.CurrencyConversionScreen) {
////        composable<CurrencyConversionRoutes.CurrencyConversionScreen> {
////            CurrencyConverterScreen(
////                onNavigateToCurrencySelection = {},
////                onNavigateBack = {
////                    navController.popBackStack()
////                }
////            )
////        }
////
////
////    }
////}
//
//@Composable
//fun CurrencyConversionNavigator(navController : NavHostController) {
//
//    val graph =
//        navController.createGraph(
//            startDestination = CurrencyConversionRoutes.CurrencyConversionScreen,
//        ) {
//            composable<CurrencyConversionRoutes.CurrencyConversionScreen> {
//                CurrencyConverterScreen(
//                    onNavigateToCurrencySelection = {},
//                    onNavigateBack = {
//                    navController.popBackStack()
//                    }
//                )
//            }
//        }
//
//        NavHost(
//            navController = navController,
//            graph = graph,
//        )
//
//}
