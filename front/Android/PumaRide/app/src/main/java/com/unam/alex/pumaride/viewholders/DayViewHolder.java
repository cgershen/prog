package com.unam.alex.pumaride.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.unam.alex.pumaride.R;
import com.unam.alex.pumaride.listeners.RecyclerViewClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alex on 25/10/16.
 */
public class DayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {
    RecyclerViewClickListener clickListener;

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            // If not long clicked, pass last variable as false.
            clickListener.onClick(v, getPosition(), false,getId());
        }
    }
    @Override
    public boolean onLongClick(View v) {
        // If long clicked, passed last variable as true.
        //clickListener.onClick(v, getPosition(), true,getId());
        return true;
    }

    @BindView(R.id.activity_maps_item_day_day)
    TextView tvDay;
    @BindView(R.id.activity_maps_item_day_hour)
    TextView tvHour;

    private int id;
    public DayViewHolder(View itemView, RecyclerViewClickListener clickListener) {
        super(itemView);
        this.clickListener = clickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        ButterKnife.bind(this, itemView);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TextView getTvDay() {
        return tvDay;
    }

    public void setTvDay(TextView tvDay) {
        this.tvDay = tvDay;
    }

    public TextView getTvHour() {
        return tvHour;
    }

    public void setTvHour(TextView tvHour) {
        this.tvHour = tvHour;
    }
}
