/**
 *
 */
package com.baofeng.widget.messageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 负责呈现没有获取到数据时的提示 </br> 通常作为ListView、GridView等呈现网络数据的布局，在其获取数据失败时的提示页面； <li>
 * 方便loading和非loading状态的提示； <li>方便获取数据结果的提示，如获取数据为空，获取数据失败，网络错误等提示。 </br></br>
 *
 * @author liruiyu E-mail:allnet@live.cn
 * @version 创建时间：2014-5-14 类说明
 */
public class MessageView extends FrameLayout {

    /**
     * 浅色模式（默认）
     */
    public static final int COLOR_MODE_LIGHT = 0;

    /**
     * 深色模式
     */
    public static final int COLOR_MODE_DARK = 1;

    public static final int LOADING = 0;
    public static final int EMPTY = 1;
    public static final int ERROR = 2;
    public static final int NONE_NETWORK = 3;
    public static final int SUCCESS = 4;
    public static final int RELOADING = 5;

    public static final String DEFAULT_MSG_NONETWORK = "网络连接异常，请检查您的网络状态";
    public static final String DEFAULT_MSG_ERROR = "数据异常";
    public static final String DEFAULT_MSG_EMPTY = "暂无数据";


    private View mProgress;
    private View mMessageLayout;
    private TextView mMessageText;
    private TextView mRetryText;
    private ImageView mImageView;
    private boolean mRetryEnable = false;
    private int mImageResId;

    private int mColorMode = COLOR_MODE_LIGHT;

    public MessageView(Context context) {
        this(context, null);
    }

