/*
 * Copyright (C) 2012 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.junpu.widget.indicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Draws a line for each page. The current page line is colored differently than
 * the unselected page lines.
 */
public class UnderlinePageIndicator extends View implements PageIndicator {
    private static final int INVALID_POINTER = -1;
    private static final int FADE_FRAME_MS = 30;

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private boolean mFades;
    private int mFadeDelay;
    private int mFadeLength;
    private int mFadeBy;

    private ViewPager mViewPager;
    //	private ViewPager.OnPageChangeListener mListener;
    private int mScrollState;
    private int mCurrentPage;
    private float mPositionOffset;

    private int mTouchSlop;
    private float mLastMotionX = -1;
    private int mActivePointerId = INVALID_POINTER;
    private boolean mIsDragging;

    private boolean isLine = false;
    private boolean isRoundLine = false;

    private int mSpace;

    private final Runnable mFadeRunnable = new Runnable() {
        @Override
        public void run() {
            if (!mFades)
                return;

            final int alpha = Math.max(mPaint.getAlpha() - mFadeBy, 0);
            mPaint.setAlpha(alpha);
            invalidate();
            if (alpha > 0) {
                postDelayed(this, FADE_FRAME_MS);
            }
        }
    };

    public UnderlinePageIndicator(Context context) {
        this(context, null);
    }

