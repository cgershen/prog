package com.unam.alex.pumaride;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.unam.alex.pumaride.fragments.ExampleFragment;
import com.unam.alex.pumaride.fragments.MatchFragment;
import com.unam.alex.pumaride.fragments.MyMapFragment;
import com.unam.alex.pumaride.fragments.SettingsFragment;
import com.unam.alex.pumaride.models.User;
import com.unam.alex.pumaride.retrofit.WebServices;
import com.unam.alex.pumaride.utils.Statics;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
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
                editor.commit();
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
        Fragment newFragment = new MyMapFragment();
        if (id == R.id.nav_home) {
            // Handle the camera action
            newFragment = new MyMapFragment();
            fab.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_matches) {
            newFragment = new MatchFragment();
        } else if (id == R.id.nav_routes) {
            newFragment = new MatchFragment();
        } else if (id == R.id.nav_about) {
            newFragment = new ExampleFragment();
        } else if (id == R.id.nav_settings) {
            newFragment = new SettingsFragment();
        } else if (id == R.id.nav_chat) {
            Intent i  = new Intent(this,MessageActivity.class);
            startActivity(i);
        }
        // Insert the fragment by replacing any existing fragment


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
        transaction.replace(R.id.app_bar_main_fragment, newFragment);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
