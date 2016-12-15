package com.rongxianren.lsn_24_animationframe_customscrollview;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by wty on 2016/12/15.
 */

public class ScrollViewItemWrapper extends FrameLayout implements ScrollChangeListenerInter {
    private boolean mIsAlpha = false;
    private boolean mIsScale = false;
    private int mTranslation = -1;
    private int mFromColor = -1;
    private int mToColor = -1;

    private final int FROM_LEFT = 0x01;
    private final int FROM_TOP = 0x02;
    private final int FROM_RIGHT = 0x04;
    private final int FROM_BOTTOM = 0x08;

    private ArgbEvaluator mArgEvaluator;

    public ScrollViewItemWrapper(Context context) {
        this(context, null);
        mArgEvaluator = new ArgbEvaluator();
    }

    public ScrollViewItemWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setmIsAlpha(boolean mIsAlpha) {
        this.mIsAlpha = mIsAlpha;
    }

    public void setmIsScale(boolean mIsScale) {
        this.mIsScale = mIsScale;
    }

    public void setmTranslation(int mTranslation) {
        this.mTranslation = mTranslation;
    }

    public void setmFromColor(int mFromColor) {
        this.mFromColor = mFromColor;
    }

    public void setmToColor(int mToColor) {
        this.mToColor = mToColor;
    }


    @Override
    public void scrollReset() {
        if (mIsAlpha) {
            setAlpha(1);
        }

        if (mIsScale) {
            setScaleX(0);
            setScaleY(0);
        }


        if (isTranslationFrom(FROM_LEFT)) {
            setTranslationX(-getWidth());
        }

        if (isTranslationFrom(FROM_TOP)) {
            setTranslationY(-getHeight());
        }

        if (isTranslationFrom(FROM_RIGHT)) {
            setTranslationX(getWidth());
        }

        if (isTranslationFrom(FROM_BOTTOM)) {
            setTranslationY(getHeight());
        }


    }

    /**
     * @param ratio the value between 0 and 1
     */
    @Override
    public void onScrollChange(float ratio) {
        System.out.println("--------ScrollViewItemWrapper----------- = " + ratio);
        if (mIsAlpha) {////透明度变化
            this.setAlpha(ratio);
        }

        if (mIsScale) {///缩放变化
            this.setScaleX(ratio);
            this.setScaleY(ratio);
        }

        if (mFromColor != -1 && mToColor != -1) {////颜色变化
            int color = (int) mArgEvaluator.evaluate(ratio, mFromColor, mToColor);
            setBackgroundColor(color);
        }


        if (isTranslationFrom(FROM_LEFT)) {
            setTranslationX(getWidth() * ratio);
        }

        if (isTranslationFrom(FROM_TOP)) {
            setTranslationY(getHeight() * ratio);
        }

        if (isTranslationFrom(FROM_RIGHT)) {
            setTranslationX(-getWidth() * ratio);
        }

        if (isTranslationFrom(FROM_BOTTOM)) {
            setTranslationY(getHeight() * (1 - ratio));
        }
    }


    private boolean isTranslationFrom(int direction) {
        if (mTranslation == -1) {
            return false;
        }
        return (direction & mTranslation) == direction;
    }
}
