package com.example.urlwatcher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.urlwatcher.menu.Log.Log;
import com.example.urlwatcher.menu.Sites.Site;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "app";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_NAME = "user";
        private static final String COLUMN_ID = "id";
        private static final String COLUMN_NAME = "name";

        public DBHelper(Context context, SQLiteDatabase.CursorFactory factory) {
                super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                String query = "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME + " TEXT)";
                db.execSQL(query);
                query = "Create table site_links (id INTEGER PRIMARY KEY,url TEXT)";
                db.execSQL(query);
                query = "Create table updateTime (id INTEGER PRIMARY KEY,updateTime INTEGER)";
                db.execSQL(query);
                query = "Create table options (id INTEGER PRIMARY KEY,token TEXT, chat_id TEXT,retryTime INTEGER)";
                db.execSQL(query);
                query = "Create table Log (id INTEGER PRIMARY KEY,date TEXT,log TEXT)";
                db.execSQL(query);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
                onCreate(db);
        }

        public void addUser(String user) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(COLUMN_ID, 1);
                values.put(COLUMN_NAME, user);
                db.insert(TABLE_NAME, null, values);

                String query = "INSERT INTO updateTime(updateTime) VALUES (60000)";
                db.execSQL(query);
                query = "INSERT INTO site_links (url) VALUES ('https://belta.by');";
                db.execSQL(query);
                query = "INSERT INTO site_links (url) VALUES ('https://blr.belta.by/');";
                db.execSQL(query);
                query = "INSERT INTO site_links (url) VALUES ('https://pol.belta.by');";
                db.execSQL(query);
                query = "INSERT INTO site_links (url) VALUES ('https://eng.belta.by');";
                db.execSQL(query);
                query = "INSERT INTO site_links (url) VALUES ('https://deu.belta.by');";
                db.execSQL(query);
                query = "INSERT INTO site_links (url) VALUES ('https://esp.belta.by');";
                db.execSQL(query);
                query = "INSERT INTO site_links (url) VALUES ('https://chn.belta.by');";
                db.execSQL(query);
                query = "INSERT INTO options (token, chat_id, retryTime) VALUES ('1345920781','7231884818:AAEyyWlbZAMLAn9swz9qDyac8v7MJZ10fIE',1000);";
                db.execSQL(query);


                db.close();
        }

        public boolean getUser() {
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = 1", null);
                boolean result = cursor.moveToFirst();
                cursor.close();
                return result;
        }

        public Integer getUpdateTime(){
                SQLiteDatabase db = this.getReadableDatabase();
                String query = "Select updateTime from updateTime where id = 1";
                try (Cursor cursor = db.rawQuery(query, null)) {
                        if (cursor.moveToFirst()) {
                                int columnIndex = cursor.getColumnIndex("updateTime");
                                return cursor.getInt(columnIndex);
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }

        public Options getOptions(){
                SQLiteDatabase db = this.getReadableDatabase();
                String query = "Select token,chat_id,retryTime from options where id = 1";
                try (Cursor cursor = db.rawQuery(query, null)) {
                        if (cursor.moveToFirst()) {
                                int token = cursor.getColumnIndex("token");
                                int chatId = cursor.getColumnIndex("chat_id");
                                int retry = cursor.getColumnIndex("retryTime");

                                Options options = new Options(cursor.getString(token), cursor.getString(chatId), cursor.getInt(retry));
                                return options;
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }

        public void setUpdateTime(int time){
                SQLiteDatabase db = this.getReadableDatabase();
                String query = "update updateTime set updateTime = "+time*1000+";";
                db.execSQL(query);
                db.close();
        }

        public void setOptions(String token,String chat_id,int retry){
                SQLiteDatabase db = this.getReadableDatabase();
                String query = "update options set token = '"+token+"',chat_id ='"+chat_id+"',retryTime ="+retry*1000+";";
                db.execSQL(query);
                db.close();
        }

        public ArrayList<Site> getSites(){
                ArrayList<Site> sites = new ArrayList<>();
                SQLiteDatabase db = this.getReadableDatabase();
                String query = "Select * from site_links";
                try (Cursor cursor = db.rawQuery(query, null)) {
                        while (cursor.moveToNext()){
                                int url_id = cursor.getColumnIndex("url");
                                Site site = new Site(cursor.getString(url_id));
                                sites.add(site);
                        }
                        return sites;
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;

        }

        public void removeSite(String url){
                SQLiteDatabase db = this.getReadableDatabase();
                String query = "delete from site_links where url like '"+url+"'";
                db.execSQL(query);
                db.close();
        }

        public void addSite(String url){
                SQLiteDatabase db = this.getReadableDatabase();
                String query = "INSERT INTO site_links (url) VALUES ('"+url+"');";
                db.execSQL(query);
        }

        public void addLog(String log){
                Date now = new Date();
                String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(now);

                SQLiteDatabase db = this.getReadableDatabase();
                String query = "INSERT INTO Log (date,log) VALUES ('"+timeStamp+"','"+log+"');";
                db.execSQL(query);
        }

        public ArrayList<Log> getLogs(){
                ArrayList<Log> logs = new ArrayList<>();
                SQLiteDatabase db = this.getReadableDatabase();
                String query = "Select * from Log ORDER BY date DESC";
                try (Cursor cursor = db.rawQuery(query, null)) {
                        while (cursor.moveToNext()){
                                int date_id = cursor.getColumnIndex("date");
                                int log_id = cursor.getColumnIndex("log");
                                Log log = new Log(cursor.getString(date_id),cursor.getString(log_id));
                                logs.add(log);
                        }
                        return logs;
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }
}
