package com.bftv.sample.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bftv.sample.R
import kotlinx.android.synthetic.main.activity_ad_bar_view.*
import java.util.ArrayList

/**
 *
 * @author chengxiaobo
 * @time 2018/3/7
 */
class AdBarViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_bar_view)

        //设置集合
        val items = ArrayList<String>()
        items.add("这是第1个")
        items.add("这是第2个")
        items.add("这是很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长的第3个")
        items.add("这是第4个")
        items.add("这是第5个")
        items.add("这是第6个")
        items.add("这是第7个")

        mv_bar1.startWithList(items)

        mv_bar2.post {
            mv_bar2.initData(items)
        }
    }
}
