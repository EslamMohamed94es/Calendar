package com.example.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDataBqase extends SQLiteOpenHelper {
    public static final String dbname = "eventitems";
    public static final int version = 5;

    public SQLiteDataBqase(Context context) {
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table IF NOT EXISTS items(id INTEGER primary key,title TEXT,description TEXT," +
                "location TEXT,startyear INTEGER,startmonth INTEGER,startday INTEGER," +
                "endyear INTEGER,endmonth INTEGER,endday INTEGER," +
                "starthour INTEGER,startminute INTEGER,startAPM TEXT," +
                "endhour INTEGER,endminute INTEGER,endAPM TEXT,allday INTEGER," +
                "reminder INTEGER,notification INTEGER,vibrate INTEGER,StartHour1 Integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if EXISTS items");
        onCreate(db);
    }

    public void insertitem(String Title, String Desc, String Location, Integer styear, Integer stmonth, Integer stday,
                           Integer edyear, Integer edmonth, Integer edday, Integer sthour, Integer stminute, String stAPM,
                           Integer edhour, Integer edminute, String edAPM, Integer allDay, Integer Remin, Integer NOTIF, Integer Vibrate, Integer Start_Hour) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", Title);
        contentValues.put("description", Desc);
        contentValues.put("location", Location);
        contentValues.put("startyear", styear);
        contentValues.put("startmonth", stmonth);
        contentValues.put("startday", stday);
        contentValues.put("endyear", edyear);
        contentValues.put("endmonth", edmonth);
        contentValues.put("endday", edday);
        contentValues.put("starthour", sthour);
        contentValues.put("startminute", stminute);
        contentValues.put("startAPM", stAPM);
        contentValues.put("endhour", edhour);
        contentValues.put("endminute", edminute);
        contentValues.put("endAPM", edAPM);
        contentValues.put("allday", allDay);
        contentValues.put("reminder", Remin);
        contentValues.put("notification", NOTIF);
        contentValues.put("vibrate", Vibrate);
        contentValues.put("StartHour1", Start_Hour);
        database.insert("items", null, contentValues);
        database.close();
    }

    public Cursor getallrecords() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from items", null);
        cursor.moveToFirst();
        database.close();
        return cursor;
    }

    public Cursor getallrecordsbyID(String ID) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] id = {ID};
        Cursor cursor = database.rawQuery("select * from items where id like ?", id);
        cursor.moveToFirst();
        database.close();
        return cursor;
    }

    public void delete(String ID) {
        String where = "id=?";
        SQLiteDatabase database = this.getWritableDatabase();
        int numberOFEntriesDeleted = database.delete("items", where, new String[]{ID});
        database.close();
    }


    public void update(String ID, String Title, String Desc, String Location, Integer styear, Integer stmonth, Integer stday,
                       Integer edyear, Integer edmonth, Integer edday, Integer sthour, Integer stminute, String stAPM,
                       Integer edhour, Integer edminute, String edAPM, Integer allDay, Integer Remin, Integer NOTIF, Integer Vibrate, Integer Start_Hour) {
        String where = "id=?";
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", Title);
        contentValues.put("description", Desc);
        contentValues.put("location", Location);
        contentValues.put("startyear", styear);
        contentValues.put("startmonth", stmonth);
        contentValues.put("startday", stday);
        contentValues.put("endyear", edyear);
        contentValues.put("endmonth", edmonth);
        contentValues.put("endday", edday);
        contentValues.put("starthour", sthour);
        contentValues.put("startminute", stminute);
        contentValues.put("startAPM", stAPM);
        contentValues.put("endhour", edhour);
        contentValues.put("endminute", edminute);
        contentValues.put("endAPM", edAPM);
        contentValues.put("allday", allDay);
        contentValues.put("reminder", Remin);
        contentValues.put("notification", NOTIF);
        contentValues.put("vibrate", Vibrate);
        contentValues.put("StartHour1", Start_Hour);
        database.update("items", contentValues, where, new String[]{ID});
        database.close();
    }

}
