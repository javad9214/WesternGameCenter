package com.western.game.center.westerngamecenter.ActiveUsers;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.Window;
import android.widget.RadioGroup;

import com.western.game.center.westerngamecenter.App;
import com.western.game.center.westerngamecenter.DataBase.DataBase_Operation;
import com.western.game.center.westerngamecenter.Fragments.Main_Active_User_Fragment;
import com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Add_new_User.Add_New_User_Fragment;
import com.western.game.center.westerngamecenter.R;
import com.western.game.center.westerngamecenter.User_Constant.ActiveUser;

import java.util.ArrayList;

public class Active_User_Base extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    Main_Active_User_Fragment main_active_user_fragment ;
    Add_New_User_Fragment add_new_user_fragment ;

    ArrayList<ActiveUser> list ;
    ActiveUser activeUser ;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active__user__base);

        final Window window = getWindow() ;
       // window.setStatusBarColor(Color.rgb(129 , 6 , 6));
        //window.setStatusBarColor(Color.TRANSPARENT);

        attachFragment();

        
    }


    public void attachFragment(){
        main_active_user_fragment = (Main_Active_User_Fragment) getSupportFragmentManager().findFragmentByTag("main_active_user_fragment");
        add_new_user_fragment = (Add_New_User_Fragment) getSupportFragmentManager().findFragmentByTag("add_new_user_fragment");

        list = new ArrayList<>();
        activeUser = new ActiveUser();
        DataBase_Operation db = App.getDataBaseOperation();
        list = db.Show_Active_user();
        activeUser = list.get(0);




        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction() ;
        Main_Active_User_Fragment main_active_user_fragment = Main_Active_User_Fragment.newInstance(activeUser.null_flag);
        fragmentTransaction.replace(R.id.placeholder , main_active_user_fragment , "main_active_user_fragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


}
