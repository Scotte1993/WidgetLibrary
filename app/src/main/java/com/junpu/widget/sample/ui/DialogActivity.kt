package com.junpu.widget.sample.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.junpu.widget.dialog.EditDialog.OnSubmitListener
import com.junpu.widget.dialog.RecyclerDialog
import com.junpu.widget.sample.R
import com.junpu.widget.sample.utils.DialogHelper
import kotlinx.android.synthetic.main.activity_dialog.*
import kotlinx.android.synthetic.main.dialog_recycler_item.view.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.toast
import java.util.*

/**
 *
 * @author Junpu
 * @time 2017/12/27 20:02
 */
class DialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        initView()
    }

    private fun initView() {
        btnSingle.setOnClickListener {
            DialogHelper.showListDialog(this, arrayOf("1"))
        }
        btnDouble.setOnClickListener {
            DialogHelper.showListDialog(this, arrayOf("1", "2"))
        }
        btnEdit.setOnClickListener {
            DialogHelper.showEditDialog(this, "备注", "", "填写备注", object : OnSubmitListener {
                override fun onSubmit(dialog: com.junpu.widget.dialog.EditDialog?, id: Int, message: String?) {
                    println("message = $message")
                }
            })
        }


        val list = arrayListOf<String>()
        for (i in 0..15) {
            list.add("item $i")
        }

        val layoutManager = GridLayoutManager(this, 3)
        val adapter = MyAdapter()
        adapter.update(list)
        adapter.setOnItemClickListener(RecyclerDialog.OnItemClickListener { _, _, position -> toast("click item $position") })
        var dialog: RecyclerDialog? = null
        btnRecyclerDialog.setOnClickListener {
            if (dialog == null) {
                dialog = RecyclerDialog.Builder(this)
//                    .setTitle("Title")
                        .setWidth(dip(350))
//                    .setHeight(dip(500))
                        .setLayoutManager(layoutManager)
                        .setAdapter(adapter)
                        .setMessage("Message")
                        .setOnItemClickListener { _, _, position -> toast("$position") }
                        .setList(list)
                        .show()
            } else {
                dialog?.setList(list)
                dialog?.show()
            }
        }
    }

}

class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

    private var data: MutableList<String> = ArrayList()
    private var onItemClickListener: RecyclerDialog.OnItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MyViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.dialog_recycler_item, viewGroup, false)
        return MyViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        val item = data[position]
        viewHolder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    internal fun setOnItemClickListener(listener: RecyclerDialog.OnItemClickListener) {
        this.onItemClickListener = listener
    }

    internal fun update(list: MutableList<String>?) {
        if (list == null) return
        data.clear()
        data = list
        notifyDataSetChanged()
    }
}

class MyViewHolder(itemView: View, private val onItemClickListener: RecyclerDialog.OnItemClickListener?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    init {
        itemView.setOnClickListener(this)
    }

    internal fun bind(text: String) {
        itemView.textMessage.text = text
    }

    override fun onClick(v: View) {
        onItemClickListener?.onItemClick(itemView.parent as RecyclerView, v, adapterPosition)
    }
}
