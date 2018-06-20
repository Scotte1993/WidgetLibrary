package com.bftv.sample.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bftv.sample.R
import com.bftv.widget.SlideView
import kotlinx.android.synthetic.main.activity_slide.*

class SlideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide)

        slideView.setListener(object :SlideView.ClickListener{
            override fun clickAddNick() {
                Toast.makeText(this@SlideActivity,"clickAddNick",Toast.LENGTH_SHORT).show()
            }

            override fun clickDelete() {
                Toast.makeText( this@SlideActivity,"clickDelete",Toast.LENGTH_SHORT).show()
            }
        })
    }
}
