package com.jiek.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class RatioViewPager extends ViewPager {

    private static final float RATIO = 1.2f;

    public RatioViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RatioViewPager(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) (MeasureSpec.getSize(widthMeasureSpec) / RATIO)//这里设置两侧预显示的大小
                , MeasureSpec.getMode(widthMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
