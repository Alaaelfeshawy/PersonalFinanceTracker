package example.app.currency_conversion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import example.app.currencu_version.presentation.screen.CurrencyConverterScreen


@Composable
fun CurrencyConversionNavHost() {
   val  navController : NavHostController = rememberNavController()

    val graph =
        navController.createGraph(
            startDestination = CurrencyConversionRoutes.CurrencyConversionScreen,
        ) {
            composable<CurrencyConversionRoutes.CurrencyConversionScreen> {
                CurrencyConverterScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

    NavHost(
        navController = navController,
        graph = graph,
    )

}