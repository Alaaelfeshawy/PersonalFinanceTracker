package example.app.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyPound
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import example.app.presentation.R
import example.app.presentation.model.TransactionUi

@Composable
fun TransactionItem(
    transaction: TransactionUi,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onExchangeRate: () -> Unit,
    onTransactionClicked: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable { transaction.id?.let { onTransactionClicked.invoke(it) } },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row {
                    transaction.category?.image?.let {
                        Icon(
                            imageVector = it,
                            contentDescription =  transaction.category.title
                        )
                    }

                    Spacer(modifier = Modifier.width(3.dp))
                    Text("${transaction.category?.title} (${transaction.type})", fontWeight = FontWeight.Bold)

                }
                Text("${stringResource(R.string.amount)}: ${transaction.amount}")
                Text("${stringResource(R.string.date)} ${transaction.date}")
                transaction.notes?.let { Text("${stringResource(R.string.note)} $it") }
            }
            Row {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.edit))
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete))
                }
                IconButton(onClick = onExchangeRate) {
                    Icon(Icons.Default.CurrencyPound, contentDescription = "Exchange rates")
                }
            }
        }
    }
}
