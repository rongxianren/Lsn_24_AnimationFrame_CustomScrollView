package com.rongxianren.lsn_24_animationframe_customscrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by wty on 2016/12/14.
 */

public class MyCustomScrollView extends ScrollView {


    private CustomScrollViewContentContainer contentView;
    private int childCount = 0;

    public MyCustomScrollView(Context context) {
        this(context, null);
    }

    public MyCustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = (CustomScrollViewContentContainer) this.getChildAt(0);
        childCount = contentView.getChildCount();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        View view = contentView.getChildAt(0);
        view.getLayoutParams().height = getHeight();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        for (int i = 0; i < childCount; i++) {
            View view = contentView.getChildAt(i);
            ScrollViewItemWrapper child = null;
            if (!(view instanceof ScrollViewItemWrapper)) {
                continue;
            }
            child = (ScrollViewItemWrapper) view;
            int childTop = child.getTop();
            float childHeight = child.getHeight();

            float currentTop = childTop - t;
            System.out.println("--------onScrollChanged---------- = " + t);
            if (currentTop <= getHeight()) {///滑进屏幕
                float deltaDistance = getHeight() - currentTop;  ///滑出的距离
                if (deltaDistance >= childHeight) {
                    deltaDistance = childHeight;
                }
                child.onScrollChange(deltaDistance / childHeight);
            } else {////滑出屏幕
                child.scrollReset();
            }
        }
    }
}
