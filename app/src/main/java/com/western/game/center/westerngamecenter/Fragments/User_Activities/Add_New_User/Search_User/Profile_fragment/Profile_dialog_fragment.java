package com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User.Profile_fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.western.game.center.westerngamecenter.App;
import com.western.game.center.westerngamecenter.DataBase.DataBase_Operation;
import com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User.Active_dialog_fragment.Custom_dialog;
import com.western.game.center.westerngamecenter.R;
import com.western.game.center.westerngamecenter.User_Constant.Convert;
import com.western.game.center.westerngamecenter.User_Constant.User;


public class Profile_dialog_fragment extends DialogFragment {

    public static final String TAG = "=====>";

    User user ;

    de.hdodenhof.circleimageview.CircleImageView btn_done ;
    TextView textView_name , textView_phone , textView_date , textView_totalmoney , textView_totaltime , textView_left_money ;


    public Profile_dialog_fragment() {
        // Required empty public constructor
    }

    public static Profile_dialog_fragment newInstance (int id){
        Profile_dialog_fragment profile_dialog_fragment = new Profile_dialog_fragment();
        Bundle args = new Bundle();
        args.putInt("ID" , id);
        profile_dialog_fragment.setArguments(args);
        return profile_dialog_fragment;
    }

    private void init (View view){

        btn_done = view.findViewById(R.id.btn_done);
        textView_name = view.findViewById(R.id.first_name);
        textView_phone = view.findViewById(R.id.phone_pro);
        textView_date = view.findViewById(R.id.date_pro);
        textView_totalmoney = view.findViewById(R.id.total_money_pro);
        textView_left_money = view.findViewById(R.id.left_money_pro);
        textView_totaltime = view.findViewById(R.id.play_time_pro);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_dialog , container , false);

        init(rootView);

        user = new User();
        DataBase_Operation db = App.getDataBaseOperation();
        user = db.Search_User(getArguments().getInt("ID"));


        setTextView(user);


        return rootView;
    }


    private void setTextView (User user){

        textView_name.setText(user.Name + "\n" + user.LastName);
        textView_date.setText(user.Date);
        textView_phone.setText("+98 " + user.Phone);
        textView_left_money.setText(String.valueOf(user.LeftMoney));
        textView_totalmoney.setText(String.valueOf(user.TotalMoney));
        Convert convert = new Convert( user.TotalMoney , 1 , true);
        textView_totaltime.setText(String.valueOf(convert.result_time()));
    }

}
