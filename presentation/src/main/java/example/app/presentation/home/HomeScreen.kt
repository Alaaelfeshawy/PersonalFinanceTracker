package example.app.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import example.app.presentation.R
import example.app.presentation.base.UIState
import example.app.presentation.components.ErrorDialog
import example.app.presentation.components.LoadingDialog
import example.app.presentation.components.TopBar
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
            FloatingActionButton(onClick = onAddClick) {
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
                LazyColumn {
                    items(state.transactionState.data ?: emptyList()){ transaction ->
                        TransactionItem(
                            transaction = transaction,
                            onEditClick = { transaction.id?.let { onEditClick(it) } },
                            onDeleteClick = {
                                viewModel.setEvent(HomeEvents.RemoveTransaction(transaction))
                            },
                            onTransactionClicked = onTransactionClicked
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
