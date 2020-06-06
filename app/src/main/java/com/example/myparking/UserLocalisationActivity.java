package com.example.myparking;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


public class UserLocalisationActivity extends FragmentActivity implements OnMapReadyCallback {
    LocationManager locationManager;
    LocationListener locationListener;
    LatLng userLatLong;
    private GoogleMap mMap;
    MarkerOptions pl1,pl2,pl3,userlocalisation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_localisation);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (ActivityCompat.checkSelfPermission(UserLocalisationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(UserLocalisationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);//activer votre localisation
                LatLng park1 = new LatLng(31.934507,-4.42506);
                LatLng park2 = new LatLng(31.932739,-4.427401);
                LatLng park3 = new LatLng(31.927330,-4.424343);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLong,13));

                pl1 = new MarkerOptions().position(park1).title("Préfecture parking");
                mMap.addMarker(pl1);
                pl2 =new MarkerOptions().position(park2).title("Place Hassan 2 parking");
                mMap.addMarker(pl2);
                pl3=new MarkerOptions().position(park3).title("Souk El Khmiss parking");
                mMap.addMarker(pl3);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(park1));
                userLatLong = new LatLng(location.getLatitude(), location.getLongitude());
                userlocalisation = new MarkerOptions().position(userLatLong).title("Votre Localisation");


                /*
                // Enregistrer la localisation de l utilisateur
                userLatLong = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.clear();                            //Supprimer l ancien marqueur de localisation dans google Map
                mMap.addMarker(new MarkerOptions().position(userLatLong).title("Votre Localisation"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLong));*/

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        //demander l autorisation d acces a la localisation
        askLocationPermission();

    }


            public void onStatusChanged(String provider, int status, Bundle extras) {

            }


            public void onProviderEnabled(String provider) {

            }


            public void onProviderDisabled(String provider) {

            }

            private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    private void askLocationPermission() {
        Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
               // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                mMap.setMyLocationEnabled(true);//activer votre localisation
                LatLng park1 = new LatLng(31.934507,-4.42506);
                LatLng park2 = new LatLng(31.932789,-4427441);
                LatLng park3 = new LatLng(31.927330,-4.424343);

                mMap.addMarker(new MarkerOptions().position(park1).title("Préfecture parking"));
                mMap.addMarker(new MarkerOptions().position(park2).title("Place Hassan 2 parking"));
                mMap.addMarker(new MarkerOptions().position(park3).title("Souk El Khmiss parking"));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(park1));

               /*  //Avoir la derniere localisation de l utilisateur pour modifier le marquer de localisation dans le Map
                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
               userLatLong = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                mMap.clear();                            //Supprimer l ancien marqueur de localisation dans google Map
                mMap.addMarker(new MarkerOptions().position(userLatLong).title("Votre Localisation"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLong));
                */

            }


            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

}