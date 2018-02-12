package com.ashishlakhmani.homeautomation;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ToggleBackground extends AsyncTask<String, Void, String> {

    private Context context;
    private ImageView imageView;
    private String type;

    public ToggleBackground(Context context, ImageView imageView, String type) {
        this.context = context;
        this.imageView = imageView;
        this.type = type;
    }

    public ToggleBackground(Context context, String type) {
        this.context = context;
        this.type = type;
    }

    @Override
    protected String doInBackground(String... params) {

        String access_url;

        if (type.equals("buzzer")) {
            access_url = "http://192.168.43.28/buzzer.php";
        } else {
            access_url = "http://192.168.43.28/toggle.php";
        }


        try {
            URL url = new URL(access_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            final HashMap<String, String> map = new HashMap<>();
            map.put("state", params[0]);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(getPostDataString(map));
            outputStreamWriter.flush();
            outputStreamWriter.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            StringBuilder sb = new StringBuilder();
            int letter = inputStreamReader.read();
            while (letter != -1) {
                sb.append((char) letter);
                letter = inputStreamReader.read();
            }
            inputStreamReader.close();
            httpURLConnection.disconnect();
            return sb.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        if (s.contains("on")) {
            if (imageView != null)
                imageView.setImageResource(R.drawable.on);
        } else {
            if (imageView != null)
                imageView.setImageResource(R.drawable.off);
        }
    }


    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

}