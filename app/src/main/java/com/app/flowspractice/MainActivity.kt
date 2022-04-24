package com.app.flowspractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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

        findViewById<Button>(R.id.button).setOnClickListener {
            lifecycleScope.launch {
                countingFlow.collect {
                    Log.v("Flows", "Received after click $it")
                }
            }
        }


        lifecycleScope.launchWhenStarted {
            countingFlow
                .buffer()
                .filter { it % 2 == 0 }
                .collect {
                    delay(2000)
                    Log.v("Flows", "Received $it")
                }
        }

        countingFlow.collectOn(lifecycleScope, this) {

        }

    }
}

fun <T> Flow<T>.collectOn(
    scope: LifecycleCoroutineScope,
    lifecycle: LifecycleOwner,
    collector: FlowCollector<T>
) = scope.launchWhenStarted {
    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
        collect(collector)
    }
}