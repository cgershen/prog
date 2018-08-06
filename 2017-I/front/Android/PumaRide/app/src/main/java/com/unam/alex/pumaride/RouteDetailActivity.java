package com.unam.alex.pumaride;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.unam.alex.pumaride.fragments.RouteFragment;
import com.unam.alex.pumaride.models.Route;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class RouteDetailActivity extends AppCompatActivity {
    @BindView(R.id.activity_route_detail_image)
    ImageView ivImage;
    @BindView(R.id.activity_route_detail_start)
    TextView tvStart;
    @BindView(R.id.activity_route_detail_end)
    TextView tvEnd;
    @BindView(R.id.activity_route_detail_match)
    TextView tvMatch;
    @BindView(R.id.activity_route_detail_ll_type)
    LinearLayout llType;
    @BindView(R.id.activity_route_detail_ib_type)
    ImageButton ibType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);
        ButterKnife.bind(this);
        if(RouteFragment.route.getImage()!=null) {
            Glide.with(getApplicationContext()).load(RouteFragment.route.getImage()).into(ivImage);
        }
        tvMatch.setText(RouteFragment.route.getMatch().getFirst_name()+" "+RouteFragment.route.getMatch().getFirst_name());
        switch (RouteFragment.route.getMode()){
            case Route.WALK:
                llType.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_background_walk));
                ibType.setBackgroundResource(R.drawable.ic_directions_walk_white_24dp);
                break;
            case Route.BIKE:
                llType.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_background_bike));
                ibType.setBackgroundResource(R.drawable.ic_directions_bike_white_24dp);
                break;
            case Route.CAR:
                llType.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_background_car));
                ibType.setBackgroundResource(R.drawable.ic_directions_car_white_24dp);
                break;
        }
        tvStart.setText("De: "+RouteFragment.route.getStart());
        tvEnd.setText("A: "+RouteFragment.route.getEnd());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.route_detail, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.route_detail_delete:
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("¿Estas seguro?")
                        .setContentText("¡La ruta ya no se mostrará nunca más!")
                        .setConfirmText("Aceptar")
                        .setCancelText("Cancelar")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                delete();
                            }
                        })
                        .show();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void delete(){
        Intent returnIntent = new Intent();
        setResult(1400, returnIntent);
        finish();
    }
}
