package com.junpu.widget.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 单选列表
 *
 * @author zhangjunpu
 * 15/8/10
 */
public abstract class SingleChoiceRecyclerAdapter<T> extends BaseRecyclerAdapter<T> implements RecyclerViewHolder.OnRecyclerItemClickListener {

    protected int checkedPosition = -1;

    public void setCheckedPosition(int position) {
        int oldPosition = checkedPosition;
        this.checkedPosition = position;
        if (checkedPosition != oldPosition) {
            notifyItemChanged(oldPosition);
            notifyItemChanged(checkedPosition);
        }
    }

    public void setCheckedPositionNotifyAll(int position) {
        this.checkedPosition = position;
        notifyDataSetChanged();
    }

    public int getCheckedPosition() {
        return checkedPosition;
    }

    public T getCheckedItem() {
        if (checkedPosition >= 0 && checkedPosition < getItemCount()) {
            return getItem(checkedPosition);
        }
        return null;
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        if (checkedPosition != position) {
            setCheckedPosition(position);
            if (listener != null) {
                listener.onItemClick(recyclerView, itemView, position);
            }
        }
    }

}
