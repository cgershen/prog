package com.unam.alex.pumaride.listeners;

import android.view.View;

/**
 * Created by alex on 25/10/16.
 */
public interface RecyclerViewClickListener {
    abstract void onClick(View v, int position, boolean isLongClick, int id);
}
