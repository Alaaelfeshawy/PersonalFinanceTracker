package example.app.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CurrencyPound
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem(
            title = "Home",
            icon = Icons.Default.Home,
            route = MainDashboardRoutes.Home
        ),
        NavigationItem(
            title = "Statistics",
            icon = Icons.Filled.BarChart,
            route = MainDashboardRoutes.BudgetPlanning
        ),
        NavigationItem(
            title = "Exchange Rate",
            icon =Icons.Default.CurrencyPound,
            route = MainDashboardRoutes.ExchangeRate
        ),
        NavigationItem(
            title = "More",
            icon = Icons.Filled.MoreHoriz,
            route = MainDashboardRoutes.Settings
        ),
    )
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = MainDashboardRoutes.fromRoute(navBackStackEntry?.destination?.route)

    NavigationBar(
        containerColor = Color.White
    ) {

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                },
                label = {
                    Text(
                        item.title,
                        color = if (currentRoute == item.route)
                            Color.Black
                        else Color.Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.surface,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )

            )
        }
    }
}

