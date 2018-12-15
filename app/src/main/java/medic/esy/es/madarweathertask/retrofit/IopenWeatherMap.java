package medic.esy.es.madarweathertask.retrofit;


import io.reactivex.Observable;
import medic.esy.es.madarweathertask.model.WeatherForcastResult;
import medic.esy.es.madarweathertask.model.WeatherResult;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IopenWeatherMap {

    @GET("weather")
    Observable<WeatherResult> getWeatherByLatLng(@Query("lat")String lat,
                                                 @Query("lon")String lng,
                                                 @Query("appid")String appid);

    @GET("forecast")
    Observable<WeatherForcastResult> getForcastWeatherByLatLng(@Query("lat")String lat,
                                                               @Query("lon")String lng,
                                                               @Query("appid")String appid
                                                               );

}
