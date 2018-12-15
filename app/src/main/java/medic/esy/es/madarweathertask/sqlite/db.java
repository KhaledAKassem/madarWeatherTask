package medic.esy.es.madarweathertask.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class db extends SQLiteOpenHelper {

    public static final String DBname ="data.db";
    public db( Context context) {
        super(context, DBname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table mytable (name TEXT ,lat TEXT,log TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS mytable");
        onCreate(sqLiteDatabase);

    }

    public boolean insertData(String name,String lat,String log){
        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("name",name);
        contentValues.put("lat",lat);
        contentValues.put("log",log);

        long result =sqLiteDatabase.insert("mytable",null,contentValues);
        if(result  == -1){
            return false;
        }else {
            return true;
        }
    }
    public ArrayList getFirstRecord(){
        ArrayList arrayList=new ArrayList();
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("select * from mytable",null);
        res.moveToFirst();
        while (res.isAfterLast()==false){
            String t1=res.getString(0);
            arrayList.add(t1);
            res.moveToNext();

        }
        return arrayList;
    }
    public ArrayList getSecondRecord(){
        ArrayList arrayList=new ArrayList();
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("select * from mytable",null);
        res.moveToFirst();
        while (res.isAfterLast()==false){
            String t2=res.getString(1);
            arrayList.add(t2);
            res.moveToNext();
        }
        return arrayList;
    }
    public ArrayList getThirdRecord(){
        ArrayList arrayList=new ArrayList();
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("select * from mytable",null);
        res.moveToFirst();
        while (res.isAfterLast()==false){
            String t1=res.getString(2);
            arrayList.add(t1);
            res.moveToNext();

        }
        return arrayList;
    }

}
