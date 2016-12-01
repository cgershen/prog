package com.unam.alex.pumaride;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.unam.alex.pumaride.fragments.AboutFragment;
import com.unam.alex.pumaride.fragments.ExampleFragment;
import com.unam.alex.pumaride.fragments.MatchFragment;
import com.unam.alex.pumaride.fragments.MyMapFragment;
import com.unam.alex.pumaride.fragments.RouteFragment;
import com.unam.alex.pumaride.fragments.SettingsFragment;
import com.unam.alex.pumaride.fragments.listeners.OnFragmentInteractionListener;
import com.unam.alex.pumaride.models.User;
import com.unam.alex.pumaride.retrofit.WebServices;
import com.unam.alex.pumaride.services.MessageService;
import com.unam.alex.pumaride.utils.Statics;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {
    @BindView(R.id.fab)
    FloatingActionMenu fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        SharedPreferences sp = getSharedPreferences("pumaride", Activity.MODE_PRIVATE);
        String token = sp.getString("token", "");
        String email = sp.getString("email","");
        String first_name = sp.getString("first_name","");
        String last_name = sp.getString("last_name","");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Statics.SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebServices webServices = retrofit.create(WebServices.class);

        Call<User> call = webServices.getUserMe("token "+token);
        Toast.makeText(getApplicationContext(),token,Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User u = response.body();
                Toast.makeText(getApplicationContext(),new Gson().toJson(u),Toast.LENGTH_SHORT).show();
                SharedPreferences sp = getSharedPreferences("pumaride", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("userid",u.getId());
                editor.putString("first_name", u.getFirst_name());
                editor.putString("last_name", u.getLast_name());
                editor.commit();
                if(!isMyServiceRunning( MessageService.class)){
                    startService(new Intent(getApplicationContext(), MessageService.class));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"ando fallando",Toast.LENGTH_SHORT).show();
            }
        });
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView tvEmail = (TextView) headerView.findViewById(R.id.nav_header_main_email);
        tvEmail.setText(email);
        TextView tvName = (TextView) headerView.findViewById(R.id.nav_header_main_name);
        tvName.setText(first_name +" "+ last_name);
    }
    public void init(){
        MyMapFragment firstFragment = new MyMapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.app_bar_main_fragment, firstFragment).commit();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fab.setVisibility(View.INVISIBLE);
        Fragment newFragment = null;
        if (id == R.id.nav_home) {
            // Handle the camera action
            newFragment = new MyMapFragment();
            fab.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_matches) {
            newFragment = new MatchFragment();
        } else if (id == R.id.nav_routes) {
            newFragment = new RouteFragment();
        } else if (id == R.id.nav_about) {
            Intent i  = new Intent(this,AboutActivity.class);
            startActivity(i);
        } /*else if (id == R.id.nav_chat) {
            Intent i  = new Intent(this,MessageActivity.class);
            startActivity(i);
        }*/
        // Insert the fragment by replacing any existing fragment
        if(newFragment!=null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.app_bar_main_fragment, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(String title) {
        getSupportActionBar().setTitle(title);
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    
    public void dibujaCamino(View v){
        MyMapFragment fragmento = (MyMapFragment) getSupportFragmentManager().findFragmentById(R.id.app_bar_main_fragment);
        //fragmento.TrazaCamino(Integer.parseInt(v.getTag().toString()));
    }
}
