//package com.yaoh.view.tablayout;
//
//import android.annotation.TargetApi;
//import android.os.Build;
//import android.support.design.widget.TabLayout;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.Menu;
//import android.view.MenuItem;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainActivity extends AppCompatActivity {
//    TabLayout mTabLayout;
//    private ViewPager mViewPager;
//    private List<String> titles = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        mTabLayout = (TabLayout) findViewById(R.id.tl);
//        mViewPager = (ViewPager) findViewById(R.id.viewpager);
//        Bundle[] mBundles = new Bundle[10];
//        for (int i = 0; i < 10; i++) {
//            titles.add("第" + (i + 1) + "个页卡");
//            Bundle mBundle = new Bundle();
//            mBundle.putString("title", "第" + (i + 1) + "个fragment");
//            mBundles[i] = mBundle;
//        }
//        FragmentPagerAdapter fragmnetpageradapter = new FragmentPagerAdapter(getSupportFragmentManager(), new Class<?>[]{FragmentApp.class, FragmentApp.class, FragmentApp.class, FragmentApp.class, FragmentApp.class, FragmentApp.class, FragmentApp.class, FragmentApp.class, FragmentApp.class}, titles, mBundles);
//        mViewPager.setAdapter(fragmnetpageradapter);
//        mTabLayout.setupWithViewPager(mViewPager);
//        //MODE_FIXED:固定tabs，并同时显示所有的tabs。
//        //MODE_SCROLLABLE：适用于多屏展示的页卡选项，并不会把所有的tab全部显示出来，会根据title的长度来显示tab的宽度。
//        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        //设置tab文字的显示效果：
//        //TabLayout.GRAVITY_FILL ：填充整个tab，必须和MODE_FIXED模式一起使用才会见效。
//        //TabLayout.GRAVITY_CENTER ：tab文字居中显示
//        //不设置TabGravity时，默认是GRAVITY_FILL。
//        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
//        //设置tabLayout文字选中和未选中效果
//        mTabLayout.setTabTextColors(getResources().getColor(R.color.c_333333),getResources().getColor(R.color.c_e53333));
//        //设置下划线颜色
//        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.c_e53333));
//        //设置下划线高度，宽度跟随tab的宽度
//        mTabLayout.setSelectedTabIndicatorHeight(5);
//    }
//
//
//}
