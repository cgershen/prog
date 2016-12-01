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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unam.alex.pumaride.R;
import com.unam.alex.pumaride.listeners.RecyclerViewClickListener;
import com.unam.alex.pumaride.models.Message;
import com.unam.alex.pumaride.viewholders.MessageViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alex on 25/10/16.
 */

public class MessageListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Message> userMessagesList;
    private LayoutInflater mLayoutInflater;
    private static final int ROW_TYPE_LOAD_EARLIER_MESSAGES = 0;
    private static final int ROW_TYPE_SENDER = 1;
    private static final int ROW_TYPE_RECEIVER = 2;
    private int userId;
    private boolean isLoadEarlierMsgs;
    private LoadEarlierMessages mLoadEarlierMessages;

    public MessageListViewAdapter(ArrayList<Message> userMessagesList,Context context) {
        mContext = context;
        this.userMessagesList = userMessagesList;
        mLayoutInflater = LayoutInflater.from(mContext);
        mLoadEarlierMessages = (LoadEarlierMessages) mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ROW_TYPE_LOAD_EARLIER_MESSAGES:
                return new LoadEarlierMsgsViewHolder(mLayoutInflater.inflate(R.layout.activity_message_load_message, parent, false));
            case ROW_TYPE_SENDER:
                return new SenderMsgViewHolder(mLayoutInflater.inflate(R.layout.activity_message_list_item,
                        parent, false));
            case ROW_TYPE_RECEIVER:
                return new ReceiverMsgViewHolder(mLayoutInflater.inflate(R.layout
                        .activity_message_list_item, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ROW_TYPE_LOAD_EARLIER_MESSAGES:
                LoadEarlierMsgsViewHolder loadEarlierMsgsViewHolder =
                        (LoadEarlierMsgsViewHolder) holder;
                if (isLoadEarlierMsgs) {
                    loadEarlierMsgsViewHolder.btLoadEarlierMessages
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (mLoadEarlierMessages != null) {
                                        mLoadEarlierMessages.onLoadEarlierMessages();
                                    }
                                }
                            });
                } else {
                    loadEarlierMsgsViewHolder.btLoadEarlierMessages.setVisibility(View.GONE);
                }
                break;
            case ROW_TYPE_SENDER:
                SenderMsgViewHolder Message_ViewHolder = (SenderMsgViewHolder) holder;
                // set data for your sender Messages bubble
                Message_ViewHolder.getTvMessage().setText(userMessagesList.get(position-1).getMessage());
                Message_ViewHolder.getTvDatetime().setText(getFormatedTime(userMessagesList.get(position-1).getDatetime()));
                if(userMessagesList.get(position-1).getType_()==0){
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
                break;
            case ROW_TYPE_RECEIVER:
                ReceiverMsgViewHolder receiverMsgViewHolder = (ReceiverMsgViewHolder) holder;
                // set data for your receiver Messages bubble
                break;
        }
    }

    @Override
    public int getItemCount() {
        return userMessagesList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ROW_TYPE_LOAD_EARLIER_MESSAGES; // row load earlier messages
        } else {
            return ROW_TYPE_SENDER; // receiver row;
        }
    }

    public interface LoadEarlierMessages {
        void onLoadEarlierMessages();
    }


    public void setLoadEarlierMsgs(boolean isLoadEarlierMsgs) {
        this.isLoadEarlierMsgs = isLoadEarlierMsgs;
    }

    static class LoadEarlierMsgsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.activity_message_load_message)
        Button btLoadEarlierMessages;

        public LoadEarlierMsgsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class SenderMsgViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.activity_message_list_item_tv_message)
        TextView tvMessage;
        @BindView(R.id.activity_message_list_item_tv_datetime)
        TextView tvDatetime;
        @BindView(R.id.activity_message_list_item_ll_message)
        LinearLayout llMessage;

        public SenderMsgViewHolder(View itemView) {
            super(itemView);
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

    static class ReceiverMsgViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.activity_message_list_item_tv_message)
        TextView tvMessage;
        @BindView(R.id.activity_message_list_item_tv_datetime)
        TextView tvDatetime;
        @BindView(R.id.activity_message_list_item_ll_message)
        LinearLayout llMessage;

        public ReceiverMsgViewHolder(View itemView) {
            super(itemView);
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
    public String getFormatedTime(long Milliseconds){
        Calendar c =  Calendar.getInstance();
        c.setTimeInMillis(Milliseconds);
        SimpleDateFormat format1 = new SimpleDateFormat("hh:mm");
        return format1.format(c.getTime());
    }
    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}