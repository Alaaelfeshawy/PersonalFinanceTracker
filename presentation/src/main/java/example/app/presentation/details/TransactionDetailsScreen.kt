package example.app.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import example.app.base.ui.UIState
import example.app.base.ui.components.ErrorDialog
import example.app.base.ui.components.LoadingDialog
import example.app.base.ui.components.TopBar
import example.app.presentation.R
import example.app.presentation.shared.convertMillisToDate
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TransactionDetailsScreen(
    transactionId : Long?=null,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    ) {
    val viewModel = hiltViewModel<DetailsViewModel>()
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val lifecycle = LocalLifecycleOwner.current

    LaunchedEffect(lifecycle) {
        viewModel.setEvent(DetailsEvents.GetTransactions(transactionId))

        viewModel.navigate.collectLatest {
            if (it){
                onBackClick.invoke()
            }
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.transaction_details) ,
                showBacButtonIcon = true,
                onBackClick = onBackClick
                )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            if (state.transactionState is UIState.Loading) {
                LoadingDialog()
            }

            if (state.transactionState is UIState.Error) {
                ErrorDialog(
                    message = state.transactionState.error,
                )
            }

            if (state.transactionState is UIState.Success) {
                val model = state.transactionState.data
                 Column(
                     modifier = Modifier
                         .fillMaxSize()
                         .padding(24.dp),
                     verticalArrangement = Arrangement.spacedBy(16.dp)
                 ) {
                     Text(text = stringResource(R.string.category), fontSize = 16.sp, color = Color.Gray)
                     Row { model?.category?.image?.let {
                             Icon(
                                 imageVector = it,
                                 contentDescription =  model.category.title
                             )
                         }

                         Spacer(modifier = Modifier.width(3.dp))
                         Text("${model?.category?.title} (${model?.type})", fontWeight = FontWeight.Bold)

                     }

                     Text(text = stringResource(R.string.amount), fontSize = 16.sp, color = Color.Gray)
                     Text("EGP ${model?.amount}", fontSize = 24.sp, fontWeight = FontWeight.Bold)

                     Text(text = stringResource(R.string.type), fontSize = 16.sp, color = Color.Gray)
                     Text("${model?.type?.value}}", fontSize = 18.sp, fontWeight = FontWeight.Medium)

                     Divider()

                      DetailRow(label = stringResource(R.string.date), value = convertMillisToDate( model?.timestamp))

                     DetailRow(label = stringResource(R.string.payment_method), value = stringResource(
                         R.string.cash
                     )
                     )
                     model?.notes?.let { DetailRow(label = stringResource(R.string.notes), value = it) }

                     Spacer(modifier = Modifier.weight(1f))

                     Row(
                         modifier = Modifier.fillMaxWidth(),
                         horizontalArrangement = Arrangement.spacedBy(12.dp)
                     ) {
                         OutlinedButton(
                             onClick = {
                                 model?.let { DetailsEvents.RemoveTransaction(it) }
                                     ?.let { viewModel.setEvent(it) }
                             },
                             modifier = Modifier.weight(1f),
                             colors = ButtonDefaults.outlinedButtonColors(
                                 contentColor = MaterialTheme.colorScheme.primary
                             ),
                         ) {
                             Text(stringResource(R.string.delete))
                         }

                         Button(
                             onClick = onEditClick,
                             modifier = Modifier.weight(1f),
                             shape = RoundedCornerShape(12.dp)
                         ) {
                             Text(stringResource(R.string.edit))
                         }
                     }
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
            onEditClick = {} // No-op for preview
        )
    }
}
