package com.example.cle30.horizonlauncher;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.w3c.dom.Document;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by cpe18 on 02/02/2018.
 */

public class ReadRss extends AsyncTask<Void,Void,Void>{
    Context context;
    String address="https://www.theguardian.com/world/rss";
    ProgressDialog progressDialog;
    URL url;

     public ReadRss(Context context){
        this.context=context;
         System.out.println(context);
     }

    @Override
    protected Void doInBackground(Void... voids) {
        ProcessXml(getData());
        return null;
    }

    private void ProcessXml(Document data) {
         if (data!=null) {
             Log.d("Root", data.getDocumentElement().getNodeName());
         }
    }

    @Override
    protected void onPreExecute() {
         progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    public Document getData(){
        try {
            url=new URL(address);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc ;
        } catch (Exception e) {
            e.printStackTrace();
            return null ;
        }
    }
}
