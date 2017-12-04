package com.baofeng.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baofeng.utils.ToolUtils;
import com.baofeng.utils.WindowUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 列表对话框
 */
public class ListDialog extends Dialog {

    private ListView mListView;
    private View mActionMenu;
    private TextView mButton1;

    private OnClickListener mOnClickListener;

    public ListDialog(Context context) {
        this(context, 0);
    }

    public ListDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_list);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setWindowAnimations(R.style.window_anim_bottom);

        setLandScape(false);
        initUI();
    }

    private void initUI() {
        mListView = (ListView) findViewById(android.R.id.list);
        mActionMenu = findViewById(R.id.ActionMenu);
        mButton1 = (TextView) findViewById(android.R.id.button1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    public void setLandScape(boolean isLand) {
        Window window = getWindow();
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

    /**
     * 设置数据
     *
     * @param list
     */
    public void setList(String[] list) {
        mListView.setAdapter(addAdapter(list));
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(ListDialog.this, position);
                }
                cancel();
            }
        });
    }

    /**
     * 设置点击事件
     *
     * @param onClickListener
     */
    public void setOnItemClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    /**
     * 设置是否显示底部功能栏
     *
     * @param has
     */
    public void setHasActionMenu(boolean has) {
        mActionMenu.setVisibility(has ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置底部功能栏文字、事件
     *
     * @param text
     * @param listener
     */
    public void setButtonText(String text, View.OnClickListener listener) {
        mButton1.setText(text);
        mButton1.setOnClickListener(listener);
    }

    /**
     * Defines the choice behavior for the List. By default, Lists do not have
     * any choice behavior ({@link ListView#CHOICE_MODE_NONE}). By setting the
     * choiceMode to {@link ListView#CHOICE_MODE_SINGLE}, the List allows up to one item
     * to be in a chosen state. By setting the choiceMode to
     * {@link ListView#CHOICE_MODE_MULTIPLE}, the list allows any number of items to be
     * chosen.
     *
     * @param choiceMode One of {@link ListView#CHOICE_MODE_NONE}, {@link ListView#CHOICE_MODE_SINGLE},
     *                   or {@link ListView#CHOICE_MODE_MULTIPLE}
     */
    public void setChoiceMode(int choiceMode) {
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    /**
     * 设置指定position的选中
     *
     * @param position
     * @param value
     */
    public void setItemChecked(int position, boolean value) {
        mListView.setItemChecked(position, value);
    }

    /**
     * 自定义Adapter
     *
     * @param adapter
     * @return
     */
    public ListDialog setAdapter(ListAdapter adapter) {
        mListView.setAdapter(adapter);
        return this;
    }

    public ListView getListView() {
        return mListView;
    }

    /**
     * 默认的Item效果的Adapter
     *
     * @return
     */
    public ArrayAdapter<?> addAdapter(List<Object> list) {
        final int resourceId;
        switch (mListView.getChoiceMode()) {
//            case ListView.CHOICE_MODE_SINGLE:
//                resourceId = android.R.layout.simple_list_item_single_choice;
//                break;
//            case ListView.CHOICE_MODE_MULTIPLE:
//                resourceId = android.R.layout.simple_list_item_multiple_choice;
//                break;
            default:
                resourceId = R.layout.dialog_list_item;
                break;
        }
        return new ArrayAdapter<Object>(getContext(), resourceId, list) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
                }

                String bean = getItem(position).toString();
                TextView nameText = (TextView) convertView;
                nameText.setText(bean);
                return convertView;
            }
        };
    }

    public ArrayAdapter<?> addAdapter(Object[] objects) {
        return addAdapter(new ArrayList<>(Arrays.asList(objects)));
    }

    public static class Builder {
        private ListDialogParams mParams;

        public Builder(Context context) {
            mParams = new ListDialogParams(context);
        }

        public Context getContext() {
            return mParams.mContext;
        }

        public Builder setList(String[] items) {
            mParams.mList = items;
            return this;
        }

        public Builder setOnItemClickListener(OnClickListener listener) {
            mParams.mOnItemClickListener = listener;
            return this;
        }

        public Builder setIsLandScape(boolean isLand) {
            mParams.isLand = isLand;
            return this;
        }

        public Builder setShowActionMenu(boolean isShow) {
            mParams.isShowActionMenu = isShow;
            return this;
        }

        public Builder setButtonText(String text, View.OnClickListener listener) {
            mParams.mButtonText = text;
            mParams.mOnButtonClickListener = listener;
            return this;
        }

        public ListDialog create() {
            ListDialog dialog = new ListDialog(mParams.mContext);
            dialog.setList(mParams.mList);
            dialog.setOnItemClickListener(mParams.mOnItemClickListener);
            if (!mParams.isShowActionMenu) {
                dialog.setHasActionMenu(mParams.isShowActionMenu);
            }
            if (mParams.isLand) {
                dialog.setLandScape(mParams.isLand);
            }
            if (!TextUtils.isEmpty(mParams.mButtonText)) {
                dialog.setButtonText(mParams.mButtonText, mParams.mOnButtonClickListener);
            }
            return dialog;
        }

        public ListDialog show() {
            ListDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

    public static class ListDialogParams {
        public Context mContext;
        public String[] mList;
        public OnClickListener mOnItemClickListener;
        public boolean isShowActionMenu = true;
        public boolean isLand;
        public String mButtonText;
        public View.OnClickListener mOnButtonClickListener;

        public ListDialogParams(Context context) {
            this.mContext = context;
        }
    }

}