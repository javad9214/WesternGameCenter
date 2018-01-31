package com.project.avanikan_pc_003.taskslist.western;

import android.annotation.TargetApi;
import android.support.v4.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.avanikan_pc_003.taskslist.R;

import github.chenupt.springindicator.SpringIndicator;


public class Custom_dialog extends DialogFragment implements View.OnClickListener {

    ViewPager viewPager ;


    SpringIndicator indicator ;

    Button btn_next , btn_previous , btn_cancel ;

    Fragment1 fragment1 ;
    Fragment2 fragment2 ;
    Fragment3 fragment3 ;

    public static final String TAG = "=====>";

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_sample , container , false);

        init(rootView);


        Custom_Adapter adapter = new Custom_Adapter(getChildFragmentManager());
        fragment1 = new Fragment1() ;
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        adapter.addFragment("1" , fragment1 );
        adapter.addFragment( "2" , fragment2 );
        adapter.addFragment("3" , fragment3);

        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        viewPager.setPageTransformer(true , new ZoomOutPageTransformer());

        if (viewPager.getCurrentItem() == 0){
            btn_previous.setEnabled(false);
        }else if (viewPager.getCurrentItem() == 3){
            btn_next.setText("Activate");
        }


        btn_next.setOnClickListener(this);


        return rootView ;
    }

    private  void  init (View view){

        btn_next = view.findViewById(R.id.btn_next);
        btn_previous = view.findViewById(R.id.btn_previous);
        btn_cancel = view.findViewById(R.id.btn_cancel);

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        indicator = view.findViewById(R.id.indicator);
    }


    @Override
    public void onClick(View view) {
        Log.i(TAG, "onClick: " + viewPager.getCurrentItem());
    }
}
