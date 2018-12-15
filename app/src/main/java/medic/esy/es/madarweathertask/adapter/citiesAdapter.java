package medic.esy.es.madarweathertask.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import medic.esy.es.madarweathertask.R;
import medic.esy.es.madarweathertask.weather2;

public class citiesAdapter extends RecyclerView.Adapter<citiesAdapter.MyViewHolder>  {
    private List<String>cities;
    private List<String>lat;
    private List<String>lon;
    private  Context context;


    public citiesAdapter(List<String>cities, List<String> lat, List<String> lon, Context context){
        this.cities=cities;
        this.lat=lat;
        this.lon=lon;
        this.context=context;
    }


    @NonNull
    @Override
    public citiesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull citiesAdapter.MyViewHolder holder, final int position) {


        holder.cityName.setText(cities.get(position));
        holder.rootCard.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,weather2.class);
                i.putExtra("lat",""+lat.get(position));
                i.putExtra("log",""+lon.get(position));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView cityName;
        private LinearLayout rootCard;

        public MyViewHolder(View view) {
            super(view);
            cityName = (TextView) view.findViewById(R.id.cityName);
            rootCard = (LinearLayout) view.findViewById(R.id.rootCard);
        }
    }
}
