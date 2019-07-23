package com.junpu.widget.adapter;


import java.util.List;

/**
 * @author zhangjunpu
 * 15/7/27
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerViewAdapter<T> {

    public RecyclerViewHolder.OnRecyclerItemClickListener listener;
    public RecyclerViewHolder.OnRecyclerItemChildClickListener childListener;
    public RecyclerViewHolder.OnRecyclerItemLongClickListener longClickListener;

    public void setOnRecyclerItemClickListener(RecyclerViewHolder.OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnRecyclerItemChildClickListener(RecyclerViewHolder.OnRecyclerItemChildClickListener listener) {
        this.childListener = listener;
    }

    public void setOnRecyclerItemLongClickListener(RecyclerViewHolder.OnRecyclerItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public int getCount() {
        return super.getItemCount();
    }

    public void add(T t) {
        getCollection().add(t);
        notifyDataSetChanged();
    }

    public void add(List<T> list) {
        getCollection().addAll(list);
        notifyDataSetChanged();
    }

    public void add(int position, T t) {
        if (position >= 0 && position <= getCollection().size()) {
            getCollection().add(position, t);
        }
        notifyDataSetChanged();
    }

    public void addFirst(T t) {
        getCollection().add(0, t);
        notifyDataSetChanged();
    }

    public void addFirst(List<T> list) {
        getCollection().addAll(0, list);
        notifyDataSetChanged();
    }

    public void update(List<T> array) {
        getCollection().update(array);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        getCollection().remove(position);
        notifyDataSetChanged();
    }

    public void remove(T t) {
        getCollection().remove(t);
        notifyDataSetChanged();
    }

    public void replace(int position, T t) {
        if (position >= 0 && position < getCollection().size()) {
            getCollection().set(position, t);
        }
        notifyDataSetChanged();
    }

    public boolean contains(T t) {
        if (!isEmpty()) {
            return getCollection().contains(t);
        }
        return false;
    }

    public void clear() {
        getCollection().clear();
        notifyDataSetChanged();
    }
}
