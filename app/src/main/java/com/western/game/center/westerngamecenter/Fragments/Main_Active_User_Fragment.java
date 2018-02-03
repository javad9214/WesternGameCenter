package com.western.game.center.westerngamecenter.Fragments;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.geniusforapp.achievementunlocked.AchievementUnlocked;
import com.geniusforapp.achievementunlocked.entity.AchievementData;
import com.geniusforapp.achievementunlocked.viewes.AchievementIconView;
import com.western.game.center.westerngamecenter.ActiveUsers.ActiveUsers_Recycler_Adapter;
import com.western.game.center.westerngamecenter.App;
import com.western.game.center.westerngamecenter.DataBase.DataBase_Operation;
import com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Add_new_User.Add_user_dialog;
import com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User.Search_User_Fragment;
import com.western.game.center.westerngamecenter.R;
import com.western.game.center.westerngamecenter.Tools.TypefaceSpan;
import com.western.game.center.westerngamecenter.User_Constant.ActiveUser;

import java.util.ArrayList;


public class Main_Active_User_Fragment extends Fragment implements  View.OnClickListener , RadioGroup.OnCheckedChangeListener{


    public static final String TAG = "===>" ;

    private BroadcastReceiver receiver ;

    FloatingActionButton fab  , fab2 ;
    Toolbar toolbar ;
    DrawerLayout drawer ;
    NavigationView navigationView ;
    Context context ;

    View view1  , view2  ;
    Animation a1 , a2 , a3 ;

    ArrayList<ActiveUser> list  = new ArrayList<>();
    ActiveUsers_Recycler_Adapter adapter ;
    RecyclerView recyclerView ;

    LinearLayout  linearLayout_main_page_empty ;



    private static final String Flag = "param7";


    ActiveUser activeUser  = new ActiveUser();



    public Main_Active_User_Fragment() {
        // Required empty public constructor
    }

    public static Main_Active_User_Fragment newInstance(Boolean flag) {
        Main_Active_User_Fragment fragment = new Main_Active_User_Fragment();
        Bundle args = new Bundle();
        args.putBoolean(Flag , flag);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main__active__user_, container, false);

        this.view2 = view ;
        checkDrawOverlayPermission();
        init(view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //navigationView.setNavigationItemSelectedListener(this);
        //ActionBarDrawerToggle();
        SpannableString s = new SpannableString("Western Game Center");
        s.setSpan(new TypefaceSpan(getContext(), "Durwent.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(s);
        //Window window = getActivity().getWindow() ;
        //window.setStatusBarColor(Color.rgb(129 , 6 , 6));

       // drawer.setStatusBarBackgroundColor(Color.TRANSPARENT);

        Window window = getActivity().getWindow() ;
        window.setStatusBarColor(Color.rgb(183 , 28 , 28));






        fab.setOnClickListener(this);

        view1 = view.findViewById(R.id.fab_add_active_user);



        setActive();


        return view ;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main , menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_new_user :
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                Add_user_dialog addUserDialog = new Add_user_dialog();
                addUserDialog.show(transaction, "add_user_dialog");
                
                break;

            case R.id.action_settings :


                showAchievement();

                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 100) {
//            if (Settings.canDrawOverlays(getContext())) {
//                showAchievement();
//            }
//        }
    }

    public boolean checkDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (!Settings.canDrawOverlays(getContext())) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getActivity().getPackageName()));
            startActivityForResult(intent, 100);
            return false;
        } else {
            return true;
        }
    }


    private void showAchievement() {
        AchievementUnlocked test = new AchievementUnlocked(getContext()).setReadingDelay(2000).setDismissible(false);
        AchievementData achievementData = new AchievementData();
        achievementData.setTitle("     New User Saved Successfully");
        achievementData.setState(AchievementIconView.AchievementIconViewStates.FADE_DRAWABLE);
        achievementData.setBackgroundColor(Color.parseColor("#ffffff"));
        achievementData.setIcon(getResources().getDrawable(R.drawable.ic_logo_western));
        achievementData.setTextColor(getResources().getColor(android.R.color.black));
        test.show(achievementData);
    }

    public void setActive (){
        DataBase_Operation db = App.getDataBaseOperation();
        list = db.Show_Active_user();

        Log.i(TAG, "setActive: " + list.get(0).isRunning);



        if (list.get(0).null_flag){
            linearLayout_main_page_empty.setVisibility(View.VISIBLE);
        }else if (list.get(0).isRunning){
            Log.i(TAG, "setActive: is running ");
            Ask_time();
        }else {

            linearLayout_main_page_empty.setVisibility(View.INVISIBLE);
            adapter = new ActiveUsers_Recycler_Adapter(list, getContext(), view1, getActivity());

            recyclerView.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(manager);

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                        fab.hide();
                    } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                        fab.show();
                    }
                }
            });
        }
    }

    public void showActive(){

        Log.i(TAG, "showActive: is running");
        DataBase_Operation db = App.getDataBaseOperation();
        list = db.Show_Active_user();
        linearLayout_main_page_empty.setVisibility(View.INVISIBLE);
        adapter = new ActiveUsers_Recycler_Adapter(list, getContext(), view1, getActivity());

        recyclerView.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });
    }

    public void setActive (View viewParent , Context context , Activity activity){

        RecyclerView recyclerView ;
        ActiveUsers_Recycler_Adapter adapter ;
        ArrayList<ActiveUser> list  = new ArrayList<>();

        DataBase_Operation db = App.getDataBaseOperation();
        list = db.Show_Active_user();

        recyclerView = (RecyclerView) activity.findViewById(R.id.recycler_active_user);

        //adapter = new ActiveUsers_Recycler_Adapter(list ,context, viewParent , activity );

       // recyclerView.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(context  , 1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE){
                    fab.hide();
                }else if (dy < 0 && fab.getVisibility() != View.VISIBLE){
                    fab.show();
                }
            }
        });
    }

    public void init(View view){
        fab = (FloatingActionButton) view.findViewById(R.id.fab_add_active_user);

        linearLayout_main_page_empty = (LinearLayout) view.findViewById(R.id.empty_main_page_background);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        drawer = (DrawerLayout)view.findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_active_user);
    }

    public void ActionBarDrawerToggle(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity() , drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onClick(View v) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction() ;
        fragmentTransaction.replace(R.id.placeholder , new Search_User_Fragment(), "search_user_fragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }


    public void Ask_time () {

        Intent intent = new Intent("onTime_Action");

        LocalBroadcastManager.getInstance(getContext()).sendBroadcastSync(intent);

        showActive();

       // receive();
    }

    private void receive (){

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Log.i(TAG, "onReceive: is running ");
                    showActive();
            }
        };

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver , new IntentFilter("onTime_Action2"));
    }




}