    public UnderlinePageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnderlinePageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode())
            return;

        final Resources res = getResources();

        // Load defaults from resources
        final int defaultFadeDelay = res.getInteger(R.integer.default_underline_indicator_fade_delay);
        final int defaultFadeLength = res.getInteger(R.integer.default_underline_indicator_fade_length);
        final int defaultSelectedColor = res.getColor(R.color.default_underline_indicator_selected_color);

        // Retrieve styles attributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UnderlinePageIndicator, defStyle, 0);

        setFades(a.getBoolean(R.styleable.UnderlinePageIndicator_fades, true));
        setSelectedColor(a.getColor(R.styleable.UnderlinePageIndicator_selectedColor, defaultSelectedColor));
        setFadeDelay(a.getInteger(R.styleable.UnderlinePageIndicator_fadeDelay, defaultFadeDelay));
        setFadeLength(a.getInteger(R.styleable.UnderlinePageIndicator_fadeLength, defaultFadeLength));
        setLine(a.getBoolean(R.styleable.UnderlinePageIndicator_isline, false));

        Drawable background = a.getDrawable(R.styleable.UnderlinePageIndicator_android_background);
        if (background != null) {
            setBackgroundDrawable(background);
        }

        a.recycle();

        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);

    }

    public boolean getFades() {
        return mFades;
    }

    public void setFades(boolean fades) {
        if (fades != mFades) {
            mFades = fades;
            if (fades) {
                post(mFadeRunnable);
            } else {
                removeCallbacks(mFadeRunnable);
                mPaint.setAlpha(0xFF);
                invalidate();
            }
        }
    }

    public int getFadeDelay() {
        return mFadeDelay;
    }

    public void setFadeDelay(int fadeDelay) {
        mFadeDelay = fadeDelay;
    }

    public int getFadeLength() {
        return mFadeLength;
    }

    public void setFadeLength(int fadeLength) {
        mFadeLength = fadeLength;
        mFadeBy = 0xFF / (mFadeLength / FADE_FRAME_MS);
    }

    public int getSelectedColor() {
        return mPaint.getColor();
    }

    public void setSelectedColor(int selectedColor) {
        mPaint.setColor(selectedColor);
        invalidate();
    }

    public boolean isLine() {
        return isLine;
    }

    public void setLine(boolean isLine) {
        this.isLine = isLine;
    }

    public boolean isRoundLine() {
        return isRoundLine;
    }

    public void setRoundLine(boolean isRoundLine) {
        this.isRoundLine = isRoundLine;
    }

    public int getSpace() {
        return mSpace;
    }

    public void setSpace(int space) {
        this.mSpace = space;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mViewPager == null) {
            return;
        }
        final int count = mViewPager.getAdapter().getCount();
        if (count == 0) {
            return;
        }

        if (mCurrentPage >= count) {
            setCurrentItem(count - 1);
            return;
        }

        final int spacing = mSpace * 2;
        final float pageWidth = getWidth() / count - spacing;
        final float left = (pageWidth + spacing) * mCurrentPage + mSpace + (mPositionOffset * (pageWidth + spacing));
        final float right = left + pageWidth;
        final float top = getPaddingTop();
        final float bottom = getHeight() - getPaddingBottom();

        if (isLine) {
            if (isRoundLine) {
                RectF rectF = new RectF(left, top, right, bottom);
                int radius = (int) getResources().getDimension(R.dimen.dimen_1_25);
                canvas.drawRoundRect(rectF, radius, radius, mPaint);
            } else {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        } else {
//        Rect rect = new Rect((int)left, (int)top, (int)right, (int)bottom);
//        RectF rectF = new RectF(rect);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.indicator_item_bg);
//        canvas.drawBitmap(bitmap, rect, rectF, mPaint);

            RectF rectF = new RectF(left, top, right, bottom);
            int radius = (int) getResources().getDimension(R.dimen.dimen_13);
            canvas.drawRoundRect(rectF, radius, radius, mPaint);
        }

//		Log.anchor("left:" + left + ", top:" + top + ", right:" + right + ", bottom:" + bottom);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (super.onTouchEvent(ev)) {
            return true;
        }
        if ((mViewPager == null) || (mViewPager.getAdapter().getCount() == 0)) {
            return false;
        }

        final int action = ev.getAction() & MotionEventCompat.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mLastMotionX = ev.getX();
                break;

            case MotionEvent.ACTION_MOVE: {
                final int activePointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                final float x = MotionEventCompat.getX(ev, activePointerIndex);
                final float deltaX = x - mLastMotionX;

                if (!mIsDragging) {
                    if (Math.abs(deltaX) > mTouchSlop) {
                        mIsDragging = true;
                    }
                }

                if (mIsDragging) {
                    mLastMotionX = x;
                    if (mViewPager.isFakeDragging() || mViewPager.beginFakeDrag()) {
                        mViewPager.fakeDragBy(deltaX);
                    }
                }

                break;
            }

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (!mIsDragging) {
                    final int count = mViewPager.getAdapter().getCount();
                    final int width = getWidth();
                    final float halfWidth = width / 2f;
                    final float sixthWidth = width / 6f;

                    if ((mCurrentPage > 0) && (ev.getX() < halfWidth - sixthWidth)) {
                        if (action != MotionEvent.ACTION_CANCEL) {
                            mViewPager.setCurrentItem(mCurrentPage - 1);
                        }
                        return true;
                    } else if ((mCurrentPage < count - 1) && (ev.getX() > halfWidth + sixthWidth)) {
                        if (action != MotionEvent.ACTION_CANCEL) {
                            mViewPager.setCurrentItem(mCurrentPage + 1);
                        }
                        return true;
                    }
                }

                mIsDragging = false;
                mActivePointerId = INVALID_POINTER;
                if (mViewPager.isFakeDragging())
                    mViewPager.endFakeDrag();
                break;

            case MotionEventCompat.ACTION_POINTER_DOWN: {
                final int index = MotionEventCompat.getActionIndex(ev);
                mLastMotionX = MotionEventCompat.getX(ev, index);
                mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                break;
            }

            case MotionEventCompat.ACTION_POINTER_UP:
                final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
                if (pointerId == mActivePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
                }
                mLastMotionX = MotionEventCompat.getX(ev, MotionEventCompat.findPointerIndex(ev, mActivePointerId));
                break;
        }

        return true;
    }

    @Override
    public void setViewPager(ViewPager viewPager) {
        if (mViewPager == viewPager) {
            return;
        }
        if (mViewPager != null) {
            // Clear us from the old pager.
            mViewPager.removeOnPageChangeListener(this);
        }
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        invalidate();
        post(new Runnable() {
            @Override
            public void run() {
                if (mFades) {
                    post(mFadeRunnable);
                }
            }
        });
    }

    @Override
    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mViewPager.setCurrentItem(item);
        mCurrentPage = item;
        invalidate();
    }

    @Override
    public void notifyDataSetChanged() {
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mScrollState = state;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mCurrentPage = position;
        mPositionOffset = positionOffset;
        if (mFades) {
            if (positionOffsetPixels > 0) {
                removeCallbacks(mFadeRunnable);
                mPaint.setAlpha(0xFF);
            } else if (mScrollState != ViewPager.SCROLL_STATE_DRAGGING) {
                postDelayed(mFadeRunnable, mFadeDelay);
            }
        }
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
            mCurrentPage = position;
            mPositionOffset = 0;
            invalidate();
            mFadeRunnable.run();
        }
    }

//	@Override
//	public void onRestoreInstanceState(Parcelable state) {
//		SavedState savedState = (SavedState) state;
//		super.onRestoreInstanceState(savedState.getSuperState());
//		mCurrentPage = savedState.currentPage;
//		requestLayout();
//	}
//
//	@Override
//	public Parcelable onSaveInstanceState() {
//		Parcelable superState = super.onSaveInstanceState();
//		SavedState savedState = new SavedState(superState);
//		savedState.currentPage = mCurrentPage;
//		return savedState;
//	}

    static class SavedState extends BaseSavedState {
        int currentPage;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPage = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPage);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}