package com.app.flowspractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar

class ComparisonActivity : AppCompatActivity(), View.OnClickListener {

    val vm by viewModels<ComparisonViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comparison)

        findViewById<Button>(R.id.livedata_btn).setOnClickListener(this)
        findViewById<Button>(R.id.sharedflow_btn).setOnClickListener(this)
        findViewById<Button>(R.id.stateflow_btn).setOnClickListener(this)
        findViewById<Button>(R.id.flow_btn).setOnClickListener(this)

        subscribeToObservables()
    }

    fun subscribeToObservables() {

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.livedata_btn -> vm.onLiveDataClick()
            R.id.sharedflow_btn -> vm.onSharedFlowClick()
            R.id.stateflow_btn -> vm.onStateFlowClick()
            R.id.flow_btn -> vm.onFlowClick()
        }
    }
}