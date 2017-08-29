package uk.co.vortexsoftware.dev.bender;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private Location mlocation;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    double currentLongitude;
    double currentLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        try {
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
                overridePendingTransition(R.anim.push_up_in,
                        R.anim.push_up_out);
            } else {
                mMapFragment.getMapAsync(this);
                overridePendingTransition(R.anim.push_up_out,
                        R.anim.push_up_in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e( "ERROR" , "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e( "ERROR" , "Can't find style. Error: ", e);
        }
        // Position the map's camera to Dronfield.
        float zoomLevel = (float) 16.0;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.3134690,-1.4574910), zoomLevel));


        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
