package com.app.flowspractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect

class ComparisonActivity : AppCompatActivity(), View.OnClickListener {

    val vm by viewModels<ComparisonViewModel>()

    lateinit var liveDataText: TextView
    lateinit var stateFlowText: TextView
    lateinit var flowText: TextView
    lateinit var sharedFlowText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comparison)

        findViewById<Button>(R.id.livedata_btn).setOnClickListener(this)
        findViewById<Button>(R.id.sharedflow_btn).setOnClickListener(this)
        findViewById<Button>(R.id.stateflow_btn).setOnClickListener(this)
        findViewById<Button>(R.id.flow_btn).setOnClickListener(this)

        liveDataText = findViewById(R.id.live_data_text)
        stateFlowText = findViewById(R.id.stateflow_text)
        flowText = findViewById(R.id.flow_text)
        sharedFlowText = findViewById(R.id.sharedflow_text)

        subscribeToObservables()
    }

    fun subscribeToObservables() {
        vm.liveData.observe(this) {
            liveDataText.text = it
        }

        lifecycleScope.launchWhenStarted {
            vm.stateFlow.collect {
                stateFlowText.text = it
            }
        }

        lifecycleScope.launchWhenStarted {
            vm.sharedFlow.collect {
                sharedFlowText.text = it
                Snackbar.make(
                    findViewById(R.id.root),
                    it,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.livedata_btn -> vm.onLiveDataClick()
            R.id.sharedflow_btn -> vm.onSharedFlowClick()
            R.id.stateflow_btn -> vm.onStateFlowClick()
            R.id.flow_btn -> {
                lifecycleScope.launchWhenStarted {
                    vm.onFlowClick().collect {
                        flowText.text = "Item $it"
                    }
                }
            }
        }
    }
}