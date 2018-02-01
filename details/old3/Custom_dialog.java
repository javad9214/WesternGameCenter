package com.project.avanikan_pc_003.taskslist.western;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.project.avanikan_pc_003.taskslist.R;

import github.chenupt.springindicator.SpringIndicator;


public class Custom_dialog extends DialogFragment implements View.OnClickListener    {

    ViewPager viewPager ;

    Switch switch_timer , switch_money ;

    EditText editText_timer , editText_money;
    long timer ,money ;

    TextView textView_money , textView_Timer ;

    ImageView imageView_timer , imageView_money ;

    EditText editText_gamePad , editText_tv_num ;
    int gamePad , tv_num ;

    RelativeLayout line_timer , line_money ;

    String name  ;

    long time  , tick[] ;

    int i = 0 ;

    private BroadcastReceiver receiver ;

    EditText edit_name , edit_time ;

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

        receive();


        Custom_Adapter adapter = new Custom_Adapter(getChildFragmentManager());
        fragment1 = new Fragment1() ;
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        adapter.addFragment("1" , fragment1 );
        adapter.addFragment("2" , fragment2 );
        adapter.addFragment("3" , fragment3 );

        Log.i(TAG, "onCreateView: " + fragment1.getView());

        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        viewPager.setPageTransformer(true , new ZoomOutPageTransformer());

        btn_previous.setVisibility(View.INVISIBLE);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               switch (position){

                   case 0 :
                       btn_previous.setVisibility(View.INVISIBLE);
                       btn_next.setText("NEXT");
                       break;

                   case 1 :
                       btn_previous.setVisibility(View.VISIBLE);
                       btn_next.setText("NEXT");
                       editText_gamePad = viewPager.getRootView().findViewById(R.id.gamePads);
                       editText_tv_num = viewPager.getRootView().findViewById(R.id.Tv_num);

                        editText_gamePad.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                editText_gamePad.setText("");
                                return false;
                            }
                        });

                       editText_tv_num.setOnTouchListener(new View.OnTouchListener() {
                           @Override
                           public boolean onTouch(View view, MotionEvent motionEvent) {
                               editText_tv_num.setHint("");
                               return false;
                           }
                       });

                       break;

                   case 2 :
                       btn_previous.setVisibility(View.VISIBLE);
                       btn_next.setText("Active");

                       switch_timer = viewPager.getRootView().findViewById(R.id.switch_timer);
                       switch_money = viewPager.getRootView().findViewById(R.id.switch_money);
                       editText_money = viewPager.getRootView().findViewById(R.id.edit_money);
                       editText_timer = viewPager.getRootView().findViewById(R.id.timer_edittext);
                       textView_money = viewPager.getRootView().findViewById(R.id.text_money);
                       textView_Timer = viewPager.getRootView().findViewById(R.id.timer_text);
                       imageView_money = viewPager.getRootView().findViewById(R.id.img_money);
                       imageView_timer = viewPager.getRootView().findViewById(R.id.timer_img);
                       line_money = viewPager.getRootView().findViewById(R.id.line_money);
                       line_timer = viewPager.getRootView().findViewById(R.id.line_timer);

                       editText_timer.setEnabled(false);


                       switch_timer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                           @Override
                           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                               if (switch_timer.isChecked()){
                                   switch_money.setChecked(false);
                                   imageView_money.setImageResource(R.drawable.ic_money_off_black_40dp);
                                   imageView_timer.setImageResource(R.drawable.ic_timer_black_40dp);
                                   line_money.setVisibility(View.VISIBLE);
                                   line_timer.setVisibility(View.INVISIBLE);
                                   editText_money.setEnabled(false);

                                   editText_timer.setEnabled(true);


                               }else {
                                   switch_money.setChecked(true);
                                   imageView_money.setImageResource(R.drawable.ic_attach_money_black_40dp);
                                   imageView_timer.setImageResource(R.drawable.ic_timer_off_black_40dp);
                                   line_money.setVisibility(View.INVISIBLE);
                                   line_timer.setVisibility(View.VISIBLE);
                                   editText_timer.setEnabled(false);

                                   editText_money.setEnabled(true);


                               }
                           }
                       });



                       switch_money.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                           @Override
                           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                               if (switch_money.isChecked()){
                                   switch_timer.setChecked(false);
                                   imageView_timer.setImageResource(R.drawable.ic_timer_off_black_40dp);
                                   imageView_money.setImageResource(R.drawable.ic_attach_money_black_40dp);
                                   line_money.setVisibility(View.INVISIBLE);
                                   line_timer.setVisibility(View.VISIBLE);
                                   editText_timer.setEnabled(false);

                                   editText_money.setEnabled(true);


                               }else {
                                   switch_timer.setChecked(true);
                                   imageView_money.setImageResource(R.drawable.ic_money_off_black_40dp);
                                   imageView_timer.setImageResource(R.drawable.ic_timer_black_40dp);
                                   line_money.setVisibility(View.VISIBLE);
                                   line_timer.setVisibility(View.INVISIBLE);
                                   editText_money.setEnabled(false);

                                   editText_timer.setEnabled(true);

                               }
                           }
                       });
                       break;
               }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn_next.setOnClickListener(this);
        btn_previous.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);


        return rootView ;
    }

    private  void  init (View view){

        btn_next = view.findViewById(R.id.btn_next);
        btn_previous = view.findViewById(R.id.btn_previous);
        btn_cancel = view.findViewById(R.id.btn_cancel);

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        indicator = view.findViewById(R.id.indicator);




    }

    private void receive (){



        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                tick = new long[10] ;

                Log.i(TAG, "onReceive: " + intent.getStringExtra("sample") );

                tick = intent.getLongArrayExtra("tick");
                for (int t = 0 ; t < 10 ; t++){

                    Log.i("========", "onReceive: " + tick[t]);
                }
            }
        };

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver , new IntentFilter("onTime_Action"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_next :

                switch (viewPager.getCurrentItem()){

                    case 0 :
                        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                        break;

                    case 1 :
                        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                        break;

                    case 2 :
                            Intent intent = new Intent(getActivity(), MyService.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                            getActivity().startService(intent);

                           dismiss();

                        break;

                }

                break;

            case R.id.btn_previous :

                switch (viewPager.getCurrentItem()){


                    case 1 :
                        viewPager.setCurrentItem(viewPager.getCurrentItem()- 1);
                        break;

                    case 2 :
                        viewPager.setCurrentItem(viewPager.getCurrentItem()- 1);
                        break;


                }

                break;


            case R.id.btn_cancel :
                Log.i(TAG, "onClick: ondetach");
                onStop();
                break;

        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: ");
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        super.onPause();

    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver , new IntentFilter("onTime_Action"));
        super.onResume();
    }


}
