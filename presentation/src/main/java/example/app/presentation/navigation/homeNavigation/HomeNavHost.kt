package example.app.presentation.navigation.homeNavigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import example.app.presentation.AddEditTransaction.AddEditTransactionScreen
import example.app.presentation.Details.TransactionDetailsScreen


fun NavGraphBuilder.homeNavHost(navController: NavController) {
    navigation<HomeRoutes.HomeScreens>(startDestination = HomeRoutes.AddTransaction) {
        composable<HomeRoutes.AddTransaction> {
            AddEditTransactionScreen(
                transactionId = null,
                onBackClick = {
                    navController.popBackStack()

                }
            )
        }
        composable<HomeRoutes.EditTransaction>(){ backStackEntry ->
            val transactionId = backStackEntry.toRoute<HomeRoutes.EditTransaction>().transactionId

            AddEditTransactionScreen(
                transactionId = transactionId,
                onBackClick = {
                    navController.popBackStack()

                }
            )
        }

        composable<HomeRoutes.TransactionDetails>{ backStackEntry ->
            val transactionId = backStackEntry.toRoute<HomeRoutes.TransactionDetails>().transactionId
            TransactionDetailsScreen(
                transactionId = transactionId,
                onBackClick = { navController.popBackStack() },
                onEditClick = {
                    navController.navigate(HomeRoutes.EditTransaction(transactionId))
                },
            )

        }


    }
}