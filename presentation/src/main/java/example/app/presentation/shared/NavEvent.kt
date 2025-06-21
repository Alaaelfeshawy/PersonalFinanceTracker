package example.app.presentation.shared

sealed class NavEvent {
    data object Navigate : NavEvent()
}