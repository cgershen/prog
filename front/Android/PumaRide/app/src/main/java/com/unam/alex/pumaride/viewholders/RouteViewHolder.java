package com.unam.alex.pumaride.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unam.alex.pumaride.R;
import com.unam.alex.pumaride.listeners.RecyclerViewClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alex on 5/11/16.
 */
public class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {
    RecyclerViewClickListener clickListener;

    @Override
    public void onClick(View v) {
        if (clickListener != null) {
            // If not long clicked, pass last variable as false.
            clickListener.onClick(v, getPosition(), false, getId());
        }
    }
    @Override
    public boolean onLongClick(View v) {
        // If long clicked, passed last variable as true.
        //clickListener.onClick(v, getPosition(), true,getId());
        return true;
    }

    public ImageView getiImage() {
        return iImage;
    }

    public void setiImage(ImageView iImage) {
        this.iImage = iImage;
    }

    @BindView(R.id.activity_match_detail_list_item_start)
    TextView tvStart;
    @BindView(R.id.activity_match_detail_list_item_end)
    TextView tvEnd;
    @BindView(R.id.activity_match_detail_list_item_image)
    ImageView iImage;
    @BindView(R.id.activity_match_detail_list_item_ll_type)
    LinearLayout llType;
    @BindView(R.id.activity_match_detail_list_item_ib_type)
    ImageButton ibType;
    private int id;
    public RouteViewHolder(View itemView, RecyclerViewClickListener clickListener) {
        super(itemView);
        this.clickListener = clickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        ButterKnife.bind(this, itemView);
    }

    public LinearLayout getLlType() {
        return llType;
    }

    public void setLlType(LinearLayout llType) {
        this.llType = llType;
    }

    public ImageButton getIbType() {
        return ibType;
    }

    public void setIbType(ImageButton ibType) {
        this.ibType = ibType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TextView getTvStart() {
        return tvStart;
    }

    public void setTvStart(TextView tvStart) {
        this.tvStart = tvStart;
    }

    public TextView getTvEnd() {
        return tvEnd;
    }

    public void setTvEnd(TextView tvEnd) {
        this.tvEnd = tvEnd;
    }
}
