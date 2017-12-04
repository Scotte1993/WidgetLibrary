package com.baofeng.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.baofeng.utils.support.WeakHandler;

/**
 * Created by lichnexing on 2016/2/24
 */

public class DialogEdit extends Dialog implements View.OnClickListener {


    private View.OnClickListener mOnClickListener;

    public DialogEdit(Context context) {
        this(context, 0);
    }

    public DialogEdit(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    public DialogEdit(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }


    private void init() {
        setContentView(R.layout.joker_dialog_edit);
        setCanceledOnTouchOutside(false);

        final Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        findViewById(R.id.Cancel).setOnClickListener(this);
        findViewById(R.id.Ok).setOnClickListener(this);
    }

    public void setText(String content) {
        ((EditText) findViewById(R.id.MessageText)).setText(content);
        findViewById(R.id.MessageText).requestFocus();

        new WeakHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 200);
    }

    public void setOnClickListener(View.OnClickListener l) {
        mOnClickListener = l;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.Cancel) {
            dismiss();

        } else if (i == R.id.Ok) {
            dismiss();

        }
        if (mOnClickListener != null) {
            mOnClickListener.onClick(view);
        }
    }
}
