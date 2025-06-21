package example.app.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Nightlife
import androidx.compose.material.icons.filled.Shop
import androidx.compose.ui.graphics.vector.ImageVector
import example.app.model.CategoryDomainModel

class CategoryUIModel(
    val category : Category,
    val title : String,
    val image : ImageVector
)
enum class Category {
    FoodDrinks,
    Shopping,
    Housing,
    Transportation,
    Vehicle,
    LifeEntertainment,
    Income,
    Others,
}

fun CategoryDomainModel.toUi(): Category = Category.entries[this.ordinal]

fun Category.toDomain(): CategoryDomainModel = CategoryDomainModel.entries[this.ordinal]

fun CategoryUIModel.toCategory(): Category = this.category

fun Category.toUIModel(): CategoryUIModel = when(this) {
    Category.FoodDrinks -> CategoryUIModel(Category.FoodDrinks ,"Food & Drinks", Icons.Default.LocalCafe)
    Category.Shopping -> CategoryUIModel( Category.Shopping,"Shopping", Icons.Default.Shop)
    Category.Housing -> CategoryUIModel(Category.Housing,"Housing", Icons.Default.House)
    Category.Transportation -> CategoryUIModel(Category.Transportation,"Transportation", Icons.Default.DirectionsCar)
    Category.Vehicle -> CategoryUIModel( Category.Vehicle,"Vehicle", Icons.Default.DirectionsCar)
    Category.LifeEntertainment -> CategoryUIModel(Category.LifeEntertainment,"Life & Entertainment", Icons.Default.Nightlife)
    Category.Income -> CategoryUIModel(Category.Income,"Income", Icons.Default.AttachMoney)
    Category.Others -> CategoryUIModel( Category.Others,"Others", Icons.Default.MoreVert)
}

val categories = listOf(
    CategoryUIModel(Category.FoodDrinks ,"Food & Drinks", Icons.Default.LocalCafe),
    CategoryUIModel( Category.Shopping,"Shopping", Icons.Default.Shop),
    CategoryUIModel(Category.Housing,"Housing", Icons.Default.House),
    CategoryUIModel(Category.Transportation,"Transportation", Icons.Default.DirectionsCar),
    CategoryUIModel( Category.Vehicle,"Vehicle", Icons.Default.DirectionsCar),
    CategoryUIModel(Category.LifeEntertainment,"Life & Entertainment", Icons.Default.Nightlife),
    CategoryUIModel(Category.Income,"Income", Icons.Default.AttachMoney),
    CategoryUIModel( Category.Others,"Others", Icons.Default.MoreVert),
)