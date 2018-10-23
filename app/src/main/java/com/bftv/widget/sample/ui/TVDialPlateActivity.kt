package com.bftv.widget.sample.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.bftv.widget.dial.DialPlate
import com.bftv.widget.sample.R
import kotlinx.android.synthetic.main.activity_tvdial_plate.*

class TVDialPlateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvdial_plate)

        dialPlate.dialClick = object : DialPlate.OnDialClickListener {
            override fun click(dialNumber: String) {
                Toast.makeText(this@TVDialPlateActivity, dialNumber, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
