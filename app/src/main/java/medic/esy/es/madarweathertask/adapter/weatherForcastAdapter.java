package medic.esy.es.madarweathertask.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import medic.esy.es.madarweathertask.R;
import medic.esy.es.madarweathertask.common.common;
import medic.esy.es.madarweathertask.model.WeatherForcastResult;

public class weatherForcastAdapter extends RecyclerView.Adapter<weatherForcastAdapter.ViewHolder> {

    Context context;
    WeatherForcastResult weatherForcastResult;

    public weatherForcastAdapter(Context context, WeatherForcastResult weatherForcastResult) {
        this.context = context;
        this.weatherForcastResult = weatherForcastResult;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

       TextView dateTime,desc,temperature;
       ImageView imgWeather;
       public ViewHolder(View itemView) {
           super(itemView);

           dateTime=(TextView)itemView.findViewById(R.id.textDate2);
           desc=(TextView)itemView.findViewById(R.id.txdescript2);
           temperature=(TextView)itemView.findViewById(R.id.textTemperature2);
           imgWeather=(ImageView) itemView.findViewById(R.id.ImageWeather2);



       }
   }

    @NonNull
    @Override
    public weatherForcastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardforecast, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull weatherForcastAdapter.ViewHolder holder, int position) {

       //Load Icon
        // Load Image

        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                .append(weatherForcastResult.list.get(position).weather.get(0).getIcon()).append(".png").toString()).into(holder.imgWeather);

        holder.dateTime.setText(new StringBuilder(common.convertUnixtoDate(weatherForcastResult.list.get(position).dt)));

        holder.desc.setText(new StringBuilder(weatherForcastResult.list.get(position).weather.get(0).getDescription()));
        holder.temperature.setText(new StringBuilder(String.valueOf(weatherForcastResult.list.get(position).main.getTemp())).append("C"));
    }

    @Override
    public int getItemCount() {
        return weatherForcastResult.list.size();
    }
}
