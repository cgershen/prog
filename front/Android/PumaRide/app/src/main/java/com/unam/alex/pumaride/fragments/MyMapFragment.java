package com.unam.alex.pumaride.fragments;

import android.Manifest;
import android.app.Activity;
import android.graphics.Color;
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
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.unam.alex.pumaride.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyMapFragment extends ComunicationFragmentManager implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMapLongClickListener {
        GoogleMap mGoogleMap;
        GoogleApiClient mGoogleApiClient;
        Location mLastLocation;
        final int ZOOM = 16;
        final String MARKER_TAG = "Mi Ubicacion";
        final int REQUEST_LOCATION = 1;
        private MapView mapView;


    // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;


        public MyMapFragment() {
        // Required empty public constructor
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyMapFragment.
         */
        // TODO: Rename and change types and number of parameters

    public static MyMapFragment newInstance(String param1, String param2) {
        MyMapFragment fragment = new MyMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_map, container, false);
    }*/
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
        if(mGoogleMap!=null)
        {
            PosicionarMapa(latLng.latitude,latLng.longitude);
        }
        else
        {
            Toast.makeText(getContext(),"No hay mapa",Toast.LENGTH_LONG).show();
        }
    }

//    @Override
//    public void onStart(){
//        super.onStart();
//        if (mGoogleMap == null)
//        {
//            SupportMapFragment.newInstance().getMapAsync(this);
//        }
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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


        mGoogleMap.addMarker(new MarkerOptions().position(posicion).title(MARKER_TAG));
        //Movemos la vista del mapa a las cercanias del punto obtenido
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, ZOOM));
        Polyline line = mGoogleMap.addPolyline(new PolylineOptions()
                .add(new LatLng(19.328454, -99.159181), new LatLng(19.325477, -99.161723))
                .width(5)
                .color(Color.RED));
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
                    .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0))
                    .width(5)
                    .color(Color.RED));
        }
        else
        {
           // SupportMapFragment.newInstance().getMapAsync(this);
            Toast.makeText(getContext(),"No hay mapa",Toast.LENGTH_LONG).show();

        }
    }


}
