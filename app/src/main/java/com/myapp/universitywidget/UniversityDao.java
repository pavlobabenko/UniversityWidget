package com.myapp.universitywidget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UniversityDao {

    DBOpenHelper dbOpenHelper;

    UniversityDao(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    public void insert(String jsonString) throws JSONException {
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        JSONObject jsonObjectEvents = new JSONObject(jsonString);
        JSONArray jsonArrayEvents = jsonObjectEvents.getJSONArray("events");
        JSONArray jsonArraySubjects = jsonObjectEvents.getJSONArray("subjects");
        database.beginTransaction();
        try {
            for (int i = 0; i < jsonArrayEvents.length(); i++) {
                JSONObject eventJSONObject = jsonArrayEvents.getJSONObject(i);
                ContentValues eventValues = new ContentValues();
                Date date = new Date(Long.parseLong(Long.toString(eventJSONObject.getLong("start_time"))) * 1000);
                String dateString = dateFormat.format(date);
                eventValues.put("subject_id", eventJSONObject.getInt("subject_id"));
                eventValues.put("start_time", dateString);
                eventValues.put("type", eventJSONObject.getInt("type"));
                eventValues.put("number_pair", eventJSONObject.getInt("number_pair"));
                eventValues.put("auditory", eventJSONObject.getString("auditory"));
                database.insert("events", null, eventValues);
            }
            for (int i = 0; i < jsonArraySubjects.length(); i++) {
                JSONObject subjectJSONObject = jsonArraySubjects.getJSONObject(i);
                ContentValues subjectValues = new ContentValues();
                subjectValues.put("subject_id", subjectJSONObject.getInt("id"));
                subjectValues.put("brief", subjectJSONObject.getString("brief"));
                database.insert("subjects", null, subjectValues);
            }
            database.execSQL("DELETE FROM subjects WHERE " +
                    "subject_id=1052981 " +
                    "OR subject_id=3802821 " +
                    "OR subject_id=5259245 " +
                    "OR subject_id=5435967 " +
                    "OR subject_id=5721560 " +
                    "OR subject_id=5721594 " +
                    "OR subject_id=5721625 " +
                    "OR subject_id=5767505 " +
                    "OR subject_id=5767507; ");
            database.execSQL("DELETE FROM events WHERE " +
                    "subject_id=1052981 " +
                    "OR subject_id=3802821 " +
                    "OR subject_id=5259245 " +
                    "OR subject_id=5435967 " +
                    "OR subject_id=5721560 " +
                    "OR subject_id=5721594 " +
                    "OR subject_id=5721625 " +
                    "OR subject_id=5767505 " +
                    "OR subject_id=5767507; ");
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
        database.close();
    }

    public Event[] readTodayEvents() {
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 15);
        Event[] events = new Event[7];
        String actualDate;
        if (calendar.before(Calendar.getInstance())) {
            actualDate = dateFormat.format(new Date(date.getTime()+(1000*60*60*24)));
        } else {
            actualDate = dateFormat.format(date);
        }
        Cursor queryCursor = database.rawQuery("SELECT events.type,events.number_pair,events.auditory,subjects.brief FROM events,subjects WHERE events.subject_id = subjects.subject_id AND events.start_time = '" + actualDate + "';", null);
        while (queryCursor.moveToNext()) {
            events[queryCursor.getInt(queryCursor.getColumnIndex("number_pair"))] = new Event(queryCursor.getInt(queryCursor.getColumnIndex("type")), queryCursor.getString(queryCursor.getColumnIndex("auditory")), queryCursor.getString(queryCursor.getColumnIndex("brief")));
        }
        queryCursor.close();
        database.close();
        return events;
    }
    public Event[] readNextDayEvents(String date) {
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Event[] events = new Event[7];
        Cursor queryCursor = database.rawQuery("SELECT events.type,events.number_pair,events.auditory,subjects.brief FROM events,subjects WHERE events.subject_id = subjects.subject_id AND events.start_time = '" + date + "';", null);
        while (queryCursor.moveToNext()) {
            events[queryCursor.getInt(queryCursor.getColumnIndex("number_pair"))] = new Event(queryCursor.getInt(queryCursor.getColumnIndex("type")), queryCursor.getString(queryCursor.getColumnIndex("auditory")), queryCursor.getString(queryCursor.getColumnIndex("brief")));
        }
        queryCursor.close();
        database.close();
        return events;
    }

    public boolean isEmpty() {
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        return !database.rawQuery("SELECT * FROM events;", null).moveToNext();
    }
}
