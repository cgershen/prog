package com.unam.alex.pumaride.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.unam.alex.pumaride.R;
import com.unam.alex.pumaride.listeners.RecyclerViewClickListener;
import com.unam.alex.pumaride.models.Message;
import com.unam.alex.pumaride.viewholders.MessageViewHolder;

import java.util.ArrayList;

/**
 * Created by alex on 25/10/16.
 */

public class MessageListViewAdapter extends RecyclerView.Adapter<MessageViewHolder> {
    private final ArrayList<Message> Message_list;
    RecyclerViewClickListener mItemClickListener;
    private Context context;
    public void SetRecyclerViewClickListener( RecyclerViewClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    public MessageListViewAdapter(ArrayList<Message> Message_list,Context context) {
        this.Message_list = Message_list;
        this.context = context;
    }
    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final View v = layoutInflater.inflate(R.layout.activity_message_list_item, viewGroup, false);
        int items = getItemCount();
        return new MessageViewHolder(v,mItemClickListener);
    }
    @Override
    public void onBindViewHolder(MessageViewHolder Message_ViewHolder, int i) {

        Message_ViewHolder.getTvMessage().setText(Message_list.get(i).getMessage());
        if(Message_list.get(i).getType()==0){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;
            params.gravity = Gravity.RIGHT;
            Message_ViewHolder.getLlMessage().setLayoutParams(params);
            Message_ViewHolder.getTvDatetime().setLayoutParams(params);
            Message_ViewHolder.getTvMessage().setBackgroundResource(R.drawable.bubble_white_normal_mdpi_3);
        }
        /*if(Message_list.get(i).getImage()!="") {
            Glide.with(context).load(Statics.URL_IMAGE + Message_list.get(i).getImage()).into(ivImage);
        }*/

        //Message_ViewHolder.settImage(ivImage);
    }
    @Override
    public int getItemCount() {
        return Message_list.size();
    }

}