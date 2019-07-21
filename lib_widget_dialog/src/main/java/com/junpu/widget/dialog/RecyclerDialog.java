package com.junpu.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ProgressBar;
import android.widget.TextView;

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

    public static final int STATUS_LOADING = 0;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_ERROR = 2;
    public static final String DEFAULT_ERROR = "数据错误";

    private TextView mTextTitle;
    private TextView mTextSubTitle;
    private TextView mTextMessage;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private TextView mTextErrorMessage;
    private DefaultRecyclerDialogAdapter mAdapter;
    private OnItemClickListener mOnItemClickListener;

    public RecyclerDialog(Context context) {
        this(context, R.style.Theme_Dialog_Recycler);
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
        setContentView(R.layout.dialog_recycler_view);
        Window window = getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(params);
        }

        initUI();
    }

    private void initUI() {
        findViewById(R.id.cancelView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        mTextTitle = findViewById(R.id.textTitle);
        mTextSubTitle = findViewById(R.id.textSubTitle);
        mTextMessage = findViewById(R.id.textMessage);
        mRecyclerView = findViewById(R.id.recyclerView);
        mProgressBar = findViewById(R.id.progress);
        mTextErrorMessage = findViewById(R.id.errorMessage);
        loading();
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

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
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

    /**
     * 设置ItemDecoration
     */
    public void addItemDecoration(RecyclerView.ItemDecoration decoration) {
        mRecyclerView.addItemDecoration(decoration);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
        if (mAdapter != null) mAdapter.setOnItemClickListener(mOnItemClickListener);
    }


    public void setList(List<String> list) {
        if (mAdapter != null) {
            mAdapter.update(list);
            success();
        }
    }

    public void setTitle(String title) {
        mTextTitle.setText(title);
        mTextTitle.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
    }

    public void setSubTitle(String subTitle) {
        mTextSubTitle.setText(subTitle);
        mTextSubTitle.setVisibility(TextUtils.isEmpty(subTitle) ? View.GONE : View.VISIBLE);
    }

    public void setMessage(String message) {
        mTextMessage.setText(message);
        mTextMessage.setVisibility(TextUtils.isEmpty(message) ? View.GONE : View.VISIBLE);
    }

    /**
     * loading状态
     */
    public void loading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mTextErrorMessage.setVisibility(View.GONE);
    }

    /**
     * 成功状态
     */
    public void success() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mTextErrorMessage.setVisibility(View.GONE);
    }

    /**
     * 错误状态
     */
    public void error(String errorMsg) {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mTextErrorMessage.setVisibility(View.VISIBLE);
        mTextErrorMessage.setText(errorMsg);
    }

    /**
     * 设置状态 STATUS_LOADING, STATUS_SUCCESS, STATUS_ERROR
     */
    public void setStatus(int status) {
        setStatus(status, DEFAULT_ERROR);
    }

    public void setStatus(int status, String errorMsg) {
        switch (status) {
            case STATUS_LOADING:
                loading();
                break;
            case STATUS_SUCCESS:
                success();
                break;
            case STATUS_ERROR:
                error(errorMsg);
                break;
        }
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
            view.setBackgroundResource(R.drawable.dialog_item_selector);
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
        private String mSubTitle;
        private String mMessage;
        private int mWidth = -3;
        private int mHeight = -3;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        private RecyclerView.ItemDecoration mItemDecoration;
        private OnItemClickListener mOnItemClickListener;
        private List<String> mList;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder setSubTitle(String subTitle) {
            this.mSubTitle = subTitle;
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

        public Builder setAdapter(RecyclerView.Adapter adapter) {
            this.mAdapter = adapter;
            return this;
        }

        public Builder setLayoutManager(RecyclerView.LayoutManager layoutManager) {
            this.mLayoutManager = layoutManager;
            return this;
        }

        public Builder setItemDecoration(RecyclerView.ItemDecoration decoration) {
            this.mItemDecoration = decoration;
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
            dialog.setSubTitle(mSubTitle);
            dialog.setMessage(mMessage);
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
            dialog.addItemDecoration(mItemDecoration);
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
