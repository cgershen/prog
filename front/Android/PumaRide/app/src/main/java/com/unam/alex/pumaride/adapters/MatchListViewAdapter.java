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
import com.unam.alex.pumaride.models.Match;
import com.unam.alex.pumaride.models.Message;
import com.unam.alex.pumaride.utils.Statics;
import com.unam.alex.pumaride.viewholders.MatchViewHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by alex on 25/10/16.
 */

public class MatchListViewAdapter extends RecyclerView.Adapter<MatchViewHolder> {
    private final List<Match> Match_list;
    RecyclerViewClickListener mItemClickListener;
    Realm realm = null;
    private Context context;
    public void SetRecyclerViewClickListener( RecyclerViewClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    public MatchListViewAdapter(List<Match> Match_list,Context context) {
        this.Match_list = Match_list;
        this.context = context;
        Realm.init(context);
        // Get a Realm instance for this thread
        realm = Realm.getDefaultInstance();
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
        Match_ViewHolder.setId(Match_list.get(i).getId());
        TextView tvName = Match_ViewHolder.gettName();
        tvName.setText(Match_list.get(i).getFirst_name());
        ImageView ivImage = Match_ViewHolder.getiImage();
        if(Match_list.get(i).getImage()!=null) {
          Glide.with(context).load(Match_list.get(i).getImage()).into(ivImage);
        }
        //String image =Statics.SERVER_BASE_URL+"static/images/"+i+".jpg";
        //Glide.with(context).load(image).into(ivImage);
        Match_ViewHolder.settName(tvName);
        try {
            Message message = realm.where(Message.class).equalTo("user_id", Match_list.get(i).getId()).or().equalTo("user_id2", Match_list.get(i).getId()).
                    findAllSorted("id", Sort.DESCENDING).first();
            Match_ViewHolder.gettLastMessage().setText(message.getMessage());
            Match_ViewHolder.gettDateTime().setText(getFormatedTime(message.getDatetime()));
        }catch(Exception e){

        }
            //Match_ViewHolder.settImage(ivImage);
    }
    @Override
    public int getItemCount() {
        return Match_list.size();
    }
    public String getFormatedTime(long Milliseconds){
        String time ="";
        Calendar c =  Calendar.getInstance();
        c.setTimeInMillis(Milliseconds);
        SimpleDateFormat format1 = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat format2 = new SimpleDateFormat("dd / MM");
        Calendar c2 = Calendar.getInstance();
        if ((c.get(Calendar.YEAR) == c2.get(Calendar.YEAR))&&(c.get(Calendar.MONTH) == c2.get(Calendar.MONTH))){
            if(c.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)){
                time = format1.format(c.getTime());
            }else if(c.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)-1){
                //It was yesterday
                time = "AYER";
            }
        }else{
            time = format2.format(c.getTime());
        }

        return time;
    }

}