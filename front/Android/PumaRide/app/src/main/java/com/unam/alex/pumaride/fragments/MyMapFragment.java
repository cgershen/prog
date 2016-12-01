package com.unam.alex.pumaride.fragments;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.unam.alex.pumaride.R;
import com.unam.alex.pumaride.models.Match;
import com.unam.alex.pumaride.models.Route;
import com.unam.alex.pumaride.models.Route2;
import com.unam.alex.pumaride.models.Route3;
import com.unam.alex.pumaride.retrofit.WebServices;
import com.unam.alex.pumaride.utils.Statics;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyMapFragment extends ComunicationFragmentManager implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMapLongClickListener {
        GoogleMap mGoogleMap;
        GoogleApiClient mGoogleApiClient;
        Location mLastLocation;
        final int ZOOM = 16;
        final String MARKER_TAG = "Mi Ubicacion";
        final int REQUEST_LOCATION = 1;
        private MapView mapView;
        private Marker mMarker1 = null;
        private Marker mMarker2 = null;
        private Polyline line = null;
        private int clickCounter = 0;


        public MyMapFragment() {
        }

    public static MyMapFragment newInstance(String param1, String param2) {
        MyMapFragment fragment = new MyMapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //set title in actionbar
        mListener.onFragmentInteraction("Home");
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_my_map, container, false);

        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        mapView = (MapView) view.findViewById(R.id.mapFragment);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Toast.makeText(getContext(),"No hay mapa",Toast.LENGTH_LONG).show();
        if(mGoogleMap!=null)
        {
            PosicionarMapa(latLng.latitude,latLng.longitude);
        }
        else
        {
            Toast.makeText(getContext(),"No hay mapa",Toast.LENGTH_LONG).show();
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
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
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "No se tienen Permisos", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            return;
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            PosicionarMapa(mLastLocation.getLatitude(), mLastLocation.getLongitude());
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

   public void TrazaCamino(int opc)
    {
        if (mGoogleMap!= null) {
            mGoogleMap.clear();
            // Add a thin red line from London to New York.
            Polyline line = mGoogleMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(19.3585,-99.166), new LatLng(19.358643,-99.167367))
                    .add(new LatLng(19.358778,-99.170101), new LatLng(19.359095,-99.170657))
                    .add(new LatLng(19.359769,-99.171687), new LatLng(19.359574,-99.171785))
                    .add(new LatLng(19.359378,-99.171886), new LatLng(19.358691,-99.170834))
                    .add(new LatLng(19.358549,-99.170579), new LatLng(19.358414,-99.170421))
                    .add(new LatLng(19.358301,-99.170316), new LatLng(19.358209,-99.169377))
                    .add(new LatLng(19.358109,-99.167988), new LatLng(19.358102,-99.167041), new LatLng(19.358053,-99.166553),
                            new LatLng(19.357982,-99.165096), new LatLng(19.357924,-99.163687), new LatLng(19.357307,-99.163733),
                            new LatLng(19.357026,-99.163743), new LatLng(19.356409,-99.163733), new LatLng(19.356156,-99.163733),
                            new LatLng(19.355508,-99.163675), new LatLng(19.354575,-99.163664), new LatLng(19.353664,-99.163745),
                            new LatLng(19.35328,-99.163733), new LatLng(19.352731,-99.163768), new LatLng(19.352259,-99.163815),
                            new LatLng(19.351767,-99.163859), new LatLng(19.351254,-99.163957), new LatLng(19.35059,-99.164136),
                            new LatLng(19.349086,-99.164484), new LatLng(19.348371,-99.164617), new LatLng(19.347631,-99.164751),
                            new LatLng(19.347076,-99.16484), new LatLng(19.346959,-99.164858), new LatLng(19.346261,-99.164974),
                            new LatLng(19.346143,-99.165009), new LatLng(19.344721,-99.16525), new LatLng(19.344498,-99.165243),
                            new LatLng(19.34442,-99.164639), new LatLng(19.344392,-99.164313), new LatLng(19.344181,-99.162272),
                            new LatLng(19.344003,-99.160926), new LatLng(19.343814,-99.159547), new LatLng(19.343722,-99.158754),
                            new LatLng(19.343502,-99.156851), new LatLng(19.343419,-99.156197), new LatLng(19.343377,-99.155885),
                            new LatLng(19.342987,-99.155556), new LatLng(19.342192,-99.15478), new LatLng(19.34185,-99.154355),
                            new LatLng(19.34185,-99.154346), new LatLng(19.341592,-99.154074), new LatLng(19.340488,-99.15303),
                            new LatLng(19.340301,-99.152832), new LatLng(19.340132,-99.152686), new LatLng(19.339997,-99.152526),
                            new LatLng(19.339334,-99.152858), new LatLng(19.33897,-99.153033), new LatLng(19.33872,-99.153137),
                            new LatLng(19.338416,-99.153232), new LatLng(19.337954,-99.153384), new LatLng(19.33737,-99.153559),
                            new LatLng(19.337036,-99.153678), new LatLng(19.336626,-99.153782), new LatLng(19.336384,-99.15379),
                            new LatLng(19.336186,-99.153813), new LatLng(19.336043,-99.153824), new LatLng(19.335717,-99.153805),
                            new LatLng(19.334973,-99.154331), new LatLng(19.334868,-99.154244), new LatLng(19.33476,-99.154228),
                            new LatLng(19.334371,-99.154225), new LatLng(19.334858,-99.156298), new LatLng(19.334883,-99.156462),
                            new LatLng(19.334907,-99.157821), new LatLng(19.335037,-99.159577), new LatLng(19.335053,-99.159956),
                            new LatLng(19.335094,-99.160566), new LatLng(19.335257,-99.161076), new LatLng(19.33528,-99.161507),
                            new LatLng(19.335315,-99.162031), new LatLng(19.335353,-99.162644), new LatLng(19.335399,-99.163205),
                            new LatLng(19.335415,-99.163433), new LatLng(19.335438,-99.16377), new LatLng(19.33545,-99.163994),
                            new LatLng(19.335465,-99.164156), new LatLng(19.335503,-99.164688), new LatLng(19.335515,-99.164867),
                            new LatLng(19.335522,-99.165066), new LatLng(19.33553,-99.165217), new LatLng(19.335548,-99.165377),
                            new LatLng(19.335613,-99.166143), new LatLng(19.335646,-99.166564), new LatLng(19.335646,-99.166693),
                            new LatLng(19.33567,-99.166934), new LatLng(19.33567,-99.167037), new LatLng(19.335678,-99.167132))

                    .width(7)
                    .color(Color.RED));

            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(19.33567,-99.166934), 13));
        }
        else
        {
           // SupportMapFragment.newInstance().getMapAsync(this);
            Toast.makeText(getContext(),"No hay mapa",Toast.LENGTH_LONG).show();

        }
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
