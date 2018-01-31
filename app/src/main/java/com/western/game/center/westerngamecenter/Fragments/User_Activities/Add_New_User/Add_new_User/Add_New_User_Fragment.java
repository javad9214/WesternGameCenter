package com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Add_new_User;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.western.game.center.westerngamecenter.App;
import com.western.game.center.westerngamecenter.DataBase.DataBase_Operation;
import com.western.game.center.westerngamecenter.Fragments.Main_Active_User_Fragment;
import com.western.game.center.westerngamecenter.R;
import com.western.game.center.westerngamecenter.Time.CalendarTool;
import com.western.game.center.westerngamecenter.User_Constant.ActiveUser;
import com.western.game.center.westerngamecenter.User_Constant.User;

import java.util.ArrayList;


public class Add_New_User_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String TAG = "===>";


    private String mParam1;
    private String mParam2;

    Toolbar toolbar ;

    private String username , password , name , phone ;

    private User user ;

    Button button ;

    ArrayList<ActiveUser> list ;
    ActiveUser activeUser ;

    EditText editText_Username , editText_FamilyName , editText_Name , editText_phone ;

    public Add_New_User_Fragment() {
        // Required empty public constructor
    }


    public static Add_New_User_Fragment newInstance(String param1, String param2) {
        Add_New_User_Fragment fragment = new Add_New_User_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add__new__user_, container, false);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        init(view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveToDB();
            }
        });

        return view ;
    }

    public void init(View view){
        editText_Username = (EditText) view.findViewById(R.id.edit_username);
        editText_Name = (EditText) view.findViewById(R.id.edit_name);
        editText_phone = (EditText) view.findViewById(R.id.edit_phone);
        editText_FamilyName = (EditText) view.findViewById(R.id.edit_family_name);


        button = (Button) view.findViewById(R.id.btn_save);
    }

    public void SaveToDB(){
        user = new User();
        user.UserName = Integer.valueOf(editText_Username.getText().toString());
        if (!editText_Name.getText().toString().trim().equals("")){
            user.Name = editText_Name.getText().toString();
        }
        if (!editText_FamilyName.getText().toString().trim().equals("")){
            user.LastName = editText_FamilyName.getText().toString();
        }
        if (!editText_phone.getText().toString().trim().equals("")){
            user.Phone =  Long.parseLong(editText_phone.getText().toString());
        }
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




        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction() ;
        Main_Active_User_Fragment main_active_user_fragment = Main_Active_User_Fragment.newInstance(activeUser.null_flag);
        fragmentTransaction.replace(R.id.placeholder , main_active_user_fragment , "main_active_user_fragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}
