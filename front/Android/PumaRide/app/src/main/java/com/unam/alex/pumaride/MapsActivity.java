package com.unam.alex.pumaride;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.unam.alex.pumaride.adapters.DayListViewAdapter;
import com.unam.alex.pumaride.dialog.CDialog;
import com.unam.alex.pumaride.models.Day;
import com.unam.alex.pumaride.models.Match;
import com.unam.alex.pumaride.models.MatchServer;
import com.unam.alex.pumaride.models.Message;
import com.unam.alex.pumaride.models.MyLatLng;
import com.unam.alex.pumaride.models.ReverseGeoCodeResult;
import com.unam.alex.pumaride.models.Route;
import com.unam.alex.pumaride.models.Route2;
import com.unam.alex.pumaride.models.User;
import com.unam.alex.pumaride.retrofit.WebServices;
import com.unam.alex.pumaride.services.MessageService;
import com.unam.alex.pumaride.utils.Statics;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    public static boolean active = false;
    BroadcastReceiver receiver;
    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private final int ZOOM = 16;
    private Marker mMarker1 = null;
    private Marker mMarker2 = null;
    private Polyline line = null;
    private int clickCounter = 0;
    private Route route ;
    Realm realm = null;
    SweetAlertDialog sweetLoadingDialog;
    Dialog loadingDialog;
    private final int REQUEST_LOCATION = 1;
    @BindView(R.id.fab)
    FloatingActionMenu fab;
    @BindView(R.id.app_bar_main_menu_item_0)
    FloatingActionButton fabItem0;
    @BindView(R.id.app_bar_main_menu_item_1)
    FloatingActionButton fabItem1;
    @BindView(R.id.app_bar_main_menu_item_2)
    FloatingActionButton fabItem2;

    @BindView(R.id.activity_maps_rv_days)
    RecyclerView rvDays;
    private DayListViewAdapter mAdapter;
    //Dialog.Builder builder = null;
    List<Day> days = new ArrayList<Day>();
    android.app.Dialog dialogLookingFor = null;
    android.app.Dialog dialogNewMatch= null;
    public LatLng latlng;
    public User user_me;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Statics.CHAT_SERVER_BASE_URL);
        } catch (URISyntaxException e) {}
    }

    Match match = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        route = new Route();
        route.setMode(Route.WALK);
        user_me = new User();
        SharedPreferences sp = getSharedPreferences("pumaride", Activity.MODE_PRIVATE);
        user_me.setId(sp.getInt("userid",1));
        user_me.setFirst_name(sp.getString("first_name",""));
        user_me.setLast_name(sp.getString("last_name",""));

        Toast.makeText(getApplicationContext(),user_me.getId()+"", Toast.LENGTH_SHORT).show();
        Realm.init(getApplicationContext());
        // Get a Realm instance for this thread
        realm = Realm.getDefaultInstance();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Day d1 = new Day();
        d1.setId(0);
        d1.setTime(0);

        Day d2 = new Day();
        d2.setId(1);
        d2.setTime(0);

        Day d3 = new Day();
        d3.setId(2);
        d3.setTime(0);

        Day d4 = new Day();
        d4.setId(3);
        d4.setTime(0);

        Day d5 = new Day();
        d5.setId(4);
        d5.setTime(0);

        Day d6 = new Day();
        d6.setId(5);
        d6.setTime(0);

        Day d7 = new Day();
        d7.setId(6);
        d7.setTime(0);

        days.add(d1);
        days.add(d2);
        days.add(d3);
        days.add(d4);
        days.add(d5);
        days.add(d6);
        days.add(d7);


        mAdapter = new DayListViewAdapter(days,getApplicationContext());
        /*
        mAdapter.SetRecyclerViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position, boolean isLongClick, final int id) {
                //Toast.makeText(MapsActivity.this, "asdas"+id, Toast.LENGTH_SHORT).show();
                builder = new TimePickerDialog.Builder( R.style.Material_App_Dialog_TimePicker, 24, 00){
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        TimePickerDialog dialog = (TimePickerDialog)fragment.getDialog();
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR,2016);
                        c.set(Calendar.MONTH,1);
                        c.set(Calendar.HOUR,dialog.getHour());
                        c.set(Calendar.MINUTE,dialog.getMinute());
                        days.get(id).setTime(c.getTimeInMillis());
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "Time is " + dialog.getFormattedTime(SimpleDateFormat.getTimeInstance()), Toast.LENGTH_SHORT).show();
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(getApplicationContext(), "Cancelled" , Toast.LENGTH_SHORT).show();
                        super.onNegativeActionClicked(fragment);
                    }
                };

                builder.positiveAction("OK")
                        .negativeAction("CANCEL");
                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(getSupportFragmentManager(), null);
            }
        });

        */
        rvDays.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvDays.setLayoutManager(layoutManager);
        rvDays.setHasFixedSize(true);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String s = intent.getStringExtra(MessageService.MESSAGE);
                Message m = new Gson().fromJson(s,Message.class);
                dialogLookingFor.dismiss();
                match = new Match();
                match.setId(m.getUser_id2());
                String name[] = m.getMessage().split(",");
                match.setFirst_name(name[1]);
                match.setLast_name("");
                createNewMatchDialog();
            }
        };
        //Toast.makeText(getApplicationContext(), realm.where(Route.class).count()+"", Toast.LENGTH_SHORT).show();

    }
    @OnClick(R.id.app_bar_main_menu_item_0) void item0(){
        fab.getMenuIconView().setImageResource(R.drawable.ic_directions_walk_white_24dp);
        fab.setMenuButtonColorNormal(Color.parseColor("#009688"));
        fab.setMenuButtonColorPressed(Color.parseColor("#009688"));
        fab.setMenuButtonColorRipple(Color.parseColor("#009688"));
        route.setMode(Route.WALK);
        fab.close(true);
    }
    @OnClick(R.id.app_bar_main_menu_item_1) void item1(){
        fab.getMenuIconView().setImageResource(R.drawable.ic_directions_bike_white_24dp);
        fab.setMenuButtonColorNormal(Color.parseColor("#8bc34a"));
        fab.setMenuButtonColorPressed(Color.parseColor("#8bc34a"));
        fab.setMenuButtonColorRipple(Color.parseColor("#8bc34a"));
        route.setMode(Route.BIKE);
        fab.close(true);
    }
    @OnClick(R.id.app_bar_main_menu_item_2) void item2(){
        fab.getMenuIconView().setImageResource(R.drawable.ic_directions_car_white_24dp);
        fab.setMenuButtonColorNormal(Color.parseColor("#ff9800"));
        fab.setMenuButtonColorPressed(Color.parseColor("#ff9800"));
        fab.setMenuButtonColorRipple(Color.parseColor("#ff9800"));
        route.setMode(Route.CAR);
        fab.close(true);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Statics.GOOGLE_API_REVERSE_GEOCODE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            @Override
            public void onMapClick(LatLng latLng) {
                latlng = latLng;
                WebServices webServices = retrofit.create(WebServices.class);
                String key,latlng_;
                latlng_ = latlng.latitude+","+latlng.longitude;
                key = "AIzaSyAp6kuJ8vLmenz8QZJQszwSvyug_AE0LpY";
                /*
                sweetLoadingDialog = new SweetAlertDialog(MapsActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                sweetLoadingDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                sweetLoadingDialog.setTitleText("Cargando...");
                sweetLoadingDialog.setCancelable(false);
                sweetLoadingDialog.show();*/
                int id_layout = R.layout.loading_cdialog;
                loadingDialog = new CDialog.Builder(MapsActivity.this,id_layout).
                        setBgAlpha(200).
                        build();
                loadingDialog.show();

                switch (clickCounter){
                    case 2:
                        clickCounter = 0;
                        mMarker1.remove();
                        mMarker2.remove();
                        line.remove();
                    case 0:
                        //mMarker1 = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Source").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle_green_a_24px)));
                        Call<ReverseGeoCodeResult> call = webServices.getAddressFromLoc(latlng_,key);
                        call.enqueue(new Callback<ReverseGeoCodeResult>() {
                                         @Override
                                         public void onResponse(Call<ReverseGeoCodeResult> call, Response<ReverseGeoCodeResult> response) {
                                             //sweetLoadingDialog.dismissWithAnimation();
                                             loadingDialog.dismiss();
                                             ReverseGeoCodeResult result = response.body();
                                             mMarker1 = mGoogleMap.addMarker(new MarkerOptions().position(latlng).title(result.getResult().get(0).getFormatted_address()).icon(BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.ic_person_pin_circle_green_a_24px))));
                                             mMarker1.showInfoWindow();
                                             route.setStart(result.getResult().get(0).getFormatted_address());
                                         }

                                         @Override
                                         public void onFailure(Call<ReverseGeoCodeResult> call, Throwable t) {

                                         }
                                     }
                        );
                        break;
                    case 1:
                        //mMarker2 = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Target").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle_orange_b_24px)));
                        latlng_ = latlng.latitude+","+latlng.longitude;
                        key = "AIzaSyAp6kuJ8vLmenz8QZJQszwSvyug_AE0LpY";

                        Call<ReverseGeoCodeResult> call2 = webServices.getAddressFromLoc(latlng_,key);
                        call2.enqueue(new Callback<ReverseGeoCodeResult>() {
                                         @Override
                                         public void onResponse(Call<ReverseGeoCodeResult> call, Response<ReverseGeoCodeResult> response) {
                                             ReverseGeoCodeResult result = response.body();

                                             mMarker2 = mGoogleMap.addMarker(new MarkerOptions().position(latlng).title(result.getResult().get(0).getFormatted_address()).icon(BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.ic_person_pin_circle_orange_b_24px))));
                                             mMarker1.hideInfoWindow();
                                             mMarker2.showInfoWindow();
                                             route.setEnd(result.getResult().get(0).getFormatted_address());
                                             drawPoliLineFromServer();
                                         }

                                         @Override
                                         public void onFailure(Call<ReverseGeoCodeResult> call, Throwable t) {

                                         }
                                     }
                        );

                        break;
                }
                clickCounter+=1;
            }
        });
    }
    public void save(){
        realm.beginTransaction();
        realm.copyToRealm(route);
        realm.commitTransaction();
    }
    public void drawPoliLineFromServer(){
        traceRealRute();
    }
    public void traceRealRute(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Statics.AUXILIAR_SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebServices webServices = retrofit.create(WebServices.class);

        String source,target;
        source = "("+mMarker1.getPosition().longitude+","+mMarker1.getPosition().latitude+")";
        target = "("+mMarker2.getPosition().longitude+","+mMarker2.getPosition().latitude+")";

        Call<Route2> call = webServices.getShortestPath(source,target,"guardar",user_me.getId());
        call.enqueue(new Callback<Route2>() {
            @Override
            public void onResponse(Call<Route2> call, Response<Route2> response) {
                //Toast.makeText(getApplicationContext(),new Gson().toJson(response.body()),Toast.LENGTH_SHORT).show();
                ArrayList<LatLng> positions = new ArrayList<LatLng>();
                Route2 route2  = new Route2();
                route2 = response.body();
                route.getShortest_path().clear();
                if (route2.getShortest_path()!=null)
                for(float[] r:route2.getShortest_path()){
                    LatLng latlng = new LatLng(r[1],r[0]);
                    MyLatLng myLL = new MyLatLng();
                    myLL.setLatitude(r[1]);
                    myLL.setLongitude(r[0]);
                    route.getShortest_path().add(myLL);
                    positions.add(latlng);
                }
                route.setId(route2.getId());
                line = mGoogleMap.addPolyline(new PolylineOptions()
                        .addAll(positions)
                        .width(5)
                        .color(Color.RED));
                //sweetLoadingDialog.dismissWithAnimation();
                loadingDialog.dismiss();
            }
            @Override
            public void onFailure(Call<Route2> call, Throwable t) {
                Toast.makeText(getApplicationContext(),new Gson().toJson(call),Toast.LENGTH_SHORT).show();
                //sweetLoadingDialog.dismissWithAnimation();
                loadingDialog.dismiss();
            }
        });
    }
    public void traceDummyRoute(){
        ArrayList<LatLng> positions = new ArrayList<LatLng>();
        positions.add(mMarker1.getPosition());
        positions.add(mMarker2.getPosition());
        route.setId(0);
        line = mGoogleMap.addPolyline(new PolylineOptions()
                .addAll(positions)
                .width(5)
                .color(Color.RED));
        //sweetLoadingDialog.dismissWithAnimation();
        loadingDialog.dismiss();
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        realizarPeticionUbicacion();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void realizarPeticionUbicacion() {
        //LAS SIGUIENTES LINEAS SIRVEN PARA REALIZAR PETICIONES DE LA UBICACION ACTUAL CADA CIERTO TIEMPO
//        LocationRequest= mLocationRequest;
//        mLocationRequest = LocationRequest.create();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(1000);
//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        //Se necesita revisar los permisos para poder utilizar el servicio de ubicacion
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "No se tienen Permisos", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            return;
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            PosicionarMapa(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissons[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                // Si la peticion es cancelada, el arreglo de resultados estara vacio.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    realizarPeticionUbicacion();
                }
            }
            return;
        }
    }


    private void PosicionarMapa(double lat, double lon) {
        //Se crea una variable con los valores de la latitud y longitud
        LatLng posicion = new LatLng(lat, lon);

        mGoogleMap.clear();
        //Se pone un marcador en la ubicacion obtenida
        //mGoogleMap.addMarker(new MarkerOptions().position(posicion).title(MARKER_TAG));

        //Movemos la vista del mapa a las cercanias del punto obtenido
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, ZOOM));

    }


    private Bitmap getBitmap(int drawableRes)
    {
        Drawable drawable = getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);

        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.maps_add, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.save_route:
                //Toast.makeText(getApplicationContext(),"Guardar",Toast.LENGTH_SHORT).show();
               // save();
                createLookingForDialog();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void createNewMatchDialog(){
        int id_layout = R.layout.activity_maps_custom_dialog_new_match;
        dialogNewMatch = new CDialog.Builder(this,id_layout).
                setBgAlpha(200).
                build();
        dialogNewMatch.show();

        //YoYo.with(Techniques.FadeIn).duration(500).playOn(dialog.findViewById(R.id.custom_dialog_content));

        Button message = (Button)dialogNewMatch.findViewById(R.id.custom_dialog_new_match_button_message);
        Button acept = (Button)dialogNewMatch.findViewById(R.id.custom_dialog_new_match_button_acept);
        CircleImageView image = (CircleImageView)dialogNewMatch.findViewById(R.id.activity_maps_custom_dialog_new_match_image);
        TextView name = (TextView)dialogNewMatch.findViewById(R.id.activity_maps_custom_dialog_new_match_name);
        //ImageView icon = (ImageView)dialog.findViewById(R.id.custom_dialog_image);
        //int green = ContextCompat.getColor(getApplicationContext(),R.color.green_primary_color);
        //icon.setColorFilter(green);
        if(match.getImage()!=null)
            Glide.with(getApplicationContext()).load(match.getImage()).into(image);
        name.setText("¡"+match.getFirst_name()+" "+match.getLast_name()+" es tu nuevo acompañante!");

        int acept_color = ContextCompat.getColor(getApplicationContext(),R.color.blue_primary_color);
        int message_color = ContextCompat.getColor(getApplicationContext(), R.color.teal_primary_color);
        dialogNewMatch.setCanceledOnTouchOutside(false);
        //acept.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, acept_color));
        //message.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, message_color));

        GradientDrawable aceptGradient = (GradientDrawable) acept.getBackground();
        aceptGradient.setStroke(2, acept_color);
        GradientDrawable messageGradient = (GradientDrawable) message.getBackground();
        messageGradient.setStroke(2, message_color);

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                dialogNewMatch.dismiss();
                Intent i  = new Intent(getApplicationContext(),MessageActivity.class);
                MessageActivity.id2 = match.getId();
                startActivity(i);
                finish();
            }
        });
        ////
        acept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validator.validate();
                save();
                dialogNewMatch.dismiss();
                finish();
            }
        });
    }
    TextView _tv;
    public void createLookingForDialog(){

        int id_layout = R.layout.activity_maps_custom_dialog_looking_for;
        dialogLookingFor = new CDialog.Builder(this,id_layout).
                setBgAlpha(200).
                build();
        dialogLookingFor.show();
        _tv = (TextView)dialogLookingFor.findViewById(R.id.activity_maps_custom_dialog_looking_for_timer);
        initCounterDown();
        checkMatchService();
        //YoYo.with(Techniques.FadeIn).duration(500).playOn(dialog.findViewById(R.id.custom_dialog_content));
        ImageButton cancel = (ImageButton)dialogLookingFor.findViewById(R.id.custom_dialog_button_cancel);
        //ImageView icon = (ImageView)dialog.findViewById(R.id.custom_dialog_image);
        //int green = ContextCompat.getColor(getApplicationContext(),R.color.green_primary_color);
        //icon.setColorFilter(green);
        int acept_color = ContextCompat.getColor(getApplicationContext(),R.color.blue_primary_color);
        int cancel_color = ContextCompat.getColor(getApplicationContext(), android.R.color.white);
        dialogLookingFor.setCanceledOnTouchOutside(false);

        cancel.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, cancel_color));
        ////
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validator.validate();
                dialogLookingFor.dismiss();
                createNewMatchDialog();
            }
        });
    }

    public void checkMatchService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Statics.AUXILIAR_SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebServices webServices = retrofit.create(WebServices.class);

        Call<List<MatchServer>> call = webServices.getMatches(route.getId());
        call.enqueue(new Callback<List<MatchServer>>() {
            @Override
            public void onResponse(Call<List<MatchServer>> call, Response<List<MatchServer>> response) {
                //Toast.makeText(getApplicationContext(),new Gson().toJson(response.body()),Toast.LENGTH_SHORT).show();
                List<MatchServer> matches = response.body();
                if(matches.size()>0){
                    MatchServer match_server = matches.get(0);
                    Toast.makeText(getApplicationContext(),new Gson().toJson(match_server),Toast.LENGTH_SHORT).show();
                    dialogLookingFor.dismiss();
                    match = new Match();
                    match.setFirst_name(match_server.getFirst_name());
                    match.setLast_name(match_server.getLast_name());
                    match.setId(match_server.getUser_id());
                    createNewMatchDialog();
                    notifyNewMatch();
                }
            }
            @Override
            public void onFailure(Call<List<MatchServer>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),new Gson().toJson(call),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void notifyNewMatch(){
        mSocket.connect();
        Message m = new Message();
        m.setUser_id2(match.getId());
        m.setUser_id(user_me.getId());
        m.setMessage("@code_13,"+user_me.getFirst_name()+" "+user_me.getLast_name());
        String message = new Gson().toJson(m);
        mSocket.emit("chat", message);
    }
    public void initCounterDown(){
        new CountDownTimer(1000000, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {
                _tv.setText(""+String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                dialogLookingFor.dismiss();
            }
        }.start();
    }
    @Override
    public void onStart() {
        super.onStart();
        active = true;
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),new IntentFilter(MessageService.MESSAGE_RESULT));
    }

    @Override
    public void onStop() {
        active = false;
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }
}
