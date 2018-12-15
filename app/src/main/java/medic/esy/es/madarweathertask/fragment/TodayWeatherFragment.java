package medic.esy.es.madarweathertask.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import medic.esy.es.madarweathertask.R;
import medic.esy.es.madarweathertask.common.common;
import medic.esy.es.madarweathertask.model.WeatherResult;
import medic.esy.es.madarweathertask.retrofit.IopenWeatherMap;
import medic.esy.es.madarweathertask.retrofit.retrofitClient;
import retrofit2.Retrofit;


public class TodayWeatherFragment extends Fragment {



    ImageView imgWeather;
    TextView cityName,humidity,sunrise,sunset,presure,temperature,description,dateTime,wind,geo;

    LinearLayout weatherPanel;
    ProgressBar loading;

    CompositeDisposable compositeDisposable;
    IopenWeatherMap iopenWeatherMap;
    public TodayWeatherFragment(){
        compositeDisposable= new CompositeDisposable();
        Retrofit retrofit=retrofitClient.getInstance();
        iopenWeatherMap=retrofit.create(IopenWeatherMap.class);
    }

    static TodayWeatherFragment instance;



    public static TodayWeatherFragment getInstance() {

        if(instance==null ){
            instance=new TodayWeatherFragment();

        }
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root =inflater.inflate(R.layout.fragment_today_weather, container, false);
        imgWeather=root.findViewById(R.id.ImageWeather);
        cityName=root.findViewById(R.id.txCityName);
        humidity=root.findViewById(R.id.humidity);
        sunrise=root.findViewById(R.id.sunrise);
        sunset=root.findViewById(R.id.Sunset);
        presure=root.findViewById(R.id.pressure);
        temperature=root.findViewById(R.id.textTemperature);
        description=root.findViewById(R.id.textDescription);
        dateTime=root.findViewById(R.id.txDateTime);
        wind=root.findViewById(R.id.wind);
        geo=root.findViewById(R.id.geoCoords);

        weatherPanel=root.findViewById(R.id.weather1);
        loading=root.findViewById(R.id.loading);

        weatherInfo();

        return root;
    }

    private void weatherInfo() {


        compositeDisposable.add(iopenWeatherMap.getWeatherByLatLng("30.97","31.16", common.AppId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherResult) throws Exception {

                       // Loading Data

                        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                        .append(weatherResult.getWeather().get(0).getIcon()).append(".png").toString()).into(imgWeather);
                        cityName.setText(weatherResult.getName());
                        description.setText(new StringBuilder("Weather in ").append(weatherResult.getName()).toString());
                        temperature.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())).append(" ").append("C°").toString());
                        dateTime.setText(common.convertUnixtoDate(weatherResult.getDt()));
                        presure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append("hpa").toString());
                        humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append("%").toString());
                        sunrise.setText(common.convertUnixtoHour(weatherResult.getSys().getSunrise()));
                        sunset.setText(common.convertUnixtoHour(weatherResult.getSys().getSunset()));
                        geo.setText(new StringBuilder("[").append(weatherResult.getCoord().toString()).append("]").toString());

                        //Display Panel

                        weatherPanel.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), ""+ throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }
}
