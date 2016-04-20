package com.jiek.view;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jiek on 16/4/18.
 */
public class TmpFragment extends Fragment {
    private static final String TAG = "TmpFragment";
    ViewPager vp;
    ArrayList<View> vp_views = new ArrayList<View>();
    View tv1, tv2;
    private RatioPagerContainer mContainer;

    public TmpFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContainer = (RatioPagerContainer) findViewById(R.id.container);
        mContainer.setBackgroundColor(Color.parseColor("#FFFFFF"));
        vp = mContainer.getViewPager();
//        vp = (ViewPager) findViewById(R.id.vp);
        initViewPager();

        /*FragmentHolder holder = new FragmentHolder((FragmentHolder.Listener) getActivity());
        try {
            List<PullToOtherLayout> list = new ArrayList<PullToOtherLayout>();
            list.add((PullToOtherLayout) getView().findViewById(R.id.pulltoRefresh));
            holder.setOnRefreshListener(list);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private View findViewById(int rsid) {
        return getView().findViewById(rsid);
    }

    private void initViewPager() {
        int count = 5;
        try {
            count += new Random().nextInt(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (vp_views != null) {
            vp_views.clear();
        }
        Log.e(TAG, "initViewPager: " + count);
//        vp.setOffscreenPageLimit(1);
//        vp.setClipChildren(false);
//        vp.setOffscreenPageLimit(3);
//        vp.setPageMargin(100);

        for (int i = 0; i < count; i++) {
            TextView tv = new TextView(getActivity());
            tv.setText("ViewPager: " + i);
            tv.setBackgroundColor(i % 2 == 0 ? 0xffff0000 : 0x0000ff00);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.parseColor("#ff000000"));
            vp_views.add(tv);
        }

        vp.setAdapter(getAdapter());
        vp.setCurrentItem(0);
//        vp.setPageTransformer(true, new ZoomOutPageTransformer());
//        vp.setCurrentItem(1000 * vp_views.size());

    }

    private PagerAdapter getAdapter() {
        return new PagerAdapter() {
            @Override
            public int getCount() {
                return vp_views.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(vp_views.get(position % vp_views.size()));//删除页卡
            }


            @Override
            public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
                try {
                    container.addView(vp_views.get(position % vp_views.size()), 0);//添加页卡
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "instantiateItem: " + position);
                return vp_views.get(position % vp_views.size());
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            /*@Override
            public float getPageWidth(int position) {
//                return super.getPageWidth(position);
                return 0.9f;
            }*/
        };
    }

    private class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        @SuppressLint("NewApi")
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            Log.e("TAG", view + " , " + position + "");

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                        / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
