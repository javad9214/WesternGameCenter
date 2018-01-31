package com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.western.game.center.westerngamecenter.App;
import com.western.game.center.westerngamecenter.DataBase.DataBase_Operation;
import com.western.game.center.westerngamecenter.Fragments.Main_Active_User_Fragment;
import com.western.game.center.westerngamecenter.Fragments.User_Activities.Add_New_User.Search_User.Active_dialog_fragment.Custom_dialog;
import com.western.game.center.westerngamecenter.R;
import com.western.game.center.westerngamecenter.Tools.RecyclerItemClickListener;
import com.western.game.center.westerngamecenter.Tools.TypefaceSpan;
import com.western.game.center.westerngamecenter.User_Constant.Convert;
import com.western.game.center.westerngamecenter.User_Constant.User;

import java.util.ArrayList;
import java.util.List;


public class Search_User_Fragment extends Fragment {

    LinearLayout linearLayout_empty_search_page ;

    DataBase_Operation db ;

    List<User> userList ;

    Toolbar toolbar ;
    DrawerLayout drawer ;
    NavigationView navigationView ;

    Intent intent ;

    int selectedUser = 0 ; // number of selected user when long clicked ...

    int positionUser = 0 ; // position of user that long clicked ...

    ImageView  imageView_selected_user ;

    RecyclerView.OnItemTouchListener onItemTouchListener_all , onItemTouchListener_custom ;

    TextView text_no_results ;

    SearchingView_Adapter adapter;

    View view ;

    TextView textView_Selected_User ;

    boolean isLongClicked = false ;

    List<Integer> position_list ;
    List<Integer> User_id_list ;

    public static final String TAG2  = "===/===";

    RecyclerView recyclerView ;

    ArrayList<User> userArrayList;

    public static final String TAG = "===>" ;

    boolean edit_menu_flag = false ;

    LayoutInflater layoutInflater ;

    ImageView imageView_close_toolbar ;

