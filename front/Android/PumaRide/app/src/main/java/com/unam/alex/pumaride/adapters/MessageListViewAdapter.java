package com.unam.alex.pumaride.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.unam.alex.pumaride.R;
import com.unam.alex.pumaride.listeners.RecyclerViewClickListener;
import com.unam.alex.pumaride.models.Message;
import com.unam.alex.pumaride.viewholders.MessageViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

        Message_ViewHolder.getTvMessage().setText(Message_list.get(i).getMessage()+" - "+Message_list.get(i).getType_());
        Message_ViewHolder.getTvDatetime().setText(getFormatedTime(Message_list.get(i).getDatetime()));
        if(Message_list.get(i).getType_()==0){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;
            params.gravity = Gravity.RIGHT;
            params2.gravity = Gravity.RIGHT;
            params.setMargins(0,dpToPx(5),0,0);
            Message_ViewHolder.getLlMessage().setLayoutParams(params);
            Message_ViewHolder.getTvDatetime().setLayoutParams(params2);
            Message_ViewHolder.getLlMessage().setBackgroundResource(R.drawable.rounded);
            Message_ViewHolder.getTvMessage().setTextColor(Color.WHITE);
            Message_ViewHolder.getTvDatetime().setTextColor(Color.WHITE);
        }else{
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;
            params.gravity = Gravity.LEFT;
            params2.gravity = Gravity.LEFT;
            params.setMargins(0,dpToPx(5),0,0);
            Message_ViewHolder.getLlMessage().setLayoutParams(params);
            Message_ViewHolder.getTvDatetime().setLayoutParams(params2);
            Message_ViewHolder.getLlMessage().setBackgroundResource(R.drawable.rounded2);
            Message_ViewHolder.getTvMessage().setTextColor(Color.BLACK);
            Message_ViewHolder.getTvDatetime().setTextColor(Color.BLACK);

        }
        /*if(Message_list.get(i).getImage()!="") {
            Glide.with(context).load(Statics.URL_IMAGE + Message_list.get(i).getImage()).into(ivImage);
        }*/

        //Message_ViewHolder.settImage(ivImage);
    }
    public String getFormatedTime(long Milliseconds){
        Calendar c =  Calendar.getInstance();
        c.setTimeInMillis(Milliseconds);
        SimpleDateFormat format1 = new SimpleDateFormat("hh:mm");
        return format1.format(c.getTime());
    }
    @Override
    public int getItemCount() {
        return Message_list.size();
    }

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}