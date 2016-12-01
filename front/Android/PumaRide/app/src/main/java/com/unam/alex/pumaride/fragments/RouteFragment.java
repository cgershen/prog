package com.unam.alex.pumaride.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

public class RouteFragment extends ComunicationFragmentManager {
    @BindView(R.id.fragment_route_rv)
    RecyclerView rvRoute;
    RouteListViewAdapter mAdapter;
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
            mListener.onFragmentInteraction("Routes");
        }
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


}
