package com.junpu.widget.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewParent;

/**
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-23.
 * <p/>
 * RecyclerView统一使用的ViewHolder
 */
public abstract class RecyclerViewHolder extends RecyclerView.ViewHolder implements OnClickListener, OnLongClickListener {
    /**
     * 负责Item点击事件
     */
    public interface OnRecyclerItemClickListener {
        /**
         * @param recyclerView RecyclerView
         * @param itemView     元素根布局
         * @param position     the adapter position that the given child view corresponds to.
         */
        void onItemClick(RecyclerView recyclerView, View itemView, int position);
    }

    /**
     * 负责Item中子View点击事件
     */
    public interface OnRecyclerItemChildClickListener {
        /**
         * @param recyclerView RecyclerView
         * @param itemView     元素根布局
         * @param clickView    响应点击事件的View
         * @param position     the adapter position that the given child view corresponds to.
         */
        void onItemChildClick(RecyclerView recyclerView, View itemView, View clickView, int position);
    }

    /**
     * 负责Item长按事件
     */
    public interface OnRecyclerItemLongClickListener {
        void onItemLongClick(RecyclerView recyclerView, View itemView, int position);
    }


    private Context context;
    public OnRecyclerItemClickListener listener;
    public OnRecyclerItemChildClickListener childClickListener;
    public OnRecyclerItemLongClickListener longClickListener;

    public RecyclerViewHolder(View view, OnRecyclerItemClickListener listener) {
        this(view, listener, null, null);
    }

    public RecyclerViewHolder(View view, OnRecyclerItemClickListener listener, OnRecyclerItemChildClickListener childListener) {
        this(view, listener, childListener, null);
    }

    public RecyclerViewHolder(View view, OnRecyclerItemClickListener listener, OnRecyclerItemLongClickListener longClickListener) {
        this(view, listener, null, longClickListener);
    }

    public RecyclerViewHolder(View view, OnRecyclerItemClickListener listener, OnRecyclerItemChildClickListener childListener, OnRecyclerItemLongClickListener longClickListener) {
        super(view);
        this.listener = listener;
        this.childClickListener = childListener;
        this.longClickListener = longClickListener;
        this.context = view.getContext();
        if (listener != null)
            view.setOnClickListener(this);
        if (longClickListener != null) {
            view.setOnLongClickListener(this);
        }
        onBindView(view);
    }

    public Context getContext() {
        return context;
    }

    public abstract void onBindView(View view);

    @Override
    public void onClick(View v) {
        if (listener != null && v.getId() == itemView.getId()) {
            listener.onItemClick(findTarget(itemView.getParent()), v, getAdapterPosition());
        } else if (childClickListener != null) {
            childClickListener.onItemChildClick(findTarget(itemView.getParent()), itemView, v, getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (longClickListener != null) {
            longClickListener.onItemLongClick(findTarget(itemView.getParent()), v, getAdapterPosition());
        }
        return false;
    }

    private RecyclerView findTarget(ViewParent parent) {
        if (parent instanceof RecyclerView)
            return (RecyclerView) parent;
        else
            return findTarget(parent);
    }
}