package com.money.common.usecases

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
* Provider for save value [T] and provide it anywhere
*
* The difference from [EventProvider] is in the order in which the event was handled.
* Here the event will be processed by subscribers lazily,
* in [EventProvider] all subscribers will work first and only then the value will be updated
*/
interface ValueProvider<T> : BaseProvider {

    /**
    * Return local value
    * @return null by default
    */
    val current: StateFlow<T?>

    /**
    * Update local value
    */
    fun setNewValue(new: T?)

}


/**
* Abstract provider for save value [T] and provide it anywhere
*/
abstract class AbstractValueProvider<T> : ValueProvider<T> {

    final override val current: MutableStateFlow<T?> = MutableStateFlow(null)

    override fun setNewValue(new: T?) {
        current.value = new
    }

}