package example.app.base.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel< State : ViewState,Event : UIEvent>(private val coroutineDispatcher: CoroutineDispatcher) :
    ViewModel() {

    abstract fun createInitialState(): State

    abstract fun handleEvent(uiEvent: Event)

    private val initialState: State by lazy { createInitialState() }

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val event = _event.asSharedFlow()

    val currentState: State
        get() = uiState.value

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        launchCoroutineScope {
            withContext(Dispatchers.Main) {
                _event.collect { event ->
                    handleEvent(event)
                }
            }
        }
    }

    fun setEvent(event: Event) {
        val newEvent = event
        launchCoroutineScope(Dispatchers.Main) {
            _event.resetReplayCache()
            _event.emit(newEvent)
        }
    }

    fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }
    protected fun launchCoroutineScope(
        dispatcher: CoroutineDispatcher = coroutineDispatcher,
        func: suspend () -> Unit
    ) {
        viewModelScope.launch(dispatcher) {
            func.invoke()
        }
    }

    override fun onCleared() {
        launchCoroutineScope {
            currentCoroutineContext().cancelChildren()
        }
        super.onCleared()
    }
}