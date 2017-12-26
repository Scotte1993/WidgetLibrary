package com.bftv.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.OnClickListener
import com.baofeng.widget.messageview.MessageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        loading.setOnClickListener(this)
        empty.setOnClickListener(this)
        error.setOnClickListener(this)
        failure.setOnClickListener(this)
        success.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            loading -> {
                messageView.setStatus(MessageView.LOADING)
            }
            empty -> {
                messageView.setStatus(MessageView.EMPTY, "", "发起聊天或视频通话后，显示在这里")
            }
            error -> {
                messageView.setStatus(MessageView.ERROR, "", "发起聊天或视频通话后，显示在这里")
            }
            failure -> {
                messageView.setStatus(MessageView.NONE_NETWORK)
            }
            success -> {
                messageView.setStatus(MessageView.SUCCESS)
            }
        }
    }
}
