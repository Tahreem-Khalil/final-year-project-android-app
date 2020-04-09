package com.example.appolio;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MapViewofChild extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    Location current_location;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int request_code = 101;
    public Button getloc,addloc;
    RequestQueue requestQueue;
    public static final String HTTP_URL  = "jdbc:mysql://192.168.10.4/coordinates";

    public static final String USER = "forandroid";
    public static final String PASS = "forandroid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_viewof_child);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        requestQueue = Volley.newRequestQueue(MapViewofChild.this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getloc=findViewById(R.id.getCurrentLocButton);
        getloc.setOnClickListener(this);

        addloc=findViewById(R.id.saveLocInDatabaseButton);
        addloc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getCurrentLocButton:
                fetch_last_location();
                getloc.setVisibility(View.INVISIBLE);
                addloc.setVisibility(View.VISIBLE);
                break;
            case R.id.saveLocInDatabaseButton:
                Send objSend = new Send();
                objSend.execute("");
                break;
        }
    }

    private class Send extends AsyncTask<String,String,String> {

        String masg = "";
        @Override
        protected void onPreExecute() {
            Toast.makeText(MapViewofChild.this,"Please wait ",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(HTTP_URL, USER, PASS);
                if (conn == null) {
                    masg = "Connection Loss";
                    Toast.makeText(MapViewofChild.this, "Connection Loss", Toast.LENGTH_SHORT).show();
                } else {
                    String query = "INSERT INTO record (Latitude,Longitude) VALUES('" + current_location.getLatitude() + "','" + current_location.getLongitude() + "')";
                    Statement stam = conn.createStatement();
                    stam.executeUpdate(query);
                    masg = "Data inserted successfully";
//                    Toast.makeText(MapsActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                }
                assert conn != null;
                conn.close();

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            return masg;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(MapViewofChild.this,""+masg,Toast.LENGTH_SHORT).show();
        }
    }


    private void fetch_last_location() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},request_code);
            return;
        }
        Task<Location> task = mFusedLocationClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    current_location = location;
                    Toast.makeText(getApplicationContext(),current_location.getLatitude()+""+current_location.getLongitude(),Toast.LENGTH_LONG).show();
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    assert mapFragment != null;
                    mapFragment.getMapAsync(MapViewofChild.this);
                }
            }
        });
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
        if (current_location!= null){
            LatLng latitiude_logitude = new LatLng(current_location.getLatitude(),current_location.getLongitude());
            MarkerOptions marker_option = new MarkerOptions().position(latitiude_logitude).title("I am here");
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latitiude_logitude));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latitiude_logitude));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latitiude_logitude,18),3000,null );

            mMap.addMarker(marker_option);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case request_code:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    fetch_last_location();
                }
                break;
        }
    }

}
