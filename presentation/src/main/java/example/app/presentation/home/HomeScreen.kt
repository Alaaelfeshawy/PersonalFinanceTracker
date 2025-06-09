package example.app.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import example.app.presentation.components.TopBar
import example.app.presentation.components.TransactionItem
import example.app.presentation.model.Category
import example.app.presentation.model.CategoryUIModel
import example.app.presentation.model.TransactionType
import example.app.presentation.model.TransactionUi

@Composable
fun HomeScreen(
    onEditClick : (Long) -> Unit = {},
    onAddClick : () -> Unit = {},
    onTransactionClicked : (Long) -> Unit = {}
) {

    val transactions = listOf(
    TransactionUi(id = null, amount = "1.02", CategoryUIModel(
        category = Category.Shopping,
        title = "Groceries",
        image = Icons.Default.Shop
    ) , "150", "2025-06-01", TransactionType.EXPENSE),
        TransactionUi(id = null, amount = "1.2", CategoryUIModel(
        category = Category.Shopping,
        title = "Groceries",
        image = Icons.Default.Shop
    ) , "150", "2025-06-01", TransactionType.EXPENSE),

)
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        },
        topBar = {
            TopBar("Home")
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(transactions.size) { index ->
                TransactionItem(
                    transaction = transactions[index],
                    onEditClick = { transactions[index].id?.let { onEditClick(it) } },
                    onDeleteClick = { },
                    onTransactionClicked = onTransactionClicked
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TransactionListScreenPreview() {
    HomeScreen()
}
