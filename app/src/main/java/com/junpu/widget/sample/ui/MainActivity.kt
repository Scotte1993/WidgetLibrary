package com.junpu.widget.sample.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.OnClickListener
import com.junpu.widget.sample.R
import com.junpu.widget.sample.launch
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        messageView.setOnClickListener(this)
        dialog.setOnClickListener(this)
        titleBar.setOnClickListener(this)
        tvAdbarview.setOnClickListener(this)
        tvFloatView.setOnClickListener(this)
        tvSlide.setOnClickListener(this)
        tvDialPlate.setOnClickListener(this)
        tvDialPlateForTv.setOnClickListener(this)
        tvPopupWindow.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            messageView -> launch(MessageViewActivity::class.java)
            dialog -> launch(DialogActivity::class.java)
            titleBar -> launch(TitleBarActivity::class.java)
            tvAdbarview -> launch(AdBarViewActivity::class.java)
            tvFloatView -> launch(FloatViewActivity::class.java)
//            tvSlide -> launch(SlideActivity::class.java)
            tvDialPlate -> launch(PhoneDialActivity::class.java)
            tvDialPlateForTv -> launch(TVDialPlateActivity::class.java)
            tvPopupWindow -> launch(PopupWindowActivity::class.java)
        }
    }
}
