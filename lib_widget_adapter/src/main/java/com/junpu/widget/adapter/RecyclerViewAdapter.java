package com.junpu.widget.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 参见 Samples.java
 * </pre>
 * <p>
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-23.
 */
public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Collection<T> array = new Collection<>();

    public Collection<T> getCollection() {
        return array;
    }

    public T getItem(int position) {
        if (array == null || position < 0 || position >= getItemCount()) return null;
        return array.get(position);
    }

    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public static class Collection<T> extends ArrayList<T> {
        public void update(List<T> array) {
            this.clear();
            this.addAll(array);
        }
    }

}