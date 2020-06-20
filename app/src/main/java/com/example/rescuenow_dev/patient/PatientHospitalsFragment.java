package com.example.rescuenow_dev.patient;

import android.Manifest;
import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rescuenow_dev.R;
import com.example.rescuenow_dev.patient.maps.GetNearbyPlacesData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatientHospitalsFragment extends Fragment implements OnMapReadyCallback , GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mGoogleMap;
    private MarkerOptions hospitalOptions = new MarkerOptions();
    private ArrayList<LatLng> latLngs = new ArrayList<>();
    MapView mMapView;
    View mView;
    private Marker currentLocationmMarker;
    private FirebaseAuth mAuth;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 10000;
    double latitude,longitude;
    Button btn_hospitals;
    TextView currentLocation;
    private GoogleApiClient client;
    private LocationRequest locationRequest;



    Map<String, Integer> markers = new HashMap<String,Integer>();


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            bulidGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(getContext(),"Permission Denied" , Toast.LENGTH_LONG).show();
                }
        }
    }

    public PatientHospitalsFragment() {
        // Required empty public constructor
    }

    protected synchronized void bulidGoogleApiClient() {
        client = new GoogleApiClient.Builder(getContext()).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_patient_hospitals, container, false);

        mAuth = FirebaseAuth.getInstance();

        return mView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);

        } else {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            latitude = location.getLatitude();
            longitude = location.getLongitude();

            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title("Current"));
            CameraPosition CurrentLoc = CameraPosition.builder().target(new LatLng(latitude, longitude)).zoom(20).bearing(0).tilt(45).build();

            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CurrentLoc));

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = mView.findViewById(R.id.patient_hospital_map);

        btn_hospitals = view.findViewById(R.id.b_hospitals);

        if(mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

        currentLocation = getActivity().findViewById(R.id.my_location);

        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);

                } else {
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

                    mGoogleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latitude, longitude))
                            .title("Current"));
                    CameraPosition CurrentLoc = CameraPosition.builder().target(new LatLng(latitude, longitude)).zoom(20).bearing(0).tilt(45).build();

                    mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CurrentLoc));

                }
            }
        });


        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            bulidGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }

        btn_hospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object dataTransfer[] = new Object[2];
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();

                switch(v.getId())
                {
                    case R.id.b_hospitals:
                        mGoogleMap.clear();
                        String hospital = "hospital";
                        String url = getUrl(latitude, longitude, hospital);
                        dataTransfer[0] = mGoogleMap;
                        dataTransfer[1] = url;

                        getNearbyPlacesData.execute(dataTransfer);
                        Toast.makeText(getContext(), "Showing Nearby Hospitals", Toast.LENGTH_SHORT).show();
                        break;


                }
            }
        });


    }

    private String getUrl(double latitude , double longitude , String nearbyPlace)
    {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyAHRD7MFO9GYQdS_RFdApxoTPNLTx3dYEg");

        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
