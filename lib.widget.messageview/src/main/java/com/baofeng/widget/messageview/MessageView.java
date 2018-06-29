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

    public static final String DEFAULT_MSG_EMPTY = "暂无数据";
    public static final String DEFAULT_MSG_ERROR = "数据异常";
    public static final String DEFAULT_MSG_NONETWORK = "网络异常";
    public static final String DEFAULT_SUBMSG_NONETWORK = "网络连接异常，请检查您的网络状态";
    public static final String DEFAULT_ERROR_RETRY_TEXT = "重试";
    public static final String DEFAULT_NONETWORK_RETRY_TEXT = "重试";


    private View mProgress;
    private View mMessageLayout;
    private TextView mMessageText;
    private TextView mSubMessageText;
    private TextView mRetryText;
    private ImageView mImageView;
    private boolean mRetryEnable = false;
    private int mImageResId;
    private int status;

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
        mSubMessageText = (TextView) view.findViewById(R.id.subMessage);
        mRetryText = (TextView) view.findViewById(R.id.retry);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MessageView, defStyle, 0);
        mColorMode = a.getInt(R.styleable.MessageView_colorMode, COLOR_MODE_LIGHT);
        a.recycle();
        setMode();
    }

    private void setMode() {
        switch (mColorMode) {
            case COLOR_MODE_LIGHT:
                mMessageText.setTextColor(getResources().getColor(R.color.message_text));
                mSubMessageText.setTextColor(getResources().getColor(R.color.message_text_sub));
                mRetryText.setTextColor(Color.WHITE);
                mRetryText.setBackgroundResource(R.drawable.nodata_retry_bg);
                break;
            case COLOR_MODE_DARK:
                mMessageText.setTextColor(getResources().getColor(R.color.message_text_dark));
                mSubMessageText.setTextColor(getResources().getColor(R.color.message_text_sub_dark));
                mRetryText.setTextColor(getResources().getColor(R.color.message_text_sub_dark));
                mRetryText.setBackgroundResource(R.drawable.nodata_retry_bg_black);
                break;
        }
    }

    public void setMessageImage(int resId) {
        mImageResId = resId;
        mImageView.setImageResource(resId);
    }

    public void setMessageImageEnable(boolean enable) {
        mImageView.setVisibility(enable ? VISIBLE : GONE);
    }

    public void setMessageColor(int color) {
        mMessageText.setTextColor(color);
    }

    public void setSubMessageColor(int color) {
        mSubMessageText.setTextColor(color);
    }

    public void setMessage(CharSequence msg) {
        mMessageText.setText(msg);
    }

    public void setMessage(CharSequence msg, CharSequence defaultMsg) {
        mMessageText.setText(TextUtils.isEmpty(msg) ? defaultMsg : msg);
    }

    public void setSubMessage(CharSequence subMsg) {
        setSubMessage(subMsg, null);
    }

    public void setSubMessage(CharSequence subMsg, CharSequence defaultSubMsg) {
        CharSequence text = TextUtils.isEmpty(subMsg) ? defaultSubMsg : subMsg;
        mSubMessageText.setVisibility(TextUtils.isEmpty(text) ? GONE : VISIBLE);
        mSubMessageText.setText(text);
    }

    public void setRetryText(String text) {
        mRetryText.setText(text);
    }

    public void setOnRetryListener(OnClickListener retryListener) {
        mRetryText.setOnClickListener(retryListener);
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
        noNetwork(null);
    }

    public void noNetwork(CharSequence msg) {
        noNetwork(msg, null);
    }

    public void noNetwork(CharSequence msg, CharSequence subMsg) {
        this.setClickable(true);
        mProgress.setVisibility(View.GONE);
        mMessageLayout.setVisibility(VISIBLE);
        setMessage(msg, DEFAULT_MSG_NONETWORK);
        setSubMessage(subMsg, DEFAULT_SUBMSG_NONETWORK);
        setRetryText(DEFAULT_NONETWORK_RETRY_TEXT);
        mRetryEnable = true;
        mRetryText.setVisibility(VISIBLE);
        mImageView.setImageResource(R.mipmap.ic_nodata_network);
    }

    /**
     * 空数据状态，status为200，但数据为空
     */
    public void empty() {
        empty(null);
    }

    public void empty(CharSequence msg) {
        empty(msg, null);
    }

    public void empty(CharSequence msg, CharSequence subMsg) {
        this.setClickable(true);
        setVisibility(VISIBLE);
        mProgress.setVisibility(View.GONE);
        mMessageLayout.setVisibility(VISIBLE);
        setMessage(msg, DEFAULT_MSG_EMPTY);
        setSubMessage(subMsg);
        mRetryEnable = false;
        mRetryText.setVisibility(mRetryEnable ? VISIBLE : GONE);
        if (mImageResId != 0) {
            mImageView.setImageResource(mImageResId);
        } else {
            mImageView.setImageResource(R.mipmap.ic_nodata_empty);
        }
    }

    /**
     * 错误状态
     */
    public void error() {
        error(null);
    }

    public void error(CharSequence msg) {
        error(msg, null);
    }

    public void error(CharSequence msg, CharSequence subMsg) {
        this.setClickable(true);
        mProgress.setVisibility(View.GONE);
        mMessageLayout.setVisibility(VISIBLE);
        setMessage(msg, DEFAULT_MSG_ERROR);
        setSubMessage(subMsg);
        mRetryEnable = true;
        setRetryText(DEFAULT_ERROR_RETRY_TEXT);
        mImageView.setImageResource(R.mipmap.ic_nodata_error);
        mRetryText.setVisibility(mRetryEnable ? VISIBLE : GONE);
    }

    public void setStatus(int flag) {
        setStatus(flag, null);
    }

    public void setStatus(int flag, String msg) {
        setStatus(flag, msg, null);
    }

    public void setStatus(int flag, String msg, String subMsg) {
        status = flag;
        switch (flag) {
            case LOADING:
                loading();
                setRetryEnable(false);
                break;
            case EMPTY:
                setVisibility(VISIBLE);
                empty(msg, subMsg);
                break;
            case ERROR:
                setVisibility(VISIBLE);
                error(msg, subMsg);
                break;
            case NONE_NETWORK:
                setVisibility(VISIBLE);
                noNetwork(msg, subMsg);
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
                return DEFAULT_SUBMSG_NONETWORK;
            case ERROR:
                return DEFAULT_MSG_ERROR;
            case EMPTY:
                return DEFAULT_MSG_EMPTY;
        }
        return null;
    }

    /**
     * 获取当前状态
     */
    public int getStatus() {
        return status;
    }
}
