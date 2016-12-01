package com.unam.alex.pumaride.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unam.alex.pumaride.R;
import com.unam.alex.pumaride.listeners.RecyclerViewClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alex on 25/10/16.
 */
public class MatchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {
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

    public CircleImageView getiImage() {
        return iImage;
    }

    public void setiImage(CircleImageView iImage) {
        this.iImage = iImage;
    }

    @BindView(R.id.fragment_match_list_item_name)
    TextView tName;
    @BindView(R.id.fragment_match_list_item_last_message)
    TextView tLastMessage;
    @BindView(R.id.fragment_match_list_item_date_time)
    TextView tDateTime;
    @BindView(R.id.fragment_match_list_item_image)
    CircleImageView iImage;

    private int id;
    public MatchViewHolder(View itemView, RecyclerViewClickListener clickListener) {
        super(itemView);
        this.clickListener = clickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        ButterKnife.bind(this, itemView);
    }

    public TextView gettLastMessage() {
        return tLastMessage;
    }

    public void settLastMessage(TextView tLastMessage) {
        this.tLastMessage = tLastMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TextView gettName() {
        return tName;
    }

    public void settName(TextView tName) {
        this.tName = tName;
    }

    public TextView gettDateTime() {
        return tDateTime;
    }

    public void settDateTime(TextView tDateTime) {
        this.tDateTime = tDateTime;
    }
}
