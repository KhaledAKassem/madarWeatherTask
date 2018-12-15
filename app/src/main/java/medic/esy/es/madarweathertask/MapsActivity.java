package medic.esy.es.madarweathertask;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import medic.esy.es.madarweathertask.sqlite.db;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button b;
    private TextView numberOfMarker;
    public String latitude;
    public String longtitude;
    public List<Address> Titles;
    public String Title;
    public static int counter=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        b=(Button)findViewById(R.id.saveLocation);
        numberOfMarker=(TextView)findViewById(R.id.numberOfMarker);
        numberOfMarker.setClickable(false);




        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MapsActivity.this,MainActivity.class);
                i.putExtra("lat",latitude);
                i.putExtra("log",longtitude);
                i.putExtra("title",Title);
                counter=1;
                startActivity(i);
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.8065273, 31.0392434), 8));


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions marker = new MarkerOptions().position(
                        latLng);
                numberOfMarker.setText(counter+"");

              latitude= String.valueOf(marker.getPosition().latitude);
              longtitude= String.valueOf(marker.getPosition().longitude);
                Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                try {
                    Titles=new ArrayList<>();
                    double la= Double.parseDouble(latitude);
                    double lo= Double.parseDouble(longtitude);
                    Titles = geocoder.getFromLocation(la, lo,1);
                    Title= Titles.get(0).getAddressLine(0);
                    counter++;
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                Toast.makeText(MapsActivity.this,latitude+ "",Toast.LENGTH_LONG).show();
//                Toast.makeText(MapsActivity.this,longtitude+ "",Toast.LENGTH_LONG).show();

                marker.icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                mMap.addMarker(marker);
                db dbb =new db(MapsActivity.this);
                Boolean result= dbb.insertData(Title,latitude+"",longtitude+"");
                if(result){
                    Toast.makeText(MapsActivity.this,"OK",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MapsActivity.this,"No",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
