package com.project.avanikan_pc_003.taskslist.western;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.avanikan_pc_003.taskslist.R;

/**
 * Created by AvaNikan-PC-003 on 11/1/2017.
 */

public class Fragment2 extends Fragment {

    public static Fragment2 createInstance(){
        Fragment2 fragment2 = new Fragment2();
        return fragment2;
    }

    public Fragment2() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sample_fragment2 , container , false);


    }
}
