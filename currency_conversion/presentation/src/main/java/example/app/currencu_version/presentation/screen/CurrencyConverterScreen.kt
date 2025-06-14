package example.app.currencu_version.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import example.app.base.ui.UIState
import example.app.base.ui.components.ErrorDialog
import example.app.base.ui.components.LoadingDialog
import example.app.base.ui.theme.PersonalFinanceTrackerTheme

@Composable
fun CurrencyConverterScreen(
    onNavigateToCurrencySelection: (isFromCurrency: Boolean) -> Unit,
    onNavigateBack: () -> Unit
) {
    val viewModel = hiltViewModel<CurrencyConverterViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycle = LocalLifecycleOwner.current

    LaunchedEffect(lifecycle) {
        viewModel.setEvent(CurrencyConverterEvent.GetExchangeRate)
    }
    Scaffold(
        topBar = {
//            TopBar(stringResource("Currency Conversion"))
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {

            if (state.exchangeRateState is UIState.Loading){
                LoadingDialog()
            }

            if (state.exchangeRateState is UIState.Error){
              ErrorDialog(message = "")
            }
            // Currency selection
//            CurrencySelectionRow(
//                label = "From",
//                currency = state.state,
//                onClick = { onNavigateToCurrencySelection(true) }
//            )

//            // Amount input
//            OutlinedTextField(
//                value = state.amount.toString(),
//                onValueChange = viewModel::onAmountChanged,
//                label = { Text("Amount") },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//            )

//            // Convert button
//            Button(
//                onClick = { viewModel.convert() },
//                enabled = !state.isLoading
//            ) {
//                Text("Convert")
//            }
//
//            // Result display
//            state.conversionResult?.let { result ->
//                Text("Result: ${result.amount} ${result.currency.code}")
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyConverterScreenPreview() {
    PersonalFinanceTrackerTheme {
        CurrencyConverterScreen(
            onNavigateToCurrencySelection = { },
            onNavigateBack = {  }
        )
    }
}