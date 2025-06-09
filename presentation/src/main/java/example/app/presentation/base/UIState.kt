package example.app.presentation.base

sealed interface UIState<out T> {
    data object Initial : UIState<Nothing>
    data object Loading : UIState<Nothing>
    data class Success<out T>(val data: T) : UIState<T>
    data class Error(val error: String) : UIState<Nothing>
}
