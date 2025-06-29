package example.app.presentation.addEditTransaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import example.app.base.ui.UIState
import example.app.base.ui.components.ErrorDialog
import example.app.base.ui.components.LoadingDialog
import example.app.base.ui.components.TopBar
import example.app.presentation.R
import example.app.presentation.components.CategoryDropdown
import example.app.presentation.components.DatePickerDocked
import example.app.presentation.components.SegmentedButton
import example.app.presentation.model.categories
import example.app.presentation.shared.NavEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditTransactionScreen(
    transactionId : Long?=null,
    onBackClick: () -> Unit,
) {
    val viewModel = hiltViewModel<AddEditTransactionViewModel>()
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val lifecycle = LocalLifecycleOwner.current

    LaunchedEffect(lifecycle) {
        if (transactionId != null){
            viewModel.setEvent(AddEditTransactionEvents.GetTransactions(transactionId))
        }
        viewModel.navigate.collectLatest {
            when(it){
                NavEvent.Navigate -> {onBackClick.invoke()}
            }
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = getTitle(transactionId),
                showBacButtonIcon = true,
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (state.transactionState is UIState.Loading){
                LoadingDialog()
            }

            if (state.transactionState is UIState.Error){
                ErrorDialog(
                    message = state.transactionState.error,
                )
            }

            SegmentedButton(
                options = state.options,
                selectedOption = state.transactionUi?.type,
                onOptionSelected = {
                    viewModel.setEvent(AddEditTransactionEvents.UpdateTransactionDetails(type = it))
                }
            )

            OutlinedTextField(
                value = state.transactionUi?.amount.orEmpty(),
                onValueChange = {viewModel.setEvent(AddEditTransactionEvents.UpdateTransactionDetails(amount = it))},
                label = { Text(stringResource(R.string.amount)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            CategoryDropdown(
                categories =categories,
                selectedCategory = state.transactionUi?.category,
                onCategorySelected = {
                    viewModel.setEvent(AddEditTransactionEvents.UpdateTransactionDetails(category = it))
                }
            )

            DatePickerDocked(
                selectedDate = state.transactionUi?.date.orEmpty(),
                onDateSelected ={
                    viewModel.setEvent(AddEditTransactionEvents.UpdateTransactionDetails(date = it))
                }
            )

            OutlinedTextField(
                value = state.transactionUi?.notes.orEmpty(),
                onValueChange = {viewModel.setEvent(AddEditTransactionEvents.UpdateTransactionDetails(notes = it))},
                label = { Text(stringResource(R.string.notes_optional)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.setEvent(AddEditTransactionEvents.SaveTransaction)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = state.isSaveButtonEnabled()
            ) {
                Text(stringResource(R.string.save_transaction))
            }
        }
    }
}


@Composable
fun getTitle(transactionId : Long?) : String{
   return if (transactionId != null) "Edit Transaction" else
       stringResource(R.string.add_transaction)
}


@Preview(showSystemUi = true)
@Composable
fun AddEditTransactionScreenPreview() {
    AddEditTransactionScreen( transactionId = null , onBackClick = {})
}
