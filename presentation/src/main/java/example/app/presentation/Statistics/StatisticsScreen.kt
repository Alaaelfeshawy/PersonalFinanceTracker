package example.app.presentation.Statistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import example.app.presentation.R
import example.app.base.ui.components.TopBar
import example.app.presentation.model.Budget

@Composable
fun BudgetScreen(
    budgets: List<Budget> = listOf(),
    onBackClick : ()->Unit
) {
    val categories = listOf(
        Budget("Food", 300f, 250f , isExpense = true),
        Budget("Transport", 150f, 160f ,  isExpense = true),
        Budget("Entertainment", 100f, 40f , isExpense = true),
        Budget("Shopping", 100f, 40f , isExpense = false),
    )
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.monthly_budget_overview) ,
                showBacButtonIcon = true,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(categories) { category ->
                    BudgetCategoryCard(category)
                }
            }
        }
    }
}

@Composable
fun BudgetCategoryCard(
    model: Budget,
    modifier: Modifier = Modifier
) {
    val percentageUsed = (model.spent / model.budget).coerceAtMost(1f)
    val bgColor = when {
        !model.isExpense -> Color(0xFFDFFFE0)
        model.spent > model.budget -> Color(0xFFFFD6D6)
        else -> Color(0xFFE0FFE0)
    }

    val progressColor = if (percentageUsed >= 1f) Color.Red else MaterialTheme.colorScheme.primary
    val labelColor = if (model.isExpense) Color(0xFFFF6B6B) else Color(0xFF2ECC71)
    val labelText = if (model.isExpense) "Expense" else "Income"

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(bgColor, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = model.name, style = MaterialTheme.typography.titleMedium)
                Box(
                    modifier = Modifier
                        .background(labelColor, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = labelText,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "${model.spent.toInt()} / ${model.budget.toInt()} ${model.currency}")

            Spacer(modifier = Modifier.height(6.dp))

            SmoothLinearProgressIndicator(
                progress = model.spent / model.budget,
                color = progressColor,
                backgroundColor = Color.LightGray.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
fun SmoothLinearProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
    height: Dp = 8.dp,
    cornerRadius: Dp = 4.dp
) {
    Box(modifier = modifier
        .height(height)
        .clip(RoundedCornerShape(cornerRadius))) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val barWidth = size.width
            val barHeight = size.height

            // Background
            drawRoundRect(
                color = backgroundColor,
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(barHeight / 2, barHeight / 2)
            )

            // Progress
            drawRoundRect(
                color = color,
                size = Size(barWidth * progress.coerceIn(0f, 1f), barHeight),
                cornerRadius = CornerRadius(barHeight / 2, barHeight / 2)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BudgetScreenPreview() {
    val categories = listOf(
        Budget("Food", 300f, 250f, isExpense = true),
        Budget("Transport", 150f, 160f, isExpense = true),
        Budget("Entertainment", 100f, 40f, isExpense = true),
        Budget("Shopping", 100f, 40f, isExpense = false),
    )
    BudgetScreen(categories){}
}
