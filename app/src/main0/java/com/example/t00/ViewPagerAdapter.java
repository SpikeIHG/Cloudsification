package com.example.t00;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // 这里可以根据需要添加条件逻辑
        if (position == 1) {
            // 处理 position 为 1 的情况（如果需要）
        }
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
        notifyDataSetChanged();
    }

    public void addFragmentAt(Fragment fragment, int position) {
        mFragmentList.add(position, fragment);
        notifyDataSetChanged();
    }

    private static final String TAG = "ViewPagerAdapter";
}
