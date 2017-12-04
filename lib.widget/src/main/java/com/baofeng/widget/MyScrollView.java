package com.baofeng.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 监听滑动的ScrollView
 * @author zhangjunpu
 * @date 2017/3/14
 */

public class MyScrollView extends ScrollView {

    private OnScrollListener mOnScrollListener = null;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScrollListener(OnScrollListener scrollViewListener) {
        this.mOnScrollListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public interface OnScrollListener {
        void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy);
    }
}
