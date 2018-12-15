package medic.esy.es.madarweathertask.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class common {

    public static final String AppId="6cdfae525b49d2e6fa71b8d1c472c39a";

    public static String convertUnixtoDate(long dt){
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm dd EEE MM yyyy");
        String formatted =sdf.format(date);
        return formatted;
    }

    public static String convertUnixtoHour(long dt){
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        String formatted =sdf.format(date);
        return formatted;
    }
}
