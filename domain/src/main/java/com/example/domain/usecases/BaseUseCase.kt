package com.example.domain.usecases

import com.example.domain.exceptions.Failure
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

abstract class BaseUseCase<in Params, out Type> where Type : Any {

    lateinit var coroutineContextUI: CoroutineContext
    abstract suspend fun run(params: Params?): Either<Failure, Type>


    operator fun invoke(params: Params? = null, onResult: (Either<Failure, Type>) -> Unit = {}) {
        val job = async(CommonPool) { run(params) }
        launch(UI) { onResult(job.await()) }
    }
}