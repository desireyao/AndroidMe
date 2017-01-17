package com.yaoh.view.ViewPager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yaoh.view.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private IndicatorView indicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter();
        for (int i = 0; i < 3; i++){
            ImageView img = new ImageView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            img.setLayoutParams(params);
            img.setImageResource(R.drawable.bigsrc);
            viewPagerAdapter.addView(img);
        }
        viewPager.setAdapter(viewPagerAdapter);

        indicatorView = (IndicatorView) findViewById(R.id.id_indicator);
        indicatorView.setViewPager(viewPager);
    }

    class ViewPagerAdapter extends PagerAdapter{
        private List<View> viewContainter = new ArrayList<>();

        public void addView(View view){
            viewContainter.add(view);
        }

        @Override
        public int getCount() {
            return viewContainter.size();
        }

        //每次滑动的时候生成的组件
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
             ((ViewPager) container).addView(viewContainter.get(position));
             View view = viewContainter.get(position);
             return view;
        }

        //滑动切换的时候销毁当前的组件
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(viewContainter.get(position));
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
