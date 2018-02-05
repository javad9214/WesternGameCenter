package com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User.Active_dialog_fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
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

import com.western.game.center.westerngamecenter.App;
import com.western.game.center.westerngamecenter.DataBase.DataBase_Operation;
import com.western.game.center.westerngamecenter.Fragments.Main_Active_User_Fragment;
import com.western.game.center.westerngamecenter.R;
import com.western.game.center.westerngamecenter.User_Constant.ActiveUser;
import com.western.game.center.westerngamecenter.User_Constant.Convert;
import com.western.game.center.westerngamecenter.User_Constant.User;

import java.util.Calendar;

import github.chenupt.springindicator.SpringIndicator;


public class Custom_dialog extends DialogFragment implements View.OnClickListener  , View.OnKeyListener {

    ViewPager viewPager ;

    boolean flag_fullName  = false ;

    private  long   endtime , EndTimeMin ;

    private  int  EndTimeHour ;

    String finalEndHour ;

    Switch switch_timer , switch_money ;

    EditText editText_timer , editText_money;
    long time , money ;

    TextView textView_money , textView_Timer  , textView_FullName ;

    ImageView imageView_timer , imageView_money ;

    EditText editText_gamePad , editText_tv_num ;
    int gamePad , tv_num = 0 ;

    RelativeLayout line_timer , line_money ;

    public int GamePad = 1 ;

    ActiveUser activeUser ;

    User user ;

    SpringIndicator indicator ;

    Button btn_next , btn_previous , btn_cancel ;

    Fragment1 fragment1 ;
    Fragment2 fragment2 ;
    Fragment3 fragment3 ;

    public static final String TAG = "=====>";

    public static Custom_dialog newInstance (int id){
        Custom_dialog custom_dialog = new Custom_dialog();
        Bundle args = new Bundle();
        args.putInt("ID" , id);
        custom_dialog.setArguments(args);
        return custom_dialog;
    }

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
        adapter.addFragment("2" , fragment2 );
        adapter.addFragment("3" , fragment3 );



        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        viewPager.setPageTransformer(true , new ZoomOutPageTransformer());

        btn_previous.setVisibility(View.INVISIBLE);

