package example.app.presentation.navigation.homeNavigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeRoutes {
    @Serializable
    data object HomeScreens : HomeRoutes

    @Serializable
    data object AddTransaction : HomeRoutes

    @Serializable
    data class TransactionDetails(
        val transactionId:String

    ): HomeRoutes

    @Serializable
    data class EditTransaction(
        val transactionId:String
    ) : HomeRoutes
}