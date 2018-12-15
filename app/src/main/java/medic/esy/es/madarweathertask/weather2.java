package medic.esy.es.madarweathertask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import medic.esy.es.madarweathertask.adapter.weatherForcastAdapter;
import medic.esy.es.madarweathertask.common.common;
import medic.esy.es.madarweathertask.model.WeatherForcastResult;
import medic.esy.es.madarweathertask.retrofit.IopenWeatherMap;
import medic.esy.es.madarweathertask.retrofit.retrofitClient;
import retrofit2.Retrofit;

public class weather2 extends AppCompatActivity {

    CompositeDisposable compositeDisposable;
    IopenWeatherMap iopenWeatherMap;
    TextView cityName,geocoord;
    RecyclerView recyclerView;

    public weather2(){
      compositeDisposable=new CompositeDisposable();
        Retrofit retrofit= retrofitClient.getInstance();
        iopenWeatherMap=retrofit.create(IopenWeatherMap.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather2);

        cityName=(TextView)findViewById(R.id.txtCityName);
        geocoord=(TextView)findViewById(R.id.txgeocoordination);
        recyclerView =(RecyclerView)findViewById(R.id.RvForecast);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        getForcastweatherInfo();


    }

    private void getForcastweatherInfo() {

        String lat=getIntent().getStringExtra("lat");
        String log=getIntent().getStringExtra("log");
        Toast.makeText(this,lat,Toast.LENGTH_LONG).show();
        compositeDisposable.add(iopenWeatherMap.getForcastWeatherByLatLng(lat,log,common.AppId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<WeatherForcastResult>() {
            @Override
            public void accept(WeatherForcastResult weatherForcastResult) throws Exception {

                displayForecastWeather(weatherForcastResult);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(weather2.this,throwable.getMessage(),Toast.LENGTH_LONG).show();
            }
        }));
    }

    private void displayForecastWeather(WeatherForcastResult weatherForcastResult) {
        cityName.setText(new StringBuilder(weatherForcastResult.city.name));
        geocoord.setText(new StringBuilder(weatherForcastResult.city.coord.toString()));
        weatherForcastAdapter weatherForcastAdapter=new weatherForcastAdapter(this,weatherForcastResult);
        recyclerView.setAdapter(weatherForcastAdapter);
    }
}
