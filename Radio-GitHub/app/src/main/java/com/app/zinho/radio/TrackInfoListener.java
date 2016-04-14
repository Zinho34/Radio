package com.app.zinho.radio;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Zinho on 15/03/2016.
 */
public class TrackInfoListener
{
    String m_artist, m_title, m_album;

    void execute(String url)
    {
        AsyncTask<String, Void, ArrayList<String>> task = new AsyncTask<String, Void, ArrayList<String>>()
        {
            private String readAll(Reader rd) throws IOException
            {
                StringBuilder sb = new StringBuilder();
                int cp;

                while ((cp = rd.read()) != -1)
                {
                    sb.append((char) cp);
                }

                return sb.toString();
            }

            public JSONObject readJsonFromUrl(String url) throws IOException, JSONException
            {
                URL objectURL = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection) objectURL.openConnection();
                InputStream is = urlConnection.getInputStream();

                try
                {
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    String jsonText = readAll(rd);
                    JSONObject json = new JSONObject(jsonText);
                    return json;
                }
                finally
                {
                    is.close();
                }
            }

            @Override
            public ArrayList<String> doInBackground(String... params)
            {
                try
                {
                    ArrayList<String> info = new ArrayList<>();

                    JSONObject json = readJsonFromUrl(params[0]);
                    JSONObject arrayData = json.getJSONObject("data");

                    m_artist = arrayData.getString("artist");
                    m_title = arrayData.getString("title");
                    m_album = arrayData.getString("album");

                    info.add(m_artist);
                    info.add(m_title);
                    info.add(m_album);

                    return info;
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                    return null;
                }
            }
        };
        task.execute(url);
    }
}
