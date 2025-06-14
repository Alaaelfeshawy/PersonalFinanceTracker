package example.app.currencu_version.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import java.util.Currency

//@Composable
//fun CurrencySelectionRow(
//    label: String,
//    currency: Currency,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Row(
//        modifier = modifier
//            .fillMaxWidth()
//            .clickable(onClick = onClick)
//            .padding(vertical = 8.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(
//            text = label,
//            style = MaterialTheme.typography.bodyLarge,
//            modifier = Modifier.weight(1f)
//        )
//
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.End,
//            modifier = Modifier.weight(1f)
//        ) {
//            Text(
//                text = currency.code,
//                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
//            )
//            Spacer(modifier = Modifier.width(8.dp))
//            Text(
//                text = currency.name,
//                style = MaterialTheme.typography.bodyMedium,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
//            )
//            Icon(
//                imageVector = Icons.Default.ArrowForward,
//                contentDescription = "Select currency",
//                modifier = Modifier.size(16.dp)
//            )
//        }
//    }
//}