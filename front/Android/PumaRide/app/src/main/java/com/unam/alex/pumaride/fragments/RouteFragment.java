package com.unam.alex.pumaride.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unam.alex.pumaride.R;
import com.unam.alex.pumaride.RouteDetailActivity;
import com.unam.alex.pumaride.adapters.MatchListViewAdapter;
import com.unam.alex.pumaride.adapters.RouteListViewAdapter;
import com.unam.alex.pumaride.listeners.RecyclerViewClickListener;
import com.unam.alex.pumaride.models.Route;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class RouteFragment extends ComunicationFragmentManager {
    @BindView(R.id.fragment_route_rv)
    RecyclerView rvRoute;
    RouteListViewAdapter mAdapter;
    Realm realm = null;

    ArrayList<Route> routes = new ArrayList<Route>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters,| e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RouteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RouteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RouteFragment newInstance(String param1, String param2) {
        RouteFragment fragment = new RouteFragment();
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route, container, false);
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        if (mListener != null) {
            //mListener.onFragmentInteraction("Routes");
        }
        Realm.init(getContext());
        // Get a Realm instance for this thread
        realm = Realm.getDefaultInstance();
        RealmResults<Route> routes_ = realm.where(Route.class).findAll();
        routes.addAll(routes_);
        mAdapter = new RouteListViewAdapter(routes,getContext());
        mAdapter.SetRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position, boolean isLongClick, int id) {
                Intent i = new Intent(getContext(), RouteDetailActivity.class);
                startActivity(i);
            }
        });
        rvRoute.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvRoute.setLayoutManager(layoutManager);
        rvRoute.setHasFixedSize(true);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main_tab, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
