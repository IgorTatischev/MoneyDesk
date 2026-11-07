package com.money.common.usecases

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

/**
* Used for subscribing and making asynchronous requests.
* All inherited classes must call [release] according to the application's behavior logic.
*/
interface SuspendUseCase : CoroutineScope, BaseUseCase {

    fun release() {
        coroutineContext.cancelChildren()
    }

}

fun coroutineContextDefault() = SupervisorJob() + Dispatchers.Default