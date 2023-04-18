package com.example.upkeep.chat_fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

    public class Dbhelper extends SQLiteOpenHelper {

        // Database Version
        private static final int DATABASE_VERSION = 1;

        // Database Name
        private static final String DATABASE_NAME = "notes_db";


        public Dbhelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // Creating Tables
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            // create notes table
            db.execSQL(Note.CREATE_TABLE);
        }

        public void createatable()
        {
            SQLiteDatabase db = this.getWritableDatabase();
            // create notes table
            db.execSQL(Note.CREATE_TABLE);
        }

        // Upgrading database
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);

            // Create tables again
            onCreate(db);
        }

        public void droptable()
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);
        }

        public long insertNote(String username,String email,String lastmesg,String readcount,String lastMesgTime)
        {
            // get writable database as we want to write data
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            // `id` and `timestamp` will be inserted automatically.
            // no need to add them
            values.put(Note.COLUMN_USERNAME, username);
            values.put(Note.COLUMN_EMAIL, email);
            values.put(Note.LAST_MESSAGE, lastmesg);
            values.put(Note.COUNT, readcount);
            values.put(Note.LAST_MESAGE_TIME, lastMesgTime);

            // insert row
            long id = db.insert(Note.TABLE_NAME, null, values);

            // close db connection
            db.close();

            // return newly inserted row id
            return id;
        }

        public ArrayList<String> getNote(String email)
        {
            ArrayList note = new ArrayList();
            // get readable database as we are not inserting anything
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM "+Note.TABLE_NAME+" WHERE "+Note.COLUMN_EMAIL+"= '"+email.trim()+"'", null);
            if (c.moveToFirst())
            {
                do
                {
                    // Passing values
                    note.add(c.getString(c.getColumnIndex(Note.COLUMN_USERNAME)));
                    note.add(c.getString(c.getColumnIndex(Note.COLUMN_EMAIL)));
                    note.add(c.getString(c.getColumnIndex(Note.LAST_MESSAGE)));
                    note.add(c.getString(c.getColumnIndex(Note.LAST_MESAGE_TIME)));
                    note.add(c.getString(c.getColumnIndex(Note.COUNT)));
                }
                while(c.moveToNext());
            }
            c.close();
            db.close();
            return note;
        }

        public ArrayList<ArrayList<String>> getAllData()
        {
            ArrayList<ArrayList<String>> note = new ArrayList<ArrayList<String>>();
            // get readable database as we are not inserting anything
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM "+Note.TABLE_NAME, null);
            if (c.moveToFirst())
            {
                do
                {
                    ArrayList note1 = new ArrayList();
                    // Passing values
                    note1.add(c.getString(c.getColumnIndex(Note.COLUMN_USERNAME)));
                    note1.add(c.getString(c.getColumnIndex(Note.COLUMN_EMAIL)));
                    note1.add(c.getString(c.getColumnIndex(Note.LAST_MESSAGE)));
                    note1.add(c.getString(c.getColumnIndex(Note.LAST_MESAGE_TIME)));
                    note1.add(c.getString(c.getColumnIndex(Note.COUNT)));

                    note.add(note1);
                }
                while(c.moveToNext());
            }
            c.close();
            db.close();
            return note;
        }

        public ArrayList<String> getAllData_username()
        {
            ArrayList note1 = new ArrayList();
            // get readable database as we are not inserting anything
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM "+Note.TABLE_NAME, null);
            if (c.moveToFirst())
            {
                do
                {

                    // Passing values
                    note1.add(c.getString(c.getColumnIndex(Note.COLUMN_USERNAME)));
                }
                while(c.moveToNext());
            }
            c.close();
            db.close();
            return note1;
        }

        public void updataData(String username,String lastmsg,String lastmsgtime)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Note.LAST_MESSAGE, lastmsg);
            values.put(Note.LAST_MESAGE_TIME, lastmsgtime);
            db.update(Note.TABLE_NAME, values, Note.COLUMN_USERNAME+"='"+username+"'", null);
        }
        //FOR UPDATING READ COUNT
        public void update_read_count(String username,String read_count,String lastname,String time)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Note.COUNT, read_count);
            values.put(Note.LAST_MESSAGE, lastname);
            values.put(Note.LAST_MESAGE_TIME, time);
            db.update(Note.TABLE_NAME, values, Note.COLUMN_USERNAME+"='"+username+"'", null);
            Log.i("readcount","upadate "+read_count);
        }

        public void updataDataread_count(String username,String read_count)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Note.COUNT, read_count);
            db.update(Note.TABLE_NAME, values, Note.COLUMN_USERNAME+"='"+username+"'", null);
            Log.i("readcount","upadate count "+read_count);
        }

}
