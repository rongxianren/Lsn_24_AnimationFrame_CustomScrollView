package com.rongxianren.lsn_24_animationframe_customscrollview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by wty on 2016/12/14.
 */

public class CustomScrollViewContentContainer extends LinearLayout {
    public CustomScrollViewContentContainer(Context context) {
        this(context, null);
    }

    public CustomScrollViewContentContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        CustomLayoutParam layoutParam = (CustomLayoutParam) params;
        if (isNeedWrappeed(layoutParam)) {////偷梁换柱 为每个设置了自定义熟悉的 view 外面包一层  自定义的view  到时用这个自定义的view来识别 这些自定义属性
            ScrollViewItemWrapper wrapperLayout = new ScrollViewItemWrapper(getContext());
            wrapperLayout.setmIsAlpha(layoutParam.isAlpha);
            wrapperLayout.setmIsScale(layoutParam.isScale);
            wrapperLayout.setmTranslation(layoutParam.translation);
            wrapperLayout.setmFromColor(layoutParam.fromColor);
            wrapperLayout.setmToColor(layoutParam.toColor);

            wrapperLayout.addView(child);
            super.addView(wrapperLayout, layoutParam);
        } else {
            super.addView(child, layoutParam);
        }
    }

    /**
     * 判读是否设置了 自定义属性
     *
     * @param params
     * @return
     */
    private boolean isNeedWrappeed(CustomLayoutParam params) {
        return params.isAlpha || params.isScale || (params.translation != -1) || (params.fromColor != -1 && params.toColor != -1);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {/////通过自定义LayoutParam 把系统view上的自定义属性传递到 外面套的自定义view上去
        CustomLayoutParam param = new CustomLayoutParam(getContext(), attrs);
        return param;
    }


    public class CustomLayoutParam extends LayoutParams {
        public boolean isAlpha;
        public boolean isScale;
        public int translation;
        public int fromColor;
        public int toColor;

        public CustomLayoutParam(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.CustomScrollViewContentContainer);
            this.isAlpha = a.getBoolean(R.styleable.CustomScrollViewContentContainer_isAlpha_able, false);
            this.isScale = a.getBoolean(R.styleable.CustomScrollViewContentContainer_isScale_able, false);
            this.translation = a.getInt(R.styleable.CustomScrollViewContentContainer_scroll_translation, -1);
            this.fromColor = a.getColor(R.styleable.CustomScrollViewContentContainer_from_color, -1);
            this.toColor = a.getColor(R.styleable.CustomScrollViewContentContainer_to_color, -1);
            a.recycle();
        }
    }

}
