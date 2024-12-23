package com.money.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Event, Effect>(
    initialState: State,
    eventFlowConfiguration: EventFlowConfiguration = EventFlowConfiguration()
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state get() = _state.asStateFlow()

    open val effects = MutableSharedFlow<Effect>()

    protected val currentState: State
        get() = state.value

    protected open val events = MutableSharedFlow<Event?>(
        replay = eventFlowConfiguration.replay,
        extraBufferCapacity = eventFlowConfiguration.extraBufferCapacity,
        onBufferOverflow = eventFlowConfiguration.onBufferOverflow
    )

    init {
        viewModelScope.launch {
            events.collect {
                it?.let(::handleEvent)
            }
        }
    }

    protected fun updateState(reducer: State.() -> State) {
        val newState = reducer.invoke(state.value)
        _state.update { newState }
        //_state.value = newState
    }

    protected open fun <E> BaseViewModel<*, *, E>.sendEffect(effect: E) {
        effects.run {
            viewModelScope.launch { emit(effect) }
        }
    }

    open fun processEvent(event: Event) {
        viewModelScope.launch {
            events.emit(event)
        }
    }

    protected open fun handleEvent(event: Event) { }

}

data class EventFlowConfiguration(
    val replay: Int = 1,
    val extraBufferCapacity: Int = 0,
    val onBufferOverflow: BufferOverflow = BufferOverflow.DROP_OLDEST
)