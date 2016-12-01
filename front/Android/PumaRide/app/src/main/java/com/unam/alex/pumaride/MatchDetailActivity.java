package com.unam.alex.pumaride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.unam.alex.pumaride.adapters.MessageListViewAdapter;
import com.unam.alex.pumaride.adapters.RouteListViewAdapter;
import com.unam.alex.pumaride.models.Route;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchDetailActivity extends AppCompatActivity {
    @BindView(R.id.activity_match_detail_rv) RecyclerView rvRoutes;
    private RouteListViewAdapter mAdapter;
    private ArrayList<Route> routes = new ArrayList<Route>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
        ButterKnife.bind(this);
        Route r1 = new Route();
        r1.setId(1);
        r1.setStart("Xalapa");
        r1.setEnd("DF");

        Route r2 = new Route();
        r2.setId(1);
        r2.setStart("Xalapa");
        r2.setEnd("DF");

        Route r3 = new Route();
        r3.setId(1);
        r3.setStart("Xalapa");
        r3.setEnd("DF");

        routes.add(r1);
        routes.add(r2);
        routes.add(r3);
        init();
    }
    public void init(){
        mAdapter = new RouteListViewAdapter(routes,getApplicationContext());
        rvRoutes.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvRoutes.setLayoutManager(layoutManager);
        rvRoutes.setHasFixedSize(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
