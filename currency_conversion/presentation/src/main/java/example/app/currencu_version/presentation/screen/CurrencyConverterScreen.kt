package example.app.currencu_version.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import example.app.base.ui.theme.PersonalFinanceTrackerTheme

@Composable
fun CurrencyConverterScreen(
    onNavigateBack: () -> Unit
) {
    val viewModel = hiltViewModel<CurrencyConverterViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycle = LocalLifecycleOwner.current

    LaunchedEffect(lifecycle) {
        viewModel.setEvent(CurrencyConverterEvent.GetAvailableCurrencies)
    }
    Scaffold(
        topBar = {
            TopBar(
                title = "Currency Conversion",
                showBacButtonIcon = false,
                onBackClick = onNavigateBack,
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {

            val currencyState = state.currencyState
            val exchangeRateState = state.exchangeRateState

            if (currencyState is UIState.Loading || exchangeRateState is UIState.Loading ){
                LoadingDialog()
            }

            if (currencyState is UIState.Error){
              ErrorDialog(message = currencyState.error )
            }

            if (exchangeRateState is UIState.Error){
              ErrorDialog(message = exchangeRateState.error )
            }

            if (currencyState is UIState.Success){
                var convertedAmount : Double? = 0.0
                if (exchangeRateState is UIState.Success){
                    convertedAmount = exchangeRateState.data?.result
                }
                currencyState.data?.currencies?.keys?.toList()?.let {
                    CurrencyConverterContent(
                        amount = state.amount.orEmpty(),
                        convertedAmount = convertedAmount.toString(),
                        fromCurrency = state.fromSelectedCurrency.orEmpty(),
                        toCurrency = state.toSelectedCurrency.orEmpty(),
                        availableCurrencies = it,
                        onAmountChange = {
                            viewModel.setEvent(
                                CurrencyConverterEvent.SetAmount(it)
                            )
                        },
                        onFromCurrencySelected = {
                            viewModel.setEvent(CurrencyConverterEvent.SetSelectedFromCurrency(it))
                        },
                        onToCurrencySelected = {
                            viewModel.setEvent(CurrencyConverterEvent.SetSelectedToCurrency(it))
                        },
                        onSwapCurrencies = {
                            viewModel.setEvent(CurrencyConverterEvent.OnSwapCurrencies)
                        }
                    )
                }
            }

        }
    }
}

@Composable
fun CurrencyConverterContent(
    amount: String,
    convertedAmount: String,
    fromCurrency: String,
    toCurrency: String,
    availableCurrencies: List<String>,
    onAmountChange: (String) -> Unit,
    onSwapCurrencies: () -> Unit,
    onFromCurrencySelected: (String) -> Unit,
    onToCurrencySelected: (String) -> Unit,
) {
    var fromExpanded by remember { mutableStateOf(false) }
    var toExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(Modifier.wrapContentSize(),horizontalArrangement = Arrangement.SpaceBetween) {

            Row(
                modifier = Modifier.wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // From Currency
                Box(modifier = Modifier.weight(1f)) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { fromExpanded = true },
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("From", style = MaterialTheme.typography.labelSmall)
                            Text(fromCurrency, style = MaterialTheme.typography.bodyLarge)
                        }
                    }

                    DropdownMenu(
                        expanded = fromExpanded,
                        onDismissRequest = { fromExpanded = false }
                    ) {
                        availableCurrencies.forEach { currency ->
                            DropdownMenuItem(
                                text = { Text(currency) },
                                onClick = {
                                    onFromCurrencySelected(currency)
                                    fromExpanded = false
                                }
                            )
                        }
                    }
                }

                // Swap Button
                IconButton(
                    onClick = onSwapCurrencies,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = "Swap currencies",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                // To Currency
                Box(modifier = Modifier.weight(1f)) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { toExpanded = true },
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text("To", style = MaterialTheme.typography.labelSmall)
                            Text(toCurrency, style = MaterialTheme.typography.bodyLarge)
                        }
                    }


                    DropdownMenu(
                        expanded = toExpanded,
                        onDismissRequest = { toExpanded = false }
                    ) {
                        availableCurrencies.forEach { currency ->
                            DropdownMenuItem(
                                text = { Text(currency) },
                                onClick = {
                                    onToCurrencySelected(currency)
                                    toExpanded = false
                                }
                            )
                        }
                    }

                }
            }

        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = amount,
                onValueChange = onAmountChange,
                label = { Text("Amount") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = convertedAmount,
                onValueChange = {},
                label = { Text("Converted") },
                modifier = Modifier.weight(1f),
                enabled = false
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CurrencyConverterScreenPreview() {
    PersonalFinanceTrackerTheme {
        CurrencyConverterScreen(
            onNavigateBack = {  }
        )
    }
}