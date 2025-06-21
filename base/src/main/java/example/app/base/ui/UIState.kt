package example.app.base.ui

sealed interface UIState<out T> {
    data object Initial : UIState<Nothing>
    data object Loading : UIState<Nothing>
    data class Success<out T>(val data: T?) : UIState<T>
    data class Error(val error: String) : UIState<Nothing>
}
