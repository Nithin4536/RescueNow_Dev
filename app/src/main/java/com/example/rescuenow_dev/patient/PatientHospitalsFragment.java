package com.example.rescuenow_dev.patient;

import android.Manifest;
import android.Manifest.permission;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rescuenow_dev.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatientHospitalsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latLngs = new ArrayList<>();
    MapView mMapView;
    View mView;
    private FirebaseAuth mAuth;
    private String currentUserID;
    TextView currentLocation;
    Double latitude, longitude;



    Map<String, Integer> markers = new HashMap<String,Integer>();


    public PatientHospitalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_patient_hospitals, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        return mView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap = googleMap;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = mView.findViewById(R.id.patient_hospital_map);

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
                        permission.ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
                    requestPermissions(new String[]{permission.ACCESS_COARSE_LOCATION}, 1000);

                } else {
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    CameraPosition myCurrentLocation = CameraPosition.builder().target(new LatLng(latitude, longitude))
                            .zoom(15).bearing(0).tilt(45).build();

                    mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(myCurrentLocation));

                }
            }
        });

    }

}
