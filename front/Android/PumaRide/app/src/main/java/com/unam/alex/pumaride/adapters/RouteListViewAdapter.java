package com.unam.alex.pumaride.adapters;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.unam.alex.pumaride.R;
import com.unam.alex.pumaride.listeners.RecyclerViewClickListener;
import com.unam.alex.pumaride.models.Route;
import com.unam.alex.pumaride.viewholders.RouteViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 5/11/16.
 */
public class RouteListViewAdapter
        extends RecyclerView.Adapter<RouteViewHolder> {
    private final List<Route> Route_list;
    RecyclerViewClickListener mItemClickListener;
    private Context context;
    public void SetRecyclerViewClickListener( RecyclerViewClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    public RouteListViewAdapter(List<Route> Route_list,Context context) {
        this.Route_list = Route_list;
        this.context = context;
    }
    @Override
    public RouteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final View v = layoutInflater.inflate(R.layout.activity_match_detail_list_item, viewGroup, false);
        int items = getItemCount();
        return new RouteViewHolder(v,mItemClickListener);
    }
    @Override
    public void onBindViewHolder(RouteViewHolder Route_ViewHolder, int i) {
        Route_ViewHolder.setId(Route_list.get(i).getId());
        Route_ViewHolder.getTvStart_End().setText(Route_list.get(i).getStart()+" - "+Route_list.get(i).getEnd());
        if(Route_list.get(i).getImage()!=null) {
            Glide.with(context).load(Route_list.get(i).getImage()).into(Route_ViewHolder.getiImage());
        }
    }
    @Override
    public int getItemCount() {
        return Route_list.size();
    }

}