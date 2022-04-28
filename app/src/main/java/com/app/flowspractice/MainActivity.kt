package com.app.flowspractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val countingFlow = flow<Int> {
            repeat(10) {
                emit(it)
                delay(1000)
            }
        }

        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(2000)
                countingFlow
                    .buffer()
                    .cancellable()
                    .filter { it % 2 == 0 }
                    .map { it * it }
                    .collect {
                        Log.v("Flows", it.toString())
                    }
            }
        }

    }
}

fun <T> Flow<T>.collectOn(
    coroutineScope: LifecycleCoroutineScope,
    lifecycleOwner: LifecycleOwner,
    collector: FlowCollector<T>
) = coroutineScope.launchWhenStarted {
    lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
        collect(collector)
    }
}