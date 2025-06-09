package example.app.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.More
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Nightlife
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Shop
import androidx.compose.ui.graphics.vector.ImageVector

data class Category(
    val name: String,
    val icon: ImageVector
)

val categories = listOf(
    Category("Food & Drinks", Icons.Default.Restaurant),
    Category("Shopping", Icons.Default.Shop),
    Category("Housing", Icons.Default.House),
    Category("Transportation", Icons.Default.DirectionsCar),
    Category("Vehicle", Icons.Default.DirectionsCar),
    Category("Life & Entertainment", Icons.Default.Nightlife),
    Category("Income", Icons.Default.AttachMoney),
    Category("Others", Icons.AutoMirrored.Default.More),
)
