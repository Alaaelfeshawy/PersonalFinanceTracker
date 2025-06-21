package example.app.presentation.budget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import example.app.base.ui.UIState
import example.app.base.ui.components.ErrorDialog
import example.app.base.ui.components.LoadingDialog
import example.app.base.ui.components.TopBar
import example.app.presentation.R
import example.app.presentation.components.SetBudgetDialog
import example.app.presentation.components.SmoothLinearProgressIndicator
import java.math.BigDecimal

@Composable
fun BudgetScreen(
    onBackClick: () -> Unit,
) {
    val viewModel: BudgetViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val lifecycle = LocalLifecycleOwner.current

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(lifecycle) {
        viewModel.setEvent(BudgetEvents.LoadBudgetData)
    }

    if (showDialog) {
        SetBudgetDialog(
            onDismiss = { showDialog = false },
            selectedCategory = state.selectedCategory,
            onCategorySelected = {
                viewModel.setEvent(BudgetEvents.UpdateCategory(it))
            },
            onSave = { category, limit , isExpense->
                viewModel.setEvent(BudgetEvents.UpdateBudget(
                    name = category.category.name,
                    limit = limit,
                    isExpense = isExpense
                ))
                showDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.monthly_budget_overview),
                showBacButtonIcon = true,
                onBackClick = onBackClick
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {showDialog = true},
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Budget")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (state.budgetState is UIState.Loading){
                LoadingDialog()
            }

            if (state.budgetState is UIState.Error){
                ErrorDialog(
                    message = (state.budgetState as UIState.Error).error,
                )
            }

            if (state.budgetState is UIState.Success){
                val response = (state.budgetState as UIState.Success).data
                if (response.isNullOrEmpty()){
                    Box(
                        modifier = Modifier
                            .fillMaxSize() ,
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_empty_wallet),
                                contentDescription = "Empty Wallet",
                                modifier = Modifier
                                    .size(200.dp)
                                    .padding(bottom = 16.dp)
                            )

                            Text(
                                text = "No budgets yet",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Gray
                                )
                            )
                            Text(
                                text = "Tap + to add your budget",
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.LightGray)
                            )
                        }
                    }
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(response ?: emptyList()){ item ->
                        BudgetCategoryCard(item)

                    }

                }
            }

        }
    }
}

@Composable
private fun BudgetCategoryCard(
    model: BudgetUIModel,
    modifier: Modifier = Modifier
) {
    val percentageUsed = (model.spent.toFloat() / model.budget).coerceAtMost(1f)
//    val bgColor = when {
//        !model.isExpense -> Color(0xFFDFFFE0)
//        model.spent > model.budget.toBigDecimal() -> Color(0xFFFFD6D6)
//        else -> Color(0xFFE0FFE0)
//    }

    val progressColor = if (percentageUsed >= 1f) Color.Red else MaterialTheme.colorScheme.primary
//    val labelColor = if (model.isExpense) Color(0xFFFF6B6B) else Color(0xFF2ECC71)
    val labelText = if (model.isExpense) "Expense" else "Income"

    Card (
        modifier = modifier
                .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFAFAFA)
        )

    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ){

            Text(text = model.name, style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "${model.spent} / ${model.budget} ${model.currency}")

            Spacer(modifier = Modifier.height(6.dp))

            SmoothLinearProgressIndicator(
                progress = model.spent.toFloat() / model.budget,
                color = progressColor,
                backgroundColor = Color.LightGray.copy(alpha = 0.3f)
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun BudgetScreenPreview() {
    val categories = listOf(
        BudgetUIModel("Food", 300f, BigDecimal(250), isExpense = true),
        BudgetUIModel("Transport", 150f, BigDecimal(160), isExpense = true),
        BudgetUIModel("Entertainment", 100f, BigDecimal(40), isExpense = true),
        BudgetUIModel("Shopping", 100f, BigDecimal(40), isExpense = false),
    )
    BudgetCategoryCard(categories.first())
}
