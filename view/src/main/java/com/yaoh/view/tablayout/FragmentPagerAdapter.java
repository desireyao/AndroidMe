package com.yaoh.view.tablayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private Class<?>[] fragmentClass;
    private Bundle[] mBundles;
    private int currentIndex;
    private List<String> titles;

    public FragmentPagerAdapter(FragmentManager fm, Class<?>[] fragmentClass, List<String> titles, Bundle[] mBundles) {
        super(fm);
        this.fragmentClass = fragmentClass;
        this.titles = titles;
        this.mBundles = mBundles;
    }

    public FragmentPagerAdapter(FragmentManager fm, Class<?>[] fragmentClass, Bundle[] mBundles) {
        super(fm);
        this.fragmentClass = fragmentClass;
        this.mBundles = mBundles;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        try {
            Class<?> c = fragmentClass[arg0];
            Fragment mFragment = (Fragment) (c.newInstance());
            if (mBundles != null && arg0 < mBundles.length) {
                mFragment.setArguments(mBundles[arg0]);
            }
            return mFragment;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return fragmentClass == null ? 0 : fragmentClass.length;
    }

    /**
     * 保存当前viewpager的下标
     *
     * @param index
     */
    public void saveCurrentIndex(int index) {
        currentIndex = index;
    }

    /**
     * 读取保存的viewpager的下标
     *
     * @return
     */
    public int getSaveCurrentIndex() {
        return currentIndex;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
