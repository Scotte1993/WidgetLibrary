package com.junpu.widget.sample.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.junpu.utils.ToolUtils
import com.junpu.widget.sample.R
import com.junpu.widget.popupwindow.ListPopup
import com.junpu.widget.popupwindow.CustomPopup
import kotlinx.android.synthetic.main.activity_popupwindow.*
import org.jetbrains.anko.toast

/**
 * PopupWindow
 * @author junpu
 * @date 2019-07-04
 */
class PopupWindowActivity : AppCompatActivity() {

    private var listPopup: ListPopup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popupwindow)

        subject.setOnClickListener {
            initListPopupIfNeed()
            listPopup?.setAnimStyle(CustomPopup.ANIM_GROW_FROM_CENTER)
            listPopup?.setPreferredDirection(CustomPopup.DIRECTION_TOP)
            listPopup?.show(it)
        }
    }

    private fun initListPopupIfNeed() {
        if (listPopup == null) {
            val listItems = arrayOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
            val data = listItems.map { it }

            val adapter = ArrayAdapter(this, R.layout.simple_list_item, data)
            listPopup = ListPopup(this, CustomPopup.DIRECTION_NONE, adapter)
            val width = ToolUtils.dp2px(this, 150)
            val maxHeight = ViewGroup.LayoutParams.WRAP_CONTENT
            listPopup?.create(width, maxHeight) { adapterView, view, position, id ->
                toast("Item $position")
                listPopup?.dismiss()
            }
            listPopup?.setOnDismissListener {
                toast("pop dismiss")
            }
        }
    }

}
