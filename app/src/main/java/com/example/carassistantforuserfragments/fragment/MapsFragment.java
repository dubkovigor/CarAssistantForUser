package com.example.carassistantforuserfragments.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.carassistantforuserfragments.R;
import com.example.carassistantforuserfragments.SendPoints;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.example.carassistantforuserfragments.constants.ConstantsList.*;

public class MapsFragment extends Fragment {


    private Logger logger;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int width = getResources().getDisplayMetrics().widthPixels;

        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        MapView mMapView = view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(Objects.requireNonNull(getActivity()).getApplicationContext());
        } catch (Exception e) {
            logger.wtf(e);
        }

        mMapView.getMapAsync(googleMap -> {
            String destionations = SendPoints.getDestination();
            String origins = SendPoints.getOrigin();
            String via = SendPoints.getVia();

            GeoApiContext geoApiContext = new GeoApiContext.Builder()
                    .apiKey(GOOGLE_KEY)
                    .build();

            try {
                DirectionsResult result = DirectionsApi.newRequest(geoApiContext)
                        .mode(TravelMode.DRIVING)
                        .origin(origins)
                        .destination(destionations)
                        .waypoints(via).await();

                List<LatLng> path = result.routes[0].overviewPolyline.decodePath();
                PolylineOptions line = new PolylineOptions();

                LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();

                for (int i = 0; i < path.size(); i++) {
                    line.add(new com.google.android.gms.maps.model.LatLng(path.get(i).lat, path.get(i).lng));
                    latLngBuilder.include(new com.google.android.gms.maps.model.LatLng(path.get(i).lat, path.get(i).lng));

                }

                line.width(16f).color(R.color.colorPrimary);

                googleMap.addPolyline(line);

                LatLngBounds latLngBounds = latLngBuilder.build();
                CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, width, width, 25);
                googleMap.moveCamera(track);


            } catch (ApiException | InterruptedException | IOException e) {
                logger.wtf(e);
            }
        });

        return view;

    }
}