    public Search_User_Fragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        this.intent = getActivity().getIntent() ;
        db = App.getDataBaseOperation() ;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.searching_user_drwable, container, false);

        init(view);


        position_list = new ArrayList<>() ;
        this.view = view ;
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
       ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SpannableString s = new SpannableString("Western Game Center");
        s.setSpan(new TypefaceSpan(getContext(), "Durwent.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(s);

        show_all_users();


        return view ;
    }

    public void init(View view){
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        drawer = (DrawerLayout)view.findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) view.findViewById(R.id.nav_view);

        text_no_results = (TextView) view.findViewById(R.id.text_no_results);

        linearLayout_empty_search_page = (LinearLayout) view.findViewById(R.id.empty_search_page_background) ;

        imageView_close_toolbar = (ImageView) view.findViewById(R.id.image_close_toolbar);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_searchView);


    }

    private void show_all_users(){

        text_no_results.setVisibility(View.INVISIBLE);
        userArrayList = db.show_user();


        if (!userArrayList.get(0).NullFlag) {
            linearLayout_empty_search_page.setVisibility(View.INVISIBLE);
            adapter = new SearchingView_Adapter(userArrayList, getContext(), getActivity());
            recyclerView.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
            manager.setOrientation(LinearLayoutManager.VERTICAL);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            linearLayoutManager.setSmoothScrollbarEnabled(true);
            recyclerView.setLayoutManager(linearLayoutManager);



            onItemTouchListener_all =  new RecyclerItemClickListener(getContext() , recyclerView , new RecyclerItemClickListener.OnItemClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onItemClick(View view, int position) {

                    User user3 = new User();

                    user3 = db.Search_User(userArrayList.get(position).UID);

                    Log.i(TAG2, "onItemClick: " + "NAME : " + user3.Name);
                    Log.i(TAG2, "onItemClick: " + "LAST NAME : " +  user3.LastName);
                    Log.i(TAG2, "onItemClick: " + "TOTAL MONEY : " + user3.TotalMoney);
                    Log.i(TAG2, "onItemClick: " + "LEFT MONEY : "  + user3.LeftMoney);
                    Log.i(TAG2, "onItemClick: " + "PHONE : "  + "+98" + user3.Phone);
                    Log.i(TAG2, "onItemClick: " + "DATE : " + user3.Date);
                    Convert convert = new Convert( user3.TotalMoney , 1 , true);
                    Log.i(TAG2, "onItemClick: " + "PLAY TIME : " + convert.result_time());


                    /*
                    TextView az_name , az_lastname , azphone , az_tatl , az_lastmon , az_date  , az_time;
                    AlertDialog.Builder  builder = new AlertDialog.Builder(getContext());
                    View dialog =  layoutInflater.inflate(R.layout.az , null);
                    az_name  = (TextView) dialog.findViewById(R.id.az_name);
                    az_lastname  = (TextView) dialog.findViewById(R.id.az_lastname);
                    azphone  = (TextView) dialog.findViewById(R.id.az_phone);
                    az_tatl  = (TextView) dialog.findViewById(R.id.az_total);
                    az_lastmon  = (TextView) dialog.findViewById(R.id.az_left);
                    az_date  = (TextView) dialog.findViewById(R.id.az_date);
                    az_time  = (TextView) dialog.findViewById(R.id.az_time);
                    az_name.setText("NAME : " + user3.Name);
                    az_lastname.setText("LAST NAME : " + user3.LastName);
                    azphone.setText("PHONE : " + user3.Phone);
                    az_tatl.setText("TOTAL MONEY : " + user3.TotalMoney);
                    az_lastmon.setText("LEFT MONEY : " + user3.LeftMoney);
                    az_date.setText("DATE : " + user3.Date);
                    Convert convert = new Convert( user3.TotalMoney , 1 , true);
                    az_time.setText("PLAY TIME : " + convert.result_time());
                    builder.setPositiveButton("OK" , null);
                    builder.show();
                     */

                    if (!isLongClicked){
                        if (db.Search_ActiveUser(db.show_user().get(position).UID) != null){


                            AlertDialog.Builder  builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("User was Activated ...");
                            builder.setMessage("You Cant Active a User More than One times ...");
                            builder.setPositiveButton("OK" , null);
                            builder.show();


                        }else {
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.addToBackStack(null);
                            Custom_dialog custom_dialog = Custom_dialog.newInstance(userArrayList.get(position).UID);
                            custom_dialog.show(transaction, "dialog");
                        }
                    }else {
                        imageView_selected_user = (ImageView) view.findViewById(R.id.image_contact);
                        boolean isActive = false ;
                        for (int i = 0 ; i < position_list.size() ; i++){
                            if (position_list.get(i) == position){
                                isActive = true ;
                                if (selectedUser == 1){
                                    imageView_selected_user.setImageResource(R.drawable.ic_account_circle_black_50dp);
                                    textView_Selected_User.setText(String.valueOf(selectedUser));
                                    position_list.clear();
                                    isLongClicked = false ;
                                    change_toolbar(false);
                                }else {
                                    selectedUser -- ;
                                    imageView_selected_user.setImageResource(R.drawable.ic_account_circle_black_50dp);
                                    textView_Selected_User.setText(String.valueOf(selectedUser));
                                    position_list.remove(i);
                                }
                            }
                        }
                        if (!isActive){
                            selectedUser ++ ;
                            position_list.add(position);
                            imageView_selected_user.setImageResource(R.drawable.ic_done_black_50dp);
                            textView_Selected_User.setText(String.valueOf(selectedUser));
                        }


                    }

                }

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onLongItemClick(View view, int position) {
                    selectedUser = 1 ;
                    position_list.add(position);
                    isLongClicked = true ;
                    change_toolbar(true);
                    Log.i(TAG, "onLongItemClick: ");
                    imageView_selected_user = (ImageView) view.findViewById(R.id.image_contact);
                    imageView_selected_user.setImageResource(R.drawable.ic_done_black_50dp);
                }
            }) ;
            recyclerView.addOnItemTouchListener(onItemTouchListener_all);


        }else {
            linearLayout_empty_search_page.setVisibility(View.VISIBLE);


        }



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        if (edit_menu_flag){
            inflater.inflate(R.menu.menu_second_toolbar, menu);
        }else {

            inflater.inflate(R.menu.option_menu , menu);
            MenuItem searchItem = menu.findItem(R.id.search);

            MenuItemCompat.setOnActionExpandListener(searchItem , new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    Log.i(TAG, "onMenuItemActionExpand: ");
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    Log.i(TAG, "onMenuItemActionCollapse: ");
                    show_all_users();
                    return true;
                }
            });



            // Associate searchable configuration with the SearchView
            SearchManager searchManager =
                    (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView =
                    (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getActivity().getComponentName()));




            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
               @Override
               public boolean onQueryTextSubmit(String query) {
                   Log.i(TAG, "onQueryTextSubmit: ");
                   handleIntent(intent , query);
                   return false;
               }

               @Override
               public boolean onQueryTextChange(String newText) {
                   if (!newText.isEmpty()) {
                       Log.i(TAG, "onQueryTextChange: " + newText);
                       handleIntent(intent , newText);

                   }
                   return false;
               }
           });

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();



        switch (id){

            case R.id.action_settings :
                Toast.makeText(getContext(), "western game center", Toast.LENGTH_SHORT).show();
                return true;


            case android.R.id.home :
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction() ;
                Main_Active_User_Fragment main_active_user_fragment = Main_Active_User_Fragment.newInstance(true);
                fragmentTransaction.replace(R.id.placeholder , main_active_user_fragment , "main_active_user_fragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true ;


            case R.id.action_delete :

                break;

            case R.id.action_edit :

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void change_toolbar(boolean change) {

        final Window window = getActivity().getWindow() ;

        if (change){
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.second_toolbar , null);
            edit_menu_flag = true ;
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true) ;
            ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(v);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.CYAN));

            window.setStatusBarColor(Color.rgb(3 , 194 ,194));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (v != null) {
                    v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            }
            getActivity(). invalidateOptionsMenu();
            textView_Selected_User = (TextView) view.findViewById(R.id.text_selected_user_num);
            imageView_close_toolbar = (ImageView) view.findViewById(R.id.image_close_toolbar);
            imageView_close_toolbar.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                    isLongClicked = false ;
                    position_list.clear();
                    recyclerView.invalidate();
                    recyclerView.removeOnItemTouchListener(onItemTouchListener_all);
                    recyclerView.removeOnItemTouchListener(onItemTouchListener_custom);
                    show_all_users();
                    unChange_Toolbar();
                }
            });


        }else {

            unChange_Toolbar();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void unChange_Toolbar (){
        final Window window = getActivity().getWindow() ;
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(false) ;
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(null);
        window.setStatusBarColor(Color.rgb(129 , 6 , 6));
        edit_menu_flag = false ;

        getActivity().invalidateOptionsMenu();
    }

    private void handleIntent(Intent intent , String query) {

            Log.i(TAG, "handleIntent: " + query);
            userList = new ArrayList<>();
            userList = db.Search_User(query);
            if (userList == null ){
                recyclerView.removeAllViewsInLayout();
                text_no_results.setVisibility(View.VISIBLE);

            }else {

                display_search_result(userList);
            }

    }

    private void display_search_result (final List<User> list){

        text_no_results.setVisibility(View.INVISIBLE);
        recyclerView.invalidate();
        adapter = new SearchingView_Adapter(list , getContext(), getActivity());
        adapter.notifyItemRangeChanged(0 , list.size());
        adapter.notifyDataSetChanged();
        recyclerView.removeOnItemTouchListener(onItemTouchListener_all);
        recyclerView.removeOnItemTouchListener(onItemTouchListener_custom);
        recyclerView.swapAdapter(adapter , false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        onItemTouchListener_custom =  new RecyclerItemClickListener(getContext() , recyclerView , new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (db.Search_ActiveUser(list.get(position).UID) != null){
                    AlertDialog.Builder  builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("This User has Already Activated ...");
                    builder.setMessage("You Cant Active a User More than One times ...");
                    builder.setPositiveButton("OK" , null);
                    builder.show();

                }else {

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    Custom_dialog custom_dialog = Custom_dialog.newInstance(list.get(position).UID);
                    custom_dialog.show(transaction, "dialog");
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onLongItemClick(View view, int position) {

                change_toolbar(true);
            }
        }) ;

        recyclerView.addOnItemTouchListener(onItemTouchListener_custom);


    }


}

