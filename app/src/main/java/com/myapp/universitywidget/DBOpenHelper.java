package com.myapp.universitywidget;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "Lessons.db";
    static final int DATABASE_VERSION = 1;
    final String TABLE_NAME_EVENTS = "events";
    final String COLUMN_NAME_SUBJECT_ID = "subject_id";
    final String COLUMN_NAME_START_TIME = "start_time";
    final String COLUMN_NAME_TYPE = "type";
    final String COLUMN_NAME_NUMBER_PAIR = "number_pair";
    final String COLUMN_NAME_AUDITORY = "auditory";
    final String TABLE_NAME_SUBJECTS = "subjects";
    final String COLUMN_NAME_BRIEF = "brief";
    final String SQL_TABLE_EVENTS_CREATE = "CREATE TABLE "+
            TABLE_NAME_EVENTS+" ("+
            COLUMN_NAME_SUBJECT_ID+" INTEGER,"+
            COLUMN_NAME_START_TIME+" TEXT,"+
            COLUMN_NAME_TYPE+" INTEGER,"+
            COLUMN_NAME_NUMBER_PAIR+" INTEGER,"+
            COLUMN_NAME_AUDITORY+" TEXT);";
    final String SQL_TABLE_SUBJECTS_CREATE = "CREATE TABLE "+
            TABLE_NAME_SUBJECTS+" ("+
            COLUMN_NAME_SUBJECT_ID+ " INTEGER,"+
            COLUMN_NAME_BRIEF+ " TEXT, FOREIGN KEY ("+COLUMN_NAME_SUBJECT_ID+") REFERENCES events("+COLUMN_NAME_SUBJECT_ID+"));";

    DBOpenHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_TABLE_EVENTS_CREATE);
        db.execSQL(SQL_TABLE_SUBJECTS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