    public MessageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.messageview, this, true);
        mProgress = view.findViewById(android.R.id.progress);
        mMessageLayout = view.findViewById(R.id.MessageViewLayout);
        mImageView = (ImageView) view.findViewById(R.id.image);
        mMessageText = (TextView) view.findViewById(R.id.message);
        mRetryText = (TextView) view.findViewById(R.id.retry);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MessageView, defStyle, 0);
        mColorMode = a.getInt(R.styleable.MessageView_colorMode, COLOR_MODE_LIGHT);
        a.recycle();
        setMode();

    }

    private void setMode() {
        switch (mColorMode) {
            case COLOR_MODE_LIGHT:
                mMessageText.setTextColor(getResources().getColor(R.color.message_text_color));
                mRetryText.setTextColor(getResources().getColor(R.color.message_text_color));
                mRetryText.setBackgroundResource(R.drawable.nodata_retry_bg);
                break;
            case COLOR_MODE_DARK:
                mMessageText.setTextColor(Color.parseColor("#d1d2d6"));
                mRetryText.setTextColor(Color.parseColor("#d1d2d6"));
                mRetryText.setBackgroundResource(R.drawable.nodata_retry_bg_black);
                break;
        }
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void setMessageImage(int resId) {
        mImageResId = resId;
        mImageView.setImageResource(resId);
    }

    public void setMessageColor(int color) {
        mMessageText.setTextColor(color);
    }

    /**
     * 设置提示的消息内容 </p>该内容会显示在布局中心，如果当前是loading状态，则显示为loading的消息，
     * 如果非loading状态，则作为提示消息。
     *
     * @param text
     */
    public void setMessage(CharSequence text) {
        mRetryText.setVisibility(mRetryText.getVisibility() != VISIBLE ? VISIBLE : GONE);
        mMessageText.setText(text);
    }

    public void setOnRetryListener(OnClickListener retryListener) {
        mRetryEnable = retryListener != null;
        mRetryText.setOnClickListener(retryListener);
    }

    public void setRetryText(String text) {
        mRetryEnable = TextUtils.isEmpty(text);
        mRetryText.setText(text);
    }

    public void setRetryTextColor(int resId) {
        mRetryText.setTextColor(getResources().getColorStateList(resId));
    }

    public void setRetryBaskground(int resId) {
        mRetryText.setBackgroundResource(resId);
    }

    public void setRetryEnable(boolean enable) {
        mRetryEnable = enable;
        mRetryText.setVisibility(enable ? VISIBLE : GONE);
    }

    /**
     * 启动loading状态
     */
    public void loading() {
        this.setClickable(false);
        mProgress.setVisibility(VISIBLE);
        mMessageLayout.setVisibility(GONE);
    }

    /**
     * 没网络，网络错误，显示重试按钮
     */
    public void noNetwork() {
        noNetwork(DEFAULT_MSG_NONETWORK);
    }

    /**
     * 没网络，网络错误，显示msg
     */
    public void noNetwork(String msg) {
        this.setClickable(true);
        mProgress.setVisibility(View.GONE);
        mMessageLayout.setVisibility(VISIBLE);
        mMessageText.setText(msg);
        mMessageText.setVisibility(VISIBLE);
        mRetryEnable = true;
        mRetryText.setVisibility(VISIBLE);
        mImageView.setImageResource(mColorMode == COLOR_MODE_LIGHT ? R.mipmap.ic_nodata_network : R.mipmap.ic_nodata_network_dark);
    }

    /**
     * 空数据状态，status为200，但数据为空
     */
    public void empty() {
        empty(DEFAULT_MSG_EMPTY);
    }

    public void empty(CharSequence message) {
        this.setClickable(true);
        setVisibility(VISIBLE);
        mProgress.setVisibility(View.GONE);
        mMessageLayout.setVisibility(VISIBLE);
        mMessageText.setText(message);
        mMessageText.setVisibility(VISIBLE);
        mRetryText.setVisibility(mRetryEnable ? VISIBLE : GONE);
        if (mImageResId != 0) {
            mImageView.setImageResource(mImageResId);
        } else {
            mImageView.setImageResource(R.mipmap.ic_nodata_empty);
        }
    }

    public void error() {
        error(DEFAULT_MSG_ERROR);
    }

    /**
     * 数据错误，status不为200，显示错误信息
     */
    public void error(CharSequence msg) {
        this.setClickable(true);
        mProgress.setVisibility(View.GONE);
        mMessageLayout.setVisibility(VISIBLE);
        mMessageText.setText(msg);
        mMessageText.setVisibility(VISIBLE);
        mImageView.setImageResource(R.mipmap.ic_nodata_error);
        mRetryText.setVisibility(mRetryEnable ? VISIBLE : GONE);
    }

    public void setStatus(int flag) {
        switch (flag) {
            case LOADING:
//                setVisibility(VISIBLE);
                loading();
                setRetryEnable(false);
                break;
            case EMPTY:
                setVisibility(VISIBLE);
                empty();
                break;
            case ERROR:
                setVisibility(VISIBLE);
                error();
                break;
            case NONE_NETWORK:
                setVisibility(VISIBLE);
                noNetwork();
                break;
            case SUCCESS:
                setVisibility(GONE);
                mProgress.setVisibility(View.GONE);
                break;
            case RELOADING:
                setVisibility(VISIBLE);
                loading();
                setRetryEnable(false);
                break;
        }
    }

    public void setStatus(int flag, String msg) {
        switch (flag) {
            case LOADING:
//                setVisibility(VISIBLE);
                loading();
                setRetryEnable(false);
                break;
            case EMPTY:
                setVisibility(VISIBLE);
                empty(msg);
                break;
            case ERROR:
                setVisibility(VISIBLE);
                error(msg);
                break;
            case NONE_NETWORK:
                setVisibility(VISIBLE);
                noNetwork(msg);
                break;
            case SUCCESS:
                setVisibility(GONE);
                break;
            case RELOADING:
                setVisibility(VISIBLE);
                loading();
                setRetryEnable(false);
                break;
        }
    }

    public static boolean isError(int code) {
        return code == ERROR || code == NONE_NETWORK;
    }

    public static String getDefaultMsg(int code) {
        switch (code) {
            case NONE_NETWORK:
                return DEFAULT_MSG_NONETWORK;
            case ERROR:
                return DEFAULT_MSG_ERROR;
            case EMPTY:
                return DEFAULT_MSG_EMPTY;
        }
        return null;
    }
}
