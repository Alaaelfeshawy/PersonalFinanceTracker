package example.app.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface MainDashboardRoutes{
    @Serializable
    data object Home : MainDashboardRoutes
    @Serializable
    data object BudgetPlanning : MainDashboardRoutes

    @Serializable
    data object ExchangeRate : MainDashboardRoutes
    @Serializable
    data object Settings : MainDashboardRoutes

}

