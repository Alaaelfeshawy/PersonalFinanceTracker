package example.app.presentation.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class NavigationItem(
    val title: String,
    @Contextual val icon: ImageVector,
    val route: MainDashboardRoutes
)