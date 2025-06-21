package example.app.base.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun ErrorDialog(
    title: String = "Error",
    message: String,
) {

    val openAlertDialog = remember { mutableStateOf(false) }

    LaunchedEffect(openAlertDialog) {
        openAlertDialog.value = !openAlertDialog.value
    }

    if (openAlertDialog.value){
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text(text = title, style = MaterialTheme.typography.titleLarge)
            },
            text = {
                Text(text = message, style = MaterialTheme.typography.bodyMedium)
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = {
                    openAlertDialog.value = false
                }) {
                    Text("Dismiss")
                }
            }
        )
    }


}
