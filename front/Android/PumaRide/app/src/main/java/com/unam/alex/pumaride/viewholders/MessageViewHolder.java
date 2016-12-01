package com.unam.alex.pumaride.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unam.alex.pumaride.R;
import com.unam.alex.pumaride.listeners.RecyclerViewClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alex on 25/10/16.
 */
public class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {
    RecyclerViewClickListener clickListener;
    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            // If not long clicked, pass last variable as false.
            //clickListener.onClick(v, getPosition(), false, getId());
        }
    }
    @Override
    public boolean onLongClick(View v) {
        // If long clicked, passed last variable as true.
        //clickListener.onClick(v, getPosition(), true,getId());
        return true;
    }
    @BindView(R.id.activity_message_list_item_tv_message)
    TextView tvMessage;
    @BindView(R.id.activity_message_list_item_tv_datetime)
    TextView tvDatetime;
    @BindView(R.id.activity_message_list_item_ll_message)
    LinearLayout llMessage;

    public MessageViewHolder(View itemView, RecyclerViewClickListener clickListener) {
        super(itemView);
        this.clickListener = clickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        ButterKnife.bind(this, itemView);
    }


    public TextView getTvDatetime() {
        return tvDatetime;
    }

    public void setTvDatetime(TextView tvDatetime) {
        this.tvDatetime = tvDatetime;
    }

    public LinearLayout getLlMessage() {
        return llMessage;
    }

    public void setLlMessage(LinearLayout llMessage) {
        this.llMessage = llMessage;
    }

    public TextView getTvMessage() {
        return tvMessage;
    }

    public void setTvMessage(TextView tvMessage) {
        this.tvMessage = tvMessage;
    }
}
