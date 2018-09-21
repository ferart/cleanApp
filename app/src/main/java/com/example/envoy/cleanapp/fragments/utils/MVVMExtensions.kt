package com.example.envoy.cleanapp.fragments.utils

import android.arch.lifecycle.*
import android.support.v4.app.Fragment
import com.example.domain.exceptions.Failure

inline fun <reified T : ViewModel> Fragment.viewModel(body: T.() -> Unit): T {
    val vm = ViewModelProviders.of(this)[T::class.java]
    vm.body()
    return vm
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
        liveData.observe(this, Observer(body))

fun <L : LiveData<Failure>> LifecycleOwner.failure(liveData: L, body: (Failure?) -> Unit) =
        liveData.observe(this, Observer(body))