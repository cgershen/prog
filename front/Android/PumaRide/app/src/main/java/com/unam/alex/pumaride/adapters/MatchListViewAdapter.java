package com.unam.alex.pumaride.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unam.alex.pumaride.R;
import com.unam.alex.pumaride.listeners.RecyclerViewClickListener;
import com.unam.alex.pumaride.models.Match;
import com.unam.alex.pumaride.viewholders.MatchViewHolder;

import java.util.ArrayList;

/**
 * Created by alex on 25/10/16.
 */

public class MatchListViewAdapter extends RecyclerView.Adapter<MatchViewHolder> {
    private final ArrayList<Match> Match_list;
    RecyclerViewClickListener mItemClickListener;
    private Context context;
    public void SetRecyclerViewClickListener( RecyclerViewClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    public MatchListViewAdapter(ArrayList<Match> Match_list,Context context) {
        this.Match_list = Match_list;
        this.context = context;
    }
    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final View v = layoutInflater.inflate(R.layout.fragment_match_list_item, viewGroup, false);
        int items = getItemCount();
        return new MatchViewHolder(v,mItemClickListener);
    }
    @Override
    public void onBindViewHolder(MatchViewHolder Match_ViewHolder, int i) {

        TextView tvName = Match_ViewHolder.gettName();
        tvName.setText(Match_list.get(i).getName());

        /*if(Match_list.get(i).getImage()!="") {
            Glide.with(context).load(Statics.URL_IMAGE + Match_list.get(i).getImage()).into(ivImage);
        }*/


        Match_ViewHolder.settName(tvName);

        //Match_ViewHolder.settImage(ivImage);
    }
    @Override
    public int getItemCount() {
        return Match_list.size();
    }

}