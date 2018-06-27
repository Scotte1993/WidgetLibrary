package com.bftv.sample.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bftv.sample.R
import kotlinx.android.synthetic.main.activity_float_view.*

class FloatViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_float_view)

        floatView.setOnClickListener {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
        }

        ChildFloatView.setOnClickListener {
            Toast.makeText(this, "ChildClick", Toast.LENGTH_SHORT).show()
        }
    }
}
