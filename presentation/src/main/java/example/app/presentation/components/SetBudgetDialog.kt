package example.app.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import example.app.presentation.model.CategoryUIModel
import example.app.presentation.model.categories

@Composable
fun SetBudgetDialog(
    onDismiss: () -> Unit,
    selectedCategory : CategoryUIModel?,
    onCategorySelected : (CategoryUIModel) -> Unit,
    onSave: (category: CategoryUIModel, limit: Float, isExpense: Boolean) -> Unit
) {
    var limitText by remember { mutableStateOf("") }
    var isExpense by remember { mutableStateOf(true) }
    val isSaveEnabled = selectedCategory?.title?.isNotBlank() == true && limitText.toFloatOrNull() != null

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Set Budget", style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CategoryDropdown(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = onCategorySelected
                )

                OutlinedTextField(
                    value = limitText,
                    onValueChange = { limitText = it },
                    label = { Text("Limit (EGP)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Is Expense?")
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = isExpense,
                        onCheckedChange = { isExpense = it }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val limit = limitText.toFloatOrNull() ?: 0f
                    selectedCategory?.let { onSave(it, limit, isExpense) }
                },
                enabled = isSaveEnabled
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
