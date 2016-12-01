package com.unam.alex.pumaride;

import android.*;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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
import com.unam.alex.pumaride.models.Route2;
import com.unam.alex.pumaride.retrofit.WebServices;
import com.unam.alex.pumaride.utils.Statics;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private final int ZOOM = 16;
    private Marker mMarker1 = null;
    private Marker mMarker2 = null;
    private Polyline line = null;
    private int clickCounter = 0;
    private final int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
            @Override
            public void onMapClick(LatLng latLng) {
                switch (clickCounter){
                    case 2:
                        clickCounter = 0;
                        mMarker1.remove();
                        mMarker2.remove();
                        line.remove();
                    case 0:
                        //mMarker1 = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Source").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle_green_a_24px)));
                        mMarker1 = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Source").icon(BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.ic_person_pin_circle_green_a_24px))));
                        break;
                    case 1:
                        //mMarker2 = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Target").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle_orange_b_24px)));
                        mMarker2 = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Target").icon(BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.ic_person_pin_circle_orange_b_24px))));
                        drawPoliLineFromServer();
                        break;
                }
                clickCounter+=1;
            }
        });
    }
    public void drawPoliLineFromServer(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Statics.AUXILIAR_SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebServices webServices = retrofit.create(WebServices.class);
        String source,target;
        source = "\"("+mMarker1.getPosition().longitude+","+mMarker1.getPosition().latitude+")\"";
        target = "\"("+mMarker2.getPosition().longitude+","+mMarker2.getPosition().latitude+")\"";

        Call<Route2> call = webServices.getShortestPath(source,target);
        call.enqueue(new Callback<Route2>() {
            @Override
            public void onResponse(Call<Route2> call, Response<Route2> response) {
                Toast.makeText(getApplicationContext(),new Gson().toJson(response.body()),Toast.LENGTH_SHORT).show();
                ArrayList<LatLng> positions = new ArrayList<LatLng>();
                Route2 res  = response.body();
                for(float[] r:res.getShortest_path()){
                    LatLng latlng = new LatLng(r[1],r[0]);
                    positions.add(latlng);
                }
                line = mGoogleMap.addPolyline(new PolylineOptions()
                        .addAll(positions)
                        .width(5)
                        .color(Color.RED));
            }
            @Override
            public void onFailure(Call<Route2> call, Throwable t) {
                Toast.makeText(getApplicationContext(),new Gson().toJson(call),Toast.LENGTH_SHORT).show();
            }
        });
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
}
