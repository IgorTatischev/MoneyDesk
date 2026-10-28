package com.money.common.usecases

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
* Provider for provide action between different places
*
* The difference from [ValueProvider] is in the order in which the event was handled.
* In [ValueProvider], the event will be processed lazily by subscribers,
* here all subscribers will work first and only then the value will be updated
*/
interface EventProvider<T> : BaseProvider {

    val current: SharedFlow<T>

    fun tryEmitNewValue(new: T)
    suspend fun emitNewValue(new: T)
}

/**
* Abstract provider for save value [T] and provide it anywhere.
*/
abstract class AbstractEventProvider<T> : EventProvider<T> {

    private val _current = MutableSharedFlow<T>(1)

    override val current: SharedFlow<T> = _current.asSharedFlow()

    override fun tryEmitNewValue(new: T) {
        _current.tryEmit(new)
    }

    override suspend fun emitNewValue(new: T) {
        _current.emit(new)
    }

}