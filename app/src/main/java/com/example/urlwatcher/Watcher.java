package com.example.urlwatcher;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.View;

import com.example.urlwatcher.menu.Sites.Site;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Watcher {

    View process;
    ArrayList<Site> sites;
    ScheduledExecutorService scheduler;
    DBHelper dbHelper;
    int Time;
    public Watcher(View processFragment){
        this.process = processFragment;
        this.dbHelper = new DBHelper(process.getContext(),null);
        this.Time = dbHelper.getUpdateTime()/1000;
    }

    public void startWatch(){
        getSites();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleWithFixedDelay(() -> {
            watching();
        }, 0, Time, TimeUnit.SECONDS);
    }

    public void stopWatch(){
        scheduler.shutdown();
    }

    private void getSites(){
        DBHelper dbHelper = new DBHelper(process.getContext(),null);
        this.sites = dbHelper.getSites();
    }

    public void watching(){
        this.checkUrl();
    }

    public void checkUrl(Void... voids) {
        for (Site site : this.sites) {
            try {
                HttpURLConnection connection = fetchDataFromUrl(site.getUrl());
                int code = connection.getResponseCode();
                if(code==200){
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        String s = response.toString();
                        String seq = "/desimages/fav.png";
                        if(!Pattern.compile(Pattern.quote(seq)).matcher(s).find()){
                            Options options = dbHelper.getOptions();
                            Thread.sleep(options.getRetry()/1000);
                            HttpURLConnection connection1 = fetchDataFromUrl(site.getUrl());
                            int code1 = connection1.getResponseCode();
                            if(code1==200){
                                try (BufferedReader reader1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()))) {
                                    StringBuilder response1 = new StringBuilder();
                                    String line1;
                                    while ((line1 = reader1.readLine()) != null) {
                                        response1.append(line1);
                                    }
                                    String s1 = response1.toString();
                                    String seq1 = "/desimages/fav.png";
                                    if(!Pattern.compile(Pattern.quote(seq1)).matcher(s1).find()){
                                        this.sendTelegramMessage("Ошибка на сайте "+site.getUrl()+" %0AКод 200 %0AСодержимое:"+s);
                                        dbHelper.addLog("Ошибка на сайте "+site.getUrl()+" %0AКод 200 %0AСодержимое:"+s);
                                    }
                                }
                            }else{
                                this.sendTelegramMessage("Ошибка на сайте "+site.getUrl()+" %0AКод ошибки: "+String.valueOf(code));
                                dbHelper.addLog("Ошибка на сайте "+site.getUrl()+" %0AКод ошибки: "+String.valueOf(code));
                            }
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    this.sendTelegramMessage("Ошибка на сайте "+site.getUrl()+" %0AКод ошибки: "+String.valueOf(code));
                    dbHelper.addLog("Ошибка на сайте "+site.getUrl()+" %0AКод ошибки: "+String.valueOf(code));
                }
            } catch (IOException e) {
                this.sendTelegramMessage("Ошибка на сайте "+site.getUrl()+" %0AIOException: "+e);
                dbHelper.addLog("Ошибка на сайте "+site.getUrl()+" %0AIOException: "+e);
            }
        }
    }

    private static HttpURLConnection fetchDataFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

    @SuppressLint("StaticFieldLeak")
    private void sendTelegramMessage(String message) {
        Options options = dbHelper.getOptions();
        String botToken = options.getToken();
        String chatId = options.getChat_id();
        String botUrl = "https://api.telegram.org/bot" + chatId + "/sendMessage?chat_id=" + botToken + "&text=" + message;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    URL url = new URL(botUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode != 200) {
                        // Handle error
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}
