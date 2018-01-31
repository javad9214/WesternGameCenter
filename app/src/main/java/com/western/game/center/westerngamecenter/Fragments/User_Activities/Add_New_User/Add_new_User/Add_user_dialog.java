package com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Add_new_User;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.western.game.center.westerngamecenter.App;
import com.western.game.center.westerngamecenter.DataBase.DataBase_Operation;
import com.western.game.center.westerngamecenter.R;
import com.western.game.center.westerngamecenter.Time.CalendarTool;
import com.western.game.center.westerngamecenter.User_Constant.ActiveUser;
import com.western.game.center.westerngamecenter.User_Constant.User;

import java.util.ArrayList;


public class Add_user_dialog extends DialogFragment implements View.OnClickListener{

    Button btn_save , btn_cancel ;

    EditText editText_firstName , editText_LastName , editText_phone ;

    private User user ;
    ArrayList<ActiveUser> list ;
    ActiveUser activeUser ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_add_user_dialog, container, false);

        init(view);

        btn_cancel.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        return view ;
    }

    private void init(View view){

        btn_save = (Button) view.findViewById(R.id.save_new_user);
        btn_cancel = (Button) view.findViewById(R.id.cancel_add_new_user);
        editText_firstName = (EditText) view.findViewById(R.id.edit_First_name);
        editText_LastName = (EditText) view.findViewById(R.id.edit_Last_name);
        editText_phone = (EditText) view.findViewById(R.id.edit_phone);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.save_new_user :
                SaveToDB();
                break;

            case R.id.cancel_add_new_user :
                dismiss();
                break;
        }
    }


    private void SaveToDB(){
        user = new User();

        if (!editText_firstName.getText().toString().trim().equals("")){
            user.Name = editText_firstName.getText().toString();
            if (!editText_LastName.getText().toString().trim().equals("")){
                if (!editText_phone.getText().toString().trim().equals("")){
                    user.Phone =  Long.parseLong(editText_phone.getText().toString());
                }else {
                    user.Phone = 0 ;
                }
                user.LastName = editText_LastName.getText().toString();
                user.TotalMoney = 0 ;
                user.LeftMoney = 0 ;
                CalendarTool calendarTool = new CalendarTool();
                user.Date = calendarTool.getIranianDate();
                DataBase_Operation db = App.getDataBaseOperation();
                db.addUser(user);
                Toast.makeText(getActivity(), "New User Saved Successfully", Toast.LENGTH_SHORT).show();

                list = new ArrayList<>();
                activeUser = new ActiveUser();
                list = db.Show_Active_user();
                activeUser = list.get(0);
                dismiss();
            }else {
                editText_LastName.setError("Required");
            }

        }else {
            editText_firstName.setError("Required");
        }


    }


}
