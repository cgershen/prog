package com.unam.alex.pumaride.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unam.alex.pumaride.R;
import com.unam.alex.pumaride.listeners.RecyclerViewClickListener;
import com.unam.alex.pumaride.models.Day;
import com.unam.alex.pumaride.models.Match;
import com.unam.alex.pumaride.models.Message;
import com.unam.alex.pumaride.utils.Statics;
import com.unam.alex.pumaride.viewholders.DayViewHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

/**
 * Created by alex on 25/10/16.
 */

public class DayListViewAdapter extends RecyclerView.Adapter<DayViewHolder> {
    private String[] days;
    private final List<Day> Day_list;
    RecyclerViewClickListener mItemClickListener;
    Realm realm = null;
    private Context context;
    public void SetRecyclerViewClickListener( RecyclerViewClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    public DayListViewAdapter(List<Day> Day_list, Context context) {
        this.Day_list = Day_list;
        this.context = context;
        Realm.init(context);
        days = context.getResources().getStringArray(R.array.days);
        // Get a Realm instance for this thread
        realm = Realm.getDefaultInstance();
    }
    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final View v = layoutInflater.inflate(R.layout.activity_maps_item_day, viewGroup, false);
        int items = getItemCount();
        return new DayViewHolder(v,mItemClickListener);
    }
    @Override
    public void onBindViewHolder(DayViewHolder Match_ViewHolder, int i) {
        Match_ViewHolder.setId(Day_list.get(i).getId());
        Match_ViewHolder.getTvDay().setText(days[Day_list.get(i).getId()]);
        if(Day_list.get(i).getTime()==0){
            Match_ViewHolder.getTvHour().setVisibility(View.INVISIBLE);
        }else {
            Match_ViewHolder.getTvHour().setVisibility(View.VISIBLE);
            Match_ViewHolder.getTvHour().setText(getFormatedTime(Day_list.get(i).getTime()));
        }
            //Match_ViewHolder.settImage(ivImage);
    }
    @Override
    public int getItemCount() {
        return Day_list.size();
    }
    public String getFormatedTime(long Milliseconds){
        String time ="";
        Calendar c =  Calendar.getInstance();
        c.setTimeInMillis(Milliseconds);
        SimpleDateFormat format2 = new SimpleDateFormat("hh:mm");
        Calendar c2 = Calendar.getInstance();
        time = format2.format(c.getTime());
        return time;
    }

}