        user = new User();
        DataBase_Operation db = App.getDataBaseOperation();
        user = db.Search_User(getArguments().getInt("ID"));



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                switch (position){
                    case 0 :
                        if (!flag_fullName) {
                            textView_FullName = (TextView) viewPager.getRootView().findViewById(R.id.first_name);
                            textView_FullName.setText(user.Name+ "\n" + user.LastName);
                            flag_fullName = true ;
                        }
                        break;
                }

            }

            @Override
            public void onPageSelected(int position) {
               switch (position){

                   case 0 :
                       btn_previous.setVisibility(View.INVISIBLE);
                       btn_next.setText("NEXT");
                       textView_FullName = (TextView) viewPager.getRootView().findViewById(R.id.first_name);
                       textView_FullName.setText(user.Name + "\n" + user.LastName);

                       break;

                   case 1 :
                       btn_previous.setVisibility(View.VISIBLE);
                       btn_next.setText("NEXT");
                       editText_gamePad = (EditText) viewPager.getRootView().findViewById(R.id.gamePads);
                       editText_tv_num = (EditText) viewPager.getRootView().findViewById(R.id.Tv_num);
                       if (editText_gamePad.getText().toString().trim().equals("")){ editText_gamePad.setText("1");}
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
                               if (editText_gamePad.getText().toString().trim().equals("")){ editText_gamePad.setText("1");}
                               return false;
                           }
                       });

                       editText_tv_num.setOnKeyListener(Custom_dialog.this);
                       editText_gamePad.setOnKeyListener(Custom_dialog.this);

                       break;

                   case 2 :
                       btn_previous.setVisibility(View.VISIBLE);
                       btn_next.setText("Active");

                       switch_timer = (Switch) viewPager.getRootView().findViewById(R.id.switch_timer);
                       switch_money = (Switch) viewPager.getRootView().findViewById(R.id.switch_money);
                       editText_money = (EditText) viewPager.getRootView().findViewById(R.id.edit_money);
                       editText_timer = (EditText) viewPager.getRootView().findViewById(R.id.timer_edittext);
                       textView_money = (TextView) viewPager.getRootView().findViewById(R.id.text_money);
                       textView_Timer = (TextView) viewPager.getRootView().findViewById(R.id.timer_text);
                       imageView_money = (ImageView) viewPager.getRootView().findViewById(R.id.img_money);
                       imageView_timer = (ImageView) viewPager.getRootView().findViewById(R.id.timer_img);
                       line_money = (RelativeLayout) viewPager.getRootView().findViewById(R.id.line_money);
                       line_timer = (RelativeLayout) viewPager.getRootView().findViewById(R.id.line_timer);

                       editText_timer.setEnabled(false);

                       editText_money.setOnKeyListener(Custom_dialog.this);
                       editText_timer.setOnKeyListener(Custom_dialog.this);



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

        btn_next = (Button) view.findViewById(R.id.btn_next);
        btn_previous = (Button) view.findViewById(R.id.btn_previous);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        indicator = (SpringIndicator) view.findViewById(R.id.indicator);




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

                        if (editText_money.getText().toString().trim().equals("") || editText_timer.getText().toString().trim().equals("")) {
                            viewPager.setCurrentItem(2);
                            editText_money.setError("Required");
                        }else if (tv_num == 0 ){
                            viewPager.setCurrentItem(1);
                            editText_tv_num = (EditText) viewPager.getRootView().findViewById(R.id.Tv_num);
                            editText_tv_num.setError("Required");
                        }else {
                            dismiss();
                            SendDataUser(time , money , GamePad );

                        }



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
                dismiss();
                break;

        }


    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    private void SendDataUser(long time1 , long money , int num){

        activeUser  = new ActiveUser();

        activeUser.Remaining_Time = time1 *60 * 1000 ;
        Calendar calendar = Calendar.getInstance();
        activeUser.startTime = String.valueOf(calendar.get(calendar.HOUR_OF_DAY)) + " : " +
                String.valueOf(calendar.get(calendar.MINUTE));

        activeUser.endTime = calcEndTime(calendar , time1);
        activeUser.money = money ;
        user.TotalMoney  = money ;
        DataBase_Operation db = App.getDataBaseOperation();
        db.Update_User(user , 2 ) ;

        if (user.LeftMoney > money){

            user.LeftMoney = user.LeftMoney - money ;
            db.Update_User(user ,3 );
        }else {
            user.LeftMoney = 0 ;
            db.Update_User(user , 3) ;
        }
        activeUser.Username_id = user.UID ;
        activeUser.NumJoyStick = num ;
        activeUser.NAME = user.Name ;
        activeUser.LastName = user.LastName ;
        activeUser.Tv_Num = tv_num ;
        activeUser.isRunning = false ;
        activeUser.null_flag = true ;


        db.addActiveUser(activeUser);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction() ;
        Main_Active_User_Fragment main_active_user_fragment = Main_Active_User_Fragment.newInstance(true);
        fragmentTransaction.replace(R.id.placeholder , main_active_user_fragment , "main_active_user_fragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private String calcEndTime(Calendar calendar , long leftTime){

        endtime = calendar.get(calendar.MINUTE) + Math.round(leftTime) ;
        EndTimeHour = (int) endtime / 60 ;

        EndTimeMin = Math.round(endtime % 60 );
        if (calendar.get(calendar.HOUR_OF_DAY)+EndTimeHour > 24){
            EndTimeHour = EndTimeHour - (24 - calendar.get(calendar.HOUR_OF_DAY));
        }
        finalEndHour = String.valueOf(Math.round((calendar.get(calendar.HOUR_OF_DAY))+EndTimeHour))+ " : " +
                String.valueOf(Math.round(EndTimeMin));
        return finalEndHour;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {



        switch (v.getId()){

            case R.id.edit_money :
                if (editText_money.getText().length() > 3){
                    money = Long.parseLong(editText_money.getText().toString()) ;
                    Convert convert = new Convert( money , GamePad , true);
                    time = convert.result_time();
                    editText_timer.setText(String.valueOf(time));
                }

                if (editText_money.getText().length() < 4){
                    editText_timer.setText("");
                }
                break;

            case R.id.timer_edittext :


                if (editText_timer.getText().length() > 1){
                    time = Long.parseLong(editText_timer.getText().toString()) ;
                    Convert convert = new Convert( time , GamePad , false);
                     money = convert.result_money();
                    editText_money.setText(String.valueOf(money));
                }

                if (editText_timer.getText().length() < 2){
                    editText_money.setText("");
                }
                break;

            case R.id.Tv_num :
                if (!editText_tv_num.getText().toString().trim().equals("")){
                    tv_num = Integer.valueOf(editText_tv_num.getText().toString()) ;
                }
                break;

            case  R.id.gamePads :
                if (editText_gamePad != null){
                    if (!editText_gamePad.getText().toString().trim().equals("")){
                        GamePad =  Integer.parseInt(editText_gamePad.getText().toString());
                        if (editText_money != null){
                            editText_money.setText("");
                            editText_timer.setText("");
                        }
                    }



                }
                break;

        }

        return false;
    }


}
