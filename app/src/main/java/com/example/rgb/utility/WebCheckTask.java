package com.example.rgb.utility;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

class WebCheckTask extends AsyncTask<String, Void, Boolean> {

    @Override
    protected Boolean doInBackground(String... urls) {
        if (urls.length == 0) {
            return false; // Agar URL topilmagan bo'lsa false qaytar
        }

        String url = urls[0];

        try {
            URL siteURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            return responseCode == 200;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            Log.d("WebCheck", "Google.com 200 OK");
        } else {
            Log.d("WebCheck", "Google.com 200 OK emas");
        }
    }
}
