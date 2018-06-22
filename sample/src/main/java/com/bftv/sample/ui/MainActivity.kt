package com.bftv.sample.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.OnClickListener
import com.bftv.sample.R.layout
import com.bftv.sample.launch
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        initView()
    }

    private fun initView() {
        messageView.setOnClickListener(this)
        listDialog.setOnClickListener(this)
        titleBar.setOnClickListener(this)
        tvAdbarview.setOnClickListener(this)
        tvFloatView.setOnClickListener(this)
        tvSlide.setOnClickListener(this)
        tvDialPlate.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            messageView -> launch(MessageViewActivity::class.java)
            listDialog -> launch(ListDialogActivity::class.java)
            titleBar -> launch(TitleBarActivity::class.java)
            tvAdbarview -> launch(AdBarViewActivity::class.java)
            tvFloatView -> launch(FloatViewActivity::class.java)
            tvSlide -> launch(SlideActivity::class.java)
            tvDialPlate->launch(DialPlateActivity::class.java)
        }
    }
}
