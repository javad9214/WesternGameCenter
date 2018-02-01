package com.project.avanikan_pc_003.taskslist.western.News;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.avanikan_pc_003.taskslist.R;
import com.project.avanikan_pc_003.taskslist.western.Custom_Adapter;
import com.project.avanikan_pc_003.taskslist.western.Fragment1;
import com.project.avanikan_pc_003.taskslist.western.Fragment2;
import com.project.avanikan_pc_003.taskslist.western.Fragment3;
import com.project.avanikan_pc_003.taskslist.western.ZoomOutPageTransformer;

public class Profile_Dialog extends DialogFragment {

    Fragment1 fragment1 ;
    Fragment2 fragment2 ;
    Fragment3 fragment3 ;

    ViewPager viewPager ;

    public static final String TAG = "=====>";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_dialog , container , false);

        init(rootView);


        Custom_Adapter adapter = new Custom_Adapter(getChildFragmentManager());
        fragment1 = new Fragment1() ;
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        adapter.addFragment("Profile" , fragment1 );
        adapter.addFragment("Information" , fragment2 );
        adapter.addFragment("Account" , fragment3 );

        TabLayout tabLayout = rootView.findViewById(R.id.tab_profile_dialog);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setPageTransformer(true , new ZoomOutPageTransformer());

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_face_black_50dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_info_black_50dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_monetization_on_black_50dp);




        return rootView;
    }



    private  void  init (View view){
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);

    }
}
