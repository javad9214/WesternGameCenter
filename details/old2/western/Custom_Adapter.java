package com.project.avanikan_pc_003.taskslist.western;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AvaNikan-PC-003 on 11/1/2017.
 */

public class Custom_Adapter extends FragmentStatePagerAdapter {

    List<Fragment> mFragmentCollection = new ArrayList<>();
    List<String> mTitleCollection = new ArrayList<>();
    public static final String TAG = "=====>";


    public void addFragment( String title ,  Fragment fragment)
    {
        mFragmentCollection.add(fragment);
        mTitleCollection.add(title);
        Log.i(TAG, "addFragment: ");
    }

    public Custom_Adapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mTitleCollection.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentCollection.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentCollection.size();
    }
}
