package medic.esy.es.madarweathertask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import medic.esy.es.madarweathertask.adapter.citiesAdapter;
import medic.esy.es.madarweathertask.sqlite.db;

public class MainActivity extends AppCompatActivity {

    private Button openMapButton;
    private RecyclerView recycleForCities;
    public static List<String>  cities;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> r1 = new ArrayList<String>();
        ArrayList<String> r2 = new ArrayList<String>();
        ArrayList<String> r3 = new ArrayList<String>();
        db qw =new db(MainActivity.this);
        r1 = qw.getFirstRecord();
        r2=qw.getSecondRecord();
        r3=qw.getThirdRecord();



        cities = new ArrayList<>();
        String title=getIntent().getStringExtra("title");
        cities.add("Cairo");
        cities.add("Tanta");
        cities.add("Alexendria");
        cities.add("Mansoura");
        cities.add("Aswan");
        cities.addAll(r1);



        List<String> lat = new ArrayList<>();
        lat.add("30.06");
        lat.add("30.79");
        lat.add("31.22");
        lat.add("31.04");
        lat.add("24.09");
        lat.addAll(r2);

        List<String> log = new ArrayList<>();
        log.add("31.25");
        log.add("31");
        log.add("29.96");
        log.add("31.38");
        log.add("32.91");
        log.addAll(r3);

        openMapButton=(Button)findViewById(R.id.openMapButton);
        recycleForCities=(RecyclerView)findViewById(R.id.rvLocation);
        recycleForCities.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycleForCities.setLayoutManager(mLayoutManager);
        recycleForCities.setItemAnimator(new DefaultItemAnimator());
        recycleForCities.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        citiesAdapter adapter = new citiesAdapter(cities,lat,log,this);
        recycleForCities.setAdapter(adapter);

        openMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if(isConnected()){
                        startActivity(new Intent(MainActivity.this,MapsActivity.class));

                    }else{
                        Toast.makeText(MainActivity.this,"Check Internet Connection and try press again",Toast.LENGTH_LONG).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public boolean isConnected() throws InterruptedException, IOException {
        final String command = "ping -c 1 google.com";
        return Runtime.getRuntime().exec(command).waitFor() == 0;
    }
}
