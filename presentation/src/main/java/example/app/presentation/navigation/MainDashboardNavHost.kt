package example.app.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import currency_conversion.app.main.CurrencyConversionRoutes
import currency_conversion.app.main.CurrencyPlugin
import example.app.presentation.Statistics.BudgetScreen
import example.app.presentation.home.HomeScreen
import example.app.presentation.navigation.homeNavigation.HomeRoutes
import example.app.presentation.navigation.homeNavigation.homeNavHost

@Composable
fun AppNavigator() {

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController) },
    ) { innerPadding ->

        val graph =
            navController.createGraph(
                startDestination = MainDashboardRoutes.Home,
                ) {
                composable<MainDashboardRoutes.Home> {
                    HomeScreen(
                        onEditClick = {
                            transactionId->
                            navController.navigate(HomeRoutes.EditTransaction(transactionId))
                        },
                        onAddClick = {
                            navController.navigate(HomeRoutes.AddTransaction)

                        },
                        onTransactionClicked = { transactionId ->
                            navController.navigate(HomeRoutes.TransactionDetails(transactionId))

                        },
                        onExchangeRate = {
                            navController.navigate(CurrencyConversionRoutes.CurrencyConversionScreen)
                        }
                    )
                }
                composable<CurrencyConversionRoutes.CurrencyConversionScreen> {
                    CurrencyPlugin.screen(navController).invoke()
                }
                homeNavHost(navController)
                composable<MainDashboardRoutes.BudgetPlanning> {
                    BudgetScreen(listOf()){
                        navController.popBackStack()
                    }
                }
                composable<MainDashboardRoutes.Settings> {
                }
            }
        NavHost(
            navController = navController,
            graph = graph,
            modifier = Modifier.padding(innerPadding)
        )

    }
}
