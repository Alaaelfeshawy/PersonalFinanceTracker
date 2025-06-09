package example.app.presentation.Details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import example.app.presentation.components.TopBar

@Composable
fun TransactionDetailsScreen(
    transactionId : Long?=null,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    ) {
    Scaffold(
        topBar = {
            TopBar(
                title = "Transaction Details" ,
                showBacButtonIcon = true,
                onBackClick = onBackClick
                )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Category", fontSize = 16.sp, color = Color.Gray)
            Text("Food", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Text("Amount", fontSize = 16.sp, color = Color.Gray)
            Text("EGP 75.00", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Text("Type", fontSize = 16.sp, color = Color.Gray)
            Text("Expense", fontSize = 18.sp, fontWeight = FontWeight.Medium)

            Divider()

            DetailRow("Date", "June 9, 2025")
            DetailRow("Time", "1:30 PM")
            DetailRow("Payment Method", "Cash")
            DetailRow("Notes", "Lunch at Koshary El Tahrir")

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                ) {
                    Text("Delete")
                }

                Button(
                    onClick = onEditClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Edit")
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.Gray)
        Text(text = value, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionDetailsPreview() {
    MaterialTheme {
        TransactionDetailsScreen(
            onBackClick = {},
            onDeleteClick ={},
            onEditClick = {} // No-op for preview
        )
    }
}
