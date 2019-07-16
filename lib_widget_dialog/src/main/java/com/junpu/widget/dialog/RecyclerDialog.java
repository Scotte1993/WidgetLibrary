package com.junpu.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.junpu.utils.ToolUtils;
import com.junpu.utils.WindowUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * RecyclerView Dialog
 *
 * @author junpu
 * @date 2019-07-16
 */
public class RecyclerDialog extends Dialog {

    private TextView mTextTitle;
    private TextView mTextMessage;
    private ImageView mImageCancel;
    private RecyclerView mRecyclerView;
    private DefaultRecyclerDialogAdapter mAdapter;
    private OnItemClickListener mOnItemClickListener;

    public RecyclerDialog(Context context) {
        this(context, 0);
    }

    public RecyclerDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected RecyclerDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_recycler_view);
        Window window = getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            window.setWindowAnimations(R.style.dialog_anim_bottom);
        }
        setLandScape(false);
        initUI();
    }

    private void initUI() {
        mTextTitle = findViewById(R.id.textTitle);
        mTextMessage = findViewById(R.id.textMessage);
        mRecyclerView = findViewById(R.id.recyclerView);
        mImageCancel = findViewById(R.id.imageCancel);
        mImageCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    /**
     * 设置横竖屏模式
     */
    public void setLandScape(boolean isLand) {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            window.getDecorView().setPadding(0, 0, 0, 0);
            if (isLand) {
                lp.width = WindowUtils.getScreenHeight(getContext()) - ToolUtils.dp2px(getContext(), 20);
            } else {
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            }
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }
    }

    /**
     * 设置Dialog宽度
     */
    public void setWidth(int width) {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = width;
            window.setAttributes(lp);
        }
    }

    /**
     * 设置Dialog高度
     */
    public void setHeight(int height) {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.height = height;
            window.setAttributes(lp);
        }
    }

    /**
     * 初始化默认RecyclerView Adapter
     */
    public void initDefaultAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new DefaultRecyclerDialogAdapter();
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 自定义RecyclerView Adapter，LayoutManager
     */
    public void setCustomAdapter(RecyclerView.LayoutManager layoutManager, RecyclerView.Adapter adapter) {
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
        if (mAdapter != null) mAdapter.setOnItemClickListener(mOnItemClickListener);
    }


    public void setList(List<String> list) {
        if (mAdapter != null) {
            mAdapter.update(list);
        }
    }

    public void setTitle(String title) {
        mTextTitle.setText(title);
        mTextTitle.setVisibility(TextUtils.isEmpty(title) ? View.INVISIBLE : View.VISIBLE);
    }

    public void setMessage(String message) {
        mTextMessage.setText(message);
        mTextMessage.setVisibility(TextUtils.isEmpty(message) ? View.GONE : View.VISIBLE);
    }

    /**
     * Default RecyclerView Adapter
     */
    private static class DefaultRecyclerDialogAdapter extends RecyclerView.Adapter<DefaultRecyclerDialogViewHolder> {

        private List<String> mData = new ArrayList<>();
        private OnItemClickListener mOnItemClickListener;

        @NonNull
        @Override
        public DefaultRecyclerDialogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.dialog_list_item, viewGroup, false);
            view.setBackgroundResource(R.drawable.default_item_selector);
            return new DefaultRecyclerDialogViewHolder(view, mOnItemClickListener);
        }

        @Override
        public void onBindViewHolder(@NonNull DefaultRecyclerDialogViewHolder recyclerDialogViewHolder, int position) {
            String item = mData.get(position);
            recyclerDialogViewHolder.bind(item);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        void setOnItemClickListener(OnItemClickListener listener) {
            this.mOnItemClickListener = listener;
        }

        void update(List<String> list) {
            if (list == null) return;
            mData.clear();
            mData = list;
            notifyDataSetChanged();
        }
    }

    /**
     * Default ViewHolder
     */
    private static class DefaultRecyclerDialogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextView;
        private OnItemClickListener mOnItemClickListener;

        DefaultRecyclerDialogViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mOnItemClickListener = listener;
            mTextView = itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(this);
        }

        void bind(String text) {
            mTextView.setText(text);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick((RecyclerView) itemView.getParent(), v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(RecyclerView recyclerView, View itemView, int position);
    }

    public static class Builder {
        private Context mContext;
        private String mTitle;
        private String mMessage;
        private int mWidth = -3;
        private int mHeight = -3;
        private Boolean isLandScape = false;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        private OnItemClickListener mOnItemClickListener;
        private List<String> mList;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder setMessage(int resId) {
            this.mMessage = mContext.getResources().getString(resId);
            return this;
        }

        public Builder setMessage(String msg) {
            this.mMessage = msg;
            return this;
        }

        public Builder setWidth(int width) {
            this.mWidth = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.mHeight = height;
            return this;
        }

        public Builder setLandScape(Boolean isLandScape) {
            this.isLandScape = isLandScape;
            return this;
        }

        public Builder setAdapter(RecyclerView.Adapter adapter) {
            this.mAdapter = adapter;
            return this;
        }

        public Builder setLayoutManager(RecyclerView.LayoutManager layoutManager) {
            this.mLayoutManager = layoutManager;
            return this;
        }

        public Builder setOnItemClickListener(OnItemClickListener listener) {
            this.mOnItemClickListener = listener;
            return this;
        }

        public Builder setList(String[] items) {
            mList = Arrays.asList(items);
            return this;
        }

        public Builder setList(List<String> items) {
            mList = items;
            return this;
        }

        public RecyclerDialog create() {
            RecyclerDialog dialog = new RecyclerDialog(mContext);
            dialog.setTitle(mTitle);
            dialog.setMessage(mMessage);
            dialog.setLandScape(isLandScape);
            if (mWidth != -3) dialog.setWidth(mWidth);
            if (mHeight != -3) dialog.setHeight(mHeight);
            if (mAdapter != null) {
                if (mLayoutManager == null) {
                    mLayoutManager = new LinearLayoutManager(mContext);
                }
                dialog.setCustomAdapter(mLayoutManager, mAdapter);
            } else {
                dialog.initDefaultAdapter();
            }
            dialog.setOnItemClickListener(mOnItemClickListener);
            dialog.setList(mList);
            return dialog;
        }

        public RecyclerDialog show() {
            RecyclerDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}
