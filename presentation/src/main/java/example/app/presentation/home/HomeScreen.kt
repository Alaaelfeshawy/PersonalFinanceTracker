package example.app.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import example.app.base.ui.UIState
import example.app.presentation.R
import example.app.base.ui.components.ErrorDialog
import example.app.base.ui.components.LoadingDialog
import example.app.base.ui.components.TopBar
import example.app.presentation.components.TransactionItem

@Composable
fun HomeScreen(
    onEditClick : (Long) -> Unit = {},
    onAddClick : () -> Unit = {},
    onTransactionClicked : (Long) -> Unit = {}
) {

    val viewModel = hiltViewModel<HomeViewModel>()
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val lifecycle = LocalLifecycleOwner.current

    LaunchedEffect(lifecycle) {
        viewModel.setEvent(HomeEvents.GetTransactions)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary
                ) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        },
        topBar = {
            TopBar(stringResource(R.string.home))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ){
            if (state.transactionState is UIState.Loading){
                LoadingDialog()
            }

            if (state.transactionState is UIState.Error){
                ErrorDialog(
                    message = state.transactionState.error,
                )
            }

            if (state.transactionState is UIState.Success){
                if (state.transactionState.data.isNullOrEmpty()){
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
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
                                text = "No transactions yet",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Gray
                                )
                            )
                            Text(
                                text = "Tap + to add your first entry",
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.LightGray)
                            )
                        }
                    }
                }

                LazyColumn {
                    items(state.transactionState.data ?: emptyList()){ transaction ->
                        TransactionItem(
                            transaction = transaction,
                            onEditClick = { transaction.id?.let { onEditClick(it) } },
                            onDeleteClick = {
                                viewModel.setEvent(HomeEvents.RemoveTransaction(transaction))
                            },
                            onTransactionClicked = onTransactionClicked,
                        )
                    }
                }
            }

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TransactionListScreenPreview() {
    HomeScreen()
}
