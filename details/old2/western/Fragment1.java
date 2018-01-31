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

public class Fragment1 extends Fragment {

    public Fragment1() {
    }

    public static Fragment1 createInstance(){
        Fragment1 fragment1 = new Fragment1();
        return fragment1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.money_dialog, container , false);
    }
}
