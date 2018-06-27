package com.bftv.sample.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bftv.sample.R
import com.bftv.widget.phonedial.PhoneDialView.OnDialNumberListener
import kotlinx.android.synthetic.main.activity_dial_plate.*

class DialPlateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dial_plate)

//        dialPlate.dialClick = object : DialPlate.OnDialClickListener {
//            override fun click(dialNumber: String) {
//                Toast.makeText(this@DialPlateActivity, dialNumber, Toast.LENGTH_SHORT).show()
//            }
//        }

        dialPlate?.setOnDialNumberListener(object : OnDialNumberListener {
            override fun onDialNumber(number: String?) {
                println("number = $number")
            }
        })
    }
}
